package com.elevator.ui.server;

import com.elevator.ui.client.LiftService;
import com.elevator.ui.shared.LiftAlreadyInstalledException;
import com.elevator.ui.shared.LiftNotInstalledException;
import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.ElevatorException;
import com.globallogic.training.Lift;
import com.globallogic.training.NativeCurrentThread;
import com.globallogic.training.RealDoor;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

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

    public int getFloorsCount() {
        if (lift == null) {
            return 0;
        }
        return lift.getFloorsCount();
    }

    public void moveTo(int floorNumber) {
        if (lift == null) {
            return;
        }
        try {
            lift.moveTo(floorNumber);
        } catch (ElevatorException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        start();
    }

    @Override
    public void destroy() {
        super.destroy();
        stop();
    }
}
