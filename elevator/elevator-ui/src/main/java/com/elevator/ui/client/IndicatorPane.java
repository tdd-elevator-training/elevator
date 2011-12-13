package com.elevator.ui.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class IndicatorPane extends HorizontalPanel {
    private int currentFloor = 0;
    private Label[] lamps;

    public IndicatorPane() {
    }

    public void buildIndicators(int floorsCount) {
        lamps = new Label[floorsCount]; 
        for (int i = 0; i < floorsCount; i++) {
            lamps[i] = new Label("" + i);
            lamps[i].setWidth("19px");
            lamps[i].setStyleName("indicatorLabel");
            add(lamps[i]);
        }
    }

    public void indicateFloor(int floorNumber) {
        lamps[currentFloor].setStyleName("indicatorLabel");
        lamps[floorNumber].setStyleName("indicatorLabelOn");
        currentFloor = floorNumber;
    }
}
