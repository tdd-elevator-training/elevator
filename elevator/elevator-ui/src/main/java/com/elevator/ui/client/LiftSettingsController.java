package com.elevator.ui.client;

public class LiftSettingsController {

    private LiftServiceAsync elevatorService;
    private LiftSettingsForm liftSettingsForm;
    private ScreenFlowManager screenFlowManager;
    private final LiftSettingsController.CreateElevatorCallback createElevatorCallback;

    public LiftSettingsController(LiftServiceAsync elevatorService, LiftSettingsForm liftSettingsForm,
                                  ScreenFlowManager screenFlowManager) {
        this.elevatorService = elevatorService;
        this.liftSettingsForm = liftSettingsForm;
        this.screenFlowManager = screenFlowManager;
        createElevatorCallback = new CreateElevatorCallback();
    }

    public LiftSettingsController(LiftServiceAsync elevatorService, ScreenFlowManager screenFlowManager) {
        this(elevatorService, null, screenFlowManager);
    }

    public void sendButtonClicked() {
        int floorsCount = 0;
        int delayBetweenFloors = 0;
        int doorSpeed = 0;
        try {
            floorsCount = Integer.parseInt(liftSettingsForm.getFieldValue("floorsCount"));
            if (floorsCount < 0) {
                liftSettingsForm.negativeInteger();
                return;
            }
        } catch (NumberFormatException e) {
            liftSettingsForm.invalidInteger("floorsCount");
            return;
        }
        try {
            delayBetweenFloors = Integer.parseInt(liftSettingsForm.getFieldValue("delayBetweenFloors"));
        } catch (NumberFormatException e) {
            liftSettingsForm.invalidInteger("delayBetweenFloors");
            return;
        }
        try {
            doorSpeed = Integer.parseInt(liftSettingsForm.getFieldValue("doorSpeed"));
        } catch (NumberFormatException e) {
            liftSettingsForm.invalidInteger("doorSpeed");
            return;

        }
        elevatorService.updateLift(floorsCount, delayBetweenFloors, doorSpeed, createElevatorCallback);
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
