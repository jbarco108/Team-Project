import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
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
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        System.out.printf("Test Count: %d.\n", result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.printf("Excellent - all local tests ran successfully.\n");
        } else {
            System.out.printf("Tests failed: %d.\n",result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
    /**
     * A framework to run public test cases.
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

        // Each of the correct outputs
        public static final String WELCOME_MESSAGE = "Welcome to My Math Helper!";
        public static final String MAIN_MENU_ONE = "Please Select an Operation";
        public static final String MAIN_MENU_TWO = "1 - Calculate Greatest Common Denominator";
        public static final String MAIN_MENU_THREE = "2 - Perform Prime Factorization";
        public static final String MAIN_MENU_FOUR = "3 - Exit";
        public static final String GCD_NOTIFICATION = "Ready to Calculate Greatest Common Denominator";
        public static final String PF_NOTIFICATION = "Ready to Perform Prime Factorization";
        public static final String INPUT_ONE = "Please Enter an Integer";
        public static final String INPUT_TWO = "Please Enter a Second Integer";
        public static final String GCD_OUTPUT = "The Greatest Common Denominator is ";
        public static final String PF_OUTPUT = "The Prime Factorization is ";
        public static final String EXIT_MESSAGE = "Thank you for using My Math Helper!";
        public static final String INVALID_MENU_SELECTION  = "Invalid selection!";
        public static final String INVALID_INPUT = "Invalid Input - Returning to Main Menu";

        @Test(timeout = 1000)
        public void testExpectedOptionOne() {

            // Set the input
            String input = "1" + System.lineSeparator() + "8" + System.lineSeparator() + "12" +
                    System.lineSeparator() + "3" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + System.lineSeparator() + MAIN_MENU_ONE +
                    System.lineSeparator() + MAIN_MENU_TWO + System.lineSeparator() +
                    MAIN_MENU_THREE + System.lineSeparator() + MAIN_MENU_FOUR + System.lineSeparator() +
                    GCD_NOTIFICATION + System.lineSeparator() + INPUT_ONE + System.lineSeparator() +
                    INPUT_TWO + System.lineSeparator() + GCD_OUTPUT + "4" +
                    System.lineSeparator() + MAIN_MENU_ONE + System.lineSeparator() + MAIN_MENU_TWO +
                    System.lineSeparator() + MAIN_MENU_THREE + System.lineSeparator() + MAIN_MENU_FOUR +
                    System.lineSeparator() + EXIT_MESSAGE;

            // Runs the program with the input values
            receiveInput(input);
            MyMathHelper.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected case for the option 1",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testExpectedOptionTwo() {

            // Set the input
            String input = "2" + System.lineSeparator() + "36" + System.lineSeparator() + "3" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + System.lineSeparator() + MAIN_MENU_ONE +
                    System.lineSeparator() + MAIN_MENU_TWO + System.lineSeparator() +
                    MAIN_MENU_THREE + System.lineSeparator() + MAIN_MENU_FOUR + System.lineSeparator() +
                    PF_NOTIFICATION + System.lineSeparator() + INPUT_ONE + System.lineSeparator() +
                    PF_OUTPUT + "2 x 2 x 3 x 3" +
                    System.lineSeparator() + MAIN_MENU_ONE + System.lineSeparator() + MAIN_MENU_TWO +
                    System.lineSeparator() + MAIN_MENU_THREE + System.lineSeparator() + MAIN_MENU_FOUR +
                    System.lineSeparator() + EXIT_MESSAGE;

            // Runs the program with the input values
            receiveInput(input);
            MyMathHelper.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected case for the option 2",
                    expected.trim(), output.trim());
        }
    }
}