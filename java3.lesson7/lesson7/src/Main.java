import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
       ClassForTesting classForTesting = new ClassForTesting();
       ClassForTesting2 classForTesting2 = new ClassForTesting2();

       TestClass.start(classForTesting, classForTesting2);
    }
}
