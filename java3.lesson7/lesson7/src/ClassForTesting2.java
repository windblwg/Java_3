import test.AfterSuite;
import test.BeforeSuite;
import test.Test;

public class ClassForTesting2 {

    @Test(priority = 1)
    public void testMethod1() {
        System.out.println("Test method priority = 1");
    }

    @Test(priority = 5)
    public void testMethod2() {
        System.out.println("Test method priority = 5");
    }

    @Test(priority = 3)
    public void testMethod3() {
        System.out.println("Test method priority = 3");
    }

    @Test(priority = 2)
    private void testMethod4() {
        System.out.println("Test method priority = 2");
    }

    @Test(priority=3)
    public void testMethod5() {
        System.out.println("Test method priority = 3");
    }


}
