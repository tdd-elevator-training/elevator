package com.elevator.ui.client;

public class ServerUpdater {
    public LiftFormController listener;

    public void addListener(LiftFormController listener) {
        this.listener = listener;
    }

    public void removeListener(LiftFormController listener) {
        if (listener == this.listener) {
            this.listener = null;
        }
    }
}
