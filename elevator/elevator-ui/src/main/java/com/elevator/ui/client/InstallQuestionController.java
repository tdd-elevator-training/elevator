package com.elevator.ui.client;

public class InstallQuestionController {

    private ScreenFlowManager screenFlowManager;

    public InstallQuestionController(ScreenFlowManager screenFlowManager) {
        this.screenFlowManager = screenFlowManager;
    }

    public void agree() {
        screenFlowManager.nextScreen(ScreenFlowManager.Form.LIFT_SETTINGS_FORM);
    }

    public void disagree() {
        screenFlowManager.nextScreen(ScreenFlowManager.Form.START_SCREEN);
    }
}
