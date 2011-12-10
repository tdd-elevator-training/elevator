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
/*
      Lift lift = new Lift(0, floorsCount, new RealDoor());
      ObjectOutputStream outStream = null;
      try {
          outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(getElevatorFile())));
          outStream.writeObject(lift);
      } catch (IOException e) {
          //todo: test it
          throw new RuntimeException(e);
      }finally {
          IOUtils.closeQuietly(outStream);
      }
*/
  }

    private File getElevatorFile() {
        return new File(createElevatorDir(), "lift.data");
    }

    private File createElevatorDir() {
        File elevatorDir = new File(FileUtils.getUserDirectory(), ".elevator");
        elevatorDir.mkdirs();
        return elevatorDir;
    }

    public boolean isElevatorInstalled() {
//        return getElevatorFile().exists();
        return true;
    }

}