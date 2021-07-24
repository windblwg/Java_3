package ru.geekbrains;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Cars implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private static CyclicBarrier startBarrier;
    private static CountDownLatch countDownLatchFinish;
    private static CountDownLatch countDownLatchReady;
    private boolean winFlag;
    private static int count=0;

    static {
        countDownLatchFinish = MainApp.countDownLatchFinish;
        countDownLatchReady = MainApp.countDownLatchReady;
        startBarrier = MainApp.startBarrier;
    }

    String getName() {
        return name;
    }
    int getSpeed() {
        return speed;
    }
    Cars(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            countDownLatchReady.countDown();
            System.out.println(this.name + " готов");
            startBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        int j = race.getStages().size()-1;
        if(race.getStages().get(j).go(this) && count==0) {
            System.out.println(this.getName() + " WIN!!");
            count++;
        }

        countDownLatchFinish.countDown();
    }

}