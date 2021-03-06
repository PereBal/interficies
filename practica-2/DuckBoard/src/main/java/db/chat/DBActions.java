package db.chat;

import model.*;
import java.util.List;
import org.bson.Document;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import static java.util.Arrays.asList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.PushOptions;
import com.mongodb.client.AggregateIterable;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import db.chat.exceptions.UserNotInPartyException;
import db.www.exceptions.UserDoesNotExistException;
import static com.mongodb.client.model.Aggregates.*;
import db.chat.exceptions.ChatDoesNotExistException;
import db.chat.exceptions.MessageDoesNotExistException;
import static com.mongodb.client.model.Projections.slice;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Indexes.descending;
import db.chat.exceptions.MessageIsNotPartOfThisChatException;
import java.util.Collection;
import java.util.Collections;

public class DBActions {

    /**
     * LLOCS A TENIR EN COMPTE!!! PEL TEMA DEL SORT A SUBSETS
     * - getChatsByUserId
     * - getMessages(String chatId, int limit, int skip)
     * - getMessages(String chatId, Integer userId, boolean unread, int limit, int skip)
     */

  public static void info() {
    try (DBConnection conn = new DBConnection()) {
      conn.open();
      System.out.println("Success: " + conn.getDatabase().getName());
    }
  }

  /////////////////////////////////
  //// Creator Entities!!!
  /////////////////////////////////

