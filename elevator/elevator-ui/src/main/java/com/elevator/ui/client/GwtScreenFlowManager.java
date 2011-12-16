package com.elevator.ui.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.HashMap;

public class GwtScreenFlowManager implements ScreenFlowManager {
    
    private final GwtLiftSettingsForm elevatorSettingsForm;
    private Composite currentForm;
    private HashMap<Form, Composite> forms = new HashMap<Form, Composite>();
    private final InstallQuestionForm installQuestionForm;
    private final LiftFormController liftFormController;

    public GwtScreenFlowManager(LiftServiceAsync elevatorService, Messages messages) {
        LiftSettingsController controller = new LiftSettingsController(elevatorService, this);
        elevatorSettingsForm = new GwtLiftSettingsForm(controller, messages, this);
        controller.setLiftSettingsForm(elevatorSettingsForm);
        forms.put(Form.LIFT_SETTINGS_FORM, elevatorSettingsForm);

        installQuestionForm = new InstallQuestionForm(messages, new InstallQuestionController(this));
        forms.put(Form.INSTALL_LIFT_QUESTION, installQuestionForm);

        forms.put(Form.START_SCREEN, new StartScreenForm());

        liftFormController = new LiftFormController(elevatorService,
                this, new GwtTimerServerUpdater(elevatorService, this), messages);
        GwtLiftForm liftForm = new GwtLiftForm(liftFormController);
        liftFormController.setForm(liftForm);
        forms.put(Form.LIFT_FORM, liftForm);

    }

    public void serverCallFailed(Throwable caught) {
        Window.alert("Server Error: " + caught.getMessage());
    }

    public void showUserError(String html) {
        Window.alert(html);
    }
    
    public void showMessage(String html){
        Window.alert(html);
    }

    public void nextScreen(Form form) {
        Composite composite = forms.get(form);
        if (composite == null) {
            throw new RuntimeException("Screen " + form + " is not defined!");
        }
        if (currentForm == composite) {
            return;
        }
        
        if (currentForm != null) {
            RootPanel.get().remove(currentForm);                                
        }

        if (currentForm == forms.get(Form.LIFT_FORM)) {
            liftFormController.onHide();
        }
        currentForm = composite;
        
        if (form == Form.LIFT_FORM) {
            liftFormController.onShow();
        }
        RootPanel.get().add(currentForm);
    }
}
