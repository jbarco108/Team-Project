import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;
/**
 * MessageTest
 *
 * Testing the constructors, getters, and setters
 * of the message class
 *
 * @author Zhengyang Wang, wang6214@purdue.edu
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class MessageTest {

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
        private User sender;
        private User receiver;
        private Message message;

        @Before
        public void setUp() {
            try {
                sender = new User("senderUsername",
                        "password",
                        20,
                        "hockey",
                        "West Lafayette");
                receiver = new User("receiverUsername",
                        "password",
                        21,
                        "soccer",
                        "DC");
            } catch (Exception e) {
                System.out.println("User Creation Error");
                e.printStackTrace();
                fail();
            }

            try {
                message = new Message(sender, receiver, "Hello, world!");
            } catch (Exception e) {
                System.out.println("Message Creation Error");
                e.printStackTrace();
                fail();
            }
        }

        @Test
        public void testParameterizedConstructor() {
            assertNotNull("Parameterized constructor should not set sender to null", message.getSender());
            assertNotNull("Parameterized constructor should not set receiver to null", message.getReceiver());
            assertEquals("Message should match the one passed to constructor", "Hello, world!", message.getMessageToBeSent());
            assertTrue("Send should be successful with parameterized constructor", message.sendSuccessful);
        }

        @Test
        public void testDefaultConstructor() {
            Message defaultMsg = new Message();
            assertNull("Default constructor should set sender to null", defaultMsg.getSender());
            assertNull("Default constructor should set receiver to null", defaultMsg.getReceiver());
            assertEquals("Default constructor should set message to invalid string", "%%INVALID_STRING%%", defaultMsg.getMessageToBeSent());
            assertFalse("Send should not be successful with default constructor", defaultMsg.sendSuccessful);
        }

        @Test
        public void testGetters() {
            assertEquals("Getter for message should return correct value", "Hello, world!", message.getMessageToBeSent());
            assertEquals("Getter for sender should return correct value", sender, message.getSender());
            assertEquals("Getter for receiver should return correct value", receiver, message.getReceiver());
        }

        @Test
        public void testSetters() {
            User newSender = new User("newSenderUsername",
                    "password",
                    22,
                    "baseball",
                    "Boston");

            message.setSender(newSender);
            message.setSendSuccessful(false);
            message.setMessageToBeSent("New message content");

            assertEquals("Setter for sender should update correctly", newSender, message.getSender());
            assertEquals("Setter for sendSuccessful should update correctly", false, message.sendSuccessful);
            assertEquals("Setter for message content should update correctly", "New message content", message.getMessageToBeSent());
        }

        @Test
        public void testToMessageFileString() {
            String expectedOutput = "senderUsername,receiverUsername,Hello, world!,";
            assertEquals("toMessageFileString should return a correctly formatted string", expectedOutput, message.toMessageFileString());
        }
    }
}