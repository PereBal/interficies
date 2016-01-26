package Database.Www;

//import Model.User;

import Model.User;

//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class DBActions {
  
  public static Boolean userExists(int user_id) {
    return true;
  }
  
  public static User getUserById(int user_id) {
    return null;
  }

//  public static ArrayList<User> getUsers() {
//    DBMySQL con = new DBMySQL();
//    ArrayList<User> userList = new ArrayList<User>();
//    try {
//      con.open();
//      Statement st = con.getConection().createStatement();
//      ResultSet rs = st.executeQuery("select * from users;");
//      User auxUser;
//      while (rs.next()) {
//        auxUser = new User();
//        auxUser.setId(rs.getInt("id"));
//        auxUser.setName(rs.getString("name"));
//        auxUser.setPassword(rs.getString("password"));
//        auxUser.setLevel(rs.getString("level"));
//        userList.add(auxUser);
//      }
//    } catch (Exception ex) {
//    } finally {
//      con.close();
//    }
//    return userList;
//  }

//  public static Boolean getUser(String name, String password) {
//    DBMySQL con = new DBMySQL();
//    User auxUser = new User();
//    try {
//      con.open();
//      Statement st = con.getConection().createStatement();
//      ResultSet rs = st.executeQuery("select * from users where name=" + name + " and password=" + password + ";");
//      while (rs.next()) {
//        return true;
//      }
//    } catch (Exception ex) {
//    } finally {
//      con.close();
//    }
//    return false;
//  }

//  public static void insertUser(String name, String password) {
//    DBMySQL con = new DBMySQL();
//    try {
//      con.open();
//      Statement st = con.getConection().createStatement();
//
//      String Query = "INSERT INTO users (name, password, level) VALUES ('" + name + "', '" + password + "', '1');";
//      st.executeUpdate(Query);
//
//    } catch (SQLException ex) {
//      Logger.getLogger(DBActions.class.getName()).log(Level.SEVERE, null, ex);
//    } finally {
//      con.close();
//    }
//  }
}
