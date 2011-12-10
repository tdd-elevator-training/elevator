package com.elevator.ui.shared;

import java.io.IOException;

public class ElevatorPersistenceException extends Exception {

    public ElevatorPersistenceException() {
    }

    public ElevatorPersistenceException(String message, IOException e) {
        super(message, e);
    }
}
