package ru.geekbrains;

public abstract class Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }
    public abstract boolean go(Cars c);
}