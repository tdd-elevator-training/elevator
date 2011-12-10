package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class InstallQuestionControllerTest {

    private MockScreenFlowManager screenFlowManager;
    private InstallQuestionController controller;

    @Before
    public void setUp() throws Exception {
        screenFlowManager = new MockScreenFlowManager();
        controller = new InstallQuestionController(screenFlowManager);
    }

    @Test
    public void shouldOpenElevatorFormWhenAgreedToInstall() {
        controller.agree();

        assertEquals(ScreenFlowManager.Form.LIFT_SETTINGS_FORM, screenFlowManager.getNextScreen());
    }

    @Test
    public void shouldOpenStartScreenFormWhenAgreedToInstall() {
        controller.disagree();

        assertEquals(ScreenFlowManager.Form.START_SCREEN, screenFlowManager.getNextScreen());
    }

}
