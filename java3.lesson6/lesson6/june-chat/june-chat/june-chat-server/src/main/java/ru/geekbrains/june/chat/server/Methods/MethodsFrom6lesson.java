package ru.geekbrains.june.chat.server.Methods;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class MethodsFrom6lesson {

 /*   public static void main(String[] args) {
        int[] array = new int[]{2,1,2,3,4,1,7};
        int[] newArray;
        newArray = afterLastFour(array);
        System.out.println(Arrays.toString(newArray));
        System.out.println(isOneOrFourInArray(array));
    } */

    public static int[] afterLastFour(int array[]) {
        int[] newArray;
        if (ArrayUtils.lastIndexOf(array, 4) > 0) {
                newArray = ArrayUtils.subarray(array, ArrayUtils.lastIndexOf(array, 4) + 1, ArrayUtils.getLength(array) + 1);
            } else throw new RuntimeException();
        return newArray;
    }
    public boolean isOneOrFourInArray(int array[]) {
            return  (ArrayUtils.contains(array, 1) || ArrayUtils.contains(array, 4));
    }
}
