package model;

import java.util.List;

public class User {

  private final int id;
  private final String email;
  private final String name;
  private String lastName;
  private final boolean isAdmin;
  private String authToken;

  public User(int id, String email, String name, boolean isAdmin) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.isAdmin = isAdmin;
    this.lastName = "";
    this.authToken = null;
  }

  public User(int id, String email, String name, String lastName, boolean isAdmin, String authToken) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.lastName = lastName;
    this.isAdmin = isAdmin;
    this.authToken = authToken;
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
    return lastName;
  }

  public String getAuthToken() {
    return authToken;
  }

  // TO DO
  public List<Chat> getChats() {
    return null;
  }

  // TO DO
  public List<Message> getUnreadMessages(String chat_id) {
    return null;
  }

  public boolean isAdmin() {
    return isAdmin;
  }
}
