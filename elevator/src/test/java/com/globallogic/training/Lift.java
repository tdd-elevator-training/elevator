package com.globallogic.training;

public class Lift {
    public static final int FLOOR_COUNT = 99;
    private int currentFloor;
    private final Door door;
    private int floorsCount;


    public Lift(int maxFloorcount, Door door) {

        this.door = door;
    }

    public void pressButton() {
        if (door.isOpen()) {
            return;
        }
        door.open();
    }

    public void gotoFloor(int floor) throws ElevatorException {
        if (floor == -1) {
            throw new ElevatorException(2, -1);
        }

        if (floor > FLOOR_COUNT) {
            throw new ElevatorException(FLOOR_COUNT, currentFloor);
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
    public void setFloorsCount(int floorsCount) {
        this.floorsCount = floorsCount;
    }


}
