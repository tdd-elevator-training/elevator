package com.elevator.ui.server;

import com.elevator.ui.shared.ElevatorPersistenceException;
import com.globallogic.training.Lift;

public interface ElevatorDao {
    void store(Lift lift) throws ElevatorPersistenceException;
}
