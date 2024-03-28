/**
 * Message
 *
 * Message class containing recipient, sender, and message sent
 *
 * @author Alston Zhang, zhan4508@purdue.edu
 *
 * @version 1.0
 *
 */
public class Message {
    private String messageToBeSent; // the actual message itself
    boolean sendSuccessful; //return value of whether the message is sent successfully or not
    private User sender; //user class ; who sent the message
    private User reciever; //user class ; who is the reciever of the message

    public Message(null,null,null) { //default constructor and sets invalid if null inputs
        this.messageToBeSent = "%%INVALID_STRING%%";
        this.sendSuccessful = false;
        this.sender = null;
        this.reciever = null;
    }
    public message(User sender, User reciever, String message) { //intended constructor.
        this.sendSuccessful = false;
        this.messageToBeSent = message;
        this.sender = sender;
        this.reciever = reciever;
    }
    public String getMessageToBeSent() {
        return messageToBeSent;
    }
    public User getReciever() {
        return reciever;
    }
    public User getSender() {
        return sender;
    }
    public void setMessageToBeSent(String messageToBeSent) {
        this.messageToBeSent = messageToBeSent;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public void setReciever(User reciever) {
        this.reciever = reciever;
    }
    public void setSendSuccessful(boolean sendSuccessful) {
        this.sendSuccessful = sendSuccessful;
    }
}

