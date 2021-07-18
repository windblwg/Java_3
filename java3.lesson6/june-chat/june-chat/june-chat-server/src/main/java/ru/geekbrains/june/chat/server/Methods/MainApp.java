package ru.geekbrains.june.chat.server.Methods;

import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {
        MethodsFrom6lesson methods = new MethodsFrom6lesson();
        int[] array = new int[]{2, 1, 2, 3, 4, 1, 7};
        int[] newArray;
        newArray = methods.afterLastFour(array);
        System.out.println(Arrays.toString(newArray));
        System.out.println(methods.isOneOrFourInArray(array));
    }
}
