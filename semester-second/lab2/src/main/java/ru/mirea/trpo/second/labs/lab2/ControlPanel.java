package ru.mirea.trpo.second.labs.lab2;

import ru.mirea.trpo.second.labs.lab2.core.House;
import ru.mirea.trpo.second.labs.lab2.logs.ElevatorCall;
import ru.mirea.trpo.second.labs.lab2.logs.ElevatorStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ControlPanel {

    private static final int FLOOR_COUNT = 17;
    private static final int MAX_TIME = 40;

    private Map<Integer, List<ElevatorCall>> elevatorCalls;
    private House house;

    ControlPanel(File file) {
        this.elevatorCalls = InputFileReader.read(file);
        this.house = new House(FLOOR_COUNT);
    }

    void go() {
        List<ElevatorStatus> statuses = new ArrayList<>(MAX_TIME);
        int time = World.getTime();
        while (time < MAX_TIME) {
            house.processElevator();
            statuses.add(constructElevatorStatus(time));
            handleCalls(time);
            World.increaseTime();
            time = World.getTime();
        }
        logResult(statuses);
    }

    private void handleCalls(int time) {
        List<ElevatorCall> list;
        if ((list = elevatorCalls.get(time)) != null) {
            list.forEach(call -> house.callElevator(call.getSourceLevel(), call.getDestinationLevel()));
        }
    }

    private ElevatorStatus constructElevatorStatus(int time) {
        int floor = house.getElevator().getCurrentFloor();
        int doors = house.getElevator().areDoorsOpened() ? 1 : 0;
        return new ElevatorStatus(time, floor, doors);
    }

    private void logResult(List<ElevatorStatus> statuses) {
        System.out.println("");
        System.out.println("time level doors");
        statuses.forEach(it -> System.out.println(it.getTime() + " " + it.getLevel() + " " + it.getDoors()));
    }

}
