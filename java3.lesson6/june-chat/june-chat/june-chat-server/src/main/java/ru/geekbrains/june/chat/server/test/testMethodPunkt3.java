package ru.geekbrains.june.chat.server.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.geekbrains.june.chat.server.Methods.MethodsFrom6lesson;
import ru.geekbrains.june.chat.server.Methods.*;

public class testMethodPunkt3 {

    private static MethodsFrom6lesson methods;

    @Before
    public void init() {
        methods = new MethodsFrom6lesson();
    }

    @Test
    public void test1() {
        Assert.assertTrue(methods.isOneOrFourInArray(new int[]{1, 5, 4, 6}));
    }

    @Test
    public void test2() {
        Assert.assertTrue(methods.isOneOrFourInArray(new int[]{4, 2, 3, 5}));
    }
    @Test
    public void test3() {
        Assert.assertTrue(methods.isOneOrFourInArray(new int[]{2, 0, 2, 1}));
    }

    @Test
    public void test4() {
        Assert.assertFalse(methods.isOneOrFourInArray(new int[]{2, 0, 2}));
    }
}
