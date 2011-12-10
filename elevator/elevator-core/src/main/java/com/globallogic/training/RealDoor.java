package com.globallogic.training;

import com.globallogic.training.Door;

import java.io.Serializable;

public class RealDoor implements Door, Serializable {
    private static final long serialVersionUID = -3923648125471098464L;

    public void open(int floor) {
    }

    public void close() {
    }

    public boolean isOpen() {
        return false;
    }
}
