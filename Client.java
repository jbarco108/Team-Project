import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    // Constructor establishes the connection to the server
    public Client(String serverAddress, int port) throws IOException {
        // Establish a connection to the server
        socket = new Socket(serverAddress, port);

        // Create an input stream to receive data from the server
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Create an output stream to send data to the server
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    // Method to send a request to the server
    public void sendRequest(int requestType) throws IOException, ClassNotFoundException {
        // Send the request type to the server
        writer.println(requestType);
        writer.flush();

        // Handle the server's response based on the request type
        switch (requestType) {
            case 1, 2, 3, 7, 8:
                // Read the user list from the server as a single string
                String userListString = reader.readLine();
                // Process the list of users
                System.out.println("Received user list: " + userListString);
                break;

            case 4, 5, 6:
                // Read the message list from the server as a single string
                String messageListString = reader.readLine();
                // Process the list of messages
                System.out.println("Received message list: " + messageListString);
                break;

            default:
                System.out.println("Unknown request type: " + requestType);
                break;
        }
    }

    // Close the connection
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            // Replace "localhost" and 4343 with the appropriate server address and port number
            Client client = new Client("localhost", 4343);

            // Create a Scanner to read user input from the console
            Scanner scanner = new Scanner(System.in);

            // Ask the user for the request type (e.g., 1, 2, 3, etc.)
            System.out.println("Enter request type:");
            int requestType = scanner.nextInt();

            // Send the request to the server
            client.sendRequest(requestType);

            // Close the client connection
            client.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}