  /**
   * Create Chat document, with the party initialized.
   *
   * @param userId1
   * @param userId2
   * @return Chat entity
   * @throws UserDoesNotExistException
   */
  public static Chat createChat(int userId1, int userId2) throws UserDoesNotExistException {
    if (!db.www.DBActions.userExists(userId1) || !db.www.DBActions.userExists(userId1)) {
      throw new UserDoesNotExistException();
    }

    try (DBConnection connection = new DBConnection();) {
      connection.open();

      Chat existChat = chatExist(userId1, userId2);
      if (existChat != null) {
          return existChat;
      }

      User u1 = db.www.DBActions.getUserById(userId1);
      User u2 = db.www.DBActions.getUserById(userId2);

      Document chat = new Document()
              .append("party", asList(
                      new Document()
                      .append("user_id", userId1)
                      .append("user_name", u1.getName() + " " + u1.getLastName())
                      .append("user_email", u1.getEmail())
                      .append("last_read_message", null)
                      .append("is_deleted", false),
                      new Document()
                      .append("user_id", userId2)
                      .append("user_name", u2.getName() + " " + u2.getLastName())
                      .append("user_email", u2.getEmail())
                      .append("last_read_message", null)
                      .append("is_deleted", false)));

      MongoDatabase db = connection.getDatabase();

      db.getCollection(DBProperties.COLL).insertOne(chat);

      return DBActions.documentToChat(chat);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Insert a message in chat document.
   *
   * @param chatId
   * @param userId
   * @param text
   * @return Message entity
   * @throws ChatDoesNotExistException
   * @throws UserDoesNotExistException
   * @throws UserNotInPartyException
   */
  public static Message createMessage(String chatId, int userId, String text) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException {
    if (!DBActions.chatExist(chatId)) {
      throw new ChatDoesNotExistException();
    }

    if (!db.www.DBActions.userExists(userId)) {
      throw new UserDoesNotExistException();
    }

    if (!DBActions.userInParty(chatId, userId)) {
      throw new UserNotInPartyException();
    }

    try (DBConnection connection = new DBConnection();) {
      connection.open();

      Document message = new Document()
              .append("_id", new ObjectId())
              .append("user_id", userId)
              .append("text", text);

      MongoDatabase db = connection.getDatabase();

      db.getCollection(DBProperties.COLL).updateOne(
              eq("_id", new ObjectId(chatId)),
              pushEach("messages", asList(message), new PushOptions().sort(-1)));

      return DBActions.documentToMessage(message, new ObjectId(chatId));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /////////////////////////////////
  //// Update Entities!!!
  /////////////////////////////////

  /**
   *
   * @param chatId
   * @param userId
   * @throws ChatDoesNotExistException
   * @throws UserDoesNotExistException
   * @throws UserNotInPartyException
   */
  public static void updateLastReadMessage(String chatId, int userId) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException {
    if (!chatExist(chatId)) {
      throw new ChatDoesNotExistException();
    }

    if (!db.www.DBActions.userExists(userId)) {
      throw new UserDoesNotExistException();
    }

    if (!userInParty(chatId, userId)) {
      throw new UserNotInPartyException();
    }

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      if (getMessages(chatId, userId, true, Message.LIMIT, 0).isEmpty()) {
          return;
      }

      Document message = (Document) db.getCollection(DBProperties.COLL).find(
              eq("_id", new ObjectId(chatId)))
              .projection(elemMatch("messages", ne("user_id", userId)))
              .first().get("messages", ArrayList.class).get(0);

      db.getCollection(DBProperties.COLL).updateOne(
              and(
                      eq("_id", new ObjectId(chatId)),
                      eq("party.user_id", userId)),
              set("party.$.last_read_message", message.getObjectId("_id")));
    }
  }

  /**
   *
   * @param chatId
   * @param userId
   * @param isDeleted
   * @throws ChatDoesNotExistException
   * @throws UserDoesNotExistException
   * @throws UserNotInPartyException
   */
  public static void updateIsDeleted(String chatId, int userId, boolean isDeleted) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException {
    if (!chatExist(chatId)) {
      throw new ChatDoesNotExistException();
    }

    if (!db.www.DBActions.userExists(userId)) {
      throw new UserDoesNotExistException();
    }

    if (!userInParty(chatId, userId)) {
      throw new UserNotInPartyException();
    }

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      db.getCollection(DBProperties.COLL).updateOne(
            and(
                    eq("_id", new ObjectId(chatId)),
                    eq("party.user_id", userId)),
            set("party.$.is_deleted", isDeleted));
    }
  }

  /**
   *
   * @param chatId
   * @param userId
   * @param messageId
   * @throws ChatDoesNotExistException
   * @throws UserDoesNotExistException
   * @throws UserNotInPartyException
   * @throws MessageDoesNotExistException
   * @throws MessageIsNotPartOfThisChatException
   */
  public static void updateLastReadMessage(String chatId, int userId, String messageId) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException,
          MessageDoesNotExistException,
          MessageIsNotPartOfThisChatException {
    if (!chatExist(chatId)) {
      throw new ChatDoesNotExistException();
    }

    if (!db.www.DBActions.userExists(userId)) {
      throw new UserDoesNotExistException();
    }

    if (!userInParty(chatId, userId)) {
      throw new UserNotInPartyException();
    }

    if (!messageExist(messageId)) {
      throw new MessageDoesNotExistException();
    }

    if (!isMessagePartOfChat(chatId, messageId)) {
      throw new MessageIsNotPartOfThisChatException();
    }

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      db.getCollection(DBProperties.COLL).updateOne(
              and(
                      eq("_id", new ObjectId(chatId)),
                      eq("party.user_id", userId)),
              set("party.$.last_read_message", messageId));
    }
  }

  /////////////////////////////////
  //// Getter Entities!!!
  /////////////////////////////////

  /**
   * Get specific chat by chat Id.
   *
   * @param id
   * @return Chat entity
   * @throws db.chat.exceptions.ChatDoesNotExistException
   * @throws db.chat.exceptions.UserNotInPartyException
   */
  public static Chat getChatById(String id) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    Document chat;

    try (DBConnection connection = new DBConnection();) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      chat = db.getCollection(DBProperties.COLL).find(
              eq("_id", new ObjectId(id)))
              .first();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    if (chat == null) {
      return null;
    }

    return DBActions.documentToChat(chat);
  }

  /**
   * Get specific message by message Id.
   *
   * @param id
   * @return
   */
  public static Message getMessageById(String id) {
    Document chat;

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      chat = db.getCollection(DBProperties.COLL).find(
              eq("messages._id", new ObjectId(id)))
              .projection(elemMatch("messages", eq("_id", new ObjectId(id))))
              .first();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    if (chat == null || !chat.containsKey("messages")) {
      return null;
    }

    Document message = (Document) chat.get("messages", List.class).get(0);

    return DBActions.documentToMessage(message, chat.getObjectId("_id"));
  }

