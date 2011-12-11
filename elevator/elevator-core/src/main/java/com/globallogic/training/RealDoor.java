package com.globallogic.training;

import com.globallogic.training.Door;

import java.io.Serializable;

public class RealDoor implements Door, Serializable {
    private static final long serialVersionUID = -3923648125471098464L;
    private boolean open;

    public void open(int floor) {
        open = true;
    }

    public void close() {
        open = false;
    }

    public boolean isOpen() {
        return open;
    }
}
