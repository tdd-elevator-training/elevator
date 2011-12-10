package com.elevator.ui.client;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class InstallQuestionControllerTest {
    @Test
    public void shouldOpenElevatorFormWhenAgreedToInstall() {
        ScreenFlowManager screenFlowManager = new ScreenFlowManager();
        InstallQuestionController installQuestionController = new InstallQuestionController(screenFlowManager);
        installQuestionController.agree();

        assertEquals("elevatorSettingsForm", screenFlowManager.getNextScreen());
    }

}
