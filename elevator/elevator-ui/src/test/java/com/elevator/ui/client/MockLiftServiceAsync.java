package com.elevator.ui.client;

import com.elevator.ui.shared.LiftState;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MockLiftServiceAsync implements LiftServiceAsync {
    public Integer floorsCount;
    public Throwable serverFailure;
    public boolean liftExists = true;
    public int fromFloor = -1;
    public int movingTo;

    public void updateLift(int floorsCount, int delayBetweenFloors, int doorSpeed, AsyncCallback<Void> callback) {
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

    public void moveTo(int movingTo, AsyncCallback<Void> callback) {
        this.movingTo = movingTo;
        callback.onSuccess(null);
    }

    public void getLiftState(AsyncCallback<LiftState> callback) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void call(int fromFloor, AsyncCallback<Void> callback) {
        this.fromFloor = fromFloor;
        callback.onSuccess(null);
    }

}
