package model;

import java.util.List;
import database.chat.DBActions;
import org.bson.types.ObjectId;
import database.chat.exceptions.UserNotInPartyException;
import database.www.exceptions.UserDoesNotExistException;
import database.chat.exceptions.ChatDoesNotExistException;
import database.chat.exceptions.MessageDoesNotExistException;
import database.chat.exceptions.MessageIsNotPartOfThisChatException;

public class Party {

  public final static int LIMIT = 2;

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

  public void setLastReadMessage(Message message) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException,
          MessageDoesNotExistException,
          MessageIsNotPartOfThisChatException {
    String messageId = message.getId().toString();

    DBActions.updateLastReadMessage(this.chatId.toString(), this.user.getId(), messageId);
    this.lastReadMessage = message;
  }

  public List<Message> getUnreadMessages() {
    return null;
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
  public static List<Party> retrieveByChatPk(String chatId) throws ChatDoesNotExistException {
    return DBActions.getPartiesByChatId(chatId, Party.LIMIT);
  }
}
