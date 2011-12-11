package com.elevator.ui.server;

import com.elevator.ui.shared.LiftAlreadyInstalledException;
import com.elevator.ui.shared.LiftNotInstalledException;
import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.*;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertFalse;
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
    public void shouldCallDaoWhenCreate() throws LiftPersistenceException, LiftAlreadyInstalledException {
        dao.store(EasyMock.capture(liftCapture));
        EasyMock.replay(dao);

        service.createLift(5);

        Lift storedLift = liftCapture.getValue();

        assertEquals(5, storedLift.getFloorsCount());
    }
    
    @Test
    public void shouldStartLiftWhenInstalled() throws LiftPersistenceException, LiftAlreadyInstalledException {

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
    public void shouldCallLiftWhenCalledByUser() throws LiftPersistenceException, LiftNotInstalledException, LiftAlreadyInstalledException {
        service.createLift(10);

        try {
            service.call(5);
        } catch (Exception e) {
            fail("It's hard to provide with proper test. Exception should not be raised here");
        }
    }
    
    @Test
    public void shouldThrowExceptionWhenCalledFromNonExistentFloor() throws LiftNotInstalledException, LiftPersistenceException, LiftAlreadyInstalledException {
        service.createLift(10);
        
        try {
            service.call(10 + 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {

        }
    }
    
    @Test
    public void shouldStopLiftWhenServiceStopped() throws LiftPersistenceException, LiftAlreadyInstalledException {
        service.createLift(10);

        service.stop();

        assertFalse(service.getLift().isStarted());
    } 

    @Test
    public void shouldNotStopNonExistentLiftWhenServiceStopped() throws LiftPersistenceException {
        try {
            service.stop();
        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    public void shouldThrowExceptionWhenInstallingExistinLift() throws LiftPersistenceException, LiftAlreadyInstalledException {
        service.createLift(123);

        try {
            service.createLift(345);
            fail();
        } catch (LiftAlreadyInstalledException e) {

        }
    }
    
    @Test
    public void shouldReturnFloorsCountWhenAsked() throws LiftAlreadyInstalledException, LiftPersistenceException {
        service.createLift(11);

        assertEquals(11, service.getFloorsCount());
    }

    @Test
    public void shouldReturn0WhenAskedFloorsCountAndLiftNotInstalled(){
        assertEquals(0, service.getFloorsCount());
    }

    @Test
    public void shouldNotThrowExceptionWhenAskedMoveOnNonInstalledLift() throws LiftAlreadyInstalledException, LiftPersistenceException, ElevatorException {
        try {
            service.moveTo(11);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void shouldReturnNullLiftStateWhenLiftIsNotInstalled() {
        assertNull(service.getLiftState());
    }

    @Test
    public void shouldReturnNotNullLiftStateWhenLiftInstalled() throws LiftAlreadyInstalledException, LiftPersistenceException {
        service.createLift(10);

        assertNotNull(service.getLiftState());
    }

    private IExpectationSetters<Boolean> liftExists(boolean liftExists) {
        return EasyMock.expect(dao.elevatorExists()).andReturn(liftExists);
    }

}
