package com.globallogic.training;

public class Lift {
    private int currentFloor;
    private final Door door;
    private int floorsCount;

    public Lift(Door door) {
        this.door = door;
    }

    public void pressButton() {
        if (door.isOpen()) {
            return;
        }
        door.open();
    }

    public void gotoFloor(int floor) throws ElevatorException {
        if (floor > 99) {
            throw new ElevatorException(99, currentFloor);
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
