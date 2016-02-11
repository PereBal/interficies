package model;

import java.util.List;
import db.chat.DBActions;
import java.text.DateFormat;
import org.bson.types.ObjectId;
import java.text.SimpleDateFormat;
import db.chat.exceptions.UserNotInPartyException;
import db.www.exceptions.UserDoesNotExistException;
import db.chat.exceptions.ChatDoesNotExistException;

public class Message {

  public final static int LIMIT = 1000;

  private final ObjectId id;
  private final ObjectId chatId;
  private final User user;
  private final String text;
  private final String createdAt;

  public Message(ObjectId id, ObjectId chatId, User user, String text) {
    this.id = id;
    this.chatId = chatId;
    this.user = user;
    this.text = text;
    DateFormat df = new SimpleDateFormat("HH:mm");
    this.createdAt = df.format(id.getDate());
  }

  public ObjectId getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public String getText() {
    return text;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public ObjectId getChatId() {
    if (this.chatId == null) {
      return null;
    }

    return this.chatId;
  }

  public Chat getChat() throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    if (this.chatId == null) {
      return null;
    }

    return DBActions.getChatById(this.chatId.toString());
  }

  @Override
  public String toString() {
    return this.id.toString();
  }


  ///////////////////////////////////
  /////   Wrappering DBActions!!!
  ///////////////////////////////////
  public static Message create(String chatId, int userId, String text) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException {
    return DBActions.createMessage(chatId, userId, text);
  }

  public static Message retrieveByPk(String id) throws ChatDoesNotExistException {
    return DBActions.getMessageById(id);
  }

  public static List<Message> retrieveUnreadMessages(String chatId, int userId) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(chatId, userId, true, Message.LIMIT, 0);
  }
}
