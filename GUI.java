import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI {

    public static void Welcome(PlatformDatabase db) {

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
                    exception.printStackTrace();
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
        Border border = (Border) BorderFactory.createLineBorder(Color.BLACK);
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
                AddFriend(currentUser);
            }
        });

        JButton removeFriend = new JButton("Remove a friend");
        removeFriend.setBounds(55, 125, 250, 30);
        action.add(removeFriend);

        JButton blockUser = new JButton("Block a user");
        blockUser.setBounds(55, 180, 250, 30);
        action.add(blockUser);

        JButton sendMessage = new JButton("Send a message");
        sendMessage.setBounds(55, 235, 250, 30);
        action.add(sendMessage);

        JButton viewMessage = new JButton("View messages");
        viewMessage.setBounds(55, 290, 250, 30);
        action.add(viewMessage);

        JButton deleteMessage = new JButton("Delete a message");
        deleteMessage.setBounds(55, 345, 250, 30);
        action.add(deleteMessage);

        JButton searchUser = new JButton("Search for a user");
        searchUser.setBounds(55, 400, 250, 30);
        action.add(searchUser);

        mainMenu.setLocationRelativeTo(null);
        mainMenu.setVisible(true);

    }

    public static void main(String[] args) {
        PlatformDatabase db = new PlatformDatabase("GUIAcc.txt","GUIMess.txt");
        Welcome(db);
    }
}
