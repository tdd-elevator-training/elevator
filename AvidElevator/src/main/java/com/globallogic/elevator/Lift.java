package com.globallogic.elevator;

public class Lift {
    private int position;

    public Lift(int position) {
        this.position = position;
    }

    public void callTo(int floorNumber) {
        position = floorNumber;
    }

    public int getPosition() {
        return position;
    }
}
