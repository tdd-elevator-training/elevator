package com.elevator.ui.server;

import com.globallogic.training.Lift;

public interface ElevatorDao {
    void store(Lift lift) throws ElevatorPersistenceException;
}
