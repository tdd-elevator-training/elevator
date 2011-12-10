package com.elevator.ui.server;

import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.Lift;

public interface LiftDao {
    void store(Lift lift) throws LiftPersistenceException;

    boolean elevatorExists();
}
