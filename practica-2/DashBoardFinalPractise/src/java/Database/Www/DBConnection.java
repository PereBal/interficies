package Database.Www;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  private Connection con;

  public DBConnection() {
    con = null;
  }

  public void open() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://"
              + DBProperties.HOST + ":" + DBProperties.PORT
              + "/" + DBProperties.DB, DBProperties.USER, DBProperties.PSSWD);
    } catch (ClassNotFoundException | SQLException ex) {
    }
  }

  public void close() {
    try {
      con.close();
    } catch (Exception ex) {
    }
  }

  public Connection getConection() {
    return con;
  }
}
