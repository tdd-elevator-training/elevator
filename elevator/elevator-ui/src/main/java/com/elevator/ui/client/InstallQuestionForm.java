package com.elevator.ui.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class InstallQuestionForm extends Composite {

    public InstallQuestionForm(Messages messages, final InstallQuestionController controller) {

        LayoutPanel layoutPanel = new LayoutPanel();
        initWidget(layoutPanel);
        layoutPanel.setSize("800px", "600px");

        Label label = new Label(messages.installLiftQuestion());
        layoutPanel.add(label);
        layoutPanel.setWidgetLeftWidth(label, 50.0, Style.Unit.PX, 500.0, Style.Unit.PX);
        layoutPanel.setWidgetTopHeight(label, 100.0, Style.Unit.PX, 18.0, Style.Unit.PX);

        Button yesButton = new Button(messages.yes());
        yesButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                controller.agree();
            }
        });
        layoutPanel.add(yesButton);
        layoutPanel.setWidgetLeftWidth(yesButton, 400.0, Style.Unit.PX, 78.0, Style.Unit.PX);
        layoutPanel.setWidgetTopHeight(yesButton, 150.0, Style.Unit.PX, 24.0, Style.Unit.PX);

        Button noButton = new Button(messages.no());
        noButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                controller.disagree();
            }
        });
        layoutPanel.add(noButton);
        layoutPanel.setWidgetLeftWidth(noButton, 300.0, Style.Unit.PX, 78.0, Style.Unit.PX);
        layoutPanel.setWidgetTopHeight(noButton, 150.0, Style.Unit.PX, 24.0, Style.Unit.PX);
    }
}
