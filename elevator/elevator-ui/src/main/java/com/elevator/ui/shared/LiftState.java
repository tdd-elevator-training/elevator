package com.elevator.ui.shared;

import java.io.Serializable;

public class LiftState implements Serializable {
    private boolean open;
    private int doorSpeed;
    private int floor;

    public LiftState() {
    }

    public LiftState(int floor, boolean open, int doorSpeed) {
        this.floor = floor;
        this.open = open;
        this.doorSpeed = doorSpeed;
    }

    public boolean isOpen() {
        return open;
    }

    public int getFloor() {
        return floor;
    }

    public int getDoorSpeed() {
        return doorSpeed;
    }
}
