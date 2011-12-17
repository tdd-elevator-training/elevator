package com.elevator.ui.client;

public interface LiftSettingsForm {
    enum FieldName {floorsCount, delayBetweenFloors, doorSpeed}

    void liftCreated();

    void invalidInteger(FieldName fieldNameName);

    void negativeInteger();

    String getFieldValue(FieldName fieldName);
}
