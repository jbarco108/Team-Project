import java.util.ArrayList;

/**
 * User
 * <p>
 * Interface for the user class, contains all the basic methods we will implement
 *
 * @author Chethan Adusumilli, cadusumi@purdue.edu
 * @version 1.0
 **/
public interface UserClassInterface {
    String getUsername();//retrieve the username

    String getPassword();//retrieve the password

    int getAge();//retrieve the age

    String getHobby();//retrieve the hobby

    String getLocation();//retrieve location

    ArrayList<String> getFriends();//retrieve friends string array

    ArrayList<String> getBlockedUsers();//retrieve blocked users string array

    void setUsername(String username);//set the username

    void setPassword(String password);//set the password

    void setAge(int age);//set the age as such

    void setHobby(String hobby);//set the hobby as such

    void setLocation(String location);//set the location as such

    void addFriend(String friendUsername);// adds a friend to user friend array

    void removeFriend(String friendUsername);// removes a friend to user friend array

    void blockUser(String username); //block the user

    String toFileString();//display the user as a string
}
