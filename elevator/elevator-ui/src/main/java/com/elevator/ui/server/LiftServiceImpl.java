package com.elevator.ui.server;

import com.elevator.ui.client.LiftService;
import com.elevator.ui.shared.LiftAlreadyInstalledException;
import com.elevator.ui.shared.LiftNotInstalledException;
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
    private Lift lift;

    public LiftServiceImpl() {
        this(new SerializationLiftDao(FileUtils.getUserDirectory()));
    }

    public LiftServiceImpl(LiftDao dao) {
        this.dao = dao;
    }

    public void createLift(int floorsCount) throws LiftPersistenceException, LiftAlreadyInstalledException {
        if (lift != null) {
            throw new LiftAlreadyInstalledException();
        }
        lift = new Lift(0, floorsCount, new RealDoor());
        lift.setStarted(true);
        dao.store(lift);
    }

    public boolean liftExists() {
        return dao.elevatorExists();
    }

    public void call(int fromFloor) throws LiftNotInstalledException {
        if (lift == null) {
            throw new LiftNotInstalledException();
        }
        if (fromFloor > lift.getFloorsCount()) {
            throw new IllegalArgumentException("Called from non existnet floor: " + fromFloor +
                    ". Maximum floor is : " + lift.getFloorsCount());
        }
        lift.call(fromFloor);
    }

    public Lift getLift() {
        return lift;
    }

    public void start() {
        if (!dao.elevatorExists()) {
            return;
        }
        lift = dao.loadLift();
        lift.setStarted(true);
    }

    public void stop() {
        if (lift == null) {
            return;
        }
        lift.setStarted(false);
    }
}
