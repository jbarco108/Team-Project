import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Server(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        System.out.printf("Connection received from %s\n", socket.getInetAddress().getHostAddress());
        try {
            PlatformDatabase db = new PlatformDatabase("Accounts.txt", "Messages.txt");
            db.readUsers();
            db.readMessages();

            // Read request type from client
            String requestTypeStr = reader.readLine();
            int requestType = Integer.parseInt(requestTypeStr);

            switch (requestType) {
                case 1, 2, 3, 7, 8:
                    CopyOnWriteArrayList<User> users = db.getUsers();
                    CopyOnWriteArrayList<String> usernames = new CopyOnWriteArrayList<>();

                    for (User user : users) {
                        usernames.add(user.getUsername()); // Assuming User class has a getUsername() method
                    }

                    // Convert list of usernames to a single line of text
                    writer.println(String.join(",", usernames));
                    break;

                case 4, 5, 6:
                    // Retrieve the list of messages from the database
                    CopyOnWriteArrayList<Message> messages = db.getMessages();

                    // Convert the list of messages into a list of strings
                    ArrayList<String> messageStrings = new ArrayList<>();
                    for (Message message : messages) {
                        // Assuming Message class has a toString method that returns the message in a suitable format
                        messageStrings.add(message.getMessageToBeSent());
                    }

                    // Join the list of message strings into a single string, separated by a delimiter (e.g., a newline or a comma)
                    String messageListString = String.join("\n", messageStrings);

                    // Send the single line of text (all messages) to the client
                    writer.println(messageListString);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Initialize server socket
            ServerSocket serverSocket = new ServerSocket(4343);
            System.out.println("Server running, waiting for connections...");

            // Listen for incoming connections and handle them in a new thread
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Server server = new Server(clientSocket);
                new Thread(server).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
