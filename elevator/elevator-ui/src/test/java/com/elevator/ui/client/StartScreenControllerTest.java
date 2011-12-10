package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class StartScreenControllerTest {

    private MockElevatorServiceAsync elevatorService;
    private ScreenFlowManager screenFlowManager;
    private StartScreenController controller;

    @Before
    public void setUp() throws Exception {
        elevatorService = new MockElevatorServiceAsync();
        screenFlowManager = new ScreenFlowManager(elevatorService);
        controller = new StartScreenController(screenFlowManager, elevatorService);
    }

    @Test
    public void shouldProposeLiftInstallationWhenLiftIsNotInstalled() {
        elevatorService.elevatorExists = false;

        controller.selectStartScreen();

        assertEquals("installQuestion", screenFlowManager.getNextScreen());
    }

    @Test
    public void shouldOpenLiftFormWhenLiftIsInstalled() {
        controller.selectStartScreen();

        assertEquals("liftForm", screenFlowManager.getNextScreen());
    }
}
