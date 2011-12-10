package com.elevator.ui.client;

public class MockScreenFlowManager implements ScreenFlowManager {

    private Form currentScreen;

    public Form getNextScreen() {
        return currentScreen;
    }

    public void nextScreen(Form form) {
        currentScreen = form;
    }
}
