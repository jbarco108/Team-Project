import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.*;

public class Server implements Runnable {
    Socket socket;
    public Server(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        System.out.printf("connection received from %s\n", socket);
        try {

            PlatformDatabase db = new PlatformDatabase("Accounts.txt", "Messages.txt");
            //CHANGED BY ALSTON | REREAD ALL USER/MESSAGE DATA BEFORE MAKING CALLS
            db.rewriteUserFile();
            db.rewriteMessageFile();
            db.readUsers();
            db.readMessages();
            // ---------------

            // open output stream to client, flush send header, then input stream...
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            //logic is to send two streams to the client, accessing the account file and the messages file and processing it by the client;
            int s2 = Integer.parseInt((String)ois.readObject());
            //CHANGED BY ALSTON | REREAD ALL USER/MESSAGE DATA BEFORE MAKING CALLS
            db.rewriteUserFile();
            db.rewriteMessageFile();
            db.readUsers();
            db.readMessages();
            // ---------------

            switch (s2) { //returns needed arraylist based off of client input on what is selected
                case 1 -> {
                    ObjectOutputStream o1 = new ObjectOutputStream(socket.getOutputStream());
                    o1.flush(); // ensure data is sent to the client;
                    o1.writeObject(db.getUsers());
                }
                case 2 -> {
                    ObjectOutputStream o2 = new ObjectOutputStream(socket.getOutputStream());
                    o2.flush(); // ensure data is sent to the client;
                    o2.writeObject(db.getUsers());
                }
                case 3 -> {
                    ObjectOutputStream o3 = new ObjectOutputStream(socket.getOutputStream());
                    o3.flush(); // ensure data is sent to the client;
                    o3.writeObject(db.getUsers());
                }
                case 4, 6, 5 -> { // messages case for methods
                    ObjectOutputStream o4 = new ObjectOutputStream(socket.getOutputStream());
                    o4.flush(); // ensure data is sent to the client;
                    o4.writeObject(db.getMessages());
                }
                case 7, 8 -> { // THIS CASE IS FOR CREATING A NEW USER MAKE SURE TO PASS THROUGH 7 AT THE START OF THE CLIENT TO LOGIN/CREATE NEW USER.
                    // Do ALL PROCESSING ON THE CLIENT
                    ObjectOutputStream o7 = new ObjectOutputStream(socket.getOutputStream());
                    o7.flush(); // ensure data is sent to the client;
                    o7.writeObject(db.getUsers());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4343);
            System.out.printf("socket open, waiting for connections on %s", serverSocket); //can comment out
            // infinite server loop: accept connection,
            // spawn thread to handle...
            // THIS WORKS DO NOT CHANGE
            while (true) {
                Socket socket = serverSocket.accept();
                Server server = new Server(socket);
                new Thread(server).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}