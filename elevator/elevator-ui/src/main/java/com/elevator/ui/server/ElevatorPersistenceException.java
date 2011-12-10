package com.elevator.ui.server;

import java.io.IOException;

public class ElevatorPersistenceException extends Exception {

    public ElevatorPersistenceException(String message, IOException e) {
        super(message, e);
    }
}
