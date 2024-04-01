import java.util.ArrayList;

/**
 * User
 * <p>
 * Interface for the user class, contains all of the basic methods we will implement
 *
 * @author Chethan Adusumilli, cadusumi@purdue.edu
 * @version 1.0
 **/
public interface UserClassInterface {
    String getUsername();
    String getPassword();
    int getAge();
    String getHobby();
    String getLocation();
    ArrayList<String> getFriends();
    ArrayList<String> getBlockedUsers();
    void setUsername(String username);
    void setPassword(String password);
    void setAge(int age);
    void setHobby(String hobby);
    void setLocation(String location);
    void addFriend(String friendUsername);
    void removeFriend(String friendUsername);
    void blockUser(String username);
    String toFileString();
}
