package com.elevator.ui.server;

import com.globallogic.training.ElevatorException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

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
        dao.createElevator(10);

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

            dao.createElevator(9);

            fail("Exception expected");
        } catch (ElevatorPersistenceException e) {
        }

    }
}
