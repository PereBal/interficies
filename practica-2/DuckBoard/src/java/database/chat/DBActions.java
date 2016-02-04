package database.chat;

import model.User;
import model.Chat;
import model.Party;
import model.Message;
import java.util.List;
import org.bson.Document;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import static java.util.Arrays.asList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.AggregateIterable;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Aggregates.skip;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.limit;
import database.chat.exceptions.UserNotInPartyException;
import static com.mongodb.client.model.Updates.addToSet;
import database.www.exceptions.UserDoesNotExistException;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Projections.slice;
import database.chat.exceptions.ChatDoesNotExistException;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Indexes.descending;
import database.chat.exceptions.MessageDoesNotExistException;
import database.chat.exceptions.MessageIsNotPartOfThisChatException;

public class DBActions {

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
    if (!database.www.DBActions.userExists(userId1) || !database.www.DBActions.userExists(userId1)) {
      throw new UserDoesNotExistException();
    }

    try (DBConnection connection = new DBConnection();) {
      connection.open();

      Document chat = new Document()
              .append("party", asList(
                      new Document()
                      .append("user_id", userId1)
                      .append("last_read_message", null),
                      new Document()
                      .append("user_id", userId2)
                      .append("last_read_message", null)));

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

    if (!database.www.DBActions.userExists(userId)) {
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
              addToSet("messages", message));

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

    if (!database.www.DBActions.userExists(userId)) {
      throw new UserDoesNotExistException();
    }

    if (!userInParty(chatId, userId)) {
      throw new UserNotInPartyException();
    }

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      String messageId = db.getCollection(DBProperties.COLL).find(
              eq("_id", new ObjectId(chatId)))
              .projection(
                      fields(
                              sort(descending("messages._id")),
                              elemMatch("messages", ne("user_id", userId))))
              .first().getObjectId("messages._id").toString();

      db.getCollection(DBProperties.COLL).updateOne(
              and(
                      eq("_id", new ObjectId(chatId)),
                      eq("party.user_id", userId)),
              set("party.$.last_read_message", messageId));
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

    if (!database.www.DBActions.userExists(userId)) {
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
   * @throws database.chat.exceptions.ChatDoesNotExistException
   * @throws database.chat.exceptions.UserNotInPartyException
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
   * @throws database.chat.exceptions.ChatDoesNotExistException
   */
  public static Message getMessageById(String id) throws ChatDoesNotExistException {
    if (!DBActions.chatExist(id)) {
      throw new ChatDoesNotExistException();
    }

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
   * @throws database.chat.exceptions.UserNotInPartyException
   */
  public static List<Message> getMessages(String chatId, int limit, int skip) throws // I DONT LIKE IT! int, integer wtf
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
              .sort(descending("messages._id"))
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
   * @throws database.chat.exceptions.UserNotInPartyException
   * @throws database.www.exceptions.UserDoesNotExistException
   */
  public static List<Message> getMessages(String chatId, Integer userId, boolean unread, int limit, int skip) throws // I DONT LIKE IT! int, integer wtf
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    if (!DBActions.chatExist(chatId)) {
      throw new ChatDoesNotExistException();
    }

    if (!database.www.DBActions.userExists(userId)) {
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
//      list.add(new Document("$project", new Document("$elemMatch", eq("party.user_id", userId)))); // project on aggregates are not working properly
      list.add(sort(descending("messages._id")));
      list.add(new Document("$unwind", "$messages"));
      list.add(match(ne("messages.user_id", userId)));

      if (unread) {
        ObjectId lastReadMessage = db.getCollection(DBProperties.COLL).find(
                eq("_id", new ObjectId(chatId)))
                .projection(fields(
                        elemMatch("party", eq("user_id", userId))))
                .first().getObjectId("party.last_read_message");

        if (lastReadMessage != null) {
          list.add(match(lt("messages._id", lastReadMessage)));
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
   * @throws database.chat.exceptions.ChatDoesNotExistException
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
    if (!database.www.DBActions.userExists(userId)) {
      throw new UserDoesNotExistException();
    }

    FindIterable<Document> iterator;

    try (DBConnection connection = new DBConnection()) {
      connection.open();
      MongoDatabase db = connection.getDatabase();

      iterator = db.getCollection(DBProperties.COLL).find(
              eq("party.user_id", userId))
              .projection(elemMatch("party", eq("user_id", userId)))
              .sort(descending("party.last_read_message"))
              .sort(descending("_id"));

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
    ObjectId lastReadMessage = document.getObjectId("last_read_message");

    User user = database.www.DBActions.getUserById(userId);

    Message message = null;

    if (lastReadMessage != null) {
      message = DBActions.getMessageById(lastReadMessage.toString());
    }

    if (parentId == null) {
      parentId = DBActions.retrieveParentDocumentByUserId(userId);
    }

    return new Party(user, parentId, message);
  }

  private static Message documentToMessage(Document document, ObjectId parentId) {
    ObjectId id = document.getObjectId("_id");

    int userId = document.getInteger("user_id");
    User user = database.www.DBActions.getUserById(userId);

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
