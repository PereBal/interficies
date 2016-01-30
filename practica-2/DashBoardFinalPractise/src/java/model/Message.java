package model;

import database.chat.DBActions;
import database.chat.exceptions.ChatDoesNotExistException;
import database.chat.exceptions.UserNotInPartyException;
import database.www.exceptions.UserDoesNotExistException;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

public class Message {

  private final ObjectId id;
  private final ObjectId chatId;
  private final User user;
  private final String text;
  private final Date createdAt;

  public Message(String chatId, int userId, String text) throws
          UserDoesNotExistException,
          UserNotInPartyException,
          ChatDoesNotExistException {
    Message m = DBActions.createMessage(chatId, userId, text);

    this.id = m.getId();
    this.chatId = new ObjectId(chatId);
    this.user = m.getUser();
    this.text = m.getText();
    this.createdAt = m.getCreatedAt();
  }

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

  public String getChatId() {
    if (this.chatId == null) {
      return null;
    }

    return this.chatId.toString();
  }

  public Chat getChat() {
    if (this.chatId == null) {
      return null;
    }

    return DBActions.getChatById(this.chatId.toString());
  }

  ///////////////////////////////////
  /////   Wrappering DBActions!!!
  ///////////////////////////////////
  public static Message retrieveByPk(String id) {
    return DBActions.getMessageById(id);
  }

  // TO DO
  public static List<Message> retrieveUnreadMessagesByChatPk(String chat_id, int user_id) {
    return null;
  }
}
