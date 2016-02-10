package model;

import java.util.List;
import db.chat.DBActions;
import org.bson.types.ObjectId;
import db.chat.exceptions.UserNotInPartyException;
import db.www.exceptions.UserDoesNotExistException;
import db.chat.exceptions.ChatDoesNotExistException;
import db.chat.exceptions.MessageDoesNotExistException;
import db.chat.exceptions.MessageIsNotPartOfThisChatException;
import java.util.Collections;

public class Party {

  public final static int LIMIT = 2;

  private final User user;
  private final ObjectId chatId;
  private final String userName;
  private final String userEmail;
  private final boolean isDeleted;
  private Message lastReadMessage;

  public Party(User user, ObjectId chatId, String userName, String userEmail, boolean isDeleted, Message lastReadMessage) {
    this.user            = user;
    this.chatId          = chatId;
    this.userName        = userName;
    this.userEmail       = userEmail;
    this.isDeleted       = isDeleted;
    this.lastReadMessage = lastReadMessage;
  }

  public User getUser() {
    return user;
  }

  public String getUserName() {
    return userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public boolean getIsDeleted() {
    return isDeleted;
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

  public int countUnreadMessages() throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(this.chatId.toString(), this.user.getId(), true, Message.LIMIT, 0).size();
  }

  public List<Message> getUnreadMessages() throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(this.chatId.toString(), this.user.getId(), true, Message.LIMIT, 0);
  }

  public List<Message> getUnreadMessages(int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(this.chatId.toString(), this.user.getId(), true, Message.LIMIT, skip);
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
