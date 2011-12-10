package com.elevator.ui.server;

import com.elevator.ui.shared.ElevatorPersistenceException;
import com.globallogic.training.Lift;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ElevatorServiceImplTest {

    private ElevatorDao dao;
    private ElevatorServiceImpl service;

    @Before
    public void setUp() throws Exception {
        dao = EasyMock.createMock(ElevatorDao.class);
        service = new ElevatorServiceImpl(dao);
    }

    @Test
    public void shouldCallDaoWhenCreate() throws ElevatorPersistenceException {
        Capture<Lift> liftCapture = new Capture<Lift>();
        dao.store(EasyMock.capture(liftCapture));
        EasyMock.replay(dao);

        service.createElevator(5);

        Lift storedLift = liftCapture.getValue();

        assertEquals(5, storedLift.getFloorsCount());
    }

}
