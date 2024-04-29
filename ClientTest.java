import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

/**
 * ClientTest
 * <p>
 * Testing the connection and implementation
 * of the Client class
 *
 * @author Zhengyang Wang, wang6214@purdue.edu
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class ClientTest {

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

    public static class TestCase extends junit.framework.TestCase {
        private Client client;

        @Override
        protected void setUp() throws Exception {
            super.setUp();
            client = new Client("localhost", 4343);
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
            client.close();
        }

        @Test
        public void testConnectionEstablished() {
            assertNotNull("Connection should be established", client);
        }

        @Test
        public void testSendValidRequest() {
            try {
                String response = client.sendRequest(1);
                assertEquals("Expected response for request type 1", "User list response for request 1", response);
            } catch (Exception e) {
                fail("Should not throw any exception: " + e.getMessage());
            }
        }

        @Test
        public void testSendInvalidRequest() {
            try {
                String response = client.sendRequest(999);
                assertEquals("Expected response for invalid request", "INVALID", response);
            } catch (Exception e) {
                fail("Should not throw any exception for invalid requests");
            }
        }
    }
}
