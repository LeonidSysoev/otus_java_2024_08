package testframework;

import java.lang.reflect.InvocationTargetException;

public class App {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TestRunner.run(TestSuite.class);
    }
}
