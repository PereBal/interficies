package model;

import java.util.List;
import db.chat.DBActions;
import db.chat.exceptions.UserNotInPartyException;
import db.www.exceptions.UserDoesNotExistException;
import db.chat.exceptions.ChatDoesNotExistException;
import java.util.Collections;

public class User {

  private final int id;
  private final String email;
  private final String name;
  private String lastName;
  private final char sex;
  private String birthDay;
  private String quote;
  private String authToken;

  public User(int id, String email, String name, char sex) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.sex = sex;
    this.lastName = "";
    this.authToken = null;
    this.birthDay = null;
    this.quote = "";
  }

  public User(int id, String email, String name, String lastName, char sex, String authToken, String birthDay, String quote) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.sex = sex;
    this.lastName = lastName;
    this.authToken = authToken;
    this.birthDay = birthDay;
    this.quote = quote;
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

  public String getBirthDay() {
    return birthDay;
  }

  public String getQuote() {
    return quote;
  }

  public char getSex() {
    return this.sex;
  }

  public void setAuthToken(String token) {
    this.authToken = token;
  }

  public List<Chat> getChats() throws
          UserDoesNotExistException,
          ChatDoesNotExistException,
          UserNotInPartyException {
    return db.chat.DBActions.getChatsByUserId(id);
  }

  public List<Message> getMessages(String chatId) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    List<Message> list = DBActions.getMessages(toString(), Message.LIMIT, 0);
    Collections.reverse(list);
    return list;
  }

  public List<Message> getMessages(String chatId, int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    List<Message> list = DBActions.getMessages(chatId, Message.LIMIT, skip);
    Collections.reverse(list);
    return list;
  }

  public List<Message> getUnreadMessages(String chatId) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    List<Message> list = DBActions.getMessages(chatId, this.id, true, Message.LIMIT, 0);
    Collections.reverse(list);
    return list;
  }

  public List<Message> getUnreadMessages(String chatId, int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    List<Message> list = DBActions.getMessages(chatId, this.id, true, Message.LIMIT, skip);
    Collections.reverse(list);
    return list;
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", email=" + email + ", name=" + name + ", lastName=" + lastName + ", sex=" + sex + ", birthDay=" + birthDay + ", quote=" + quote + ", authToken=" + authToken + '}';
  }

  public org.json.JSONObject toJSON() {
    return new org.json.JSONObject().put(
            "id", id
    ).put(
            "email", email
    ).put(
            "name", name
    ).put(
            "last_name", lastName
    ).put(
            "sex", sex
    ).put(
            "birth_day", birthDay
    ).put(
            "quote", quote
    );
  }

}
