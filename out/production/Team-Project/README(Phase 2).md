Team Project Phase 2

For this project, all processing should be done client side. Please compile all the classes and run the MainMethod class. Before doing this, please create completely blank files Accounts.txt and Messages.txt.

Jocge Barco - Submitted Vocareum workspace, client side and missed methods
Alston Zhang - server side implementation
Chethan Adusumilli - client side and missed methods
Zhengyang Wang - test cases and readme

Server Class

The Server class in Java is designed to manage client-server communication within a networked application. This class uses TCP/IP sockets to handle incoming connections, process requests, and send responses back to the clients. It is part of a larger system that likely involves handling user and message data for a messaging platform. Tested in ServerTest class.

Once the server is running, it will wait for client connections and handle their requests based on the request type provided:

Request Types 1, 2, 3, 7, and 8 will retrieve and send user names.
Request Types 4, 5, and 6 will retrieve and send messages.
Clients should connect to the server at the specified port (4343) and send their requests in the proper format.

ServerTest Class

The ServerTest class is designed to validate the functionality of the Server class which manages network connections in a Java application. It uses the JUnit testing framework to ensure that the Server can be instantiated and operated without errors. This test suite is crucial for verifying the robustness of the server implementation before deployment in a production environment. A server environment must be set up and listening on the appropriate port (localhost:4343) before running the tests.

Methods:

testServerConstruction(): This test ensures that a Server object is properly instantiated when provided with a valid socket. It will fail if the constructor throws an exception or returns a null reference.

testServerRunMethodDoesNotThrowException(): Checks that the server's run method, when executed in a new thread, does not throw exceptions for a set period. This helps confirm that the server's main operational loop is stable.

Client Class

The Client class facilitates client-side operations for a networked application, handling communication with a server via TCP/IP sockets. It allows users to send various types of requests to the server and receive information such as user lists and messages. This class is part of a broader system that includes user authentication, messaging, and friend management features. Tested in ClientTest class.

Upon running, the client will prompt for the server IP address and port, then provide a menu-driven interface to interact with the server. Users can log in, add friends, send messages, and perform other actions as outlined in the main method's switch-case structure.

ClientTest Class

ClientTest is a comprehensive test suite developed to verify the connectivity and functionality of the Client class in a Java networking application. This suite checks that the client can establish a connection to a server, send requests, and handle both valid and invalid inputs appropriately. A server must be running on localhost:4343 or change the test setup to the appropriate server address and port.

Methods:

testConnectionEstablished(): Confirms that the Client object is non-null post-initialization, indicating a successful connection setup.

testSendValidRequest(): Checks the client's ability to send a valid request (e.g., request type 1) and receive the correct response, which should match the expected result.

testSendInvalidRequest(): Ensures that the client correctly handles invalid request codes by returning a predefined "INVALID" response, and does not throw unnecessary exceptions.
