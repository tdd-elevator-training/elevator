package com.elevator.ui.client;

import com.elevator.ui.shared.LiftPersistenceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("elevator")
public interface LiftService extends RemoteService {
    void createLift(int floorsCount) throws IllegalArgumentException, LiftPersistenceException;

    boolean liftExists();

    void call(int fromFloor);
}
