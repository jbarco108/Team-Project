import java.util.ArrayList;
import java.util.Scanner;

/**
 * User
 * <p>
 * User class containing the user and
 * their habits
 *
 * @author Jorge Barco, barco0@purdue.edu
 * @version 1.0
 */
public class User { //User class that consists of the username, password, age, hobby
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
        return new ArrayList<>(friends); // Return a copy to prevent external modifications
    }

    public ArrayList<String> getBlockedUsers() {
        return new ArrayList<>(blockedUsers); // Return a copy to prevent external modifications
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


    //Method to change the specific trait of the user
    public void editUser(String trait) {
        boolean correctInput = false;
        do {

            System.out.println("What would you like to change?");
            Scanner scanner = new Scanner(System.in);
            trait = scanner.nextLine();


            if (trait.equalsIgnoreCase("age")) {
                this.age = Integer.parseInt(trait);
                correctInput = true;
            } else if (trait.equalsIgnoreCase("hobby")) {
                this.hobby = trait;
                correctInput = true;
            } else if (trait.equalsIgnoreCase("location")) {
                this.location = trait;
                correctInput = true;
            } else {
                System.out.println("Please make a decision!");
            }


        }while (!correctInput) ;
    }

    public void addFriend(String username) {
        if (!friends.contains(username) && !blockedUsers.contains(username)) {
            friends.add(username);
        }
    }

    //add a flag if successsfull

    public void removeFriend(String username) {
        friends.remove(username);
    }


    public void blockUser(String username) {
        if (!blockedUsers.contains(username)) {
            blockedUsers.add(username);
            friends.remove(username);
        }
    }

    public String toFileString() {
        String friendsStr = String.join(";", friends);
        String blockedStr = String.join(";", blockedUsers);

        // Ensure to only append non-empty strings for friends and blocked users
        String result = username + "," + password + "," + age + "," + hobby + "," + location;
        if (!friends.isEmpty()){
            result += "," + friendsStr;
        }
        if (!blockedUsers.isEmpty()){
            result += "," + blockedStr;
        }
        return result;
    }

}
