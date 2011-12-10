package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class StartScreenController {
    private ScreenFlowManager.Form currentScreen = ScreenFlowManager.Form.INSTALL_ELEVATOR_QUESTION;
    private ScreenFlowManager screenFlowManager;
    private ElevatorServiceAsync elevatorService;
    private final StartScreenController.ElevatorExistsCallback callback;

    public StartScreenController(ScreenFlowManager screenFlowManager, ElevatorServiceAsync elevatorService) {
        this.screenFlowManager = screenFlowManager;
        this.elevatorService = elevatorService;
        callback = new ElevatorExistsCallback();
    }

    public void selectStartScreen() {
        elevatorService.elevatorExists(callback);
    }

    private class ElevatorExistsCallback extends DefaultAsyncCallback<Boolean> {

        public ElevatorExistsCallback() {
            super(StartScreenController.this.screenFlowManager);
        }

        public void onSuccess(Boolean result) {
            if (result) {
                currentScreen = ScreenFlowManager.Form.ELEVATOR_FORM;
            }
            screenFlowManager.nextScreen(currentScreen);
        }
    }
}
