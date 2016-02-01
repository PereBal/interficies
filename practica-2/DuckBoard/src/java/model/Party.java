package model;

import database.chat.DBActions;
import java.util.List;
import org.bson.types.ObjectId;

public class Party {

  private final User user;
  private final ObjectId chatId;
  private Message lastReadMessage;

  public Party(User user, ObjectId chatId, Message lastReadMessage) {
    this.user = user;
    this.chatId = chatId;
    this.lastReadMessage = lastReadMessage;
  }

  public User getUser() {
    return user;
  }

  public Message getLastReadMessage() {
    return lastReadMessage;
  }

  // TO DO
  public void setLastReadMessage(Message message) {
    //String chat_id    = this.chat_id.toString();
    //String message_id = message.getId().toString();
    //int user_id       = this.user.getId();

    //DBActions.updateLastReadMessage(chat_id, message_id, user_id);
    this.lastReadMessage = message;
  }

  // TO DO
  public List<Message> getUnreadMessages() {
    return null;
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
  // TO DO
  public static List<Party> retrieveByChatPk(String chat_id) {
    return null;
  }
}
