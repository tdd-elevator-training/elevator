package com.elevator.ui.client;

import com.elevator.ui.shared.LiftSettings;

import java.util.HashMap;

import static com.elevator.ui.client.LiftSettingsForm.FieldName.*;

public class LiftSettingsController implements FormController {

    private LiftServiceAsync service;
    private LiftSettingsForm liftSettingsForm;
    private ScreenFlowManager screenFlowManager;
    private final LiftSettingsController.CreateElevatorCallback createElevatorCallback;
    private final HashMap<LiftSettingsForm.FieldName,Integer> fieldValues;

    public LiftSettingsController(LiftServiceAsync elevatorService, LiftSettingsForm liftSettingsForm,
                                  ScreenFlowManager screenFlowManager) {
        this.service = elevatorService;
        this.liftSettingsForm = liftSettingsForm;
        this.screenFlowManager = screenFlowManager;
        createElevatorCallback = new CreateElevatorCallback();
        fieldValues = new HashMap<LiftSettingsForm.FieldName, Integer>();
    }

    public LiftSettingsController(LiftServiceAsync elevatorService, ScreenFlowManager screenFlowManager) {
        this(elevatorService, null, screenFlowManager);
    }

    public void createButtonClicked() {
        if (!parseFieldValues(floorsCount, delayBetweenFloors, doorSpeed, delayAfterOpen)) {
            return;
        }

        if (getParsedValue(floorsCount) < 0) {
            liftSettingsForm.negativeInteger();
            return;
        }
        service.updateLift(getParsedValue(floorsCount),
                getParsedValue(delayBetweenFloors),
                getParsedValue(doorSpeed),
                getParsedValue(delayAfterOpen), createElevatorCallback);
    }

    private Integer getParsedValue(LiftSettingsForm.FieldName fieldName) {
        return fieldValues.get(fieldName);
    }

    private boolean parseFieldValues(LiftSettingsForm.FieldName... fieldNames) {
        for (LiftSettingsForm.FieldName fieldName : fieldNames) {
            if (!parseFieldValue(fieldName)) {
                return false;
            }
        }
        return true;
    }

    private boolean parseFieldValue(LiftSettingsForm.FieldName fieldName) {
        try {
            fieldValues.put(fieldName, parseStringField(fieldName));
            return true;
        } catch (NumberFormatException e) {
            liftSettingsForm.invalidInteger(fieldName);
            return false;
        }
    }

    private int parseStringField(LiftSettingsForm.FieldName fieldName) {
        return Integer.parseInt(liftSettingsForm.getFieldValue(fieldName));
    }

    public void setLiftSettingsForm(LiftSettingsForm liftSettingsForm) {
        this.liftSettingsForm = liftSettingsForm;
    }

    public void onShow() {
        service.getLiftSettings(new DefaultAsyncCallback<LiftSettings>(screenFlowManager) {
            public void onSuccess(LiftSettings result) {
                String floorsCountStr = "1";
                String delayBetweenFloorsStr = "0";
                String doorSpeedStr = "0";
                String delayAfterOpenStr = "0";
                if (result != null) {
                    floorsCountStr = ""+result.getFloorsCount();
                    delayBetweenFloorsStr = "" + result.getDelayBetweenFloors();
                    doorSpeedStr = "" + result.getDoorSpeed();
                    delayAfterOpenStr = "" + result.getDelayAfterOpen();
                }
                liftSettingsForm.setFieldValue(floorsCount, floorsCountStr);
                liftSettingsForm.setFieldValue(delayBetweenFloors, delayBetweenFloorsStr);
                liftSettingsForm.setFieldValue(doorSpeed, doorSpeedStr);
                liftSettingsForm.setFieldValue(delayAfterOpen, delayAfterOpenStr);
            }
        });
    }

    public void onHide() {

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
