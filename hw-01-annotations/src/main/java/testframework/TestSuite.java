package testframework;

import testframework.annotations.AfterSuite;
import testframework.annotations.BeforeSuite;
import testframework.annotations.Test;

public class TestSuite {
    @BeforeSuite
    public static void init() {
        System.out.println("init");
    }

    @Test(priority = 2)
    public static void test1() {
        System.out.println("test1");
    }

    @Test(priority = 9)
    public static void test2() {
        System.out.println("test2");
    }

    @Test(priority = 1)
    public static void test3() {
        System.out.println("test3");
    }
    @Test(priority = 1)
    public static void test4() {
        throw new RuntimeException();
    }

    @AfterSuite
    @Deprecated
    public static void after() {
        System.out.println("after");
    }
}
