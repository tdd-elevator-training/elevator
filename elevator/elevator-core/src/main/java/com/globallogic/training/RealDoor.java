package com.globallogic.training;

import java.io.Serializable;

public class RealDoor implements Door, Serializable {
    private static final long serialVersionUID = -3923648125471098464L;
    private boolean open;
    private int doorSpeedMilliseconds;

    public void open(int floor) {
        sleep();
        open = true;
    }

    private void sleep() {
        try {
            Thread.sleep(doorSpeedMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void close() {
        sleep();
        open = false;
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
