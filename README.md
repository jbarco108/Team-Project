Team Project

To run this project, please compile all the classes and run the MainMethod class. Before doing this, please create completely blank files Accounts.txt and Messages.txt.It will give a main method error if it is not empty before.

Jorge Barco - Submitted Vocareum workspace

User Class

The User class represents a user in the social media platform, encapsulating the properties and functionalities related to user profiles within the social media platform such as username, password, age, hobby, location, friends, and blocked users.

Methods: 
addFriend(String friendUsername): Adds a friend to the user's friends list. Users who are blocked or already added as friends cannot be added.
removeFriend(String friendUsername): Removes a friend from the user's friends list.
blockUser(String username): Blocks a user, removing them from the user's friends list.
toFileString(): Generates a string representation of the user's profile suitable for storing in the user file.

Testing:
User Creation: Tested the creation of users with various attributes.
Friend Management: Tested adding, removing, and blocking friends.
String Representation: Verified the correct generation of the string representation for file storage.

Message Class

The Message class defines the structure of a message, including its sender, receiver, and actual message content, encapsulating the properties and functionalities related to messages exchanged between users within the social media platform.

Method:
toMessageFileString(): Generates a string representation of the message suitable for storing in the message file, containing sender, receiver, and message content.

Testing:
Message Creation: Tested the creation of messages using both default and parameterized constructors with various message content, sender, and receiver combinations.
String Representation: Verified the correct generation of the string representation for file storage.

Relationship to Other Classes:
User Class: Interacts with the User class to associate senders and receivers of messages.

PlatformDatabase Class

The PlatformDatabase class serves as the database management system for the social media platform. It handles user data, message storage, and various database operations, managing user data and message storage for the social media platform. It includes methods for reading user and message data from files, user authentication, user management, message handling, and data persistence.

Methods:

readUsers(): Reads user data from the user file and constructs user objects.
createUser(Scanner scanner): Creates a new user and stores it in the database.
storeUser(User newUser): Stores a new user in the user file.
addFriend(String username, String friendUsername): Adds a friend to a user's friend list.
removeFriend(String username, String friendUsername): Removes a friend from a user's friend list.
blockUser(String username, String blockUsername): Blocks a user.
rewriteUserFile(): Rewrites the user file with updated user data.
login(String username, String password): Authenticates user login credentials.
findUserByUsername(String username): Finds a user by their username.

storeMessage(Message newMessage): Stores a new message in the message file.
viewMessages(String username): Displays messages for a user.
deleteMessage(String senderUsername, String receiverUsername, String messageContent): Deletes a message.
readMessages(): Reads message data from the message file and constructs message objects.
viewMessages(String username): Displays messages for a user.
rewriteMessageFile(): Rewrite the message file with updated message data.

Testing:
User Management: Tested user creation, adding/removing friends, and blocking users.
Message Management: Tested message storage, retrieval, and deletion.
Data Persistence: Ensured user and message data was correctly read from and written to files.
User Authentication: Verified user login functionality.

Relation to Other Classes:
User Class: Utilizes user objects for user-related operations and interactions.
Message Class: Utilizes message objects for message-related operations and interactions.

Platform Interface

The Platform interface outlines the functionalities and methods required for managing users, direct messages (DMs), login processes, user permissions, data persistence, and graphical user interface (GUI) interactions within the social media platform.

Methods:
addFriend(User currentUser, User friend): Adds a friend to the current user's friend list.
blockFriend(User currentUser, User friend): Blocks a friend from the current user's friend list.
removeFriend(User currentUser, User friend): Removes a friend from the current user's friend list.
getName(User user): Retrieves the name of a user.
getAge(User user): Retrieves the age of a user.
getLocation(User user): Retrieves the location of a user.
getHobby(User user): Retrieves the hobby of a user.

sendMessage(User sender, User receiver, String message): Sends a direct message from a sender to a receiver.
blockUser(User currentUser, User userToBlock): Blocks a user from sending messages to the current user.
restrictMessage(User currentUser, boolean friendsOnly): Sets the message restriction for the current user.

login(String username, String password): Validates user login credentials.
createUser(String username, String password, String name, int age, String location, String hobby): Creates a new user account with provided details.
hasPermission(User user, String permission): Checks whether a user has specific permission.

saveUserData(): Saves user data to persistent storage.
loadUserData(): Loads previously saved user data from persistent storage.

displayUserProfile(User user): Displays the profile of a user in the GUI.
displayMessage(User sender, User receiver, String message): Displays a message in the GUI between a sender and receiver.

The Platform interface serves as a contract for implementing classes to ensure consistent functionality across different components of the social media platform. Classes implementing this interface will provide the necessary functionality for managing users, messages, login processes, data persistence, and GUI interactions.

MainMethod Class

The MainMethod class serves as the entry point for the social media platform application, handling user interactions and orchestrating functionalities provided by other classes in the project. To run this phase, please use this class.

Methods:
Provides options for users to either login or create a new user account; utilizes the PlatformDatabase class to validate login credentials or create a new user.
Allows users to perform various actions such as adding friends, removing friends, blocking users, sending messages, viewing messages, and deleting messages; interacts with the PlatformDatabase class to execute these actions and manage user data.

Testing:
Login and User Creation: Tested the functionality by providing valid and invalid login credentials, as well as creating new user accounts.
User Actions: Tested each action option provided in the menu by simulating user interactions and verifying the expected outcomes.

Relationship to Other Classes:
User: Interacts with the User class to handle user-related functionalities such as authentication and friend/block management.
Message: Utilizes the Message class to create, store, and manage messages exchanged between users.
PlatformDatabase: Utilizes methods from this class to manage user accounts, messages, and interactions with the database files (Accounts.txt and Messages.txt).
