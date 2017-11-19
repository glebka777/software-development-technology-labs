package ru.mirea.trpo.second.labs.lab2.logs;

public class ElevatorCall {

    private int sourceLevel;
    private int destinationLevel;

    @Override
    public String toString() {
        return "ElevatorCall{" +
                "sourceLevel=" + sourceLevel +
                ", destinationLevel=" + destinationLevel +
                '}';
    }

    public ElevatorCall(int sourceLevel, int destinationLevel) {
        this.sourceLevel = sourceLevel;
        this.destinationLevel = destinationLevel;
    }

    public int getSourceLevel() {
        return sourceLevel;
    }

    public int getDestinationLevel() {
        return destinationLevel;
    }

}
