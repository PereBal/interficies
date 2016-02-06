package model;

import java.util.List;
import java.time.LocalDate;
import org.bson.types.ObjectId;
import db.chat.DBActions;
import db.chat.exceptions.UserNotInPartyException;
import db.www.exceptions.UserDoesNotExistException;
import db.chat.exceptions.ChatDoesNotExistException;
import db.chat.exceptions.MessageDoesNotExistException;
import db.chat.exceptions.MessageIsNotPartOfThisChatException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Chat {

  private final ObjectId id;
  private final List<Party> parties;
  private final List<Message> messages;
  private final LocalDate createdAt;

  public Chat(ObjectId id, List<Party> parties, List<Message> messages) {
    this.id = id;
    this.parties = parties;
    this.messages = messages;
    this.createdAt = LocalDate.parse(id.getDate().toString(), DateTimeFormatter.ISO_DATE_TIME);
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
    List<Message> list = DBActions.getMessages(toString(), Message.LIMIT, 0);
    Collections.reverse(list);
    return list;
  }

  public List<Message> getMessages(int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException {
    List<Message> list = DBActions.getMessages(toString(), Message.LIMIT, skip);
    Collections.reverse(list);
    return list;
  }

  public int countUnreadMessages(int userId) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    return DBActions.getMessages(toString(), userId, true, Message.LIMIT * 50, 0).size();
  }

  public List<Message> getUnreadMessages(int userId) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    List<Message> list = DBActions.getMessages(toString(), userId, true, Message.LIMIT, 0);
    Collections.reverse(list);
    return list;
  }

  public List<Message> getUnreadMessages(int userId, int skip) throws
          ChatDoesNotExistException,
          UserNotInPartyException,
          UserDoesNotExistException {
    List<Message> list = DBActions.getMessages(toString(), userId, true, Message.LIMIT, skip);
    Collections.reverse(list);
    return list;
  }

  public void setMessages(List<Message> messages) throws
          UserDoesNotExistException,
          UserNotInPartyException,
          ChatDoesNotExistException {
    for (Message message : messages) {
      Message msg = DBActions.createMessage(toString(), message.getUser().getId(), message.getText());
      this.messages.add(msg);
    }
  }

  public void setLastReadMessage(int userId) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException {
    DBActions.updateLastReadMessage(toString(), userId);
  }

  public void setLastReadMessage(int userId, String messageId) throws
          ChatDoesNotExistException,
          UserDoesNotExistException,
          UserNotInPartyException,
          MessageDoesNotExistException,
          MessageIsNotPartOfThisChatException {
    DBActions.updateLastReadMessage(toString(), userId, messageId);
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  @Override
  public String toString() {
    return this.id.toString();
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
    return db.chat.DBActions.getChatsByUserId(id);
  }
}
