package model;

import database.chat.DBActions;
import java.util.List;
import org.bson.types.ObjectId;

public class Party {

  private final User user;
  private final ObjectId chat_id;
  private Message last_read_message;

  public Party(User user, ObjectId chat_id, Message message) {
    this.user = user;
    this.chat_id = chat_id;
    this.last_read_message = message;
  }

  public User getUser() {
    return user;
  }

  public Message getLastReadMessage() {
    return last_read_message;
  }

  // TO DO
  public void setLastReadMessage(Message message) {
    //String chat_id    = this.chat_id.toString();
    //String message_id = message.getId().toString();
    //int user_id       = this.user.getId();

    //DBActions.updateLastReadMessage(chat_id, message_id, user_id);
    this.last_read_message = message;
  }

  // TO DO
  public List<Message> getUnreadMessages() {
    return null;
  }

  public String getChatId() {
    if (this.chat_id == null) {
      return null;
    }

    return this.chat_id.toString();
  }

  public Chat getChat() {
    if (this.chat_id == null) {
      return null;
    }

    return DBActions.getChatById(this.chat_id.toString());
  }

  ///////////////////////////////////
  /////   Wrappering DBActions!!!
  ///////////////////////////////////
  // TO DO
  public static List<Party> retrieveByChatPk(String chat_id) {
    return null;
  }
}
