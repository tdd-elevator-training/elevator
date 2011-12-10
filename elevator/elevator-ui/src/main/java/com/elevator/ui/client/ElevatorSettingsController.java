package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ElevatorSettingsController {

    private ElevatorServiceAsync elevatorService;
    private ElevatorSettingsForm elevatorSettingsForm;
    private ScreenFlowManager screenFlowManager;
    private final ElevatorSettingsController.CreateElevatorCallback createElevatorCallback;

    public ElevatorSettingsController(ElevatorServiceAsync elevatorService, ElevatorSettingsForm elevatorSettingsForm,
                                      ScreenFlowManager screenFlowManager) {
        this.elevatorService = elevatorService;
        this.elevatorSettingsForm = elevatorSettingsForm;
        this.screenFlowManager = screenFlowManager;
        createElevatorCallback = new CreateElevatorCallback();
    }

    public ElevatorSettingsController(ElevatorServiceAsync elevatorService, ScreenFlowManager screenFlowManager) {
        this(elevatorService, null, screenFlowManager);
    }

    public void sendButtonClicked() {
        try {
            int floorsCount = Integer.parseInt(elevatorSettingsForm.getFloorsCount());
            if (floorsCount < 0) {
                elevatorSettingsForm.negativeInteger();
                return;
            }

            elevatorService.createElevator(floorsCount, createElevatorCallback);
        } catch (NumberFormatException e) {
            elevatorSettingsForm.invalidInteger();
        }
    }

    public void setElevatorSettingsForm(ElevatorSettingsForm elevatorSettingsForm) {
        this.elevatorSettingsForm = elevatorSettingsForm;
    }

    private class CreateElevatorCallback extends DefaultAsyncCallback<Void> {

        public CreateElevatorCallback() {
            super(ElevatorSettingsController.this.screenFlowManager);
        }

        public void onSuccess(Void result) {
            elevatorSettingsForm.elevatorCreated();
        }
    }
}
