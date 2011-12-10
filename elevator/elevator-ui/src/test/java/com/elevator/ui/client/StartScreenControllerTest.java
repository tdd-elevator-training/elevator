package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class StartScreenControllerTest {

    private MockLiftServiceAsync liftService;
    private MockScreenFlowManager screenFlowManager;
    private StartScreenController controller;

    @Before
    public void setUp() throws Exception {
        liftService = new MockLiftServiceAsync();
        screenFlowManager = new MockScreenFlowManager();
        controller = new StartScreenController(screenFlowManager, liftService);
    }

    @Test
    public void shouldProposeLiftInstallationWhenLiftIsNotInstalled() {
        liftService.liftExists = false;

        controller.selectStartScreen();

        assertEquals(ScreenFlowManager.Form.INSTALL_LIFT_QUESTION, screenFlowManager.getNextScreen());
    }

    @Test
    public void shouldOpenLiftFormWhenLiftIsInstalled() {
        controller.selectStartScreen();

        assertEquals(ScreenFlowManager.Form.LIFT_FORM, screenFlowManager.getNextScreen());
    }
}
