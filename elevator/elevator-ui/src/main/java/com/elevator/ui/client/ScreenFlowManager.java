package com.elevator.ui.client;

public interface ScreenFlowManager {
    void serverCallFailed(Throwable caught);

    public enum Form {START_SCREEN, ELEVATOR_FORM, INSTALL_ELEVATOR_QUESTION, ELEVATOR_SETTINGS_FORM}

    void nextScreen(Form form);
}
