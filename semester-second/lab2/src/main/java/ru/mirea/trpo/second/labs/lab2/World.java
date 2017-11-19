package ru.mirea.trpo.second.labs.lab2;

public class World {

    private static int time = 1;

    public static int getTime() {
        return time;
    }

    static void increaseTime() {
        time++;
    }
}