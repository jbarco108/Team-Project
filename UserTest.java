import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;
/**
 * UserTest
 *
 * Testing the constructors, getters, setters
 * and methods of the user class
 *
 * @author Zhengyang Wang, wang6214@purdue.edu
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class UserTest {

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
        private User user;

        @Before
        public void setUp() {
            try {
                user = new User("username",
                        "password",
                        20,
                        "hockey",
                        "West Lafayette");
            } catch (Exception e) {
                System.out.println("User Creation Error");
                e.printStackTrace();
                fail();
            }
        }

        @Test
        public void testParameterizedConstructor() {
            assertEquals("username", user.getUsername());
            assertEquals("password", user.getPassword());
            assertEquals(20, user.getAge());
            assertEquals("hockey", user.getHobby());
            assertEquals("West Lafayette", user.getLocation());
        }

        @Test
        public void testDefaultConstructor() {
            User defaultUser = new User();
            assertEquals("defaultUsername", defaultUser.getUsername());
            assertEquals("defaultPassword", defaultUser.getPassword());
            assertEquals(999, defaultUser.getAge());
            assertEquals("defaultHobby", defaultUser.getHobby());
            assertEquals("defaultLocation", defaultUser.getLocation());
        }

        @Test
        public void testGetters() {
            assertEquals("username", user.getUsername());
            assertEquals("password", user.getPassword());
            assertEquals(20, user.getAge());
            assertEquals("hockey", user.getHobby());
            assertEquals("West Lafayette", user.getLocation());
        }

        @Test
        public void testSetters() {
            user.setUsername("username2");
            user.setPassword("newpassword");
            user.setAge(21);
            user.setHobby("soccer");
            user.setLocation("DC");

            assertEquals("username2", user.getUsername());
            assertEquals("newpassword", user.getPassword());
            assertEquals(21, user.getAge());
            assertEquals("soccer", user.getHobby());
            assertEquals("DC", user.getLocation());
        }

        @Test
        public void testAddAndRemoveFriend() {
            user.addFriend("friend1");
            assertTrue(user.getFriends().contains("friend1"));

            user.removeFriend("friend1");
            assertFalse(user.getFriends().contains("friend1"));
        }

        @Test
        public void testBlockUser() {
            user.addFriend("friend2");
            user.blockUser("friend2");
            assertTrue(user.getBlockedUsers().contains("friend2"));
            assertFalse(user.getFriends().contains("friend2"));

            user.addFriend("friend2");
            assertFalse(user.getFriends().contains("friend2"));
        }

        @Test
        public void testToFileString() {
            user.addFriend("friend1");
            user.addFriend("friend2");
            user.blockUser("enemy1");

            String expected = "username,password,20,hockey,West Lafayette,friends:friend1;friend2,blocked:enemy1";
            assertEquals(expected, user.toFileString());
        }
    }
}