import java.io.IOException;
import java.util.Scanner;

/**
 * User
 * <p>
 * Interface for the message class, contains all the basic methods we will implement
 *
 * @author Chethan Adusumilli, cadusumi@purdue.edu
 * @version 1.0
 **/
public interface Platform {

    // User methods
    User createUser(Scanner scanner) throws Exception; //create the user as a user object and adds to users arary

    void storeUser(User newUser) throws IOException; //store the user in text file

    void addFriend(String username, String friendUsername) throws IOException; //adds friend to specific user array

    void removeFriend(String username, String friendUsername) throws IOException; //removes friend from specific user

    // array
    void blockUser(String username, String blockUsername) throws IOException; // add user to blocked array

    // Message methods
    void storeMessage(Message newMessage) throws IOException; //add message to message array and write in text file

    void viewMessages(String username); //reviews text file to retrieve user specific messages

    //delete message from text file and from user array
    void deleteMessage(String senderUsername, String receiverUsername, String messageContent) throws IOException;


    // Other methods
    User login(String username, String password); //set the current user as user object already present in text file

    User findUserByUsername(String username); //find user in users array

    void rewriteUserFile() throws IOException; //rewrites the user objects in text files

    void rewriteMessageFile() throws IOException; //rewrites the message object in text files
}
