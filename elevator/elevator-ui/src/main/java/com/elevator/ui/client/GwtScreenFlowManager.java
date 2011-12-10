package com.elevator.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.HashMap;

public class GwtScreenFlowManager implements ScreenFlowManager {

    private final GwtLiftSettingsForm elevatorSettingsForm;
    private Composite currentForm;
    private HashMap<Form, Composite> forms =  new HashMap<Form, Composite>();
    private final InstallQuestionForm installQuestionForm;
    private LiftDialogBox dialogBox;

    public GwtScreenFlowManager(LiftServiceAsync elevatorService, Messages messages) {
        LiftSettingsController controller = new LiftSettingsController(elevatorService, this);
        elevatorSettingsForm = new GwtLiftSettingsForm(controller, messages);
        controller.setLiftSettingsForm(elevatorSettingsForm);
        forms.put(Form.LIFT_SETTINGS_FORM, elevatorSettingsForm);

        installQuestionForm = new InstallQuestionForm(messages, new InstallQuestionController(this));
        forms.put(Form.INSTALL_LIFT_QUESTION, installQuestionForm);

        forms.put(Form.START_SCREEN, new StartScreenForm());

        forms.put(Form.LIFT_FORM, new GwtLiftForm());

        dialogBox = new LiftDialogBox();
    }

    public void serverCallFailed(Throwable caught) {
        dialogBox.setTitle("Error");
        dialogBox.setText("Error");
        dialogBox.setMessageText("Server Error: " + caught.getMessage());
        dialogBox.center();
    }

    public void showUserError(String html) {
        dialogBox.setTitle("Error");
        dialogBox.setText("Error");
        dialogBox.setMessageText(html);
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
