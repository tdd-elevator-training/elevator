package com.elevator.ui.shared;

import java.io.Serializable;

public class LiftSettings implements Serializable {
    private int floorsCount;
    private int delayBetweenFloors;
    private int doorSpeed;
    private int delayAfterOpen;

    //for GWT
    public LiftSettings() {
    }

    public LiftSettings(int floorsCount, int delayBetweenFloors, int doorSpeed, int delayAfterOpen) {
        this.floorsCount = floorsCount;
        this.delayBetweenFloors = delayBetweenFloors;
        this.doorSpeed = doorSpeed;
        this.delayAfterOpen = delayAfterOpen;
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public int getDelayBetweenFloors() {
        return delayBetweenFloors;
    }

    public int getDoorSpeed() {
        return doorSpeed;
    }

    public int getDelayAfterOpen() {
        return delayAfterOpen;
    }
}
