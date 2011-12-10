package com.elevator.ui.client;

public class LiftFormController {
    private LiftServiceAsync liftServiceAsync;
    private ScreenFlowManager screenFlowManager;
    private LiftForm form;

    public LiftFormController(LiftServiceAsync liftServiceAsync,
                                  ScreenFlowManager screenFlowManager,
                                  final LiftForm form) {
        this.liftServiceAsync = liftServiceAsync;
        this.screenFlowManager = screenFlowManager;
        this.form = form;
        form.setEnterButtonDown(false);
        form.setCallButtonEnabled(true);
        form.setCurrentFloor(0);
        form.showWaitPanel();
        liftServiceAsync.getFloorsCount(new DefaultAsyncCallback<Integer>(screenFlowManager) {
            public void onSuccess(Integer floorsCount) {
                form.buildIndicatorPane(floorsCount);
                form.buildButtonsPane(floorsCount);
            }
        });
    }

    public void callPressed() {
        liftServiceAsync.call(0, new DefaultAsyncCallback<Void>(screenFlowManager) {
            public void onSuccess(Void result) {
                form.liftCalled();
            }
        });
    }
}
