package com.elevator.ui.server;

import com.globallogic.training.Lift;
import com.globallogic.training.RealDoor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class SerializationElevatorDao {
    private File rootDataFolder;

    public SerializationElevatorDao(File rootDataFolder) {
        this.rootDataFolder = rootDataFolder;
    }

    public void createElevator(int floorsCount) throws ElevatorPersistenceException {
      Lift lift = new Lift(0, floorsCount, new RealDoor());
      ObjectOutputStream outStream = null;
      try {
          outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(getElevatorFile())));
          outStream.writeObject(lift);
      } catch (IOException e) {
          throw new ElevatorPersistenceException("Unable to persist elevator to file " + getElevatorFile().getAbsolutePath(), e);
      }finally {
          IOUtils.closeQuietly(outStream);
      }
    }

    public boolean isElevatorInstalled() {
        return getElevatorFile().exists();
    }
    private File getElevatorFile() {
        return new File(createElevatorDir(), "lift.data");
    }

    private File createElevatorDir() {
        File elevatorDir = new File(rootDataFolder, ".elevator");
        elevatorDir.mkdirs();
        return elevatorDir;
    }

    public Lift loadLift() {
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(getElevatorFile())));
            return (Lift) inputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
