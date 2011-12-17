package com.elevator.ui.client;

import com.elevator.ui.shared.LiftState;
import com.google.gwt.user.client.Timer;

public class GwtTimerServerUpdater extends ServerUpdater {
    private LiftServiceAsync liftServiceAsync;
    private ScreenFlowManager screenFlowManager;
    private Timer timer;

    public GwtTimerServerUpdater(LiftServiceAsync liftServiceAsync, ScreenFlowManager screenFlowManager) {
        this.liftServiceAsync = liftServiceAsync;
        this.screenFlowManager = screenFlowManager;
    }

    @Override
    public void addListener(LiftFormController listener) {
        super.addListener(listener);
        timer = new Timer() {

            @Override
            public void run() {
                liftServiceAsync.getLiftState(new DefaultAsyncCallback<LiftState>(screenFlowManager) {
                    public void onSuccess(LiftState result) {
                        GwtTimerServerUpdater.this.listener.synchronize(
                                result.isOpen(), result.getFloor(), result.getDoorSpeed());
                    }
                });
            }
        };
        timer.scheduleRepeating(500);
    }

    @Override
    public void removeListener(LiftFormController listener) {
        super.removeListener(listener);
        if (timer != null) {
            timer.cancel();
        }
    }
}
