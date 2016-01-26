package Database.Chat;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DBConnection {
  
  private MongoClient client;
  
  public DBConnection() {
    client = null;
  }

  public void open() {
    client = new MongoClient(DBProperties.HOST, DBProperties.PORT);
  }

  public void close() {
    client.close();
  }

  public MongoDatabase getDatabase() {
    return client.getDatabase(DBProperties.DB);
  }
}
