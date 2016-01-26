package Model;

public class User {

  private int id;
  private String email;
  private String name;
  private String last_name;
  private Boolean is_admin;
  private String password;
  private String remember_token;
  
  public User() { 
  }

  public int getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return last_name;
  }

  public Boolean getIsAdmin() {
    return is_admin;
  }

  public String getPassword() {
    return password;
  }

  public String getRememberToken() {
    return remember_token;
  }
}
