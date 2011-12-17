package com.elevator.ui.server;

import com.elevator.ui.client.LiftService;
import com.elevator.ui.shared.*;
import com.globallogic.training.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LiftServiceImpl extends RemoteServiceServlet implements
        LiftService, Runnable, FloorListener {

    private LiftDao dao;
    private Lift lift;
    private final Thread liftThread;
    private AtomicInteger currentFloor = new AtomicInteger();


    public LiftServiceImpl() {
        this(new SerializationLiftDao(FileUtils.getUserDirectory()));
    }

    public LiftServiceImpl(LiftDao dao) {
        this.dao = dao;
        liftThread = new Thread(this);
    }

    public void updateLift(int floorsCount, int delayBetweenFloors, int doorSpeed) throws LiftPersistenceException, LiftAlreadyInstalledException {
        if (lift != null && lift.getFloorsCount() != floorsCount) {
            throw new LiftAlreadyInstalledException("Unable to change floors count for installed lift!");
        }
        if (lift == null) {
            lift = new Lift(0, floorsCount, new RealDoor());
        }
        lift.getDoor().setDoorSpeed(doorSpeed);
        lift.setMoveBetweenFloorsDelay(delayBetweenFloors);
        dao.store(lift);
        if (lift.isStarted()) {
            return;
        }
        startLift();
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
        startLift();
    }

    private void startLift() {
        lift.setStarted(true);
        liftThread.start();
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

    //WARN: Not tested
    public void run() {
        lift.setFloorListener(this);
        lift.call(0);
        lift.run();
    }

    //WARN: not tested properly
    public LiftState getLiftState() {
        if (lift == null) {
            return null;
        }
        return new LiftState(currentFloor.get(), lift.getDoor().isOpen());
    }

    public LiftSettings getLiftSettings() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void atFloor(int floorNumber) {
        currentFloor.set(floorNumber);
    }
}
