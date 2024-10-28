package testframework;

public class TestStatistic {
    static void printStatistic(TestContext testContext, int failsCounter) {
        System.out.println("Test  results");
        System.out.println("Total test completed: " + testContext.getTestMethods().size());
        System.out.println("Successful: " + (testContext.getTestMethods().size() - failsCounter));
        System.out.println("Failed: " + failsCounter);

    }
}
