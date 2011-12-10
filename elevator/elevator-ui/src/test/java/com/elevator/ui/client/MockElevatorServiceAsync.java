package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MockElevatorServiceAsync implements ElevatorServiceAsync {
    public Integer floorsCount;
    public Throwable serverFailure;
    public boolean elevatorExists = true;
    public int fromFloor = -1;

    public void createElevator(int floorsCount, AsyncCallback<Void> callback) {
        this.floorsCount = floorsCount;
        if (serverFailure == null) {
            callback.onSuccess(null);
        } else {
            callback.onFailure(serverFailure);
        }
    }

    public void elevatorExists(AsyncCallback<Boolean> callback) {
        callback.onSuccess(elevatorExists);
    }

    public void call(int fromFloor, AsyncCallback<Void> callback) {
        this.fromFloor = fromFloor;
        callback.onSuccess(null);
    }
}
