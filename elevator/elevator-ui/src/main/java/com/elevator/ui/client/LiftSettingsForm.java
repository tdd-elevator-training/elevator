package com.elevator.ui.client;

public interface LiftSettingsForm {
    String getFloorsCount();

    void liftCreated();

    void invalidInteger(String fieldName);

    void negativeInteger();

    String getDelayBetweenFloors();

    String getDoorSpeed();

    String getFieldValue(String fieldName);
}
