package com.elevator.ui.server;

import com.elevator.ui.client.ElevatorService;
import com.globallogic.training.Lift;
import com.globallogic.training.RealDoor;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ElevatorServiceImpl extends RemoteServiceServlet implements
        ElevatorService {

    private ElevatorDao dao;

    public ElevatorServiceImpl(ElevatorDao dao) {
        this.dao = dao;
    }

    public void createElevator(int floorsCount) throws ElevatorPersistenceException {
        dao.store(new Lift(0, floorsCount, new RealDoor()));
    }

    public boolean isElevatorInstalled() {
//        return getElevatorFile().exists();
        return true;
    }

}
