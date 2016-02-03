package model;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import database.chat.DBActions;
import database.chat.exceptions.UserNotInPartyException;
import database.www.exceptions.UserDoesNotExistException;
import database.chat.exceptions.ChatDoesNotExistException;

public class Chat {

  private final ObjectId id;
  private final List<Party> parties;
  private final List<Message> messages;
  private final Date createdAt;

  public Chat(ObjectId id, List<Party> parties, List<Message> messages) {
    this.id = id;
    this.parties = parties;
    this.messages = messages;
    this.createdAt = id.getDate(); //revisar el format del date object id
  }

  public ObjectId getId() {
    return id;
  }

  public List<Party> getParties() {
    return parties;
  }

  public List<Message> getMessages() throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    return DBActions.getMessages(this.id.toString(), Message.LIMIT, 0);
  }

  public List<Message> getMessages(int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    return DBActions.getMessages(this.id.toString(), Message.LIMIT, skip);
  }

  public List<Message> getUnreadMessages(int userId) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(this.id.toString(), userId, true, Message.LIMIT, 0);
  }

  public List<Message> getUnreadMessages(int userId, int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(this.id.toString(), userId, true, Message.LIMIT, skip);
  }

  public void setMessages(List<Message> messages) throws
          UserDoesNotExistException,
          UserNotInPartyException,
          ChatDoesNotExistException {
    for (Message message : messages) {
      Message msg = DBActions.createMessage(this.id.toString(), message.getUser().getId(), message.getText());
      this.messages.add(msg);
    }
  }

  public void setLastReadMessage(int userId) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException {
    DBActions.updateLastReadMessage(this.id.toString(), userId);
  }

  public void setLastReadMessage(int userId, String messageId) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException {
    DBActions.updateLastReadMessage(this.id.toString(), userId, messageId);
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  ///////////////////////////////////
  /////   Wrappering DBActions!!!
  ///////////////////////////////////
  public static Chat create(int userId1, int userId2) throws UserDoesNotExistException {
    return DBActions.createChat(userId1, userId2);
  }

  public static Chat retrieveByPk(String id) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    return DBActions.getChatById(id);
  }

  public static List<Chat> retrieveChatsByUserPk(int id) throws
          UserDoesNotExistException,
          ChatDoesNotExistException,
          UserNotInPartyException {
    return database.chat.DBActions.getChatsByUserId(id);
  }
}
