package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ElevatorSettingsController {

    private ElevatorServiceAsync elevatorService;
    private ElevatorSettingsForm elevatorSettingsForm;

    public ElevatorSettingsController(ElevatorServiceAsync elevatorService, ElevatorSettingsForm elevatorSettingsForm) {
        this.elevatorService = elevatorService;
        this.elevatorSettingsForm = elevatorSettingsForm;
    }

    public void sendButtonClicked() {
        elevatorService.createElevator(Integer.parseInt(elevatorSettingsForm.getFloorsCount()), new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {

            }

            public void onSuccess(Void result) {
                elevatorSettingsForm.elevatorCreated();
            }
        });
    }
}
