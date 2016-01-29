package database.chat;

import model.Chat;
import model.Message;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Test per emprar MongoDB!
 */
public class MongoTest {

  public static Message createMsg(String chat_id, int user_id, String text) {
    MongoClient client = new MongoClient("localhost", 27017);
    MongoDatabase db = client.getDatabase("adiiu");

    Document query = new Document("_id", new ObjectId(chat_id));

    Document msg = new Document("messages", new Document()
      .append("_id", new ObjectId())
      .append("user_id", user_id)        
      .append("text", text));


    db.getCollection("chats").updateOne(query, 
      new Document("$addToSet", msg));

    client.close();
    
    //parse document to object
    return null;
  }   
  
  public static List<Chat> getChats(int user_id) {
    MongoClient client = new MongoClient("localhost", 27017);
    MongoDatabase db = client.getDatabase("adiiu");

    Document query1 = new Document("user_id1", user_id);
    Document query2 = new Document("user_id2", user_id);
    
    FindIterable<Document> it = db.getCollection("chats").find(
      new Document("$or", asList(query1, query2)));
    
    client.close();
    
    //parse document to object
    return null;
  }
  
  public static Message GetLastReadMessage(String chat_id, int user_id) {
    MongoClient client = new MongoClient("localhost", 27017);
    MongoDatabase db = client.getDatabase("adiiu");

    Document query = new Document("_id", new ObjectId(chat_id));

    //Document user1 = new Document("user_id1", user_id);
    //Document user2 = new Document("user_id2", user_id);
      
    db.getCollection("chats").find(query).first().get("Party", List.class);
    
    return null;
  }
  
  public static List<Message> getMessages(String chat_id, Boolean unread, int user_id) {
    MongoClient client = new MongoClient("localhost", 27017);
    MongoDatabase db = client.getDatabase("adiiu");

    Document query = new Document("_id", new ObjectId(chat_id));
    
    if (unread) {
      Document user1 = new Document("user_id1", user_id);
      Document user2 = new Document("user_id2", user_id);
      
      db.getCollection("chats").find(query).first().get("Party", List.class);
    }
    
    
    //.first().get("Messages", List.class);
            
    client.close();
    
    List<Message> messages = new ArrayList<>();
    
    //parse document to object
    return null;
  }
  
  
  
}
