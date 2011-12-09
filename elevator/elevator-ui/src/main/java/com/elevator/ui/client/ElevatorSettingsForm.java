package com.elevator.ui.client;

public interface ElevatorSettingsForm {
    String getFloorsCount();

    void elevatorCreated();

    void invalidInteger();

    void negativeInteger();

    void serverCallFailed(Throwable caught);
}
