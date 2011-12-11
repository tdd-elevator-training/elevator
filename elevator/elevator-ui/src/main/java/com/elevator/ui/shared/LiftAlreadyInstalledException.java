package com.elevator.ui.shared;

public class LiftAlreadyInstalledException extends Exception {
    public LiftAlreadyInstalledException() {
        super("Lift already installed");
    }
}
