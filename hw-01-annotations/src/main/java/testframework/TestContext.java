package testframework;

import testframework.annotations.AfterSuite;
import testframework.annotations.BeforeSuite;
import testframework.annotations.Test;
import testframework.exception.AnnotationsException;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestContext {
    private final List<Method> beforeSuiteMethods;
    private final List<Method> testMethods;
    private final List<Method> afterSuiteMethods;
    private final Class<?> clazz;

    public TestContext(Class<?> clazz) {
        this.beforeSuiteMethods = new ArrayList<>();
        this.testMethods = new ArrayList<>();
        this.afterSuiteMethods = new ArrayList<>();
        this.clazz = clazz;
    }

    static void fillTextContext(TestContext testClassContext) {
        checkAnnotations(testClassContext.clazz);
        for (Method method : testClassContext.clazz.getMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                testClassContext.beforeSuiteMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testClassContext.testMethods.add(method);
                if (method.getAnnotation(Test.class).priority() < 1 ||
                        method.getAnnotation(Test.class).priority() > 10) {
                    throw new AnnotationsException("Priority may be >= 1 and <= 10");
                }
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                testClassContext.afterSuiteMethods.add(method);
            }
        }
        testClassContext.testMethods.sort(Comparator.comparingInt((Method sortMethod)->
                sortMethod.getAnnotation(Test.class).priority()).reversed());
        if (testClassContext.beforeSuiteMethods.size() > 1 || testClassContext.afterSuiteMethods.size() > 1) {
            throw new AnnotationsException("Annotations BeforeSuite/AfterSuite may be not > 1");
        }
    }
    static private void checkAnnotations(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if ((method.isAnnotationPresent(BeforeSuite.class) && method.isAnnotationPresent(Test.class))
                    || (method.isAnnotationPresent(AfterSuite.class) && method.isAnnotationPresent(Test.class))) {
                throw new AnnotationsException("The @Test and @BeforeSuite/@AfterSuite annotations cannot occur on the same method");
            }
        }
    }

    public List<Method> getBeforeSuiteMethods() {
        return beforeSuiteMethods;
    }

    public List<Method> getTestMethods() {
        return testMethods;
    }

    public List<Method> getAfterSuiteMethods() {
        return afterSuiteMethods;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
