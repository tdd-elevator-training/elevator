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
        int floorsCount = 0;
        try {
            floorsCount = Integer.parseInt(elevatorSettingsForm.getFloorsCount());
        } catch (NumberFormatException e) {
            elevatorSettingsForm.invalidInteger();
        }

        elevatorService.createElevator(floorsCount, new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {

            }

            public void onSuccess(Void result) {
                elevatorSettingsForm.elevatorCreated();
            }
        });
    }
}
