package com.elevator.ui.client;

public class ElevatorFormController {
    private ElevatorServiceAsync elevatorServiceAsync;
    private ScreenFlowManager screenFlowManager;

    public ElevatorFormController(ElevatorServiceAsync elevatorServiceAsync, ScreenFlowManager screenFlowManager) {
        this.elevatorServiceAsync = elevatorServiceAsync;
        this.screenFlowManager = screenFlowManager;
    }

    public void callPressed() {
        elevatorServiceAsync.call(0, new DefaultAsyncCallback<Void>(screenFlowManager) {
            public void onSuccess(Void result) {

            }
        });
    }
}
