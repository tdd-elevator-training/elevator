package com.elevator.ui.client;

public class InstallQuestionController {

    private ScreenFlowManager screenFlowManager;

    public InstallQuestionController(ScreenFlowManager screenFlowManager) {
        this.screenFlowManager = screenFlowManager;
    }

    public void agree() {
        screenFlowManager.nextScreen("elevatorSettingsForm");
    }

    public void disagree() {
        screenFlowManager.nextScreen("startScreen");
    }
}
