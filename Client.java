import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public String sendRequest(int requestType) throws IOException, ClassNotFoundException {
        // Send the request type to the server
        writer.println(requestType);
        writer.flush();

        // Handle the server's response based on the request type
        switch (requestType) {
            case 1, 2, 3, 7, 8:
                // Read the user list from the server as a single string
                String userListString = reader.readLine();
                // Process the list of users
                return userListString;
            case 4, 5, 6:
                // Read the message list from the server as a single string
                String messageListString = reader.readLine();
                // Process the list of messages
                return messageListString;
            default:
                return "INVALID";
        }
    }

    // Close the connection
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }

    public static void Welcome(PlatformDatabase db) {

        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {

            }
        }));

        JFrame welcome = new JFrame("Welcome");
        welcome.setSize(600,500);
        welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("WELCOME!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif",Font.BOLD,70));
        welcomeLabel.setBorder(new EmptyBorder(100,150,10,150));

        Dimension buttonSize = new Dimension(150,50);
        JButton login = new JButton("Login");
        login.setPreferredSize(buttonSize);
        JButton signUp = new JButton("Sign Up");
        signUp.setPreferredSize(buttonSize);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.add(login);
        JPanel signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signUpPanel.add(signUp);

        welcome.setLayout(new BorderLayout());
        JPanel topWelPanel = new JPanel();
        topWelPanel.add(welcomeLabel);
        welcome.add(topWelPanel, BorderLayout.NORTH);
        topWelPanel.setPreferredSize(new Dimension(welcome.getWidth(),300));

        JPanel bottomWelPanel = new JPanel(new GridLayout(1,2));
        bottomWelPanel.add(loginPanel);
        bottomWelPanel.add(signUpPanel);
        welcome.add(bottomWelPanel, BorderLayout.CENTER);

        login.addActionListener(e -> {
            LoginManu(db);
            welcome.dispose();
        });

        signUp.addActionListener(e -> {
            SignUpMenu(db);
            welcome.dispose();
        });
        welcome.setLocationRelativeTo(null);
        welcome.setVisible(true);
    }

    public static void LoginManu(PlatformDatabase db) {

        JFrame loginMenu = new JFrame();
        loginMenu.setSize(600,500);
        loginMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginMenu.setLayout(null);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Serif",Font.BOLD,60));

        JButton back = new JButton("<- Back");
        back.addActionListener(e -> {
            Welcome(db);
            loginMenu.dispose();
        });

        back.setBounds(0,0,80,50);
        loginMenu.add(back);

        loginLabel.setBounds(100,80,150,80);
        loginMenu.add(loginLabel);

        JLabel username = new JLabel("Username");
        username.setFont(new Font("Serif",Font.BOLD,25));
        username.setBounds(50,200,120,40);
        loginMenu.add(username);

        JTextField usernameField = new JTextField(0);
        usernameField.setBounds(210,215,300,20);
        loginMenu.add(usernameField);

        JLabel password = new JLabel("Password");
        password.setFont(new Font("Serif",Font.BOLD,25));
        password.setBounds(50,280,120,40);
        loginMenu.add(password);

        JTextField passwordField = new JTextField(0);
        passwordField.setBounds(210,295,300,20);
        loginMenu.add(passwordField);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            String usernameStr = usernameField.getText();
            String passwordStr = passwordField.getText();
            if (db.login(usernameStr,passwordStr) != null) {
                JOptionPane.showMessageDialog(null,
                        "Login Successful!",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                MainMenu(db,db.login(usernameStr,passwordStr));
                loginMenu.dispose();
            } else {
                JOptionPane.showMessageDialog(null,
                        "User does not exist or wrong password.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        confirm.setBounds(450,380,100,30);
        loginMenu.add(confirm);

        loginMenu.setLocationRelativeTo(null);
        loginMenu.setVisible(true);

    }

    public static void SignUpMenu(PlatformDatabase db) {

        JFrame signUpMenu = new JFrame();
        signUpMenu.setSize(600,500);
        signUpMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUpMenu.setLayout(null);

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("Serif",Font.BOLD,60));

        JButton back = new JButton("<- Back");
        back.addActionListener(e -> {
            Welcome(db);
            signUpMenu.dispose();
        });

        back.setBounds(0,0,80,50);
        signUpMenu.add(back);

        signUpLabel.setBounds(80,70,300,80);
        signUpMenu.add(signUpLabel);

        JLabel username = new JLabel("Username");
        username.setFont(new Font("Serif",Font.BOLD,25));
        username.setBounds(50,190,120,40);
        signUpMenu.add(username);

        JTextField usernameField = new JTextField(0);
        usernameField.setBounds(210,205,300,20);
        signUpMenu.add(usernameField);

        JLabel password = new JLabel("Password");
        password.setFont(new Font("Serif",Font.BOLD,25));
        password.setBounds(50,240,120,40);
        signUpMenu.add(password);

        JTextField passwordField = new JTextField(0);
        passwordField.setBounds(210,255,300,20);
        signUpMenu.add(passwordField);

        JLabel hobby = new JLabel("Hobby");
        hobby.setFont(new Font("Serif",Font.BOLD,25));
        hobby.setBounds(50,290,80,40);
        signUpMenu.add(hobby);

        JTextField hobbyField = new JTextField(0);
        hobbyField.setBounds(140,305,110,20);
        signUpMenu.add(hobbyField);

        JLabel location = new JLabel("Location");
        location.setFont(new Font("Serif",Font.BOLD,25));
        location.setBounds(280,290,100,40);
        signUpMenu.add(location);

        JTextField locationField = new JTextField(0);
        locationField.setBounds(400,305,110,20);
        signUpMenu.add(locationField);

        JLabel age = new JLabel("Age");
        age.setFont(new Font("Serif",Font.BOLD,25));
        age.setBounds(50,340,60,40);
        signUpMenu.add(age);

        JTextField ageField = new JTextField(0);
        ageField.setBounds(120,355,60,20);
        signUpMenu.add(ageField);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            String usernameStr = usernameField.getText();
            String passwordStr = passwordField.getText();
            String hobbyStr = hobbyField.getText();
            String locationStr = locationField.getText();
            String ageStr = ageField.getText();
            if (usernameStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Username cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (passwordStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Password cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (hobbyStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Hobby cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (locationStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Location cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Age cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            for (int i = 0; i < db.getUsers().size(); i++) {
                if (db.getUsers().get(i).getUsername().equals(usernameStr)) {
                    JOptionPane.showMessageDialog(null,
                            "User already exists",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            try {
                int ageInt = Integer.parseInt(ageStr);
                User user = new User(usernameStr,
                        passwordStr,
                        ageInt,
                        hobbyStr,
                        locationStr);
                try {
                    db.storeUser(user);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
                JOptionPane.showMessageDialog(null,
                        "User Creation Successful",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                LoginManu(db);
                signUpMenu.dispose();
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null,
                        "Please enter your age in numbers",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        confirm.setBounds(450,380,100,30);
        signUpMenu.add(confirm);

        signUpMenu.setLocationRelativeTo(null);
        signUpMenu.setVisible(true);

    }

    public static void MainMenu(PlatformDatabase db, User currentUser) {

        JFrame mainMenu = new JFrame("Main Menu");
        mainMenu.setSize(400, 700);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setLayout(null);

        JLabel username = new JLabel(currentUser.getUsername());
        JLabel age = new JLabel("Age: " + currentUser.getAge());
        JLabel hobby = new JLabel("Hobby: " + currentUser.getHobby());
        JLabel location = new JLabel("Location: " + currentUser.getLocation());
        JLabel profile = new JLabel("User Profile");

        profile.setFont(new Font("Serif", Font.BOLD, 40));
        profile.setBounds(30, 20, 250, 40);
        mainMenu.add(profile);

        username.setFont(new Font("Serif", Font.BOLD, 30));
        username.setBounds(40, 80, 300, 30);
        mainMenu.add(username);

        age.setFont(new Font("Serif", Font.BOLD, 15));
        age.setBounds(40, 120, 300, 30);
        mainMenu.add(age);

        hobby.setFont(new Font("Serif", Font.BOLD, 15));
        hobby.setBounds(40, 150, 300, 30);
        mainMenu.add(hobby);

        location.setFont(new Font("Serif", Font.BOLD, 15));
        location.setBounds(40, 180, 300, 30);
        mainMenu.add(location);

        JPanel action = new JPanel();
        action.setLayout(null);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        action.setBorder(border);
        action.setBounds(10, 240, 365, 400);
        mainMenu.add(action);

        JLabel actionLabel = new JLabel("Action");
        actionLabel.setFont(new Font("Serif", Font.BOLD, 25));
        actionLabel.setBounds(10, 5, 75, 45);
        actionLabel.setBorder(border);
        action.add(actionLabel);

        JButton addFriend = new JButton("Add a friend");
        addFriend.setBounds(55, 70, 250, 30);
        action.add(addFriend);
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFriend(db, currentUser);
            }
        });

        JButton removeFriend = new JButton("Remove a friend");
        removeFriend.setBounds(55, 115, 250, 30);
        action.add(removeFriend);
        removeFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveFriend(db, currentUser);
            }
        });

        JButton blockUser = new JButton("Block a user");
        blockUser.setBounds(55, 160, 250, 30);
        action.add(blockUser);
        blockUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BlockUser(db, currentUser);
            }
        });

        JButton sendMessage = new JButton("Send a message");
        sendMessage.setBounds(55, 205, 250, 30);
        action.add(sendMessage);
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendMessage(db, currentUser);
            }
        });

        JButton viewMessage = new JButton("View messages");
        viewMessage.setBounds(55, 250, 250, 30);
        action.add(viewMessage);
        viewMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewMessage(db, currentUser);
            }
        });

        JButton deleteMessage = new JButton("Delete a message");
        deleteMessage.setBounds(55, 295, 250, 30);
        action.add(deleteMessage);
        deleteMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteMessage(db, currentUser);
            }
        });

        JButton searchUser = new JButton("Search for a user");
        searchUser.setBounds(55, 340, 250, 30);
        action.add(searchUser);
        searchUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchUser(db, currentUser);
            }
        });

        mainMenu.setLocationRelativeTo(null);
        mainMenu.setVisible(true);

    }

    public static void AddFriend(PlatformDatabase db, User currentUser) {

        JFrame addFriend = new JFrame("Action");
        addFriend.setSize(500,300);
        addFriend.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFriend.setLayout(null);

        JLabel add = new JLabel("Add a Friend");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        add.setBorder(border);
        add.setFont(new Font("Serif",Font.BOLD,30));
        add.setBounds(10,10,180,50);
        addFriend.add(add);

        JLabel enter = new JLabel("Please Enter the Username");
        enter.setFont(new Font("Serif",Font.BOLD,20));
        enter.setBounds(50,80,300,40);
        addFriend.add(enter);

        JTextField username = new JTextField(0);
        username.setBounds(50,140,350,20);
        addFriend.add(username);

        JButton confirm = new JButton("Confirm");
        confirm.setBounds(350,180,100,50);
        addFriend.add(confirm);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String friend = username.getText();
                if (friend.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Username cannot be empty",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (friend.equals(currentUser.getUsername())) {
                    JOptionPane.showMessageDialog(null,
                            "You Cannot Add Yourself",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                User checkFriend = db.findUserByUsername(friend);
                if (checkFriend == null) {
                    JOptionPane.showMessageDialog(null,
                            "The User Does Not Exist",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (String friends : currentUser.getFriends()) {
                    if (friends.equals(friend)) {
                        JOptionPane.showMessageDialog(null,
                                "You Have Added This User",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                try {
                    db.addFriend(currentUser.getUsername(), friend);
                    JOptionPane.showMessageDialog(null,
                            "The User " + friend + " Was Added");
                    addFriend.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addFriend.setLocationRelativeTo(null);
        addFriend.setVisible(true);

    }

    public static void RemoveFriend(PlatformDatabase db, User currentUser) {

        JFrame removeFriend = new JFrame("Action");
        removeFriend.setSize(500,300);
        removeFriend.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeFriend.setLayout(null);

        JLabel remove = new JLabel("Remove a Friend");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        remove.setBorder(border);
        remove.setFont(new Font("Serif",Font.BOLD,30));
        remove.setBounds(10,10,230,50);
        removeFriend.add(remove);

        JLabel enter = new JLabel("Please Enter the Username");
        enter.setFont(new Font("Serif",Font.BOLD,20));
        enter.setBounds(50,80,300,40);
        removeFriend.add(enter);

        JTextField username = new JTextField(0);
        username.setBounds(50,140,350,20);
        removeFriend.add(username);

        JButton confirm = new JButton("Confirm");
        confirm.setBounds(350,180,100,50);
        removeFriend.add(confirm);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String friend = username.getText();
                if (friend.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Username cannot be empty",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (String friends : currentUser.getFriends()) {
                    if (friends.equals(friend)) {
                        try {
                            db.removeFriend(currentUser.getUsername(),friend);
                            JOptionPane.showMessageDialog(null,
                                    "The Friend " + friend + " Was Removed");
                            removeFriend.dispose();
                            return;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null,
                        "This user is not your friend",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        removeFriend.setLocationRelativeTo(null);
        removeFriend.setVisible(true);

    }

    public static void BlockUser(PlatformDatabase db, User currentUser) {

        JFrame blockUser = new JFrame("Action");
        blockUser.setSize(500,300);
        blockUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        blockUser.setLayout(null);

        JLabel block = new JLabel("Block a User");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        block.setBorder(border);
        block.setFont(new Font("Serif",Font.BOLD,30));
        block.setBounds(10,10,170,50);
        blockUser.add(block);

        JLabel enter = new JLabel("Please Enter the Username");
        enter.setFont(new Font("Serif",Font.BOLD,20));
        enter.setBounds(50,80,300,40);
        blockUser.add(enter);

        JTextField username = new JTextField(0);
        username.setBounds(50,140,350,20);
        blockUser.add(username);

        JButton confirm = new JButton("Confirm");
        confirm.setBounds(350,180,100,50);
        blockUser.add(confirm);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                if (user.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Username cannot be empty",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (String friends : currentUser.getFriends()) {
                    if (friends.equals(user)) {
                        JOptionPane.showMessageDialog(null,
                                "This user is your friend",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (user.equals(currentUser.getUsername())) {
                    JOptionPane.showMessageDialog(null,
                            "You Cannot Block Yourself",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                User checkUser = db.findUserByUsername(user);
                if (checkUser == null) {
                    JOptionPane.showMessageDialog(null,
                            "The User Does Not Exist",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (String block : currentUser.getBlockedUsers()) {
                    if (block.equals(user)) {
                        JOptionPane.showMessageDialog(null,
                                "You Have Blocked This User",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    db.blockUser(currentUser.getUsername(), user);
                    JOptionPane.showMessageDialog(null,
                            "The User " + user + " Was Blocked");
                    blockUser.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        blockUser.setLocationRelativeTo(null);
        blockUser.setVisible(true);

    }

    public static void SendMessage(PlatformDatabase db, User currentUser) {

        JFrame sendMessage = new JFrame("Action");
        sendMessage.setSize(500,300);
        sendMessage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sendMessage.setLayout(null);

        JLabel send = new JLabel("Send a Message");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        send.setBorder(border);
        send.setFont(new Font("Serif",Font.BOLD,30));
        send.setBounds(10,10,210,50);
        sendMessage.add(send);

        JLabel enterUsername = new JLabel("Please Enter the Receiver");
        enterUsername.setFont(new Font("Serif",Font.BOLD,20));
        enterUsername.setBounds(50,80,300,40);
        sendMessage.add(enterUsername);

        JTextField username = new JTextField(0);
        username.setBounds(300,93,100,20);
        sendMessage.add(username);

        JLabel enterMessage = new JLabel("Please Enter the Message");
        enterMessage.setFont(new Font("Serif",Font.BOLD,20));
        enterMessage.setBounds(50,130,300,40);
        sendMessage.add(enterMessage);

        JTextField messageField = new JTextField(0);
        messageField.setBounds(50,190,280,20);
        sendMessage.add(messageField);

        JButton confirm = new JButton("Confirm");
        confirm.setBounds(350,180,100,50);
        sendMessage.add(confirm);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String receiver = username.getText();
                String message = messageField.getText();
                if (receiver.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Receiver cannot be empty",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Message cannot be empty",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                User checkReceiver = db.findUserByUsername(receiver);
                if (checkReceiver == null) {
                    JOptionPane.showMessageDialog(null,
                            "The User Does Not Exist",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (String blocks : checkReceiver.getBlockedUsers()) {
                    if (blocks.equals(currentUser.getUsername())) {
                        JOptionPane.showMessageDialog(null,
                                "You have blocked the user " + receiver,
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                for (String friends : currentUser.getFriends()) {
                    if (friends.equals(receiver)) {
                        Message newMessage = new Message(currentUser, checkReceiver, message);
                        db.storeMessage(newMessage);
                        JOptionPane.showMessageDialog(null,
                                "Message Sent");
                        sendMessage.dispose();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null,
                        "You Are Not Friends",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        sendMessage.setLocationRelativeTo(null);
        sendMessage.setVisible(true);

    }

    public static void ViewMessage(PlatformDatabase db, User currentUser) {

        JFrame viewMessage = new JFrame("Action");
        viewMessage.setSize(500,600);
        viewMessage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewMessage.setLayout(null);

        JLabel view = new JLabel("View Messages");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        view.setBorder(border);
        view.setFont(new Font("Serif",Font.BOLD,30));
        view.setBounds(10,10,200,50);
        viewMessage.add(view);

        ArrayList<String> messages = new ArrayList<>();
        for (Message message : db.getMessages()) {
            if (message.getSender().equals(currentUser)) {
                messages.add("You sent to " + message.getReceiver().getUsername() + ": " + message.getMessageToBeSent());
            }
            if (message.getReceiver().equals(currentUser)) {
                messages.add("The user " + message.getSender().getUsername() + " send to you: " + message.getMessageToBeSent());
            }
        }
        String[] message = new String[messages.size()];
        for (int i = 0; i < messages.size(); i++) {
            message[i] = messages.get(i);
        }
        JList<String> list = new JList<>(message);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10,80,470,460);
        viewMessage.add(scrollPane);

        viewMessage.setLocationRelativeTo(null);
        viewMessage.setVisible(true);

    }

    public static void DeleteMessage(PlatformDatabase db, User currentUser) {

        JFrame deleteMessage = new JFrame("Action");
        deleteMessage.setSize(500,600);
        deleteMessage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteMessage.setLayout(null);

        JLabel delete = new JLabel("Delete a Message");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        delete.setBorder(border);
        delete.setFont(new Font("Serif",Font.BOLD,30));
        delete.setBounds(10,10,230,50);
        deleteMessage.add(delete);

        ArrayList<String> messages = new ArrayList<>();
        for (Message message : db.getMessages()) {
            if (message.getSender().equals(currentUser)) {
                messages.add("You sent to " + message.getReceiver().getUsername() + ": " + message.getMessageToBeSent());
            }
            if (message.getReceiver().equals(currentUser)) {
                messages.add("The user " + message.getSender().getUsername() + " send to you: " + message.getMessageToBeSent());
            }
        }
        String[] message = new String[messages.size()];
        for (int i = 0; i < messages.size(); i++) {
            message[i] = messages.get(i);
        }
        JList<String> list = new JList<>(message);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10,80,470,400);
        deleteMessage.add(scrollPane);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(350,500,100,50);
        deleteMessage.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = list.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(null,
                            "Please Select a Message",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                selected = selected.substring(selected.indexOf(':') + 2);
                for (Message mes : db.getMessages()) {
                    if (mes.getMessageToBeSent().equals(selected)) {
                        db.getMessages().remove(mes);
                        try {
                            db.rewriteMessageFile();
                            JOptionPane.showMessageDialog(null,
                                    "Message Deleted");
                            deleteMessage.dispose();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        deleteMessage.setLocationRelativeTo(null);
        deleteMessage.setVisible(true);

    }

    public static void SearchUser(PlatformDatabase db, User currentUser) {

        JFrame searchUser = new JFrame("Action");
        searchUser.setSize(500, 300);
        searchUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchUser.setLayout(null);

        JLabel search = new JLabel("Search a User");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        search.setBorder(border);
        search.setFont(new Font("Serif", Font.BOLD, 30));
        search.setBounds(10, 10, 190, 50);
        searchUser.add(search);

        JLabel enter = new JLabel("Please Enter the Username");
        enter.setFont(new Font("Serif", Font.BOLD, 20));
        enter.setBounds(50, 80, 300, 40);
        searchUser.add(enter);

        JTextField username = new JTextField(0);
        username.setBounds(50, 140, 350, 20);
        searchUser.add(username);

        JButton confirm = new JButton("Confirm");
        confirm.setBounds(350, 180, 100, 50);
        searchUser.add(confirm);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = username.getText();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Username cannot be empty",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (User users : db.getUsers()) {
                    if (users.getUsername().equals(name)) {
                        JOptionPane.showMessageDialog(null,
                                "Username: " + name + "\n" +
                                        "Age: " + users.getAge() + "\n" +
                                        "Hobby: " + users.getHobby() + "\n" +
                                        "Location: " + users.getLocation() + "\n",
                                "Profile",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null,
                        "The User Does Not Exist",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        searchUser.setLocationRelativeTo(null);
        searchUser.setVisible(true);
    }

        public static void main(String[] args) {
        PlatformDatabase db = new PlatformDatabase("GUIAcc.txt", "GUIMess.txt");
        try {
            Client client = new Client("localhost", 4343);
            Welcome(db);


        } catch (Exception e) {
            System.out.println("Server is not started");
            e.printStackTrace();
        }
    }

}