package com.elevator.ui.client;

public interface LiftSettingsForm {
    void setFieldValue(FieldName fieldName, String value);

    enum FieldName {floorsCount, delayBetweenFloors, doorSpeed}

    void liftCreated();

    void invalidInteger(FieldName fieldName);

    void negativeInteger();

    String getFieldValue(FieldName fieldName);
}
