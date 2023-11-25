import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/**
 * @see testRunner
 * 
 *      - Class testRunner is the main class for the tests, it would run all the tests at the specified order, then it would print the final result at the terminal.
 * 
 * @Note result.wasSuccessful() will return true if all the test have passed and false if even on hasn't so in the terminal :
 * 
 *      All tests passed: true -> means that tests were successful
 *      All tests passed: false -> means that tests were not successful
 * 
 * @Note by running the main method of this class you can check the result of all the tests.
 * 
 * @author Amirali Famili
 */
public class testRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(testCardGame.class, testPlayer.class, testCard.class,
                testInputOutput.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getMessage());
        }

        System.out.println("All tests passed: " + result.wasSuccessful());

        System.exit(0);
    }
}
