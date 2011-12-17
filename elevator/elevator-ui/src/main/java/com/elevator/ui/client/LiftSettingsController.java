package com.elevator.ui.client;

import java.util.HashMap;

public class LiftSettingsController {

    private LiftServiceAsync elevatorService;
    private LiftSettingsForm liftSettingsForm;
    private ScreenFlowManager screenFlowManager;
    private final LiftSettingsController.CreateElevatorCallback createElevatorCallback;
    private final HashMap<String,Integer> fieldValues;

    public LiftSettingsController(LiftServiceAsync elevatorService, LiftSettingsForm liftSettingsForm,
                                  ScreenFlowManager screenFlowManager) {
        this.elevatorService = elevatorService;
        this.liftSettingsForm = liftSettingsForm;
        this.screenFlowManager = screenFlowManager;
        createElevatorCallback = new CreateElevatorCallback();
        fieldValues = new HashMap<String, Integer>();
    }

    public LiftSettingsController(LiftServiceAsync elevatorService, ScreenFlowManager screenFlowManager) {
        this(elevatorService, null, screenFlowManager);
    }

    public void sendButtonClicked() {
        if (!parseFieldValues("floorsCount", "delayBetweenFloors", "doorSpeed")) {
            return;
        }

        if (getParsedValue("floorsCount") < 0) {
            liftSettingsForm.negativeInteger();
            return;
        }
        elevatorService.updateLift(getParsedValue("floorsCount"),
                getParsedValue("delayBetweenFloors"),
                getParsedValue("doorSpeed"), createElevatorCallback);
    }

    private Integer getParsedValue(String fieldName) {
        return fieldValues.get(fieldName);
    }

    private boolean parseFieldValues(String... fieldNames) {
        for (String fieldName : fieldNames) {
            if (!parseFieldValue(fieldName)) {
                return false;
            }
        }
        return true;
    }

    private boolean parseFieldValue(String fieldName) {
        try {
            fieldValues.put(fieldName, parseStringField(fieldName));
            return true;
        } catch (NumberFormatException e) {
            liftSettingsForm.invalidInteger(fieldName);
            return false;
        }
    }

    private int parseStringField(String fieldName) {
        return Integer.parseInt(liftSettingsForm.getFieldValue(fieldName));
    }

    public void setLiftSettingsForm(LiftSettingsForm liftSettingsForm) {
        this.liftSettingsForm = liftSettingsForm;
    }

    private class CreateElevatorCallback extends DefaultAsyncCallback<Void> {

        public CreateElevatorCallback() {
            super(LiftSettingsController.this.screenFlowManager);
        }

        public void onSuccess(Void result) {
            liftSettingsForm.liftCreated();
            screenFlowManager.nextScreen(ScreenFlowManager.Form.LIFT_FORM);
        }
    }
}
