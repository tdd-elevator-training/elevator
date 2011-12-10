package com.elevator.ui.server;

import com.elevator.ui.shared.ElevatorPersistenceException;
import com.globallogic.training.Lift;
import com.globallogic.training.RealDoor;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.*;

public class ElevatorDaoTest {

    private SerializationElevatorDao dao;
    private File testFolder;

    @Before
    public void setUp() throws Exception {
        testFolder = new File(FileUtils.getTempDirectory(), "test");
        testFolder.mkdirs();
        dao = new SerializationElevatorDao(testFolder);
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.forceDelete(testFolder);
    }

    @Test
    public void shouldPersistElevatorWhenCreateServiceCalled() throws ElevatorPersistenceException {
        Lift lift = new Lift(0, 10, new RealDoor());
        dao.store(lift);

        assertTrue(dao.isElevatorInstalled());
    }

    @Test
    public void shouldReturnFalseWhenElevatorIsNotInstalled() {
        assertFalse(dao.isElevatorInstalled());
    }

    @Test
    public void shouldThrowUserExceptionWhenUnableToStoreLift() throws IOException {
        SerializationElevatorDao dao = new SerializationElevatorDao(new File("a:\\nonExistentPath"));
        try {

            Lift lift = new Lift(0, 9, new RealDoor());
            dao.store(lift);

            fail("Exception expected");
        } catch (ElevatorPersistenceException e) {
        }
    }

    @Test
    public void shouldLoadLiftWhenSaved() throws ElevatorPersistenceException {
        Lift lift1 = new Lift(0, 8, new RealDoor());
        dao.store(lift1);

        Lift lift = dao.loadLift();

        assertEquals(8, lift.getFloorsCount());
    }

    @Test
    public void shouldReturnTrueWhenElevatorExists() throws ElevatorPersistenceException {
        dao.store(new Lift(0, 123, new RealDoor()));

        assertTrue(dao.elevatorExists());
    }

    @Test
    public void shouldReturnFalseWhenElevatorDoesNotExist() throws ElevatorPersistenceException {
        assertFalse(dao.elevatorExists());
    }

}
