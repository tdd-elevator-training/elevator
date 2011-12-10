package com.elevator.ui.server;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ElevatorServiceTest {

    private ElevatorServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new ElevatorServiceImpl();
    }

    @Test
    public void shouldPersistElevatorWhenCreateServiceCalled() {
        service.createElevator(10);

        assertTrue(service.isElevatorInstalled());
    }

    @Test
    @Ignore
    public void shouldReturnFalseWhenElevatorIsNotInstalled() {
        assertFalse(service.isElevatorInstalled());
    }

}
