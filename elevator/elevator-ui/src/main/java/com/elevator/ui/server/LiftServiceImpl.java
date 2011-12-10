package com.elevator.ui.server;

import com.elevator.ui.client.LiftService;
import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.Lift;
import com.globallogic.training.RealDoor;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.io.FileUtils;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LiftServiceImpl extends RemoteServiceServlet implements
        LiftService {

    private LiftDao dao;

    public LiftServiceImpl() {
        this(new SerializationLiftDao(FileUtils.getUserDirectory()));
    }

    public LiftServiceImpl(LiftDao dao) {
        this.dao = dao;
    }

    public void createLift(int floorsCount) throws LiftPersistenceException {
        dao.store(new Lift(0, floorsCount, new RealDoor()));
    }

    public boolean liftExists() {
        return dao.elevatorExists();
    }

    public void call(int fromFloor) {

    }

}
