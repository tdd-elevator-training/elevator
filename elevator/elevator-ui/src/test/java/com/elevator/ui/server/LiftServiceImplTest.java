package com.elevator.ui.server;

import com.elevator.ui.shared.LiftNotInstalledException;
import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.ElevatorException;
import com.globallogic.training.Lift;
import com.globallogic.training.RealDoor;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        liftExists(true);
        EasyMock.replay(dao);

        service.start();

        Lift lift = service.getLift();
        assertTrue(lift.isStarted());
    }

    @Test
    public void shouldNotStartLiftWhenServiceStartedAndLiftDoesNotExist() throws LiftPersistenceException {
        EasyMock.expect(dao.loadLift()).andThrow(new RuntimeException());
        liftExists(false);
        EasyMock.replay(dao);

        service.start();

        assertNull(service.getLift());
    }

    @Test
    public void shouldThrowExceptionWhenCallNonExistentLift() {
        try {
            service.call(123);
            fail("LiftNotInstalledException expected");
        } catch (LiftNotInstalledException e) {
            
        }
    } 

    @Test
    public void shouldCallLiftWhenCalledByUser() throws LiftPersistenceException, LiftNotInstalledException {
        service.createLift(10);

        try {
            service.call(5);
        } catch (Exception e) {
            fail("It's hard to provide with proper test. Exception should not be raised here");
        }
    }
    
    @Test
    public void shouldThrowExceptionWhenCalledFromNonExistentFloor() throws LiftNotInstalledException, LiftPersistenceException {
        service.createLift(10);
        
        try {
            service.call(10 + 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {

        }
    } 
    private IExpectationSetters<Boolean> liftExists(boolean liftExists) {
        return EasyMock.expect(dao.elevatorExists()).andReturn(liftExists);
    }

}
