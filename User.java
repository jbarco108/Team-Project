import java.util.ArrayList;

/**
 * User
 * <p>
 * User class containing the user and
 * their habits
 *
 * @author Jorge Barco, barco0@purdue.edu
 * @version 1.0
 */
public class User implements UserClassInterface { //User class that consists of the username, password, age, hobby
    //location, specific friends, and specific blocked friends
    private String username;
    private String password;
    private int age;
    private String hobby;
    private String location;
    private ArrayList<String> friends = new ArrayList<>();
    private ArrayList<String> blockedUsers = new ArrayList<>();


    // Primary constructor to initialize user traits alongside the added friends and blocked friends
    public User(String username, String password, int age, String hobby, String location) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.hobby = hobby;
        this.location = location;
        this.friends = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }

    public User() { //Constructor that takes in wrong input
        this.username = "defaultUsername";
        this.password = "defaultPassword";
        this.age = 999; //
        this.hobby = "defaultHobby";
        this.location = "defaultLocation";
    }

    // Getters for the information of the user
    //include getting username, password, age, hobby, and location
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getFriends() {
        return new ArrayList<>(friends); // Return a new friends list of the same type to prevent external changes

    }

    public ArrayList<String> getBlockedUsers() {
        return new ArrayList<>(blockedUsers); // Return a new blockedUsers list of the same type to prevent external
        //changes
    }


    // Setting the information of the user
    // include setting username, password, age, hobby, and location
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addFriend(String friendUsername) { //Method inserted to add friend to users friends list
        if (blockedUsers.contains(friendUsername)) {
            //System.out.println(friendUsername + " is blocked and cannot be added as a friend.");
            return;
        }
        if (!friends.contains(friendUsername)) {
            friends.add(friendUsername);
            //System.out.println(friendUsername + " added as a friend.");
        }

        // System.out.println(friendUsername + " is already a friend.");

    }

    public void removeFriend(String friendUsername) { //Method inserted to remove a friend from users friends list
        if (friends.contains(friendUsername)) {
            friends.remove(friendUsername);
        }
    }


    public void blockUser(String username) { // Method inserted to add a user to the blocked list if not already
        if (!blockedUsers.contains(username)) {
            blockedUsers.add(username);
            removeFriend(username); // This ensures the user is removed from friends list as well
        }
    }

    public String toFileString() {//This to string is used to lay out the users traits and specific friends and blocked
        // Basic user info
        String basicInfo = String.join(",", username, password, String.valueOf(age), hobby, location);
        // The string.join method adds a comma between each variable

        // Friends and blocked users, separated by special markers, that is ";"
        //Friends are identified off by the friends keyword

        String friendsStr = "friends:" + String.join(";", friends);

        //Blocked users are identified by the blocked keyword
        String blockedUsersStr = "blocked:" + String.join(";", blockedUsers);

        return String.join(",", basicInfo, friendsStr, blockedUsersStr); //return a string of the traits,
        // friends and blocked users all seperated with a comma

    }
}

