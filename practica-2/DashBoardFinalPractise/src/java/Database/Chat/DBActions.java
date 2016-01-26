package Database.Chat;

import Model.User;
import Model.Chat;
import Model.Party;
import Model.Message;
import java.util.List;
import org.bson.Document;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import static java.util.Arrays.asList;
import com.mongodb.client.MongoDatabase;
import Database.Www.Exceptions.UserDoNotExistsException;
import Database.Chat.Exceptions.ChatDoNotExistsException;
import Database.Chat.Exceptions.UserIsNotInPartyException;

/**
 * TO-DO:
 *   - getUnreadMessages(String chat_id, int user_id)
 *   - updateLastReadMessage(String chat_id, String message_id, int user_id)
 *   - getParty(String chat_id)
 *   - getChats(int user_id)
 */

public class DBActions {
  
  /////////////////////////////////
  //// Creator Entities!!!
  /////////////////////////////////

  public static Chat createChat(int user_id1, int user_id2) throws UserDoNotExistsException {
    if (!Database.Www.DBActions.userExists(user_id1) || !Database.Www.DBActions.userExists(user_id1)) {
      throw new UserDoNotExistsException();
    }

    DBConnection connection = new DBConnection();
    connection.open();

    MongoDatabase db = connection.getDatabase();

    Document chat = new Document()
      .append("is_deleted", false)
      .append("party", asList(
        new Document()
          .append("user_id", user_id1)
          .append("last_read_message", null),
        new Document()
          .append("user_id", user_id2)
          .append("last_read_message", null)));

    db.getCollection(DBProperties.COLL).insertOne(chat);

    connection.close();

    return DBActions.documentToChat(chat);
  }

  public static Message createMessage(String chat_id, int user_id, String text) throws
    ChatDoNotExistsException,
    UserDoNotExistsException,
    UserIsNotInPartyException
  {
    if (!DBActions.chatExists(chat_id)) {
      throw new ChatDoNotExistsException();
    }

    if (!Database.Www.DBActions.userExists(user_id)) {
      throw new UserDoNotExistsException();
    }

    if (!DBActions.userInParty(chat_id ,user_id)) {
      throw new UserIsNotInPartyException();
    }

    DBConnection connection = new DBConnection();
    connection.open();

    MongoDatabase db = connection.getDatabase();

    Document query = new Document("_id", new ObjectId(chat_id));

    Document message = new Document("messages", new Document()
      .append("_id", new ObjectId())
      .append("user_id", user_id)
      .append("text", text));


    db.getCollection(DBProperties.COLL).updateOne(query,
      new Document("$addToSet", message));

    connection.close();

    return DBActions.documentToMessage(message, query.getObjectId("_id"));
  }

  /////////////////////////////////
  //// Update Entities!!!
  /////////////////////////////////

  public static void updateIsDeleted (String chat_id, Boolean is_deleted) throws  ChatDoNotExistsException {
    if (!DBActions.chatExists(chat_id)) {
      throw new ChatDoNotExistsException();
    }

    DBConnection connection = new DBConnection();
    connection.open();

    MongoDatabase db = connection.getDatabase();

    Document query = new Document("_id", new ObjectId(chat_id));

    db.getCollection(DBProperties.COLL).updateOne(query,
      new Document("$set", new Document("is_deleted", is_deleted)));

    connection.close();
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

  public static Chat getChatById(String id) {
    DBConnection connection = new DBConnection();
    connection.open();

    MongoDatabase db = connection.getDatabase();

    Document query = new Document("_id", new ObjectId(id));

    Document chat = db.getCollection(DBProperties.COLL).find(query).first();

    connection.close();

    if (chat == null) {
      return null;
    }

    return DBActions.documentToChat(chat);
  }

  /////////////////////////////////
  //// Query Conditions!!!
  /////////////////////////////////

  public static Boolean userInParty(String chat_id, int user_id) {
    DBConnection connection = new DBConnection();
    connection.open();

    MongoDatabase db = connection.getDatabase();

    Document query = new Document("_id", new ObjectId(chat_id))
      .append("party.user_id", user_id);
    
    Document chat = db.getCollection(DBProperties.COLL).find(query).first();

    connection.close();

    return chat != null;
  }

  public static Boolean chatExists(String chat_id) {
    DBConnection connection = new DBConnection();
    connection.open();

    MongoDatabase db = connection.getDatabase();

    Document query = new Document("_id", new ObjectId(chat_id));

    Document chat = db.getCollection(DBProperties.COLL).find(query).first();

    connection.close();

    return chat != null;
  }

  /////////////////////////////////
  //// Entity Parsers!!!
  /////////////////////////////////

  private static Chat documentToChat(Document document) {
    ObjectId id        = document.getObjectId("_id");
    Boolean is_deleted = document.getBoolean("is_deleted");

    List<Party> parties = new ArrayList<>();

    for (Object doc : document.get("party", ArrayList.class)) {
      Party party = DBActions.documentToParty((Document) doc, id);
      parties.add(party);
    }

    List<Message> messages = new ArrayList<>();

    for (Object doc : document.get("messages", ArrayList.class)) {
      Message message = DBActions.documentToMessage((Document) doc, id);
      messages.add(message);
    }

    return new Chat(id, is_deleted, parties, messages);
  }

  private static Party documentToParty(Document document, ObjectId parent_id) {
    int user_id                = document.getInteger("user_id");
    ObjectId last_read_message = document.getObjectId("last_read_message");

    User user = Database.Www.DBActions.getUserById(user_id);

    Message message = null;

    if (last_read_message != null) {
      message = DBActions.getMessageById(last_read_message.toString());
    }
    
    if (parent_id == null) {
      parent_id = DBActions.retrieveParentDocumentByUserId(user_id);
    }
    
    return new Party(user, parent_id, message);
  }

  private static Message documentToMessage(Document document, ObjectId parent_id) {
    ObjectId id = document.getObjectId("_id");

    int user_id = document.getInteger("user_id");
    User user   = Database.Www.DBActions.getUserById(user_id);

    String text = document.getString("text");
    
    if (parent_id == null) {
      parent_id = DBActions.retrieveParentDocumentByMessageId(id);
    }
    
    return new Message(id, parent_id, user, text);
  }
  
  private static ObjectId retrieveParentDocumentByMessageId(ObjectId id) {
    DBConnection connection = new DBConnection();
    connection.open();
      
    MongoDatabase db = connection.getDatabase();

    Document parent = db.getCollection(DBProperties.COLL).find(new Document("messages._id", id)).first();

    connection.close();
      
    return parent.getObjectId("_id");
  }
  
  private static ObjectId retrieveParentDocumentByUserId(int id) {
    DBConnection connection = new DBConnection();
    connection.open();

    MongoDatabase db = connection.getDatabase();

    Document parent = db.getCollection(DBProperties.COLL).find(new Document("party.user_id", id)).first();

    connection.close();

    return parent.getObjectId("_id");
  }
}
