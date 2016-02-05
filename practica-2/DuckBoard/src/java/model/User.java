package model;

import java.util.List;
import database.chat.DBActions;
import database.chat.exceptions.UserNotInPartyException;
import database.www.exceptions.UserDoesNotExistException;
import database.chat.exceptions.ChatDoesNotExistException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {

  private final int id;
  private final String email;
  private final String name;
  private String lastName;
  private final char sex;
  private LocalDate birthDay;
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

  public User(int id, String email, String name, String lastName, char sex, String authToken, java.sql.Date birthDay, String quote) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.sex = sex;
    this.lastName = lastName;
    this.authToken = authToken;
    //this.birthDay = LocalDate.parse(birthDay, DateTimeFormatter.ISO_DATE);
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
  
  public LocalDate getBirthDate() {
    return birthDay;
  }

  public String getBirthDay() {
    if (birthDay == null)
      return null;
    return birthDay.format(DateTimeFormatter.ISO_DATE);
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

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", email=" + email + ", name=" + name + ", lastName=" + lastName + ", sex=" + sex + ", birthDay=" + birthDay + ", quote=" + quote + ", authToken=" + authToken + '}';
  }

}
