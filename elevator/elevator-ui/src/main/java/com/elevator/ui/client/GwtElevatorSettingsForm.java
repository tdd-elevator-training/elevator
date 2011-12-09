package com.elevator.ui.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class GwtElevatorSettingsForm extends Composite implements ElevatorSettingsForm {

    private Messages messages;
    private TextBox textBox;
    private Label validationLabel;
    private DialogBox dialogBox;
    private HTML dialogBoxMessage;


    public GwtElevatorSettingsForm(final ElevatorSettingsController controller, Messages messages) {
        this.messages = messages;

        LayoutPanel layoutPanel = new LayoutPanel();
        initWidget(layoutPanel);
        layoutPanel.setSize("416px", "322px");

        Label label = new Label(messages.floorsCount());
        layoutPanel.add(label);
        layoutPanel.setWidgetLeftWidth(label, 91.0, Unit.PX, 134.0, Unit.PX);
        layoutPanel.setWidgetTopHeight(label, 94.0, Unit.PX, 18.0, Unit.PX);

        textBox = new TextBox();
        layoutPanel.add(textBox);
        layoutPanel.setWidgetLeftWidth(textBox, 231.0, Unit.PX, 151.0, Unit.PX);
        layoutPanel.setWidgetTopHeight(textBox, 86.0, Unit.PX, 26.0, Unit.PX);

        validationLabel = new Label();
        layoutPanel.add(validationLabel);
        layoutPanel.setWidgetLeftWidth(validationLabel, 91.0, Unit.PX, 200.0, Unit.PX);
        layoutPanel.setWidgetTopHeight(validationLabel, 120.0, Unit.PX, 18.0, Unit.PX);
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
        layoutPanel.setWidgetTopHeight(button, 167.0, Unit.PX, 24.0, Unit.PX);
        dialogBox = new DialogBox(false);
        VerticalPanel dialogBoxContents = new VerticalPanel();
        dialogBoxMessage = new HTML("Click 'Close' to close");
        button = new Button("Close", new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });
        SimplePanel holder = new SimplePanel();
        holder.add(button);
        dialogBoxContents.add(dialogBoxMessage);
        dialogBoxContents.add(holder);
        dialogBox.setWidget(dialogBoxContents);
    }


    public String getFloorsCount() {
        return textBox.getText();
    }

    public void elevatorCreated() {
        validationLabel.setVisible(false);
        dialogBox.setTitle("Ok");
        dialogBox.setText("Ok");
        dialogBoxMessage.setText("Elevator created successfully");
        dialogBox.center();
    }

    public void invalidInteger() {
        textBox.setFocus(true);
        validationLabel.setText(messages.invalidFloorInteger());
        validationLabel.setVisible(true);
    }

    public void negativeInteger() {
        validationLabel.setText(messages.negativeFloor());
        validationLabel.setVisible(true);
    }

    public void serverCallFailed(Throwable caught) {
        dialogBox.setTitle("Error");
        dialogBox.setText("Error");
        dialogBoxMessage.setText("Server Error: " + caught.getMessage());
        dialogBox.center();
    }
}
