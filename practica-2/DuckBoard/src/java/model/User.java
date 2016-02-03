package model;

import java.util.List;
import database.chat.DBActions;
import database.chat.exceptions.UserNotInPartyException;
import database.www.exceptions.UserDoesNotExistException;
import database.chat.exceptions.ChatDoesNotExistException;

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

  public List<Chat> getChats() throws
          UserDoesNotExistException,
          ChatDoesNotExistException,
          UserNotInPartyException {
    return database.chat.DBActions.getChatsByUserId(id);
  }

  public List<Message> getUnreadMessages(String chatId) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(chatId, this.id, true, Message.LIMIT, 0);
  }

  public List<Message> getUnreadMessages(String chatId, int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(chatId, this.id, true, Message.LIMIT, skip);
  }

  public boolean isAdmin() {
    return isAdmin;
  }
}
