package com.elevator.ui.server;

import com.elevator.ui.client.ElevatorService;
import com.globallogic.training.Lift;
import com.globallogic.training.RealDoor;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ElevatorServiceImpl extends RemoteServiceServlet implements
        ElevatorService {

  public void createElevator(int floorsCount) throws IllegalArgumentException {
  }

    public boolean isElevatorInstalled() {
//        return getElevatorFile().exists();
        return true;
    }

}
