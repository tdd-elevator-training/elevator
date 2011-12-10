package com.elevator.ui.server;

import com.elevator.ui.shared.LiftPersistenceException;
import com.globallogic.training.Lift;
import com.globallogic.training.NativeCurrentThread;
import com.globallogic.training.RealDoor;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.*;

public class LiftDaoTest {

    private SerializationLiftDao dao;
    private File testFolder;

    @Before
    public void setUp() throws Exception {
        testFolder = new File(FileUtils.getTempDirectory(), "test");
        testFolder.mkdirs();
        dao = new SerializationLiftDao(testFolder);
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.forceDelete(testFolder);
    }

    @Test
    public void shouldPersistLiftWhenCreateServiceCalled() throws LiftPersistenceException {
        Lift lift = new Lift(0, 10, new RealDoor());
        dao.store(lift);

        assertTrue(dao.isLiftInstalled());
    }

    @Test
    public void shouldReturnFalseWhenElevatorIsNotInstalled() {
        assertFalse(dao.isLiftInstalled());
    }

    @Test
    public void shouldThrowUserExceptionWhenUnableToStoreLift() throws IOException {
        SerializationLiftDao dao = new SerializationLiftDao(new File("a:\\nonExistentPath"));
        try {

            Lift lift = new Lift(0, 9, new RealDoor());
            dao.store(lift);

            fail("Exception expected");
        } catch (LiftPersistenceException e) {
        }
    }

    @Test
    public void shouldLoadLiftWhenSaved() throws LiftPersistenceException {
        Lift lift1 = new Lift(0, 8, new RealDoor());
        dao.store(lift1);

        Lift lift = dao.loadLift();

        assertEquals(8, lift.getFloorsCount());
    }

    @Test
    public void shouldReturnTrueWhenElevatorExists() throws LiftPersistenceException {
        dao.store(new Lift(0, 123, new RealDoor()));

        assertTrue(dao.elevatorExists());
    }

    @Test
    public void shouldReturnFalseWhenElevatorDoesNotExist() throws LiftPersistenceException {
        assertFalse(dao.elevatorExists());
    }

    @Test
    public void shouldHavNotNullCurrentThreadFieldWhenLoad() throws LiftPersistenceException {
        dao.store(new Lift(0, 10, new RealDoor()));

        Lift lift = dao.loadLift();

        try {
            lift.setStarted(true);
            lift.call(123);
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception expected: "+ e.getMessage());
        }

    } 
}
