package com.globallogic.training;

public class Lift {
    private int currentFloor;
    private final Door door;

    public Lift(Door door) {
        this.door = door;
    }

    public void pressButton() {
        if (door.isOpen()) {
            return;
        }
        door.open();
    }

    public void gotoFloor(int floor) {
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
