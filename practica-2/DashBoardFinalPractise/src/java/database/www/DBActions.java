package database.www;

import model.User;
import database.tools.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBActions {

  public static Boolean userExists(int user_id) {
    return true;
  }

  public static User getUserById(int user_id) {
    return null;
  }

  public static void info() {

  }

  public ArrayList<User> getUsers() {
    DBConnection con = new DBConnection();
    ArrayList<User> userList = new ArrayList<>(20);
    try {
      con.open();
      Statement st = con.getConection().createStatement();
      // OJO al límit
      ResultSet rs = st.executeQuery("SELECT id, email, name, level FROM users LIMIT 20;");
      while (rs.next()) {
        userList.add(new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("level").equals("1")));
      }
    } catch (Exception ex) {
    } finally {
      con.close();
    }
    return userList;
  }

  public User getUser(String name, String password) {
    DBConnection con = new DBConnection();
    User auxUser = new User();
    try {
      con.open();
      Statement st = con.getConection().createStatement();
      ResultSet rs = st.executeQuery(
              "SELECT id, email, name, last_name, level, auth_token FROM users"
              + " WHERE name='" + Utils.cleanName(name) + "'"
              + " AND password='" + Utils.hash(Utils.cleanPwd(password)) + "';");
      if (rs.next()) {
        return new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("last_name"),
                rs.getString("level").equals("1"),
                rs.getString("remember_token"));
      }
    } catch (Exception ex) {
    } finally {
      con.close();
    }
    return null;
  }

  public void insertUser(String email, String name, String password, String level) {
    insertUser(email, name, null, password, level);
  }

  public void insertUser(String email, String name, String lastName, String password, String level) {
    DBConnection con = new DBConnection();
    try {
      con.open();
      Statement st = con.getConection().createStatement();

      String Query = "INSERT INTO users (email, name, last_name, password, level) VALUES"
              + "("
              + "'" + Utils.cleanEmail(email) + "',"
              + "'" + Utils.cleanName(name) + "',"
              + "'" + Utils.cleanLastName(lastName) + "',"
              + "'" + Utils.hash(Utils.cleanPwd(password)) + "',"
              + "'" + Utils.cleanLevel(level) + "'"
              + ");";
      st.executeUpdate(Query);

    } catch (SQLException ex) {
      Logger.getLogger(DBActions.class
              .getName()).log(Level.SEVERE, null, ex);
    } finally {
      con.close();
    }
  }
}
