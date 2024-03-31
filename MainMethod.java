import java.util.Scanner;

public class MainMethod {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        PlatformDatabase db = new PlatformDatabase("Accounts.txt", "Messages.txt");


        System.out.println("Welcome to the platform. Choose an option:");
        System.out.println("1. Login");
        System.out.println("2. Create a new user");

        int initialChoice = scanner.nextInt();
        scanner.nextLine();

        User currentUser = null;

        if (initialChoice == 1) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            currentUser = db.login(username, password);

            if (currentUser == null) {
                System.out.println("Login failed. User not found or password incorrect.");
                return;
            }
        } else if (initialChoice == 2) {
            currentUser = db.createUser(scanner);
        }

        boolean running = true;

        while (running && currentUser != null) {
            System.out.println("Choose an action:");
            System.out.println("1. Add a friend");
            System.out.println("2. Remove a friend");
            System.out.println("3. Block a user");
            System.out.println("4. Send a message");
            System.out.println("5. View messages");
            System.out.println("6. Exit");

            int action = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (action) {
                case 1: // Add a friend
                    System.out.println("Enter the username of the friend:");
                    String friendUsername = scanner.nextLine();
                    db.addFriend(currentUser.getUsername(), friendUsername);
                    break;
                case 2: // Remove a friend
                    System.out.println("Enter the username of the friend to remove:");
                    friendUsername = scanner.nextLine();
                    db.removeFriend(currentUser.getUsername(), friendUsername);

                    break;
                case 3: // Block a user
                    System.out.println("Enter the username of the user to block:");
                    String blockUsername = scanner.nextLine();
                    db.blockUser(currentUser.getUsername(), blockUsername);
                    break;
                case 4: // Send a message
                    System.out.println("Enter the username of the receiver:");
                    String receiverUsername = scanner.nextLine();
                    System.out.println("Enter your message:");
                    String messageContent = scanner.nextLine();
                    Message message = new Message(currentUser, db.findUserByUsername(receiverUsername), messageContent);
                    db.storeMessage(message);
                    break;
                case 5: // View messages
                    db.viewMessages(currentUser.getUsername());
                    break;
                case 6: // Exit
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        System.out.println("Thank you for using the platform. Goodbye!");
    }
}
