package com.globallogic.training;

public class ElevatorException extends Exception {

    private int selectedFloor;
    private int currentFloor;

    public ElevatorException(int selectedFloor, int currentFloor) {
        this.selectedFloor = selectedFloor;
        this.currentFloor = currentFloor;
    }

    public int getSelectedFloor() {
        return selectedFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}
