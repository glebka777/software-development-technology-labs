package ru.mirea.trpo.second.labs.lab2.logs;

public class ElevatorStatus {

    private int time;
    private int level;
    private int doors;

    public ElevatorStatus(int time, int level, int doors) {
        this.time = time;
        this.level = level;
        this.doors = doors;
    }

    public int getTime() {

        return time;
    }

    public int getLevel() {
        return level;
    }

    public int getDoors() {
        return doors;
    }

}
