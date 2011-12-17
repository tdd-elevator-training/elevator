package com.elevator.ui.shared;

public class LiftSettings {
    private int floorsCount;
    private int delayBetweenFloors;
    private int doorSpeed;

    //for GWT
    public LiftSettings() {
    }

    public LiftSettings(int floorsCount, int delayBetweenFloors, int doorSpeed) {
        this.floorsCount = floorsCount;
        this.delayBetweenFloors = delayBetweenFloors;
        this.doorSpeed = doorSpeed;
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
}
