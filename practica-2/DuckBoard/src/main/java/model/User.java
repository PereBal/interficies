package model;

import java.util.List;
import db.chat.DBActions;
import db.chat.exceptions.UserNotInPartyException;
import db.www.exceptions.UserDoesNotExistException;
import db.chat.exceptions.ChatDoesNotExistException;
import jgravatar.Gravatar;
import jgravatar.GravatarDefaultImage;

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
    this.quote= quote;
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

  public String getGravatarUrl() {
    Gravatar gravatar = new Gravatar();
    gravatar.setSize(420);
    gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
    return gravatar.getUrl(this.email);
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
    return DBActions.getMessages(toString(), Message.LIMIT, 0);
  }

  public List<Message> getMessages(String chatId, int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    return DBActions.getMessages(chatId, Message.LIMIT, skip);
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

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", email=" + email + ", name=" + name + ", lastName=" + lastName + ", sex=" + sex + ", birthDay=" + birthDay + ", quote=" + quote + ", authToken=" + authToken + ", gravatarUrl=" + getGravatarUrl() + "}";
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
    ).put(
            "gravatarUrl", getGravatarUrl()
    );
  }

}
