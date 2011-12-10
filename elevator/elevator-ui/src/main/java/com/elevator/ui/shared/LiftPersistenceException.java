package com.elevator.ui.shared;

import java.io.IOException;

public class LiftPersistenceException extends Exception {

    public LiftPersistenceException() {
    }

    public LiftPersistenceException(String message, IOException e) {
        super(message, e);
    }
}
