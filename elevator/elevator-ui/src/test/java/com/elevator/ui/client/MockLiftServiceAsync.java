package com.elevator.ui.client;

import com.elevator.ui.shared.LiftSettings;
import com.elevator.ui.shared.LiftState;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MockLiftServiceAsync implements LiftServiceAsync {
    public Integer floorsCount;
    public int delayBetweenFloors;
    public int doorSpeed;
    public Throwable serverFailure;
    public boolean liftExists = true;
    public int fromFloor = -1;
    public int movingTo;
    public LiftSettings liftSettings;
    public int delayAfterOpen;

    public void updateLift(int floorsCount, int delayBetweenFloors, int doorSpeed, int delayAfterOpen,
                           AsyncCallback<Void> callback) {
        this.floorsCount = floorsCount;
        this.delayBetweenFloors = delayBetweenFloors;
        this.doorSpeed = doorSpeed;
        this.delayAfterOpen = delayAfterOpen;
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

    public void getLiftSettings(AsyncCallback<LiftSettings> callback) {
        callback.onSuccess(liftSettings);
    }

    public void call(int fromFloor, AsyncCallback<Void> callback) {
        this.fromFloor = fromFloor;
        callback.onSuccess(null);
    }

}
