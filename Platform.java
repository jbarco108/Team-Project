public interface Platform{

    // USER RELATED METHODS
    void addFriend(User currentUser, User friend);
    void blockFriend(User currentUser, User friend);
    void removeFriend(User currentUser, User friend);
    String getName(User user);
    int getAge(User user);
    String getLocation(User user);
    String getHobby(User user);

    // DM RELATED METHODS
    void sendMessage(User sender, User receiver, String message);
    void blockUser(User currentUser, User userToBlock);
    void restrictMessage(User currentUser, boolean friendsOnly);

    // LOGIN AND USER PERMISSIONS RELATED METHODS
    boolean login(String username, String password);
    boolean createUser(String username, String password, String name, int age, String location, String hobby);
    boolean hasPermission(User user, String permission);

    // DATA PERSISTENCE RELATED METHODS
    void saveUserData();
    void loadUserData();

    // ADDITIONAL METHODS FOR GUI INTERACTION
    void displayUserProfile(User user);
    void displayMessage(User sender, User receiver, String message);
}
