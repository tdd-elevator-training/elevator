package com.elevator.ui.client;

import com.elevator.ui.shared.LiftAlreadyInstalledException;
import com.elevator.ui.shared.LiftNotInstalledException;
import com.elevator.ui.shared.LiftPersistenceException;
import com.elevator.ui.shared.LiftState;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("elevator")
public interface LiftService extends RemoteService {
    void updateLift(int floorsCount, int delayBetweenFloors, int doorSpeed) throws IllegalArgumentException, LiftPersistenceException, LiftAlreadyInstalledException;

    boolean liftExists();

    void call(int fromFloor) throws LiftNotInstalledException;

    int getFloorsCount();

    void moveTo(int floorNumber);

    LiftState getLiftState();
}
