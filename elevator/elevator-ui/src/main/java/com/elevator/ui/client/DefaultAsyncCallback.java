package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class DefaultAsyncCallback<T> implements AsyncCallback<T>{
    private ScreenFlowManager screenFlowManager;

    public DefaultAsyncCallback(ScreenFlowManager screenFlowManager) {
        this.screenFlowManager = screenFlowManager;
    }

    public void onFailure(Throwable caught) {
        screenFlowManager.serverCallFailed(caught);
    }

}
