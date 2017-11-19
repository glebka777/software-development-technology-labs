package ru.mirea.trpo.second.labs.lab2.core;

import org.apache.log4j.Logger;
import ru.mirea.trpo.second.labs.lab2.World;
import ru.mirea.trpo.second.labs.lab2.core.util.Movement;

import java.util.ArrayList;
import java.util.List;

public class House {

    private static final Logger logger = Logger.getLogger(House.class);

    private Elevator elevator;
    private List<Floor> floors;

    private int currentFloor;

    public House(int numOfFloors) {
        elevator = new Elevator();
        floors = new ArrayList<>();
        floors.add(new Floor(-1));
        for (int i = 1; i <= numOfFloors; i++) {
            floors.add(new Floor(i));
        }
        currentFloor = 1;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void callElevator(int srcLevel, int destination) {
        Floor floor = this.floors.get(srcLevel);
        floor.onCall(destination);
    }

    public void processElevator() {
        if (elevator.areDoorsOpened()) {
            elevator.closeDoors();
            return;
        }
        Floor floor = floors.get(currentFloor);
        boolean doorsOpenedForExit = false;
        if (elevator.isCall(currentFloor)) {
            logger.info("Elevator handling call. Floor: [" + currentFloor + "] | Time: [" + World.getTime() + "]");
            doorsOpenedForExit = true;
        }
        boolean grabbed = false;
        Movement movement = elevator.getNextMovement();
        logger.debug("Moving " + movement.toString() + ". Doors are open: " + floor.isPressedUp() + ". Floor: " + currentFloor);
        switch (movement) {
            case UP: {
                if (floor.isPressedUp()) {
                    grabPeople(
                            floor.collectUpDestinations(),
                            currentFloor
                    );
                    grabbed = true;
                }
                break;
            }
            case DOWN: {
                if (floor.isPressedDown()) {
                    grabPeople(
                            floor.collectDownDestinations(),
                            currentFloor
                    );
                    grabbed = true;
                }
                break;
            }
            case NONE: {
                if (floor.isPressedUp() || floor.isPressedDown()) {
                    List<Integer> destinations = new ArrayList<>();
                    destinations.addAll(floor.collectUpDestinations());
                    destinations.addAll(floor.collectDownDestinations());
                    grabPeople(destinations, currentFloor);
                    grabbed = true;
                }
                if (elevator.getNextMovement() == Movement.NONE) {
                    floors.forEach(it -> {
                        if (it.isPressedDown() || it.isPressedUp())
                            elevator.addTarget(it.getLevel());
                    });
                }
                break;
            }
        }
        if (grabbed || doorsOpenedForExit) {
            elevator.openDoors();
            return;
        }
        currentFloor = elevator.move();
        logger.info("Moved to floor: " + currentFloor);
    }

    private void grabPeople(List<Integer> destinations, int floor) {
        destinations.forEach(elevator::addTarget);
        logger.info("Grabbing people. Floor: [" + floor + "] | Time: [" + World.getTime() + "]");
    }

}


//    grabbing people on: 1
//        moved to: 8
//        grabbing people on: 8
//        moved to: 10
//        moved to: 9
//        grabbing people on: 9
//        moved to: 2

//    public static void main(String[] args) {
//        House house = new House(11);
//        house.callElevator(1, 8);
//        for (int i = 0; i < 20; i++) {
//            house.processElevator();
//        }
//        house.callElevator(8, 10);
//        house.callElevator(9, 2);
//        for (int i = 0; i < 40; i++) {
//            house.processElevator();
//        }
//    }