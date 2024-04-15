import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * PlatformDatabaseTest
 *
 * Testing the methods
 * of the PlatformDatabase class
 *
 * @author Zhengyang Wang, wang6214@purdue.edu
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class PlatformDatabaseTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        private final static String USER_FILE = "TestAccounts.txt";
        private final static String MESSAGE_FILE = "TestMessage.txt";
        private PlatformDatabase db;

        @Before
        public void setUp() throws IOException {
            db = new PlatformDatabase(USER_FILE, MESSAGE_FILE);
            prepareTestData();
            System.setOut(new PrintStream(outContent));
        }

        @After
        public void restoreStreams() {
            System.setOut(originalOutput);
        }

        private void prepareTestData() throws IOException {
            try (PrintWriter userWriter = new PrintWriter(new FileWriter(USER_FILE));
                 PrintWriter messageWriter = new PrintWriter(new FileWriter(MESSAGE_FILE))) {
                userWriter.println("username,password,20,hockey,West Lafayette,friends:friend1;friend2,blocked:block1");
                messageWriter.println("username,username,Hello World!");
            }
        }

        @Test
        public void testReadUsers() {
            db.readUsers();
            assertEquals("Should have one user", 1, db.getUsers().size());
        }

        @Test
        public void testReadMessages() {
            db.readMessages();
            assertEquals("Should have one message", 1, db.getMessages().size() - 1);
        }

        @Test
        public void testLogin() {
            User user = db.login("username", "password");
            Assert.assertNotNull("User should log in successfully", user);
        }

        @Test
        public void testFindUserByUsername() {
            User user = db.findUserByUsername("username");
            Assert.assertNotNull("Should find the user", user);
        }

        @Test
        public void testRewriteUserFile() throws IOException {
            db.rewriteUserFile();
            Assert.assertTrue("User file should exist", new File(USER_FILE).exists());
        }

        @Test
        public void testRewriteMessageFile() {
            db.readMessages();
            Assert.assertTrue("Message file should exist", new File(MESSAGE_FILE).exists());
        }

        @Test
        public void testAddFriend() throws IOException {
            User user = new User("username2", "password2", 21, "soccer", "DC");
            db.getUsers().add(user);
            db.addFriend("username2", "username");
            assertTrue(user.getFriends().contains("username"), "User should have 'username' added as a friend");
            db.rewriteUserFile();
        }

        @Test
        public void testRemoveFriend() throws IOException {
            User user = new User("username3", "password3", 22, "baseball", "Boston");
            user.addFriend("username");
            db.getUsers().add(user);
            db.removeFriend("username3", "username");
            assertFalse(user.getFriends().contains("username"), "User should have 'username' removed from friends");
            db.rewriteUserFile();
        }

        @Test
        public void testBlockUser() throws IOException {
            User user = new User("username4", "password4", 23, "basketball", "Chicago");
            db.getUsers().add(user);
            db.blockUser("username4", "username");
            assertTrue(user.getBlockedUsers().contains("username"), "User should have 'username' added to blocked users");
            db.rewriteUserFile();
        }

        @Test
        public void testViewMessages() throws IOException {
            PrintWriter writer = new PrintWriter(new FileWriter(MESSAGE_FILE, true));
            writer.println("username,username,Hello!");
            writer.close();

            db.readMessages();
            final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            db.viewMessages("username");

            Assert.assertTrue("Output should contain the message", outContent.toString().contains("Hello!"));
        }

        @Test
        public void testDeleteMessage() throws IOException {
            PrintWriter writer = new PrintWriter(new FileWriter(MESSAGE_FILE));
            writer.println("username,username,Hello!");
            writer.close();

            db.readMessages();
            db.deleteMessage("username", "username", "Hello!");

            BufferedReader reader = new BufferedReader(new FileReader(MESSAGE_FILE));
            String line;
            boolean isDeleted = true;
            while ((line = reader.readLine()) != null) {
                if (line.contains("username,username,Hello!")) {
                    isDeleted = false;
                    break;
                }
            }
            reader.close();

            Assert.assertTrue("Message should be deleted from the file", isDeleted);
        }
    }
}
