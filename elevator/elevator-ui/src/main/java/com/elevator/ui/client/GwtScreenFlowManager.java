package com.elevator.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.HashMap;

public class GwtScreenFlowManager implements ScreenFlowManager {

    private final GwtElevatorSettingsForm elevatorSettingsForm;
    private Composite currentForm;
    private HashMap<Form, Composite> forms =  new HashMap<Form, Composite>();
    private final InstallQuestionForm installQuestionForm;
    private ElevatorDialogBox dialogBox;

    public GwtScreenFlowManager(ElevatorServiceAsync elevatorService, Messages messages) {
        ElevatorSettingsController controller = new ElevatorSettingsController(elevatorService, this);
        elevatorSettingsForm = new GwtElevatorSettingsForm(controller, messages);
        controller.setElevatorSettingsForm(elevatorSettingsForm);
        forms.put(Form.ELEVATOR_SETTINGS_FORM, elevatorSettingsForm);

        installQuestionForm = new InstallQuestionForm(messages, new InstallQuestionController(this));
        forms.put(Form.INSTALL_ELEVATOR_QUESTION, installQuestionForm);

        forms.put(Form.START_SCREEN, new StartScreenForm());

        forms.put(Form.ELEVATOR_FORM, new ElevatorForm());

        dialogBox = new ElevatorDialogBox();
    }

    public void serverCallFailed(Throwable caught) {
        dialogBox.setTitle("Error");
        dialogBox.setText("Error");
        dialogBox.setText("Server Error: " + caught.getMessage());
        dialogBox.center();
    }

    public void nextScreen(Form form) {
        Composite composite = forms.get(form);
        if (composite == null) {
            throw new RuntimeException("Screen " + form + " is not defined!");
        }
        if (currentForm != null) {
            RootPanel.get().remove(currentForm);
        }
        currentForm = composite;
        RootPanel.get().add(currentForm);
    }
}
