package Database;

import Objects.Chat;
import Objects.Message;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import static java.util.Arrays.asList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Test per emprar MongoDB!
 *
 * @author dobleme
 */
public class MongoTest {

  public static Chat insertChat(int user_id1, int user_id2) {
    MongoClient client = new MongoClient("localhost", 27017);

    MongoDatabase db = client.getDatabase("adiiu");

    Document chat = new Document()
      .append("is_deleted", false)
      .append("party", asList(
        new Document()
          .append("user_id1", user_id1)
          .append("last_read_message", null),
        new Document()
          .append("user_id2", user_id2)
          .append("last_read_message", null)));

    db.getCollection("chats").insertOne(chat);

    client.close();

    //return chat.getObjectId("_id").toString();
    
    //parse document to object
    return null;
  }

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
}
