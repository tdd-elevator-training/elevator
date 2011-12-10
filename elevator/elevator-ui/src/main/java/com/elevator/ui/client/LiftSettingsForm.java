package com.elevator.ui.client;

public interface LiftSettingsForm {
    String getFloorsCount();

    void liftCreated();

    void invalidInteger();

    void negativeInteger();

}
