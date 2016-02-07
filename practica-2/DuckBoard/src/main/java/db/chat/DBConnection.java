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
      if (con == null) {
        con = new MongoClientURI("mongodb://"
                + "@" + db.chat.DBProperties.HOST + ":" + db.chat.DBProperties.PORT
                + "/" + db.chat.DBProperties.DB + db.chat.DBProperties.OPTS());
      }
      if (client == null) {
        //client = new MongoClient(con); // No me tira, no he cercat tampoc
        client = new MongoClient("localhost", 27017);
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
    return client.getDatabase(DBProperties.DB);
  }
}
