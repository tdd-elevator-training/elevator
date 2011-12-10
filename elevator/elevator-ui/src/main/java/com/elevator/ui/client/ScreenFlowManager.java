package com.elevator.ui.client;

public interface ScreenFlowManager {
    public enum Form {START_SCREEN, LIFT_FORM, INSTALL_LIFT_QUESTION, LIFT_SETTINGS_FORM}

    void nextScreen(Form form);
}
