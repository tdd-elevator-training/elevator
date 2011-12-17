package com.elevator.ui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ElevatorUi implements EntryPoint {

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final LiftServiceAsync elevatorService = GWT.create(LiftService.class);

    private final Messages messages = GWT.create(Messages.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        GwtScreenFlowManager screenFlowManager = new GwtScreenFlowManager(elevatorService, messages);
        String admin = Window.Location.getParameter("admin");
        if (admin!=null) {
            screenFlowManager.nextScreen(ScreenFlowManager.Form.LIFT_SETTINGS_FORM);
        } else {
            new StartScreenController(screenFlowManager, elevatorService).selectStartScreen();
        }
    }

}
