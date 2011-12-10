package com.elevator.ui.client;

public class StartScreenController {
    private ScreenFlowManager.Form currentScreen = ScreenFlowManager.Form.INSTALL_LIFT_QUESTION;
    private ScreenFlowManager screenFlowManager;
    private LiftServiceAsync elevatorService;
    private final StartScreenController.ElevatorExistsCallback callback;

    public StartScreenController(ScreenFlowManager screenFlowManager, LiftServiceAsync elevatorService) {
        this.screenFlowManager = screenFlowManager;
        this.elevatorService = elevatorService;
        callback = new ElevatorExistsCallback();
    }

    public void selectStartScreen() {
        elevatorService.liftExists(callback);
    }

    private class ElevatorExistsCallback extends DefaultAsyncCallback<Boolean> {

        public ElevatorExistsCallback() {
            super(StartScreenController.this.screenFlowManager);
        }

        public void onSuccess(Boolean result) {
            if (result) {
                currentScreen = ScreenFlowManager.Form.LIFT_FORM;
            }
            screenFlowManager.nextScreen(currentScreen);
        }
    }
}
