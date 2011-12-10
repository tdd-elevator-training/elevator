package com.elevator.ui.client;

import com.elevator.ui.shared.ElevatorPersistenceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("elevator")
public interface ElevatorService extends RemoteService {
  void createElevator(int floorsCount) throws IllegalArgumentException, ElevatorPersistenceException;
}
