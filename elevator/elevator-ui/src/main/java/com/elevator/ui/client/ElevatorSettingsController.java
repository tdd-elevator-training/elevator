package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ElevatorSettingsController {

    private ElevatorServiceAsync elevatorService;

    public ElevatorSettingsController(ElevatorServiceAsync elevatorService, ElevatorSettingsForm elevatorSettingsForm) {
        this.elevatorService = elevatorService;
    }

    public void sendButtonClicked() {
        elevatorService.createElevator(10, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {

            }

            public void onSuccess(Void result) {
            }
        });
    }
}
