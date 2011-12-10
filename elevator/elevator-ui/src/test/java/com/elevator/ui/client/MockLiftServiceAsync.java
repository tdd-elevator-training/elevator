package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MockLiftServiceAsync implements LiftServiceAsync {
    public Integer floorsCount;
    public Throwable serverFailure;
    public boolean liftExists = true;
    public int fromFloor = -1;

    public void createLift(int floorsCount, AsyncCallback<Void> callback) {
        this.floorsCount = floorsCount;
        if (serverFailure == null) {
            callback.onSuccess(null);
        } else {
            callback.onFailure(serverFailure);
        }
    }

    public void liftExists(AsyncCallback<Boolean> callback) {
        callback.onSuccess(liftExists);
    }

    public void getFloorsCount(AsyncCallback<Integer> callback) {
        callback.onSuccess(floorsCount);
    }

    public void call(int fromFloor, AsyncCallback<Void> callback) {
        this.fromFloor = fromFloor;
        callback.onSuccess(null);
    }
}
