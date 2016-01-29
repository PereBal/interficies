package database.www;

import model.User;
import database.tools.Utils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
              rs.getInt("id"),
              rs.getString("email"),
              rs.getString("name"),
              rs.getBoolean("admin"));
    }

    @Override
    public String fields() {
      return "id, email, name, admin";
    }
  };

  private static UserFactory advancedFactory = new UserFactory() {
    @Override
    public User toUser(ResultSet rs) throws SQLException {
      return new User(
              rs.getInt("id"),
              rs.getString("email"),
              rs.getString("name"),
              rs.getString("last_name"),
              rs.getBoolean("admin"),
              rs.getString("auth_token"));
    }

    @Override
    public String fields() {
      return "id, email, name, last_name, admin, auth_token";
    }
  };

  public static Boolean userExists(int userId) {
    return getUser(basicFactory, "id=" + userId) != null;
  }

  public static User getUserById(int userId) {
    return getUser(advancedFactory, "id=" + userId);
  }

  public User getUserByEmail(String email, String password) {
    try {
      return getUser(advancedFactory, "email='" + Utils.cleanEmail(email) + "'"
              + "AND pwd='" + Utils.encrypt(password) + "'");
    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public User getUserByName(String name, String password) {
    try {
      return getUser(advancedFactory, "name='" + Utils.cleanName(name) + "'"
              + " AND pwd='" + Utils.encrypt(password) + "'");
    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  private static User getUser(UserFactory factory, String filter) {
    try (DBConnection con = new DBConnection();) {
      con.open();
      Statement st = con.getConection().createStatement();
      ResultSet rs = st.executeQuery("SELECT " + factory.fields() + " FROM user WHERE " + filter + ";");
      if (rs.next()) {
        return factory.toUser(rs);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public ArrayList<User> getUsers() {
    // OJO al límit
    final int lim = 20;
    ArrayList<User> userList = null;
    try (DBConnection con = new DBConnection();) {
      con.open();
      Statement st = con.getConection().createStatement();
      ResultSet rs = st.executeQuery("SELECT " + basicFactory.fields() + " FROM user LIMIT " + lim + ";");

      userList = new ArrayList<>(lim);
      while (rs.next()) {
        userList.add(basicFactory.toUser(rs));
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return userList;
  }

  public boolean insertUser(String email, String name, String password, String isAdmin) {
    return insertUser(email, name, null, password, isAdmin, null);
  }

  public boolean insertUser(String email, String name, String lastName, String password, String isAdmin, String authToken) {
    try (DBConnection con = new DBConnection();) {
      con.open();
      Statement st = con.getConection().createStatement();

      String Query = "INSERT INTO user (email, name, last_name, password, admin) VALUES"
              + "("
              + "'" + Utils.cleanEmail(email) + "',"
              + "'" + Utils.cleanName(name) + "',"
              + "'" + Utils.cleanLastName(lastName) + "',"
              + "'" + Utils.encrypt(password) + "',"
              + Utils.cleanAdmin(isAdmin) + ","
              + "'" + Utils.cleanAuthToken(authToken) + "'"
              + ");";
      st.executeUpdate(Query);
      return true;
    } catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException ex) {
      ex.printStackTrace();
      return false;
    }
  }

}
