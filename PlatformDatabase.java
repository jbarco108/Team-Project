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

    private ArrayList<User> users = new ArrayList<>(); // Initialized as empty list for users
    private ArrayList<Message> messages = new ArrayList<>(); //Initialized as empty list for messages


    // Constructor, getters, and setters would follow
    public PlatformDatabase(String userIn, String messageIn) { //Constructor of the database
        this.userIn = userIn; //Source file for accounts
        this.messageIn = messageIn; //Sources file for messages
        users = new ArrayList<>(); //Users are initialized as empty list
        messages = new ArrayList<>(); //Messages are initialized as empty list
        readUsers(); //Method in database that reads in Accounts.txt file and constructs users from each line, added
        // to users list
        readMessages(); //Method in databse that read in Messages.txt file and constructs messages from each line, added
        //to messages list
    }

    public void readUsers() {//Method to read each line in text file and construct User objects

        this.users.clear(); // Clear existing users

        try (BufferedReader br = new BufferedReader(new FileReader(userIn))) { //Read with resources

            String line;
            while ((line = br.readLine()) != null) { //Iterate through each line

                String[] parts = line.split(",", -1); //Split each field by ","

                if (parts.length >= 7) { // Ensure all parts including friends and blocked are present
                    String username = parts[0].trim(); //Username is first field
                    String password = parts[1].trim();//Password is second field
                    int age = Integer.parseInt(parts[2].trim()); //Age is third field
                    String hobby = parts[3].trim();//Hobby is 4th field
                    String location = parts[4].trim(); //Location is 5th field
                    User user = new User(username, password, age, hobby, location); //Construct user object

                    // Parse friends if present
                    if (parts[5].startsWith("friends:") && !parts[5].substring(8).isEmpty()) { //Check if
                        //friends segment has users

                        String[] friends = parts[5].substring(8).split(";");//List of friends array is
                        //created and split between each user, begins after the

                        for (String friend : friends) user.addFriend(friend.trim());
                    }

                    // Parse blocked users if present
                    if (parts[6].startsWith("blocked:") && !parts[6].substring(8).isEmpty()) {
                        String[] blockedUsers = parts[6].substring(8).split(";");
                        for (String blocked : blockedUsers) user.blockUser(blocked.trim());
                    }

                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public User findUserByUsername(String username) { //Method to find users
        for (User user : users) {
            if (user.getUsername().equals(username)) {
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

    public void rewriteMessageFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(messageIn, false))) {
            for (Message message : messages) {
                writer.println(message.toMessageFileString());
            }
        }
        System.out.println("testing");
    }


    //USER METHODS

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
        } else{
            System.out.println("User does not exist");
        }

        rewriteUserFile();
    }


    //MESSAGE METHODS

    public void storeMessage(Message newMessage) { //Method to store messages in text file
        System.out.println("Message created successfully: " + newMessage.getMessageToBeSent());
        try (PrintWriter writer = new PrintWriter(new FileWriter("Messages.txt", true))) {
            writer.print(newMessage.getSender().getUsername() + ","); // Added separators (comma) for clarity
            writer.print(newMessage.getReceiver().getUsername() + ",");
            writer.print(newMessage.getMessageToBeSent() + ",");
            writer.println();
            readMessages();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading the message: " + e.getMessage());
        }
    }

    public void viewMessages(String username) {
        System.out.println("Messages for " + username + ":");
        for (Message message : messages) {
            if (message.getReceiver().getUsername().equals(username)) {
                System.out.println("You received from " + message.getSender().getUsername() + ": " + message.getMessageToBeSent());
            } else if (message.getSender().getUsername().equals(username)) {
                System.out.println("You sent to " + message.getReceiver().getUsername() + ": " + message.getMessageToBeSent());
            }
        }
    }

    public void deleteMessage(String senderUsername, String receiverUsername, String messageContent) throws IOException {
        // Identify the message to delete
        Message messageToDelete = null;
        for (Message message : messages) {
            if (message.getSender().getUsername().equals(senderUsername) &&
                    message.getReceiver().getUsername().equals(receiverUsername) &&
                    message.getMessageToBeSent().equals(messageContent)) {
                messageToDelete = message;
                break;
            }
        }

        // Proceed if a matching message was found
        if (messageToDelete != null) {
            messages.remove(messageToDelete);
            System.out.println("Message deleted successfully.");
            rewriteMessageFile(); // Ensure this method overwrites the file completely

        } else {
            System.out.println("No matching message found.");
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in); //import scanner in class

        PlatformDatabase db = new PlatformDatabase("Accounts.txt", "Messages.txt");
        //Database consists of two files already in the system - Accounts & Messages

        //User option for logging in or creating a new user

        System.out.println("Welcome to the platform. Choose an option:");
        System.out.println("1. Login");
        System.out.println("2. Create a new user");

        //Receive the choice from the user
        int initialChoice = scanner.nextInt();
        scanner.nextLine();

        //Set the user as null into we either create or login
        User currentUser = null;

        //If user decides to log in, they must present the username and password to continue into the user


        if (initialChoice == 1) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            //User is returned from Accounts file if the user already exists, that is what the log in method does
            currentUser = db.login(username, password);

            //If the user can not be found , print "Login failed. User not found or password incorrect:".
            if (currentUser == null) {
                System.out.println("Login failed. User not found or password incorrect.");
                return;
            }

            //If user decides to create an account, we implement createUser method which creates a User object
        } else if (initialChoice == 2) {
            currentUser = db.createUser(scanner);

        }

        //Flag to continue the application from closing after one round
        boolean running = true;

        // While the currentUser exits and the running flag continues to be true, ask what the user would like to do
        while (running && currentUser != null) {
            System.out.println("Choose an action:");
            System.out.println("1. Add a friend"); //Add a friend to users friend array while storing in text file
            System.out.println("2. Remove a friend"); //Remove a friend from users friend array while deleting
            System.out.println("3. Block a user"); //Remove from friends array if on there and add to blocked array
            System.out.println("4. Send a message"); //Send a message to another user on the text file
            System.out.println("5. View messages"); //Iterate through text file and retrieve messages that include user
            System.out.println("6. Delete a message"); //Iterate through users messages in text file and remove from
            System.out.println("7. Exit"); //End the program

            //Receive user input and continue to run methods corresponding to what option they chose
            int action = scanner.nextInt();
            scanner.nextLine();


            switch (action) {
                case 1: // Add a friend

                    //Receive a friends username
                    System.out.println("Enter the username of the friend:");
                    String friendUsername = scanner.nextLine();

                    //Run the database add friend method to add a friend in the currentUsers friend list,
                    //this also adds the friend from the text file corresponding to the user

                    db.addFriend(currentUser.getUsername(), friendUsername);
                    break;


                case 2: // Remove a friend

                    //Receive friends username
                    System.out.println("Enter the username of the friend to remove:");
                    friendUsername = scanner.nextLine();

                    //Run the database method to remove the friend in the currentUsers friend list,
                    //this also removes the friend from the text file corresponding to the user
                    db.removeFriend(currentUser.getUsername(), friendUsername);

                    break;
                case 3: // Block a user

                    //Receive a username
                    System.out.println("Enter the username of the user to block:");
                    String blockUsername = scanner.nextLine();

                    //Run the database method to add the user to the currentUsers blocked list,
                    //this also adds the user to the blocked segment of the User in the text file
                    db.blockUser(currentUser.getUsername(), blockUsername);
                    break;


                case 4: // Send a message

                    //Receive the username of the directed user
                    System.out.println("Enter the username of the receiver:");
                    String receiverUsername = scanner.nextLine();

                    //Receive the content of the message
                    System.out.println("Enter your message:");
                    String messageContent = scanner.nextLine();

                    //Create the message object from the user input of the user and content
                    Message message = new Message(currentUser, db.findUserByUsername(receiverUsername), messageContent);
                    db.storeMessage(message); //storeMessage method to add the message in the text file, in the
                    // format sender, receiver, and content


                    break;
                case 5: // View messages
                    db.viewMessages(currentUser.getUsername()); //Method to retreive all the messages that involve
                    // the user
                    break;

                case 6://Delete a message
                    //Receive the user whose message you want to delete
                    System.out.println("Enter the username of the receiver:");
                    String receiver = scanner.nextLine();

                    //Receive the content of the message
                    System.out.println("Enter your message:");
                    String words = scanner.nextLine();

                    db.deleteMessage(currentUser.getUsername(), receiver, words );//Method to delete the message from
                    //the message list in the database while deleting message object in the text file

                    break;

                case 7: // Exit the program
                    running = false;
                    break;

                default: //Valid option was not chosen
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        //Program was completed
        System.out.println("Thank you for using the platform. Goodbye!");
    }
}

