import java.util.Scanner;

public class MainMethod { //Main method the social media code

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

