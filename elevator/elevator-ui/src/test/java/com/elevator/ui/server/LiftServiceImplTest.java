package com.elevator.ui.server;

import com.elevator.ui.shared.LiftAlreadyInstalledException;
import com.elevator.ui.shared.LiftNotInstalledException;
import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.*;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;
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
        liftCapture = new Capture<Lift>(CaptureType.ALL);
    }

    @Test
    public void shouldCallDaoWhenCreate() throws LiftPersistenceException, LiftAlreadyInstalledException {
        dao.store(EasyMock.capture(liftCapture));
        EasyMock.replay(dao);

        service.updateLift(5, 2000, 1000);

        Lift storedLift = liftCapture.getValue();

        assertEquals(5, storedLift.getFloorsCount());
    }
    
    @Test
    public void shouldStartLiftWhenInstalled() throws LiftPersistenceException, LiftAlreadyInstalledException {

        service.updateLift(123, 2000, 1000);

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
        service.updateLift(10, 2000, 1000);

        try {
            service.call(5);
        } catch (Exception e) {
            fail("It's hard to provide with proper test. Exception should not be raised here");
        }
    }
    
    @Test
    public void shouldThrowExceptionWhenCalledFromNonExistentFloor() throws LiftNotInstalledException, LiftPersistenceException, LiftAlreadyInstalledException {
        service.updateLift(10, 2000, 1000);
        
        try {
            service.call(10 + 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {

        }
    }
    
    @Test
    public void shouldStopLiftWhenServiceStopped() throws LiftPersistenceException, LiftAlreadyInstalledException {
        service.updateLift(10, 2000, 1000);

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
    public void shouldThrowExceptionWhenUpdatingFloorCountForInstalledLift() throws LiftPersistenceException, LiftAlreadyInstalledException {
        service.updateLift(123, 2000, 1000);

        try {
            service.updateLift(345, 2000, 1000);
            fail();
        } catch (LiftAlreadyInstalledException e) {
        }
    }

    @Test
    public void shouldCreateWithParameters() throws LiftPersistenceException, LiftAlreadyInstalledException {
        dao.store(EasyMock.capture(liftCapture));
        EasyMock.replay(dao);

        service.updateLift(123, 2000, 1000);

        assertLiftParams(liftCapture.getValue(), 2000, 1000);
    }

    private void assertLiftParams(Lift storedLift, int delayBetweenFloors, int doorSpeed) {
        assertEquals(delayBetweenFloors, storedLift.getMoveBetweenFloorsDelay());
        assertEquals(doorSpeed, storedLift.getDoor().getDoorSpeed());
    }

    @Test
    public void shouldUpdateLiftParameters() throws LiftPersistenceException, LiftAlreadyInstalledException {
        dao.store(EasyMock.capture(liftCapture));
        EasyMock.expectLastCall().anyTimes();
        EasyMock.replay(dao);
        service.updateLift(123, 2000, 1000);

        service.updateLift(123, 500, 700);

        Lift storedLift = liftCapture.getValues().get(liftCapture.getValues().size() - 1);
        assertLiftParams(storedLift, 500, 700);
    }


    @Test
    public void shouldReturnFloorsCountWhenAsked() throws LiftAlreadyInstalledException, LiftPersistenceException {
        service.updateLift(11, 2000, 1000);

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
        service.updateLift(10, 2000, 1000);

        assertNotNull(service.getLiftState());
    }

    private IExpectationSetters<Boolean> liftExists(boolean liftExists) {
        return EasyMock.expect(dao.elevatorExists()).andReturn(liftExists);
    }

}
