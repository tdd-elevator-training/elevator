package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class InstallQuestionControllerTest {

    private ScreenFlowManager screenFlowManager;
    private InstallQuestionController controller;

    @Before
    public void setUp() throws Exception {
        screenFlowManager = new ScreenFlowManager(new MockElevatorServiceAsync());
        controller = new InstallQuestionController(screenFlowManager);
    }

    @Test
    public void shouldOpenElevatorFormWhenAgreedToInstall() {
        controller.agree();

        assertEquals("elevatorSettingsForm", screenFlowManager.getNextScreen());
    }

    @Test
    public void shouldOpenStartScreenFormWhenAgreedToInstall() {
        controller.disagree();

        assertEquals("startScreen", screenFlowManager.getNextScreen());
    }

}
