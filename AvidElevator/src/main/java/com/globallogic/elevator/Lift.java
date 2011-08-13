package com.globallogic.elevator;

public class Lift {
    private int position;
    private boolean isOpen;

    public Lift(int position) {
        this.position = position;
    }

    public void callTo(int floorNumber) {
        isOpen = true;
        position = floorNumber;
    }

    public int getPosition() {
        return position;
    }

    public boolean isOpenDoor() {
        return isOpen;
    }
}
