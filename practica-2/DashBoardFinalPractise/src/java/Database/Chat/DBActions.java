package Database.Chat;

import Database.Chat.Exceptions.ChatDoNotExistsException;
import Database.Chat.Exceptions.UserIsNotInPartyException;
import Model.Chat;
import Model.Party;
import Model.Message;
import org.bson.Document;
import static java.util.Arrays.asList;
import com.mongodb.client.MongoDatabase;
import Database.Www.Exceptions.UserDoNotExistsException;
import Model.User;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class DBActions {
  
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
    
    return DBActions.documentToMessage(message);
  }   
  
  public static Message getMessageById(String id) {
    DBConnection connection = new DBConnection();
    connection.open();
    
    MongoDatabase db = connection.getDatabase();

    Document query = new Document("messages._id", new ObjectId(id));

    Document message = db.getCollection(DBProperties.COLL).find(query).first();
    
    // null si msg no existes

    connection.close();
    
    return DBActions.documentToMessage(message);
  }
  
  public static Chat getChatById(String id) {
    DBConnection connection = new DBConnection();
    connection.open();
    
    MongoDatabase db = connection.getDatabase();

    Document query = new Document("_id", new ObjectId(id));

    Document message = db.getCollection(DBProperties.COLL).find(query).first();
    
    // null si chat no existeix

    connection.close();
    
    return DBActions.documentToChat(message);
  }
  
  public static Boolean userInParty(String chat_id, int user_id) {
    return true;
  }
  
  public static Boolean chatExists(String chat_id) {
    return true;
  }
  
  /////////////////////////////////
  //// Entity Parsers!!!
  /////////////////////////////////
  
  private static Chat documentToChat(Document document) {
    ObjectId id        = document.getObjectId("_id");
    Boolean is_deleted = document.getBoolean("is_deleted");
    
    List<Party> parties = new ArrayList<>();
    
    for (Object doc : document.get("party", ArrayList.class)) {
      Party party = DBActions.documentToParty((Document) doc);
      parties.add(party);
    }
    
    List<Message> messages = new ArrayList<>();
    
    for (Object doc : document.get("messages", ArrayList.class)) {
      Message message = DBActions.documentToMessage((Document) doc);
      messages.add(message);
    }
    
    return new Chat(id, is_deleted, parties, messages);
  }
  
  private static Party documentToParty(Document document) {
    int user_id                = document.getInteger("user_id");
    ObjectId last_read_message = document.getObjectId("last_read_message");
    
    User user = Database.Www.DBActions.getUserById(user_id);
    
    Message message = null;
    
    if (last_read_message != null) {
      message = DBActions.getMessageById(last_read_message.toString());
    }
    
    return new Party(user, message);
  }
  
  private static Message documentToMessage(Document document) {
    return null;
  }
}
