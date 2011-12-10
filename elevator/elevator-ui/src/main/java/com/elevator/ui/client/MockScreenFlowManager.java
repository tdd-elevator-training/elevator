package com.elevator.ui.client;

public class MockScreenFlowManager implements ScreenFlowManager {
    public Throwable serverCallFailed;

    private Form currentScreen;

    public Form getNextScreen() {
        return currentScreen;
    }

    public void nextScreen(Form form) {
        currentScreen = form;
    }

    public void serverCallFailed(Throwable caught) {
        serverCallFailed = caught;
    }

}
