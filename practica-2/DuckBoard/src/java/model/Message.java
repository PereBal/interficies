package model;

import java.util.Date;
import java.util.List;
import database.chat.DBActions;
import org.bson.types.ObjectId;
import database.chat.exceptions.UserNotInPartyException;
import database.www.exceptions.UserDoesNotExistException;
import database.chat.exceptions.ChatDoesNotExistException;

public class Message {

  public final static int LIMIT = 20;

  private final ObjectId id;
  private final ObjectId chatId;
  private final User user;
  private final String text;
  private final Date createdAt;

  public Message(ObjectId id, ObjectId chatId, User user, String text) {
    this.id = id;
    this.chatId = chatId;
    this.user = user;
    this.text = text;
    this.createdAt = id.getDate();
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

  public Date getCreatedAt() {
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
