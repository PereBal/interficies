package Database;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import static java.util.Arrays.asList;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Test per emprar MongoDB!
 *
 * @author dobleme
 */
public class MongoTest {

  public static String insertChat(int user_id1, int user_id2) {
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

    return chat.getObjectId("_id").toString();
  }

  public static void insertMsg(String chat_id, int user_id, String text) {
    MongoClient client = new MongoClient("localhost", 27017);

    MongoDatabase db = client.getDatabase("adiiu");

    Document query = new Document("_id", new ObjectId(chat_id));
    
    Document msg = new Document("messages", new Document()
      .append("_id", new ObjectId())
      .append("user_id", user_id)        
      .append("text", text));


    db.getCollection("chats").updateOne(query, new Document("$addToSet", msg));

//    it = db.getCollection("chats").find(query);
//
//    it.forEach(new Block<Document>() {
//      @Override
//      public void apply(Document t) {
//        System.out.println("Despues");
//        //ArrayList a = t.get("messages", ArrayList.class);
//        //Document c = (Document) a.toArray()[0];
//        System.out.println(t);
//      }
//    });

    client.close();
  }  
}
