import java.io.IOException;

/**
 * User
 * <p>
 * Interface for the PlatFormDatabase class, contains all of the basic methods we will implement
 *
 * @author Chethan Adusumilli, cadusumi@purdue.edu
 * @version 1.0
 **/
public interface Platform{

    // USER RELATED METHODS
    void addFriend(String username, String friendUsername);
    void blockFriend(String username, String friendUsername);
    void removeFriend(String username, String friendUsername);
    String getName(User user);
    int getAge(User user);
    String getLocation(User user);
    String getHobby(User user);
    User login(String username, String password);
    boolean createUser(String username, String password, String name, int age, String location, String hobby);

    // DM RELATED METHODS
    void sendMessage(User sender, User receiver, String message);
    void blockUser(User currentUser, User userToBlock);
    void restrictMessage(User currentUser, boolean friendsOnly);
    boolean hasPermission(User user, String permission);
    // DATA PERSISTENCE RELATED METHODS
    void saveUserData();
    void loadUserData();
    void storeUser(User newUser);
    void storeMessage(Message newMessage);
    void viewMessage(String username);
    void deleteMessage(String senderUsername, String recieverUsername, String messageContent );


    // ADDITIONAL METHODS FOR GUI INTERACTION
    void displayUserProfile(User user);
    void displayMessage(User sender, User receiver, String message);

    void readUsers();
    void readMessages();
   // User login(String username, String password);
    User findUserByUsername(String username);
    void rewriteUserFile() throws IOException;
    void rewriteMessageFile() throws IOException;
}
