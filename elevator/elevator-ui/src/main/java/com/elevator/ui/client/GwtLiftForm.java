package com.elevator.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class GwtLiftForm extends Composite implements LiftForm {


    public GwtLiftForm() {
        initWidget(new HTMLPanel("<b>Lift form - coming soon!<b>"));
    }

    public void liftCalled() {
        //will implement later
    }

    public void setCallButtonEnabled(boolean enabled) {

    }

    public void setCurrentFloor(int floorNumber) {

    }

    public void setWaitPanelVisible(boolean visible) {

    }

    public void buildIndicatorPane(int floorsCount) {

    }

    public void buildButtonsPane(int floorsCount) {

    }

    public void setEnterButtonState(boolean enabled, boolean isDown) {

    }

    public void setButtonsPaneState(boolean visible, boolean enabled) {

    }
}
