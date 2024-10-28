package testframework;


import java.lang.reflect.InvocationTargetException;

public class TestRunner {

    public static void run(Class<?> testSuiteClass) throws InvocationTargetException, IllegalAccessException {
        TestContext testContext = new TestContext(testSuiteClass);
        TestContext.fillTextContext(testContext);
        TestStatistic.printStatistic(testContext,TestExecutor.executeTest(testContext));

    }

}
