package com.elevator.ui.client;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ScreenFlowManagerTest {

    @Test
    public void shouldProposeLiftInstallationWhenLiftIsNotInstalled() {
        ScreenFlowManager screenFlowManager = new ScreenFlowManager();

        assertEquals("installQuestion", screenFlowManager.getNextScreen());
    }

}
