import java.io.*;
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
 **/

public class PlatformDatabase { //Constructor of the database
    private String userIn; //The source file used to generate the user
    private String messageIn; // The source file for messages

    private ArrayList<User> users = new ArrayList<>(); // Initialized as empty list
    private ArrayList<Message> messages = new ArrayList<>();


    // Constructor, getters, and setters would follow
    public PlatformDatabase(String userIn, String messageIn) { //Constructor of the database
        this.userIn = userIn;
        this.messageIn = messageIn;
        readUsers();
        readMessages();
    }

    public void readUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(userIn))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Ensure there are enough parts to construct a User
                if (parts.length >= 5) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    String hobby = parts[3].trim();
                    String location = parts[4].trim();
                    User user = new User(username, password, age, hobby, location);

                    // Optionally handle friends and blockedUsers if stored in file
                    if(parts.length > 5 && !parts[5].isEmpty()) {
                        for(String friend : parts[5].split(";")) {
                            user.addFriend(friend.trim());
                        }
                    }
                    if(parts.length > 6 && !parts[6].isEmpty()) {
                        for(String blockedUser : parts[6].split(";")) {
                            user.blockUser(blockedUser.trim());
                        }
                    }

                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading the user file: ");
        }
    }

    public void readMessages() {
        try (BufferedReader br = new BufferedReader(new FileReader(messageIn))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    User sender = findUserByUsername(parts[0].trim());
                    User receiver = findUserByUsername(parts[1].trim());
                    String content = parts[2].trim();
                    if (sender != null && receiver != null) {
                        Message message = new Message(sender, receiver, content);
                        messages.add(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading the message file: " + e.getMessage());
        }
    }


    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void rewriteUserFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(userIn, false))) {
            for (User user : users) {
                writer.println(user.toFileString());
            }
        }
    }

    public User createUser(Scanner scanner) throws Exception { //Method to create users
        System.out.println("What is your username?");
        String username = scanner.nextLine();

        System.out.println("What is your password?");
        String password = scanner.nextLine();

        System.out.println("What is your age?");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("What is your favorite hobby?");
        String hobby = scanner.nextLine();

        System.out.println("What is your location?");
        String location = scanner.nextLine();

        User newUser = new User(username, password, age, hobby, location);
        System.out.println("User created successfully: " + newUser.getUsername());

        storeUser(newUser);
        return newUser;
    }


    public User findUserByUsername(String username) { //Method to find users
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void storeMessage(Message newMessage) { //Method to store messages in text file
        System.out.println("Message created successfully: " + newMessage.getMessageToBeSent());
        try (PrintWriter writer = new PrintWriter(new FileWriter("Messages.txt", true))) {
            writer.print(newMessage.getSender() + ","); // Added separators (comma) for clarity
            writer.print(newMessage.getReceiver() + ",");
            writer.print(newMessage.getMessageToBeSent() + ",");
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeUser(User newUser) throws IOException { //Method to store in user in text file
        users.add(newUser);
        rewriteUserFile(); // Now it also updates the file immediately after adding a new users
    }

    public void addFriend(String username, String friendUsername) throws IOException {
        User user = findUserByUsername(username);
        User friend = findUserByUsername(friendUsername);

        if (user == null || friend == null) {
            System.out.println("Either the user or the friend does not exist.");
            return; // Exit if either user is not found
        }

        if (!user.getFriends().contains(friendUsername) && !user.getBlockedUsers().contains(friendUsername)) {
            user.addFriend(friendUsername);
            friend.addFriend(username); // Assuming mutual friendship
            rewriteUserFile(); // Reflect changes in the file
        }
    }

    public void removeFriend(String username, String friendUsername) throws IOException {
        User user = findUserByUsername(username);
        User friend = findUserByUsername(friendUsername);

        if (user != null && friend != null) {
            user.removeFriend(friendUsername);
            friend.removeFriend(username); // Assuming mutual friendship removal
            rewriteUserFile(); // Reflect changes in the file
        }
    }

    public void blockUser(String username, String blockUsername) throws IOException {
        User user = findUserByUsername(username);
        User blockedUser = findUserByUsername(blockUsername);

        if (user != null && blockedUser != null) {
            user.blockUser(blockUsername);
        }
    }

    public void viewMessages(String username) {
        for (Message message : messages) {
            if (message.getReceiver().getUsername().equals(username)) {
                System.out.println(message.getMessageToBeSent());
            }
        }
    }

}
