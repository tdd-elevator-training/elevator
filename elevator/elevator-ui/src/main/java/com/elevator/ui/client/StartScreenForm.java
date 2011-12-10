package com.elevator.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class StartScreenForm extends Composite {
    public StartScreenForm() {
        initWidget(new HTMLPanel("<b>Welcome to our building!<b>"));
    }
}
