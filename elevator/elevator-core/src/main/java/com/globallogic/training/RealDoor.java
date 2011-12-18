package com.globallogic.training;

import java.io.Serializable;

public class RealDoor implements Door, Serializable {
    private static final long serialVersionUID = -3923648125471098464L;
    private boolean open;
    private int doorSpeedMilliseconds;

    public void open(int floor) {
        open = true;
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(doorSpeedMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void close() {
        open = false;
        sleep();
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public void setDoorSpeed(int doorSpeedMilliseconds) {
        this.doorSpeedMilliseconds = doorSpeedMilliseconds;
    }

    @Override
    public int getDoorSpeed() {
        return doorSpeedMilliseconds;
    }
}
