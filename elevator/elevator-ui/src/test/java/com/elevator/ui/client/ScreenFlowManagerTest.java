package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ScreenFlowManagerTest {

    private MockElevatorServiceAsync elevatorService;
    private ScreenFlowManager screenFlowManager;

    @Before
    public void setUp() throws Exception {
        elevatorService = new MockElevatorServiceAsync();
        screenFlowManager = new ScreenFlowManager(elevatorService);
    }

    @Test
    public void shouldProposeLiftInstallationWhenLiftIsNotInstalled() {
        elevatorService.elevatorExists = false;
        screenFlowManager.selectStartScreen();

        assertEquals("installQuestion", screenFlowManager.getNextScreen());
    }

    @Test
    public void shouldOpenLiftFormWhenLiftIsInstalled() {
        screenFlowManager.selectStartScreen();

        assertEquals("liftForm", screenFlowManager.getNextScreen());
    }
}
