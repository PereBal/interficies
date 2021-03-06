package db.www;

import model.User;
import db.tools.Utils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBActions {

  // WHY? cause I want to learn more about the factory programming pattern
  private interface UserFactory {

    /**
     * Map from ResultSet to User. Each Factory object must implement its own mapper.
     *
     * @param rs <b>java.sql.ResultSet></b>
     * @return User
     * @throws SQLException
     */
    User toUser(ResultSet rs) throws SQLException;

    /**
     * User fields with value on SQLString way. Use this function to specify which fields will be on the result set.
     *
     * @return A String of field names with the format: <b>"</b><i>fieldName1</i>, <i>fieldName2</i>, ...,
     * <i>fnameN</i><b>"</b>
     */
    String fields();
  }

  private static UserFactory basicFactory = new UserFactory() {
    @Override
    public User toUser(ResultSet rs) throws SQLException {
      return new User(
              rs.getInt(1),
              rs.getString(2),
              rs.getString(3),
              rs.getString(4).charAt(0));
    }

    @Override
    public String fields() {
      return "id, email, name, sex";
    }
  };

  private static UserFactory advancedFactory = new UserFactory() {
    @Override
    public User toUser(ResultSet rs) throws SQLException {
      return new User(
              rs.getInt(1),
              rs.getString(2),
              rs.getString(3),
              rs.getString(4),
              rs.getString(5).charAt(0),
              rs.getString(6),
              rs.getDate(7).toString(),
              rs.getString(8)
      );
    }

    @Override
    public String fields() {
      return "id, email, name, last_name, sex, auth_token, birth_day, quote";
    }
  };

  public static boolean userExists(int userId) {
    return getUser(basicFactory, "id=" + userId) != null;
  }

  public static boolean userExists(int userId, String authToken) {
    return getUser(basicFactory, "id=" + userId + " AND auth_token=" + Utils.cleanAuthToken(authToken)) != null;
  }
  
  public static boolean emailExists(String email) {
    return getUser(basicFactory, "email=" + Utils.cleanEmail(email)) != null;
  }

  public static User getUserById(int userId) {
    return getUser(advancedFactory, "id=" + userId);
  }

  public static User getUserByEmail(String email) {
    return getUser(advancedFactory, "email=" + Utils.cleanEmail(email));
  }

  public static User getUserByEmail(String email, String password) {
    try {
      return getUser(advancedFactory, "email=" + Utils.cleanEmail(email) + " AND pwd=" + Utils.encrypt(password));
    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      return null;
    }
  }

  public static User getUserByToken(String email, String token) {
    return getUser(advancedFactory, "email=" + Utils.cleanEmail(email) + " AND auth_token=" + Utils.cleanAuthToken(token));
  }

  private static User getUser(UserFactory factory, String filter) {
    try (DBConnection con = new DBConnection();) {
      con.open();
      Statement st = con.getConection().createStatement();

      String query = "SELECT " + factory.fields() + " FROM user WHERE " + filter + ";";
      ResultSet rs = st.executeQuery(query);
      if (rs.next()) {
        return factory.toUser(rs);
      }
    } catch (Exception ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * Inserts an user into the DB.
   *
   * @param newUser
   * @param password
   * @return true|false
   */
  public static boolean insertUser(User newUser, String password) {
    try (DBConnection con = new DBConnection();) {
      con.open();
      Statement st = con.getConection().createStatement();

      String query = "INSERT INTO user (email, name, last_name, pwd, sex, auth_token, birth_day, quote) VALUES "
              + "("
              + Utils.cleanEmail(newUser.getEmail()) + ","
              + Utils.cleanName(newUser.getName()) + ","
              + Utils.cleanLastName(newUser.getLastName()) + ","
              + Utils.encrypt(password) + ","
              + Utils.cleanSex(newUser.getSex()) + ","
              + Utils.cleanAuthToken(newUser.getAuthToken()) + ","
              + Utils.cleanBirthDay(newUser.getBirthDay()) + ","
              + Utils.cleanQuote(newUser.getQuote())
              + ");";
      st.executeUpdate(query);
      return true;
    } catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      return false;
    }
  }

  public static boolean updateUser(User newUser) {
    return update(newUser, null);
  }

  public static boolean updateUser(User newUser, String newPwd) {
    return update(newUser, newPwd);
  }

  private static boolean update(User newUser, String newPwd) {
    try (DBConnection conn = new DBConnection();) {
      conn.open();

      Statement st = conn.getConection().createStatement();

      String subQuery = "";
      if (newUser.getEmail() != null) {
        subQuery += "email=" + Utils.cleanEmail(newUser.getEmail()) + ",";
      }
      if (newUser.getName() != null) {
        subQuery += "name=" + Utils.cleanName(newUser.getName()) + ",";
      }
      if (newUser.getLastName() != null) {
        subQuery += "last_name=" + Utils.cleanLastName(newUser.getLastName()) + ",";
      }
      if (newUser.getSex() != '\0') {
        subQuery += "sex=" + Utils.cleanSex(newUser.getSex()) + ",";
      }
      if (newUser.getAuthToken() != null) {
        subQuery += "auth_token=" + Utils.cleanAuthToken(newUser.getAuthToken()) + ",";
      }
      if (newUser.getBirthDay() != null) {
        subQuery += "birth_day=" + Utils.cleanBirthDay(newUser.getBirthDay()) + ",";
      }
      if (newUser.getQuote() != null) {
        subQuery += "quote=" + Utils.cleanQuote(newUser.getQuote()) + ",";
      }
      if (newPwd != null) {
        subQuery += "pwd=" + Utils.encrypt(newPwd) + ",";
      }
      if (!subQuery.equals("")) {
        subQuery = subQuery.substring(0, subQuery.length() - 1);
        subQuery = "UPDATE user SET " + subQuery + " WHERE id=" + newUser.getId() + ";";
        System.out.println(subQuery);
        st.executeUpdate(subQuery);
        return true;
      }
    } catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return false;
  }

  public static boolean deleteUser(int id) {
    return delete("id=" + id);
  }

  public static boolean deleteUser(String email) {
    return delete("email=" + Utils.cleanEmail(email));
  }

  public static boolean deleteUser(User u) {
    return delete("id=" + u.getId());
  }

  private static boolean delete(String filter) {
    try (DBConnection conn = new DBConnection();) {
      conn.open();

      Statement st = conn.getConection().createStatement();

      String query = "DELETE FROM user WHERE " + filter + ";";

      st.executeUpdate(query);
      return true;
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      return false;
    }
  }

  public static String setAuthToken(String email, String token) {
    String auth = token;
    try (DBConnection conn = new DBConnection();) {
      conn.open();

      Statement st = conn.getConection().createStatement();

      String query = "UPDATE user SET auth_token=" + Utils.cleanAuthToken(token) + " "
              + "WHERE "
              + "email=" + Utils.cleanEmail(email) + ";";
      st.executeUpdate(query);
      return auth;
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      return null;
    }
  }

  public static boolean destroyAuthToken(int userId) {
    try (DBConnection conn = new DBConnection()) {
      conn.open();

      Statement st = conn.getConection().createStatement();

      String query = "UPDATE user SET auth_token=" + null + " "
              + "WHERE "
              + "id=" + userId + ";";

      st.executeUpdate(query);
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(DBActions.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
  }

  public static JSONArray getJSONUsers(String q) {
    JSONArray res = new JSONArray();
    try (DBConnection conn = new DBConnection()) {
      conn.open();

      Statement st = conn.getConection().createStatement();

      String query = "SELECT "
              + "id,"
              + "email,"
              + "name "
              + "FROM user ";

      if (q != null) {
        query += "WHERE "
                + "LOWER(email) LIKE '" + q.toLowerCase().replaceAll("[^a-z0-9@.]", "") + "%' OR "
                + "name LIKE '" + q.toUpperCase().replaceAll("[^A-Z0-9]", "") + "%' ";
      }
      query += "LIMIT 8;";
      
      ResultSet rs = st.executeQuery(query);

      while (rs.next()) {
        res.put(new JSONObject().put(
                "id", rs.getInt(1)
        ).put(
                "email", rs.getString(2)
        ).put(
                "name", rs.getString(3)
        ));
      }
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return res;
  }

}
