package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class StartScreenController {
    private String currentScreen = "installQuestion";
    private ScreenFlowManager screenFlowManager;
    private ElevatorServiceAsync elevatorService;

    public StartScreenController(ScreenFlowManager screenFlowManager, ElevatorServiceAsync elevatorService) {
        this.screenFlowManager = screenFlowManager;
        this.elevatorService = elevatorService;
    }

    public void selectStartScreen() {
        elevatorService.elevatorExists(new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(Boolean result) {
                if (result) {
                    screenFlowManager.nextScreen("liftForm");
                }
            }
        });
    }

}
