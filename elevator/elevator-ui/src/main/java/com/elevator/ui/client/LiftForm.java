package com.elevator.ui.client;

public interface LiftForm {
    void liftCalled();

    void setEnterButtonDown(boolean pressed);

    void setCallButtonEnabled(boolean enabled);

    void setCurrentFloor(int floorNumber);

    void setWaitPanelVisible(boolean visible);

    void buildIndicatorPane(int floorsCount);

    void buildButtonsPane(int floorsCount);
}
