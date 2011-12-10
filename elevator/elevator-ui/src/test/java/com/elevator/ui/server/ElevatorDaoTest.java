package com.elevator.ui.server;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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
    public void shouldPersistElevatorWhenCreateServiceCalled() {
        dao.createElevator(10);

        assertTrue(dao.isElevatorInstalled());
    }

    @Test
    public void shouldReturnFalseWhenElevatorIsNotInstalled() {
        assertFalse(dao.isElevatorInstalled());
    }

}
