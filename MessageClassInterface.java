/**
 * User
 * <p>
 * Interface for the message class, contains all the basic methods we will implement
 *
 * @author Chethan Adusumilli, cadusumi@purdue.edu
 * @version 1.0
 **/
public interface MessageClassInterface {
    String getMessageToBeSent(); //content of message

    User getReceiver(); //retrieve receiver

    User getSender(); //retrive sender

    void setMessageToBeSent(String messageToBeSent); //set content

    void setSender(User sender); //set sender

    /* void setReciever(User reciever);*/ //method later to be implemented

    void setSendSuccessful(boolean sendSuccessful); //boolean condition for messages

    String toMessageFileString(); //display the message as a string
}
