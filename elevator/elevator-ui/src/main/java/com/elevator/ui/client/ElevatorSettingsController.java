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
        try {
            int floorsCount = Integer.parseInt(elevatorSettingsForm.getFloorsCount());
            if (floorsCount < 0) {
                elevatorSettingsForm.negativeInteger();
                return;
            }

            elevatorService.createElevator(floorsCount, new AsyncCallback<Void>() {

                public void onFailure(Throwable caught) {
                    elevatorSettingsForm.serverCallFailed(caught);
                }

                public void onSuccess(Void result) {
                    elevatorSettingsForm.elevatorCreated();
                }
            });
        } catch (NumberFormatException e) {
            elevatorSettingsForm.invalidInteger();
        }

    }
}
