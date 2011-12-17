package com.globallogic.training;

public interface Door {

    void open(int floor);

    void close();

    boolean isOpen();

    void setDoorSpeed(int milliseconds);

    int getDoorSpeed();
}
