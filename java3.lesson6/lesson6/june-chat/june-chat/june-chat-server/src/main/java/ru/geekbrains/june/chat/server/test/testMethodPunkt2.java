package ru.geekbrains.june.chat.server.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.geekbrains.june.chat.server.Methods.*;

//@RunWith(Parameterized.class)

public class testMethodPunkt2 {
  /*  @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new int[][][]{
                {{1, 5, 4, 6, 8}, {6, 8}},
               {{4, 3, 1, 5}, {3, 1, 5}},
                {{1, 1, 5, 4}, {}}});
    }

    private int array[];
    private int result[];

    public void testMethodPunkt2(int array[], int result[]) {
        this.array = array;
        this.result = result;
    }   */

    private static MethodsFrom6lesson methods;

    @Before
    public void init() {
        methods = new MethodsFrom6lesson();
    }

    @Test
    public void testOne() {
        Assert.assertArrayEquals(new int[]{6, 8}, methods.afterLastFour(new int[]{1, 5, 4, 6, 8}));
    }

    @Test(expected = RuntimeException.class)
    public void testTwo() {
        Assert.assertArrayEquals(new int[]{}, methods.afterLastFour(new int[]{1, 5, 6, 8}));
    }

    @Test
    public void testThree() {
        Assert.assertArrayEquals(new int[]{1, 0}, methods.afterLastFour(new int[]{1, 4, 5, 4, 1, 0}));
    }

    @Test
    public void testFour() {
        Assert.assertArrayEquals(new int[]{}, methods.afterLastFour(new int[]{1, 5, 4}));
    }

}