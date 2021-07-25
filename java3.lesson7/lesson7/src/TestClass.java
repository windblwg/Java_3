import test.AfterSuite;
import test.BeforeSuite;
import test.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TestClass {
    public static int beforeCount = 0;
    public static int afterCount = 0;

    public static void start(Object...obj){
        int length = obj.length;
        Class[] classNames = new Class[length];

        for(int i=0; i<length; i++) {
            classNames[i] = obj[i].getClass();
        }

        for (int i=0; i<length; i++) {
            if (!isBeforeOrAfter(classNames[i])) {
                throw new RuntimeException();
            }
        }

        Method before = null;
        Method after = null;
        Method[] objMethods = new Method[length];
        ArrayList<Method> testMethods = new ArrayList<>();

        for(int j=0; j<length; j++){
            objMethods = classNames[j].getDeclaredMethods();
            for (Method method : objMethods) {
                if (method.getAnnotation(BeforeSuite.class) != null) {
                    before = method;
                } else if (method.getAnnotation(AfterSuite.class) != null) {
                    after = method;
                } else if (method.getAnnotation(Test.class) != null) {
                    testMethods.add(method);
                }
            }
        }

        testMethods.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()));

        if (before != null) {
            testMethods.add(0, before);
        }

        if (after != null) {
            testMethods.add(after);
        }

        try {
            for (Method testMethod : testMethods) {
                if (Modifier.isPrivate(testMethod.getModifiers())) {
                    testMethod.setAccessible(true);
                }
                testMethod.invoke((Object) testMethod.getDeclaringClass().newInstance());
                }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static boolean isBeforeOrAfter(Class cl) {
        for (Method method : cl.getDeclaredMethods()) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                beforeCount++;
            }
            if (method.getAnnotation(AfterSuite.class) != null) {
                afterCount++;
            }
        }
        return (beforeCount < 2) && (afterCount < 2);
    }
}