  /**
   * Get list of messages of a chat.
   *
   * @param chatId
   * @param limit
   * @param skip
   * @return
   * @throws ChatDoesNotExistException
   * @throws db.chat.exceptions.UserNotInPartyException
   */
  public static List<Message> getMessages(String chatId, int limit, int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    if (!DBActions.chatExist(chatId)) {
      throw new ChatDoesNotExistException();
    }

    Document chat;

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      chat = db.getCollection(DBProperties.COLL).find(
              eq("_id", new ObjectId(chatId)))
//              .sort(descending("messages._id")) -> aixo no canvia l'ordre
              .projection(slice("messages", skip, limit))
              .first();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    List<Message> messages = new ArrayList<>();

    if (chat != null && chat.containsKey("messages")) {
      for (Object doc : chat.get("messages", List.class)) {
        Message messageDocument = DBActions.documentToMessage((Document) doc, new ObjectId(chatId));
        messages.add(messageDocument);
      }
    }
    
    Collections.reverse(messages);

    return messages;
  }

  /**
   * Get list of messages by user point of view.
   *
   * @param chatId
   * @param limit
   * @param unread
   * @param userId
   * @param skip
   * @return
   * @throws ChatDoesNotExistException
   * @throws db.chat.exceptions.UserNotInPartyException
   * @throws db.www.exceptions.UserDoesNotExistException
   */
  public static List<Message> getMessages(String chatId, Integer userId, boolean unread, int limit, int skip) throws // I DONT LIKE IT! int, integer wtf
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    if (!DBActions.chatExist(chatId)) {
      throw new ChatDoesNotExistException();
    }

    if (!db.www.DBActions.userExists(userId)) {
      throw new UserDoesNotExistException();
    }

    if (!userInParty(chatId, userId)) {
      throw new UserNotInPartyException();
    }

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      List list = new ArrayList<>();

      list.add(match(eq("_id", new ObjectId(chatId))));
      list.add(new Document("$unwind", "$messages"));
      list.add(match(ne("messages.user_id", userId)));

      if (unread) {
        Document lastReadMessage = (Document) db.getCollection(DBProperties.COLL).find(
                eq("_id", new ObjectId(chatId)))
                .projection(fields(
                        elemMatch("party", eq("user_id", userId))))
                .first().get("party", ArrayList.class).get(0);

        if (lastReadMessage.getObjectId("last_read_message") != null) {
          list.add(match(gt("messages._id", lastReadMessage.getObjectId("last_read_message"))));
        }
      }

      list.add(limit(limit));
      list.add(skip(skip));
      AggregateIterable<Document> iterator = db.getCollection(DBProperties.COLL).aggregate(list);

      List<Message> messages = new ArrayList<>();

      if (iterator != null) {
        for (Document doc : iterator) {
          Document msg = doc.get("messages", Document.class);
          Message messageDocument = DBActions.documentToMessage(msg, new ObjectId(chatId));
          messages.add(messageDocument);
        }
      }

      Collections.reverse(messages);
      
      return messages;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Get list of chat party.
   *
   * @param id
   * @param limit
   * @return
   * @throws db.chat.exceptions.ChatDoesNotExistException
   */
  public static List<Party> getPartiesByChatId(String id, int limit) throws ChatDoesNotExistException {
    if (!DBActions.chatExist(id)) {
      throw new ChatDoesNotExistException();
    }

    Document chat;

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      chat = db.getCollection(DBProperties.COLL).find(
              eq("_id", new ObjectId(id)))
              .projection(slice("party", limit))
              .first();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    List<Party> parties = new ArrayList<>();
    List<Object> partiesDocument = chat.get("party", List.class);

    if (partiesDocument != null) {
      for (Object doc : partiesDocument) {
        Party partyDocument = DBActions.documentToParty((Document) doc, new ObjectId(id));
        parties.add(partyDocument);
      }
    }

    return parties;
  }

