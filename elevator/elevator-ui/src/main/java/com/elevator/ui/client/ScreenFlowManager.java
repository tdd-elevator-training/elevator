package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ScreenFlowManager {

    private String currentScreen = "installQuestion";
    private ElevatorServiceAsync elevatorService;

    public ScreenFlowManager(ElevatorServiceAsync elevatorService) {
        this.elevatorService = elevatorService;
    }

    public String getNextScreen() {
        return currentScreen;
    }

    public void nextScreen(String screenName) {
        currentScreen = screenName;
    }

    public void selectStartScreen() {
        elevatorService.elevatorExists(new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(Boolean result) {
                currentScreen = "liftForm";
            }
        });
    }
}
