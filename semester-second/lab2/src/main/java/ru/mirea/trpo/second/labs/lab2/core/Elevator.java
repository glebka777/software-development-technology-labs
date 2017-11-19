package ru.mirea.trpo.second.labs.lab2.core;

import org.apache.log4j.Logger;
import ru.mirea.trpo.second.labs.lab2.core.util.Movement;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@SuppressWarnings("ComparatorCombinators")
public class Elevator {

    private static final Logger logger = Logger.getLogger(Elevator.class);

    private boolean doorsOpened;
    private int currentFloor;
    private Queue<Integer> targets;

    Elevator() {
        currentFloor = 1;
        targets = new LinkedList<>();
        doorsOpened = false;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public boolean areDoorsOpened() {
        return doorsOpened;
    }

    int move() {
        int nextFloor;
        Movement movement = getNextMovement();
        if (movement == Movement.UP)
            nextFloor = moveUp();
        else if (movement == Movement.DOWN)
            nextFloor = moveDown();
        else
            nextFloor = currentFloor;
        return nextFloor;
    }

    boolean isCall(int possibleTarget) {
        if (targets.size() > 0 && possibleTarget == targets.peek()) {
            targets.poll();
            return true;
        }
        else return false;
    }

    void openDoors() {
        doorsOpened = true;
        logger.debug("Opening doors. Floor: " + currentFloor);
    }

    void closeDoors() {
        doorsOpened = false;
        logger.debug("Closing doors. Floor: " + currentFloor);
    }

    Movement getNextMovement() {
        if (targets.peek() == null || currentFloor == targets.peek())
            return Movement.NONE;
        else if (currentFloor > targets.peek())
            return Movement.DOWN;
        else return Movement.UP;
    }

    void addTarget(int newTarget) {
        if (targets.contains(newTarget)) return;
        targets.add(newTarget);
        List<Integer> least = targets.stream()
                .filter(it -> it < currentFloor)
                .collect(Collectors.toList());
        List<Integer> most = targets.stream()
                .filter(it -> it > currentFloor)
                .collect(Collectors.toList());
        Movement movement = getNextMovement();
        most.sort((a, b) -> a - b);
        least.sort((a, b) -> b - a);
        targets.clear();
        if (movement == Movement.UP) {
            targets.addAll(most);
            targets.addAll(least);
        }
        else if (movement == Movement.DOWN) {
            targets.addAll(least);
            targets.addAll(most);
        }
        else if (movement == Movement.NONE) {
            if (least.contains(newTarget)) {
                targets.addAll(most);
                targets.addAll(least);
            }
            else {
                targets.addAll(least);
                targets.addAll(most);
            }
        }
//        System.out.println(targets);
    }

    private int moveUp() {
        return ++currentFloor;
    }

    private int moveDown() {
        return --currentFloor;
    }

}