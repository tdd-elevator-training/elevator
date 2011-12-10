package com.elevator.ui.server;

import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.Lift;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ElevatorServiceImplTest {

    private LiftDao dao;
    private LiftServiceImpl service;

    @Before
    public void setUp() throws Exception {
        dao = EasyMock.createMock(LiftDao.class);
        service = new LiftServiceImpl(dao);
    }

    @Test
    public void shouldCallDaoWhenCreate() throws LiftPersistenceException {
        Capture<Lift> liftCapture = new Capture<Lift>();
        dao.store(EasyMock.capture(liftCapture));
        EasyMock.replay(dao);

        service.createLift(5);

        Lift storedLift = liftCapture.getValue();

        assertEquals(5, storedLift.getFloorsCount());
    }

}
