package com.elevator.ui.server;

import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.Lift;
import com.globallogic.training.RealDoor;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class LiftServiceImplTest {

    private LiftDao dao;
    private LiftServiceImpl service;
    private Capture<Lift> liftCapture;

    @Before
    public void setUp() throws Exception {
        dao = EasyMock.createMock(LiftDao.class);
        service = new LiftServiceImpl(dao);
        liftCapture = new Capture<Lift>();
    }

    @Test
    public void shouldCallDaoWhenCreate() throws LiftPersistenceException {
        dao.store(EasyMock.capture(liftCapture));
        EasyMock.replay(dao);

        service.createLift(5);

        Lift storedLift = liftCapture.getValue();

        assertEquals(5, storedLift.getFloorsCount());
    }
    
    @Test
    public void shouldStartLiftWhenInstalled() throws LiftPersistenceException {

        service.createLift(123);

        Lift lift = service.getLift();
        assertTrue(lift.isStarted());
    }
    
    @Test
    public void shouldStartLiftWhenServiceStarted() throws LiftPersistenceException {
        EasyMock.expect(dao.loadLift()).andReturn(new Lift(0, 12, new RealDoor()));
        EasyMock.replay(dao);

        service.start();

        Lift lift = service.getLift();
        assertTrue(lift.isStarted());
    } 

}
