import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {

    public static void runTests(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Object testObject = clazz.getDeclaredConstructor().newInstance();

            int totalTests = 0;
            int passedTests = 0;
            int failedTests = 0;

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    method.invoke(testObject);
                }

                if (method.isAnnotationPresent(Test.class)) {
                    totalTests++;
                    try {
                        method.invoke(testObject);
                        passedTests++;
                    } catch (Throwable e) {
                        System.err.println("Test failed: " + method.getName());
                        e.printStackTrace();
                        failedTests++;
                    }
                }

                if (method.isAnnotationPresent(After.class)) {
                    method.invoke(testObject);
                }
            }

            System.out.println("Total tests: " + totalTests);
            System.out.println("Passed tests: " + passedTests);
            System.out.println("Failed tests: " + failedTests);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        runTests("MyTestClass");
    }
}

