package com.elevator.ui.shared;

import java.io.Serializable;

public class LiftState implements Serializable {
    private boolean open;
    private int floor;

    public LiftState() {
    }

    public LiftState(int floor, boolean open) {
        this.floor = floor;
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public int getFloor() {
        return floor;
    }
}
