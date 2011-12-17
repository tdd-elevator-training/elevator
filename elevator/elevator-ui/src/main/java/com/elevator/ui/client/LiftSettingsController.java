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
        try {
            int floorsCount = Integer.parseInt(liftSettingsForm.getFloorsCount());
            if (floorsCount < 0) {
                liftSettingsForm.negativeInteger();
                return;
            }

            elevatorService.updateLift(floorsCount, -1, -1, createElevatorCallback);
        } catch (NumberFormatException e) {
            liftSettingsForm.invalidInteger();
        }
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