  /**
   *
   * @param userId
   * @return
   * @throws UserDoesNotExistException
   * @throws ChatDoesNotExistException
   * @throws UserNotInPartyException
   */
  public static List<Chat> getChatsByUserId(int userId) throws
          UserDoesNotExistException,
          ChatDoesNotExistException,
          UserNotInPartyException {
    if (!db.www.DBActions.userExists(userId)) {
      throw new UserDoesNotExistException();
    }

    FindIterable<Document> iterator;

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      iterator = db.getCollection(DBProperties.COLL).find(
              and(
                eq("party.user_id", userId),
                eq("party.is_deleted", false)))
              .projection(elemMatch("party", eq("user_id", userId)))
              .sort(descending("party.last_read_message")) // a saber si rula, els sorts fan un poc el que volen per subsets
              .sort(descending("_id"));                    // a saber si rula, aixo son els docs a pel, hauria de tirar

      List<Chat> chats = new ArrayList<>();

      if (iterator != null) {
        for (Document doc : iterator) {
          Chat chat = documentToChat(doc);
          chats.add(chat);
        }
      }

      return chats;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /////////////////////////////////
  //// Query Conditions!!!
  /////////////////////////////////

  /**
   *
   * @param chatId
   * @param userId
   * @return
   */
  public static Boolean userInParty(String chatId, int userId) {
    Document party = null;

    try (DBConnection connection = new DBConnection();) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      Document query = new Document("_id", new ObjectId(chatId)).
              append("party.user_id", userId);

      party = db.getCollection(DBProperties.COLL).find(query).limit(1).first();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return party != null;
  }

  /**
   *
   * @param chatId
   * @return
   */
  public static Boolean chatExist(String chatId) {
    Document chat = null;

    try (DBConnection connection = new DBConnection();) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      chat = db.getCollection(DBProperties.COLL).find(
              eq("_id", new ObjectId(chatId)))
              .first();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return chat != null;
  }

    /**
   *
   * @param userId1
   * @param userId2
   * @return
   * @throws db.chat.exceptions.ChatDoesNotExistException
   * @throws db.chat.exceptions.UserNotInPartyException
   */
  private static Chat chatExist(int userId1, int userId2) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    Document chat = null;

    try (DBConnection connection = new DBConnection();) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      chat = db.getCollection(DBProperties.COLL).find(
              and(eq("party.user_id", userId1), eq("party.user_id", userId2)))
              .first();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (chat == null) {
        return null;
    }

    return DBActions.documentToChat(chat);
  }

  /**
   *
   * @param messageId
   * @return
   */
  public static Boolean messageExist(String messageId) {
    Document chat = null;

    try (DBConnection connection = new DBConnection();) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      chat = db.getCollection(DBProperties.COLL).find(
              eq("messages._id", new ObjectId(messageId)))
              .first();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return chat != null;
  }

  /**
   *
   * @param chatId
   * @param messageId
   * @return
   */
  public static Boolean isMessagePartOfChat(String chatId, String messageId) {
    Document chat = null;

    try (DBConnection connection = new DBConnection();) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      chat = db.getCollection(DBProperties.COLL).find(
              and(
                      eq("_id", new ObjectId(chatId)),
                      eq("messages._id", new ObjectId(messageId)))).first();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return chat != null;
  }

  /////////////////////////////////
  //// Entity Parsers!!!
  /////////////////////////////////
  private static Chat documentToChat(Document document) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    ObjectId id = document.getObjectId("_id");

    List<Party> parties = DBActions.getPartiesByChatId(id.toString(), Party.LIMIT);

    List<Message> messages = null;

    return new Chat(id, parties, messages);
  }

  private static Party documentToParty(Document document, ObjectId parentId) throws ChatDoesNotExistException {
    int userId = document.getInteger("user_id");
    String userName = document.getString("user_name");
    String userEmail = document.getString("user_email");
    boolean isDeleted = document.getBoolean("is_deleted");
    ObjectId lastReadMessage = document.getObjectId("last_read_message");

    User user = null;
    if (db.www.DBActions.userExists(userId)) {
      user = db.www.DBActions.getUserById(userId);
    }

    Message message = null;

    if (lastReadMessage != null) {
      message = DBActions.getMessageById(lastReadMessage.toString());
    }

    if (parentId == null) {
      parentId = DBActions.retrieveParentDocumentByUserId(userId);
    }

    return new Party(user, parentId, userName, userEmail, isDeleted, message);
  }

  private static Message documentToMessage(Document document, ObjectId parentId) {
    ObjectId id = document.getObjectId("_id");

    int userId = document.getInteger("user_id");
    User user = db.www.DBActions.getUserById(userId);

    String text = document.getString("text");

    if (parentId == null) {
      parentId = DBActions.retrieveParentDocumentByMessageId(id);
    }

    return new Message(id, parentId, user, text);
  }

  private static ObjectId retrieveParentDocumentByMessageId(ObjectId id) {
    try (DBConnection connection = new DBConnection();) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      Document parent = db.getCollection(DBProperties.COLL).find(eq("messages._id", id)).first();

      if (parent == null) {
        return null;
      }

      return parent.getObjectId("_id");
    }
  }

  private static ObjectId retrieveParentDocumentByUserId(int id) {
    try (DBConnection connection = new DBConnection();) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      Document parent = db.getCollection(DBProperties.COLL).find(eq("party.user_id", id)).first();

      if (parent == null) {
        return null;
      }

      return parent.getObjectId("_id");
    }
  }
}
