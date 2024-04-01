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
    private User receiver; //user class ; who is the receiver of the message

    public Message() { //default constructor and sets invalid if null inputs
        this.messageToBeSent = "%%INVALID_STRING%%";
        this.sendSuccessful = false;
        this.sender = null;
        this.receiver = null;
    }
    public Message(User sender, User receiver, String message) { //intended constructor.
        this.sendSuccessful = true;
        this.messageToBeSent = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessageToBeSent() {
        return messageToBeSent;
    }
    public User getReceiver() {
        return receiver;
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
    public void setReceiver(User reciever) {
        this.receiver = reciever;
    }
    public void setSendSuccessful(boolean sendSuccessful) {
        this.sendSuccessful = sendSuccessful;
    }

    public String toMessageFileString() {
        return String.format(sender.getUsername() + "," + receiver.getUsername() + "," + messageToBeSent + ",");
    }
}
