package com.elevator.ui.client;

public class LiftFormController {
    private LiftServiceAsync liftServiceAsync;
    private ScreenFlowManager screenFlowManager;
    private LiftForm form;
    private final ServerUpdater updater;

    public LiftFormController(LiftServiceAsync liftServiceAsync,
                              ScreenFlowManager screenFlowManager,
                              final LiftForm form, ServerUpdater updater) {
        this.liftServiceAsync = liftServiceAsync;
        this.screenFlowManager = screenFlowManager;
        this.form = form;
        this.updater = updater;
    }

    public void callPressed() {
        liftServiceAsync.call(0, new DefaultAsyncCallback<Void>(screenFlowManager) {
            public void onSuccess(Void result) {
                form.liftCalled();
            }
        });
    }

    public void synchronize() {
        form.setWaitPanelVisible(false);
    }

    public void onHide() {
        updater.removeListener(this);
    }

    public void onShow() {
        form.setEnterButtonDown(false);
        form.setCallButtonEnabled(true);
        form.setCurrentFloor(0);
        form.setWaitPanelVisible(true);
        liftServiceAsync.getFloorsCount(new DefaultAsyncCallback<Integer>(screenFlowManager) {
            public void onSuccess(Integer floorsCount) {
                form.buildIndicatorPane(floorsCount);
                form.buildButtonsPane(floorsCount);
            }
        });
        this.updater.addListener(this);
    }
}
