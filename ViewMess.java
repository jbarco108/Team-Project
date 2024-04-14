import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ViewMess {

    public static void main(String[] args) {
        PlatformDatabase runtimePlatformDatabase = new PlatformDatabase("Accounts.txt", "Messages.txt");
        try {

            ServerSocket serverSocket = new ServerSocket(1111);
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            // open output stream to client, flush send header, then input stream...
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream()); //this is to send data to the client
            output.flush(); // ensure data is sent to the client
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());







        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}