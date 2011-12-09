package com.elevator.ui.server;

import com.elevator.ui.client.ElevatorService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ElevatorServiceImpl extends RemoteServiceServlet implements
        ElevatorService {

  public void createElevator(int input) throws IllegalArgumentException {
  }
}
