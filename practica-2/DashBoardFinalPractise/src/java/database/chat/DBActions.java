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
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import database.www.exceptions.UserDoesNotExistException;
import database.chat.exceptions.ChatDoesNotExistException;
import database.chat.exceptions.UserNotInPartyException;

/**
 * TO-DO: - getUnreadMessages(String chat_id, int user_id) - updateLastReadMessage(String chat_id, String message_id,
 * int user_id) - getParty(String chat_id) - getChats(int user_id)
 */
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
   *
   * @param userId1
   * @param userId2
   * @return Chat or null
   * @throws UserDoesNotExistException
   */
  public static Chat createChat(int userId1, int userId2) throws UserDoesNotExistException {
    if (!database.www.DBActions.userExists(userId1) || !database.www.DBActions.userExists(userId1)) {
      throw new UserDoesNotExistException();
    }

    try (DBConnection connection = new DBConnection();) {
      connection.open();

      Document chat = new Document()
              .append("is_deleted", false)
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

  public static Message createMessage(String chat_id, int user_id, String text) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException {
    if (!DBActions.chatExists(chat_id)) {
      throw new ChatDoesNotExistException();
    }

    if (!database.www.DBActions.userExists(user_id)) {
      throw new UserDoesNotExistException();
    }

    if (!DBActions.userInParty(chat_id, user_id)) {
      throw new UserNotInPartyException();
    }

    try (DBConnection connection = new DBConnection();) {
      connection.open();

      Document query = new Document("_id", new ObjectId(chat_id));

      Document message = new Document("messages", new Document()
              .append("_id", new ObjectId())
              .append("user_id", user_id)
              .append("text", text));

      MongoDatabase db = connection.getDatabase();
      db.getCollection(
              DBProperties.COLL
      ).updateOne(
              query,
              new Document("$addToSet", message)
      );

      return DBActions.documentToMessage(message, query.getObjectId("_id"));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /////////////////////////////////
  //// Update Entities!!!
  /////////////////////////////////
  public static void updateIsDeleted(String chat_id, Boolean is_deleted) throws ChatDoesNotExistException {
    if (!DBActions.chatExists(chat_id)) {
      throw new ChatDoesNotExistException();
    }

    try (DBConnection connection = new DBConnection();) {
      connection.open();

      Document query = new Document("_id", new ObjectId(chat_id));

      MongoDatabase db = connection.getDatabase();
      db.getCollection(
              DBProperties.COLL
      ).updateOne(
              query,
              new Document("$set", new Document("is_deleted", is_deleted))
      );
    }
  }

  /////////////////////////////////
  //// Getter Entities!!!
  /////////////////////////////////
  public static Message getMessageById(String id) {
    DBConnection connection = new DBConnection();
    connection.open();

    MongoDatabase db = connection.getDatabase();

    Document query = new Document("messages._id", new ObjectId(id));

    Document chat = db.getCollection(DBProperties.COLL).find(query).first();

    connection.close();

    if (chat == null) {
      return null;
    }

    Document message = null;

    // REALLY??? I DONT LIKE IT!!! I DONT HAVE FOUND ANY SOLUTION TO THIS 
    for (Object obj : chat.get("messages", List.class)) {
      Document doc = (Document) obj;

      if (doc.getObjectId("_id").equals(query)) {
        message = doc;
        break;
      }
    }

    if (message == null) {
      return null;
    }

    return DBActions.documentToMessage(message, chat.getObjectId("_id"));
  }

  public static Message getMessageById_v2(String id) {
    // TODO: Test This shiet
    // > db.party.find({"msg._id": 1}, {"msg": {$elemMatch: {_id: 1}}})
    // Perque cal tenir 1 missatge en concret? No bastaria amb retornar un subset?
    // A mes de separar els missatges a una classe apart...
    Document message = null;

    try (DBConnection connection = new DBConnection();) {
      connection.open();

      MongoDatabase db = connection.getDatabase();
      message = db.getCollection(
              DBProperties.COLL
      ).find(
              eq("messages._id", new ObjectId(id))
      ).projection(
              elemMatch("messages", eq("_id", new ObjectId(id)))
      ).first();
      // message is like:
      // { _id: "foo",
      //   message: {_id: "msgfoo", ...} <- this field might exist, or not
      // }
      if (message == null || !message.containsKey("messages")) {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return DBActions.documentToMessage((Document) message.get("messages"), message.getObjectId("_id"));
  }

  public static Chat getChatById(String id) {
    try (DBConnection connection = new DBConnection();) {
      connection.open();

      MongoDatabase db = connection.getDatabase();
      Document chat = db.getCollection(
              DBProperties.COLL
      ).find(
              eq("_id", new ObjectId(id))
      ).first();
      if (chat == null) {
        return null;
      }

      return DBActions.documentToChat(chat);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /////////////////////////////////
  //// Query Conditions!!!
  /////////////////////////////////
  public static Boolean userInParty(String chat_id, int user_id) {
    Document party = null;
    try (DBConnection connection = new DBConnection();) {
      connection.open();

      Document query = new Document(
              "_id", new ObjectId(chat_id)
      ).append(
              "party.user_id", user_id
      );

      MongoDatabase db = connection.getDatabase();
      party = db.getCollection(DBProperties.COLL).find(query).limit(1).first();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return party != null;
  }

  public static Boolean chatExists(String chat_id) {
    Document chat = null;
    try (DBConnection connection = new DBConnection();) {
      connection.open();

      Document query = new Document("_id", new ObjectId(chat_id));

      MongoDatabase db = connection.getDatabase();
      chat = db.getCollection(DBProperties.COLL).find(query).first();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return chat != null;
  }

  /////////////////////////////////
  //// Entity Parsers!!!
  /////////////////////////////////
  private static Chat documentToChat(Document document) {
    ObjectId id = document.getObjectId("_id");
    Boolean isDeleted = document.getBoolean("is_deleted");

    List<Party> parties = new ArrayList<>(100);

    for (Object doc : document.get("party", ArrayList.class)) {
      Party party = DBActions.documentToParty((Document) doc, id);
      parties.add(party);
    }

    List<Message> messages = new ArrayList<>(100); // 100... hem de fer un limit per algún lloc

    for (Object doc : document.get("messages", ArrayList.class)) {
      Message message = DBActions.documentToMessage((Document) doc, id);
      messages.add(message);
    }

    return new Chat(id, isDeleted, parties, messages);
  }

  private static Party documentToParty(Document document, ObjectId parentId) {
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
      Document parent = db.getCollection(DBProperties.COLL).find(new Document("messages._id", id)).first();
      if (parent == null) {
        // Create a new empty Document
      }
      return parent.getObjectId("_id");
    }
  }

  /**
   *
   * @param id
   * @return Document (might be empty)
   */
  private static ObjectId retrieveParentDocumentByUserId(int id) {
    try (DBConnection connection = new DBConnection();) {
      connection.open();

      MongoDatabase db = connection.getDatabase();
      Document parent = db.getCollection(DBProperties.COLL).find(new Document("party.user_id", id)).first();
      // first() returns the first element or null
      if (parent == null) {
        // Create a new empty Doument
      }
      return parent.getObjectId("_id");
    }
  }
}
