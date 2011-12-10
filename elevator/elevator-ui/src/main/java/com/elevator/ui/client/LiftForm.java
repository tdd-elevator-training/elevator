package com.elevator.ui.client;

public interface LiftForm {
    void liftCalled();

    void setCallButtonEnabled(boolean enabled);

    void setCurrentFloor(int floorNumber);

    void setWaitPanelVisible(boolean visible);

    void buildIndicatorPane(int floorsCount);

    void buildButtonsPane(int floorsCount);

    void setEnterButtonState(boolean enabled, boolean isDown);

    void setButtonsPaneState(boolean visible, boolean enabled);

    void indicateFloor(int floorNumber);
}
