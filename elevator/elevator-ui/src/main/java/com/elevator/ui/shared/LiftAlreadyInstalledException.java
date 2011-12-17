package com.elevator.ui.shared;

public class LiftAlreadyInstalledException extends Exception {
    public LiftAlreadyInstalledException() {
    }

    public LiftAlreadyInstalledException(String message) {
        super(message);
    }
}
