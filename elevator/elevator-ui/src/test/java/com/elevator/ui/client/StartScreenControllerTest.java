package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class StartScreenControllerTest {

    private MockElevatorServiceAsync elevatorService;
    private MockScreenFlowManager screenFlowManager;
    private StartScreenController controller;

    @Before
    public void setUp() throws Exception {
        elevatorService = new MockElevatorServiceAsync();
        screenFlowManager = new MockScreenFlowManager();
        controller = new StartScreenController(screenFlowManager, elevatorService);
    }

    @Test
    public void shouldProposeLiftInstallationWhenLiftIsNotInstalled() {
        elevatorService.elevatorExists = false;

        controller.selectStartScreen();

        assertEquals(ScreenFlowManager.Form.INSTALL_ELEVATOR_QUESTION, screenFlowManager.getNextScreen());
    }

    @Test
    public void shouldOpenLiftFormWhenLiftIsInstalled() {
        controller.selectStartScreen();

        assertEquals(ScreenFlowManager.Form.ELEVATOR_FORM, screenFlowManager.getNextScreen());
    }
}