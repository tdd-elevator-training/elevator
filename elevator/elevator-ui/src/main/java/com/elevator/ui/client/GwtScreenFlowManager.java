package com.elevator.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.HashMap;

public class GwtScreenFlowManager implements ScreenFlowManager {

    private final GwtElevatorSettingsForm elevatorSettingsForm;
    private Composite currentForm;
    private HashMap<String, Composite> forms =  new HashMap<String, Composite>();
    
    public GwtScreenFlowManager(ElevatorServiceAsync elevatorService, Messages messages) {
        ElevatorSettingsController controller = new ElevatorSettingsController(elevatorService);
        elevatorSettingsForm = new GwtElevatorSettingsForm(controller, messages);
        controller.setElevatorSettingsForm(elevatorSettingsForm);
        forms.put("liftForm", elevatorSettingsForm);
        
        
    }

    public void nextScreen(Form form) {
        Composite composite = forms.get(form);
        if (composite == null) {
            throw new RuntimeException("Screen " + form + " is not defined!");
        }
        RootPanel.get().remove(currentForm);
        currentForm = composite;
        RootPanel.get().add(currentForm);
    }
}
