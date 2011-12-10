package com.elevator.ui.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class ElevatorDialogBox extends DialogBox {
    private HTML dialogBoxMessage;
    private Button button;

    public ElevatorDialogBox() {
        super(false);
        VerticalPanel dialogBoxContents = new VerticalPanel();
        dialogBoxMessage = new HTML("Click 'Close' to close");
        button = new Button("Close", new ClickHandler() {
            public void onClick(ClickEvent event) {
                ElevatorDialogBox.this.hide();
            }
        });
        SimplePanel holder = new SimplePanel();
        holder.add(button);
        dialogBoxContents.add(dialogBoxMessage);
        dialogBoxContents.add(holder);
        this.setWidget(dialogBoxContents);
    }

    public void setMessageText(String text) {
        dialogBoxMessage.setText(text);
    }
}
