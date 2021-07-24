package ru.geekbrains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FruitBox<T extends Fruit> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public FruitBox(T... obj) {
        list = Arrays.asList(obj);
    }

    public FruitBox() {
        list = new ArrayList<T>();
    }

    void add(T obj) {
        list.add(obj);
    }

    void moveAt(FruitBox<T> box) {
        box.getList().addAll(list);
        list.clear();
    }

    void info() {
        if (list.isEmpty()) {
            System.out.println("Empty FruitBox");
        } else {
            System.out.println(list.size() +" in the FruitBox");
        }
    }

    float getWeight() {
        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size() * list.get(0).getWeight();
        }
    }

    boolean compare(FruitBox<? extends Fruit> box) {
        return this.getWeight() == box.getWeight();
    }

}
