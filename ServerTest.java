import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import java.net.Socket;

import static org.junit.Assert.*;
/**
 * ServerTest
 *
 * Testing the Exception and Construction
 * of the Server class
 *
 * @author Zhengyang Wang, wang6214@purdue.edu
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class ServerTest {

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

        @Test
        public void testServerConstruction() {
            try (Socket testSocket = new Socket("localhost", 4343)) {
                Server server = new Server(testSocket);
                assertNotNull("Server instance should not be null", server);
            } catch (Exception e) {
                fail("Construction should not fail. Caught an exception: " + e.getMessage());
            }
        }

        @Test
        public void testServerRunMethodDoesNotThrowException() {
            try (Socket testSocket = new Socket("localhost", 4343)) {
                Server server = new Server(testSocket);
                Thread serverThread = new Thread(server);
                serverThread.start();

                Thread.sleep(2000);

                assertTrue("Server thread should be alive", serverThread.isAlive());
                serverThread.interrupt();
            } catch (Exception e) {
                fail("Running should not throw any exception. Caught: " + e.getMessage());
            }
        }
    }
}