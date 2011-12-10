package com.elevator.ui.client;

public class ScreenFlowManager {

    private String currentScreen = "installQuestion";

    public String getNextScreen() {
        return currentScreen;
    }

    public void nextScreen(String screenName) {
        currentScreen = screenName;
    }
}
