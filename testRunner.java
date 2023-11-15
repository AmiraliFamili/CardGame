import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;



public class testRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(testCard.class, testPlayer.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.println("All tests passed: " + result.wasSuccessful());
        System.exit(0);
    }
}
