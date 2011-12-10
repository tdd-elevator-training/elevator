package com.elevator.ui.client;

public class LiftFormController {
    private LiftServiceAsync liftServiceAsync;
    private ScreenFlowManager screenFlowManager;
    private LiftForm form;

    public LiftFormController(LiftServiceAsync liftServiceAsync,
                                  ScreenFlowManager screenFlowManager,
                                  LiftForm form) {
        this.liftServiceAsync = liftServiceAsync;
        this.screenFlowManager = screenFlowManager;
        this.form = form;
    }

    public void callPressed() {
        liftServiceAsync.call(0, new DefaultAsyncCallback<Void>(screenFlowManager) {
            public void onSuccess(Void result) {
                form.liftCalled();
            }
        });
    }
}
