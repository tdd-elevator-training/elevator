package com.elevator.ui.server;

import com.elevator.ui.client.ElevatorService;
import com.elevator.ui.shared.ElevatorPersistenceException;
import com.globallogic.training.Lift;
import com.globallogic.training.RealDoor;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.io.FileUtils;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ElevatorServiceImpl extends RemoteServiceServlet implements
        ElevatorService {

    private ElevatorDao dao;

    public ElevatorServiceImpl() {
        this(new SerializationElevatorDao(FileUtils.getUserDirectory()));
    }

    public ElevatorServiceImpl(ElevatorDao dao) {
        this.dao = dao;
    }

    public void createElevator(int floorsCount) throws ElevatorPersistenceException {
        dao.store(new Lift(0, floorsCount, new RealDoor()));
    }

}
