package db.chat;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DBConnection implements java.lang.AutoCloseable {

  private MongoClient client;
  private MongoClientURI con;

  public DBConnection() {
    con = null;
    client = null;
  }

  public void open() {
    try {
      if (client == null) {
        client = new MongoClient(db.chat.Sample.HOST, db.chat.Sample.PORT);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void close() {
    try {
      client.close();
      client = null;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public MongoClient getConnection() throws NullPointerException {
    if (client == null) {
      throw new NullPointerException("Connection not opened");
    }
    return client;
  }

  public MongoDatabase getDatabase() throws NullPointerException {
    if (client == null) {
      throw new NullPointerException("Connection not opened");
    }
    return client.getDatabase(Sample.DB);
  }
}