package com.elevator.ui.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class GwtElevatorSettingsForm extends Composite {

    private Messages messages;

    public GwtElevatorSettingsForm() {
		
		LayoutPanel layoutPanel = new LayoutPanel();
		initWidget(layoutPanel);
		layoutPanel.setSize("416px", "322px");
		
		Label label = new Label(messages.floorsCount());
		layoutPanel.add(label);
		layoutPanel.setWidgetLeftWidth(label, 91.0, Unit.PX, 134.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(label, 94.0, Unit.PX, 18.0, Unit.PX);
		
		IntegerBox integerBox = new IntegerBox();
		layoutPanel.add(integerBox);
		layoutPanel.setWidgetLeftWidth(integerBox, 231.0, Unit.PX, 151.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(integerBox, 86.0, Unit.PX, 26.0, Unit.PX);
		
		Button button = new Button(messages.createButton());
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		layoutPanel.add(button);
		layoutPanel.setWidgetLeftWidth(button, 305.0, Unit.PX, 78.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(button, 167.0, Unit.PX, 24.0, Unit.PX);
	}

    public GwtElevatorSettingsForm(Messages messages) {
        this.messages = messages;
    }
}
