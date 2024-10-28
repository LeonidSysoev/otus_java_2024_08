package testframework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testframework.exception.AnnotationsException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TestExecutor.class);

    public TestExecutor() {
    }

    static int executeTest(TestContext testContext) throws InvocationTargetException, IllegalAccessException {
        int failsCounter = 0;
        if (testContext.getTestMethods().isEmpty()) {
            throw new AnnotationsException("Tests not found");
        }
        var testObject = ReflectionHelper.instantiate(testContext.getClazz());
        testContext.getBeforeSuiteMethods().get(0).invoke(testObject);
        logger.info("Before {} is done", testContext.getBeforeSuiteMethods().get(0).getName());
        for (Method method : testContext.getTestMethods()) {

            try {
                method.invoke(testObject);
                logger.info("Test {} is done", method.getName());
            } catch (Exception e) {
                logger.info("Test {} not passed", method.getName());
                failsCounter++;
            }
        }
        testContext.getAfterSuiteMethods().get(0).invoke(testObject);
        logger.info("After {} is done", testContext.getAfterSuiteMethods().get(0).getName());
        return failsCounter;
    }


}
