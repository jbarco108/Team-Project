import java.io.*;
import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Summer 2022</p>
 *
 * @author Purdue CS
 * @version June 13, 2022
 */
@RunWith(Enclosed.class)
public class RunLocalTest {
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

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Summer 2022</p>
     *
     * @author Purdue CS
     * @version June 13, 2022
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000)
        public void testAmusement() {
            try {
                PlatformDatabase platform = new PlatformDatabase("Accounts.txt", "Messages.txt");
            } catch (Exception e) {
                System.out.println("Platform Database Creation error");
                e.printStackTrace();
                fail();
            }

            try {
                User a = new User("username", "password", 20, "hockey", "West Lafayette");
                User b = new User("username2", "password2", 21, "soccer", "DC");
                User c = new User("username3", "password3", 22, "baseball", "boston");
            } catch (Exception e) {
                System.out.println("User Creation error");
                e.printStackTrace();
                fail();
            }

            try {
                User a = new User("username", "password", 20, "hockey", "West Lafayette");
                User b = new User("username2", "password2", 21, "soccer", "DC");
                User c = new User("username3", "password3", 22, "baseball", "boston");
                a.addFriend("b");
                if (a.getAge() != 20) {
                    System.out.println("Expected: 20 actual: " + a.getAge());
                    fail();
                }
                if (!a.getFriends().get(0).equals("b")) {
                    System.out.println("Expected: b actual: " + a.getFriends().get(0));
                    fail();
                }
                if (!a.getUsername().equals("username")) {
                    System.out.println("Expected: username actual: " + a.getUsername());
                    fail();
                }
                if (!a.getHobby().equals("hockey")) {
                    System.out.println("Expected: hockey actual: " + a.getHobby());
                    fail();
                }
                if (a.getLocation() != "West Lafayette") {
                    System.out.println("Expected: West Lafayette actual: " + a.getLocation());
                    fail();
                }
                if (a.getPassword() != "password") {
                    System.out.println("Expected: password actual: " + a.getPassword());
                    fail();
                }
                a.blockUser("b");
                if (!a.getBlockedUsers().get(0).equals("b")) {
                    System.out.println("Blocked User Failure");
                    fail();
                }

                try {
                    Message m = new Message(a,b,"hello");
                    if (!m.getReceiver().getUsername().equals("username2"))
                        fail("Receiver failure");
                    if (!m.getSender().getUsername().equals("username"))
                        fail("sender failure");
                    if (!m.getMessageToBeSent().equals("hello"))
                        fail("Message failure");
                } catch (Exception e) {
                    System.out.println("Message Creation Failure");
                    e.printStackTrace();
                    fail();
                }
            } catch (Exception e) {
                System.out.println("User Creation error");
                e.printStackTrace();
                fail();
            }
            try {
                String input = "2" + System.lineSeparator() + "username" + System.lineSeparator() + "password" + System.lineSeparator() + "3" +
                        System.lineSeparator() + "hockey" + System.lineSeparator() + "wl" + System.lineSeparator() + "7" + System.lineSeparator();
                receiveInput(input);
                String[] arguments = new String[] {"123"};
                PlatformDatabase.main(arguments);

                String input2 = "2" + System.lineSeparator() + "username2" + System.lineSeparator() + "password2" + System.lineSeparator() + "4" +
                        System.lineSeparator() + "soccer" + System.lineSeparator() + "lw" + System.lineSeparator() + "1" + System.lineSeparator() + "username" + System.lineSeparator() + "7";
                receiveInput(input2);
                String[] arguments2 = new String[] {"123"};
                PlatformDatabase.main(arguments2);

                BufferedReader br = new BufferedReader(new FileReader("Accounts.txt"));
                if (!br.readLine().equals("username,password,3,hockey,wl,friends:username2,blocked:"))
                    fail("Line 1 output does not match on accounts.txt");
                if (!br.readLine().equals("username2,password2,4,soccer,lw,friends:username,blocked:"))
                    fail("Line 2 output does not match on accounts.txt");
            } catch (Exception e) {
            }

        }
    }

}

