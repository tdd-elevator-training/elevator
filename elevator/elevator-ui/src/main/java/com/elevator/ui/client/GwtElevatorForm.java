package com.elevator.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class GwtElevatorForm extends Composite implements ElevatorForm {


    public GwtElevatorForm() {
        initWidget(new HTMLPanel("<b>Lift form - coming soon!<b>"));
    }

    public void liftCalled() {
        //will implement later
    }
}
