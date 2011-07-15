package com.globallogic.training;

public class Lift {
    private int currentFloor;
    private final Door door;
    private int floorsCount;


    public Lift(int maxFloorcount, Door door) {
        this.floorsCount = maxFloorcount;
        this.door = door;
    }

    public void pressButton() {
        if (door.isOpen()) {
            return;
        }
        door.open();
    }

    public void gotoFloor(int floor) throws ElevatorException {
        if (floor < 0) {
            throw new ElevatorException(floor, currentFloor);
        }

        if (floor > floorsCount) {
            throw new ElevatorException(floor, currentFloor);
        }
        if (floor == currentFloor) {
            return;
        }
        door.close();
        this.currentFloor = floor;
        door.open();
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

}
