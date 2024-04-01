/**
 * User
 * <p>
 * Interface for the message class, contains all of the basic methods we will implement
 *
 * @author Chethan Adusumilli, cadusumi@purdue.edu
 * @version 1.0
 **/
public interface MessageClassInterface {
    String getMessageToBeSent();
    User getReciever();
    User getSender();
    void setMessageToBeSent(String messageToBeSent);
    void setSender(User sender);
    void setReciever(User reciever);
    void setSendSuccessful(boolean sendSuccessful);
    String toMessageFileString();
}
