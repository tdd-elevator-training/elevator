package com.elevator.ui.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.HashMap;

public class GwtScreenFlowManager implements ScreenFlowManager {
    
    private Composite currentForm;
    private HashMap<Form, Composite> forms = new HashMap<Form, Composite>();
    private HashMap<Form, Object> controllers = new HashMap<Form, Object>();
    private Form currentFormName;

    public GwtScreenFlowManager(LiftServiceAsync elevatorService, Messages messages) {
        LiftSettingsController liftSettingsController = new LiftSettingsController(elevatorService, this);
        GwtLiftSettingsForm liftSettingsForm = new GwtLiftSettingsForm(liftSettingsController, messages, this);
        liftSettingsController.setLiftSettingsForm(liftSettingsForm);
        forms.put(Form.LIFT_SETTINGS_FORM, liftSettingsForm);
        controllers.put(Form.LIFT_SETTINGS_FORM, liftSettingsController);

        InstallQuestionController installQuestionController = new InstallQuestionController(this);
        InstallQuestionForm installQuestionForm = new InstallQuestionForm(messages, installQuestionController);
        forms.put(Form.INSTALL_LIFT_QUESTION, installQuestionForm);
        controllers.put(Form.INSTALL_LIFT_QUESTION, installQuestionController);

        forms.put(Form.START_SCREEN, new StartScreenForm());

        LiftFormController liftFormController = new LiftFormController(elevatorService,
                this, new GwtTimerServerUpdater(elevatorService, this), messages);
        GwtLiftForm liftForm = new GwtLiftForm(liftFormController);
        liftFormController.setForm(liftForm);
        forms.put(Form.LIFT_FORM, liftForm);
        controllers.put(Form.LIFT_FORM, liftFormController);
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

        if (controllers.get(currentFormName) instanceof FormController) {
            ((FormController) controllers.get(currentFormName)).onHide();
        }
        currentForm = composite;
        currentFormName = form;

        if (controllers.get(currentFormName) instanceof FormController) {
            ((FormController) controllers.get(currentFormName)).onShow();
        }
        RootPanel.get().add(currentForm);
    }
}
