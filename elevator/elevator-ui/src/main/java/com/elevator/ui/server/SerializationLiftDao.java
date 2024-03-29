package com.elevator.ui.server;

import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.Lift;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class SerializationLiftDao implements LiftDao {
    private File rootDataFolder;

    public SerializationLiftDao(File rootDataFolder) {
        this.rootDataFolder = rootDataFolder;
    }

    public void store(Lift lift) throws LiftPersistenceException {
        ObjectOutputStream outStream = null;
        try {
            outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(getElevatorFile())));
            outStream.writeObject(lift);
        } catch (IOException e) {
            throw new LiftPersistenceException("Unable to persist elevator to file " + getElevatorFile().getAbsolutePath(), e);
        } finally {
            IOUtils.closeQuietly(outStream);
        }
    }

    public boolean isLiftInstalled() {
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
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public boolean elevatorExists() {
        try {
            loadLift();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
