package com.elevator.ui.client;

public class ElevatorFormController {
    private ElevatorServiceAsync elevatorServiceAsync;
    private ScreenFlowManager screenFlowManager;
    private ElevatorForm form;

    public ElevatorFormController(ElevatorServiceAsync elevatorServiceAsync,
                                  ScreenFlowManager screenFlowManager,
                                  ElevatorForm form) {
        this.elevatorServiceAsync = elevatorServiceAsync;
        this.screenFlowManager = screenFlowManager;
        this.form = form;
    }

    public void callPressed() {
        elevatorServiceAsync.call(0, new DefaultAsyncCallback<Void>(screenFlowManager) {
            public void onSuccess(Void result) {
                form.liftCalled();
            }
        });
    }
}
