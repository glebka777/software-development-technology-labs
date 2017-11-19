package ru.mirea.trpo.second.labs.lab2.core;

import java.util.ArrayList;
import java.util.List;

class Floor {

    private int level;
    private List<Integer> upDestinations;
    private List<Integer> downDestinations;

    Floor(int level) {
        this.level = level;
        this.upDestinations = new ArrayList<>();
        this.downDestinations = new ArrayList<>();
    }

    public List<Integer> getUpDestinations() {
        return upDestinations;
    }

    public void setUpDestinations(List<Integer> upDestinations) {
        this.upDestinations = upDestinations;
    }

    public List<Integer> getDownDestinations() {
        return downDestinations;
    }

    public void setDownDestinations(List<Integer> downDestinations) {
        this.downDestinations = downDestinations;
    }

    int getLevel() {
        return level;
    }

    void onCall(int destination) {
        if (level < destination)
            upDestinations.add(destination);
        else
            downDestinations.add(destination);
    }

    List<Integer> collectUpDestinations() {
        List<Integer> toReturn = upDestinations;
        upDestinations = new ArrayList<>();
        return toReturn;
    }

    List<Integer> collectDownDestinations() {
        List<Integer> toReturn = downDestinations;
        downDestinations = new ArrayList<>();
        return toReturn;
    }

    boolean isPressedUp() {
        return upDestinations.size() != 0;
    }

    boolean isPressedDown() {
        return downDestinations.size() != 0;
    }

}
