package ru.geekbrains;

/*
--- практика 3 курс, задание 1 ---
        1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);

        2. Написать метод, который преобразует массив в ArrayList;

        3. Большая задача:
        a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
        b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
        c. Для хранения фруктов внутри коробки можете использовать ArrayList;
        d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
        e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
        f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
        g. Не забываем про метод добавления фрукта в коробку.
        ------------------------------------
*/

import java.util.ArrayList;
import java.util.Arrays;

public class MainApp {

    public static void main(String[] args) {

        // ----------  задача 1
        System.out.println("=== Task 1 ===");
        String[] arrStr = new String[]{"One","Two","Three"};
        System.out.println(" Before : " + Arrays.deepToString(arrStr));
        exchange(arrStr, 0, 1);
        System.out.println(" After :" + Arrays.deepToString(arrStr));
        // ----------

        // ----------  задача 2
        System.out.println("=== Task 2 ===");
        ArrayList<String> arrList = toArrayList(arrStr);
        System.out.println(arrList);
        // ----------

        // ----------  задача 3
        System.out.println("=== Task 3 ===");

        FruitBox<Oranges> orangeBoxFirst = new FruitBox();
        FruitBox<Oranges> orangeBoxSecond = new FruitBox();
        FruitBox<Apples> appleBox = new FruitBox();

        for (int i = 0; i < 3; i++) {
            orangeBoxFirst.add(new Oranges());
        }
        for (int i = 0; i < 6; i++) {
            appleBox.add(new Apples());
        }

        orangeBoxSecond.add(new Oranges());

        System.out.print("Oranges : ");
        orangeBoxFirst.info();
        System.out.print("Apples : ");
        appleBox.info();

        Float orangesWeigth = orangeBoxFirst.getWeight();
        Float applesWeigth = appleBox.getWeight();
        System.out.println("OrangeBox Weigth : " + orangesWeigth);
        System.out.println("ApplesBox Weigth : " + applesWeigth);

        System.out.println("isEqual : " + orangeBoxFirst.compare(appleBox));

        orangeBoxFirst.moveAt(orangeBoxSecond);

        System.out.print("Oranges First: ");
        orangeBoxFirst.info();
        System.out.print("Apples Box: ");
        appleBox.info();
        System.out.print("Oranges Second : ");
        orangeBoxSecond.info();
        // ----------
    }

    public static <T> ArrayList<T> toArrayList(T[] arr) {
        return new ArrayList<T>(Arrays.asList(arr));
    }

    public static void exchange(Object[] arr, int index1, int index2) {
           Object tmp = arr[index1];
           arr[index1] = arr[index2];
           arr[index2] = tmp;
    }
}
