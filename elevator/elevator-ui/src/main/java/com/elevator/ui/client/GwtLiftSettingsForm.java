package com.elevator.ui.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

import java.util.HashMap;

public class GwtLiftSettingsForm extends Composite implements LiftSettingsForm {

    private Messages messages;
    private ScreenFlowManager screenFlowManager;
    private TextBox floorsCountBox;
    private Label validationLabel;
    private final HashMap<String,TextBox> textFields = new HashMap<String, TextBox>();


    public GwtLiftSettingsForm(final LiftSettingsController controller, Messages messages,
                               ScreenFlowManager screenFlowManager) {
        this.messages = messages;
        this.screenFlowManager = screenFlowManager;

        LayoutPanel layoutPanel = new LayoutPanel();
        initWidget(layoutPanel);
        layoutPanel.setSize("416px", "322px");

        createTextField(layoutPanel, "floorsCount", 86.0, messages.floorsCount());
        createTextField(layoutPanel, "delayBetweenFloors", 86.0 + 30, messages.delayBetweenFloors());
        createTextField(layoutPanel, "doorSpeed", 86.0 + 30 + 30, messages.doorSpeed());

        validationLabel = new Label();
        layoutPanel.add(validationLabel);
        layoutPanel.setWidgetLeftWidth(validationLabel, 91.0, Unit.PX, 200.0, Unit.PX);
        layoutPanel.setWidgetTopHeight(validationLabel, 120.0 + 30 + 30, Unit.PX, 18.0, Unit.PX);
        validationLabel.setStyleName("validationLabelError");
        validationLabel.setVisible(false);

        Button button = new Button(messages.createButton());
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                validationLabel.setVisible(false);
                controller.sendButtonClicked();
            }
        });
        layoutPanel.add(button);
        layoutPanel.setWidgetLeftWidth(button, 305.0, Unit.PX, 78.0, Unit.PX);
        layoutPanel.setWidgetTopHeight(button, 167.0 + 30 + 30, Unit.PX, 24.0, Unit.PX);
    }

    private void createTextField(LayoutPanel layoutPanel, String fieldName, double top, String labelText) {
        Label label = new Label(labelText);
        layoutPanel.add(label);
        layoutPanel.setWidgetLeftWidth(label, 91.0, Unit.PX, 134.0, Unit.PX);
        layoutPanel.setWidgetTopHeight(label, top + 5, Unit.PX, 18.0, Unit.PX);

        TextBox floorsCountBox = new TextBox();
        layoutPanel.add(floorsCountBox);
        layoutPanel.setWidgetLeftWidth(floorsCountBox, 231.0, Unit.PX, 151.0, Unit.PX);
        layoutPanel.setWidgetTopHeight(floorsCountBox, top, Unit.PX, 26.0, Unit.PX);
        textFields.put(fieldName, floorsCountBox);
    }


    public String getFloorsCount() {
        return floorsCountBox.getText();
    }

    public void liftCreated() {
        validationLabel.setVisible(false);
        screenFlowManager.showMessage("Lift created!");
    }

    public void invalidInteger(String fieldName) {
        floorsCountBox.setFocus(true);
        validationLabel.setText(messages.invalidFloorInteger());
        validationLabel.setVisible(true);
    }

    public void negativeInteger() {
        validationLabel.setText(messages.negativeFloor());
        validationLabel.setVisible(true);
    }

    public String getFieldValue(String fieldName) {
        return null;
    }

}
