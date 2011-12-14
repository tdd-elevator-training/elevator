package com.elevator.ui.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class GwtLiftForm extends Composite implements LiftForm {
    private final AbsolutePanel rightDoor = new AbsolutePanel();
    private final AbsolutePanel leftDoor = new AbsolutePanel();
    private LayoutPanel buttonsPane;
    private boolean doorIsOpen;
    //    private final Label waitLabel;
    private final ToggleButton callbutton;
    private LiftFormController controller;
    private final ToggleButton enterButton;
    private final DockLayoutPanel mainPane;
    private final IndicatorPane indicatorPane;
    private final LayoutPanel buttonsBorder;


    public GwtLiftForm(LiftFormController controller) {
        this.controller = controller;
        mainPane = new DockLayoutPanel(Style.Unit.PX);
        initWidget(mainPane);

        mainPane.setWidth("600px");
        mainPane.setHeight("800px");

        indicatorPane = new IndicatorPane();
        mainPane.addNorth(indicatorPane, 30);

        LayoutPanel callButtonPane = new LayoutPanel();
        callbutton = new ToggleButton("Call");
        callbutton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                GwtLiftForm.this.controller.callButtonPressed();
            }
        });
        callButtonPane.add(callbutton);
        callButtonPane.setWidgetLeftWidth(callbutton, 0, Style.Unit.PX, 50.0, Style.Unit.PX);
        callButtonPane.setWidgetBottomHeight(callbutton, 375, Style.Unit.PX, 50.0, Style.Unit.PX);
        mainPane.addEast(callButtonPane, 50);

        buttonsPane = new LayoutPanel();
        buttonsBorder = new LayoutPanel();
        buttonsPane.add(buttonsBorder);
        enterButton = new ToggleButton("Enter");
        enterButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                GwtLiftForm.this.controller.enterButtonClicked();
            }
        });
        enterButton.getDownFace().setText("Exit");
        buttonsPane.add(enterButton);
        buttonsPane.setWidgetLeftWidth(enterButton, 200.0, Style.Unit.PX, 200.0, Style.Unit.PX);
        buttonsPane.setWidgetTopHeight(enterButton, 700.0, Style.Unit.PX, 50.0, Style.Unit.PX);

        leftDoor.setStyleName("doorPane");
        buttonsPane.add(leftDoor);
        rightDoor.setStyleName("doorPane");
        buttonsPane.add(rightDoor);
        setDoorsClosedConstraints();

/*
        waitLabel = new Label("Please wait a second");
        waitLabel.setDirectionEstimator(true);
        waitLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        buttonsPane.add(waitLabel);
        waitLabel.setSize("500", "700");
        waitLabel.setStyleName("waitLabel");
*/
//        callbutton.setEnabled(false);
        buttonsPane.getElement().getStyle().setZIndex(10);
        buttonsPane.forceLayout();

        mainPane.add(buttonsPane);
    }

    private void setDoorsClosedConstraints() {
        buttonsPane.setWidgetLeftWidth(leftDoor, 0.0, Style.Unit.PX, 250.0, Style.Unit.PX);
        buttonsPane.setWidgetTopHeight(leftDoor, 0.0, Style.Unit.PX, 770.0, Style.Unit.PX);
        buttonsPane.setWidgetLeftWidth(rightDoor, 251.0, Style.Unit.PX, 250.0, Style.Unit.PX);
        buttonsPane.setWidgetTopHeight(rightDoor, 0.0, Style.Unit.PX, 770.0, Style.Unit.PX);
    }

    private void setDoorsOpenConstraints() {
        buttonsPane.setWidgetLeftWidth(leftDoor, 0.0, Style.Unit.PX, 10.0, Style.Unit.PX);
        buttonsPane.setWidgetTopHeight(leftDoor, 0.0, Style.Unit.PX, 770.0, Style.Unit.PX);
        buttonsPane.setWidgetLeftWidth(rightDoor, 490.0, Style.Unit.PX, 10.0, Style.Unit.PX);
        buttonsPane.setWidgetTopHeight(rightDoor, 0.0, Style.Unit.PX, 770.0, Style.Unit.PX);
    }

    public void liftCalled() {
        callbutton.setDown(true);
    }

    public void setCallButtonEnabled(boolean enabled) {
//        callbutton.setEnabled(enabled);
        callbutton.setVisible(true);
    }

    public void setCurrentFloor(int floorNumber) {
        System.out.println("current floor: " + floorNumber);
    }

    public void setWaitPanelVisible(boolean visible) {
/*
        if (waitLabel.isVisible() != visible) {
            if (visible) {
                waitLabel.getElement().getStyle().setZIndex(0);
            } else {
                waitLabel.getElement().getStyle().setZIndex(11);
            }
        }
        waitLabel.setVisible(visible);
*/
//        callbutton.setEnabled(visible);
    }

    public void buildIndicatorPane(int floorsCount) {
        indicatorPane.buildIndicators(floorsCount);
    }

    public void buildButtonsPane(int floorsCount) {
        int buttonSize = 40;
        int buttonSpace = buttonSize + 5 + 5;
        int columnsCount = 3;
        int buttonsBorderWidth = buttonSpace * columnsCount;
        buttonsBorder.setWidth(buttonsBorderWidth + "px");
        int rowsCount = floorsCount / columnsCount + 1;
        int borderHeight = rowsCount * buttonSpace;
        buttonsBorder.setHeight(borderHeight + "px");
        int i = 0;
        while (i < floorsCount) {
            for (int j = 0; j < columnsCount && i < floorsCount; j++) {
                LayoutPanel buttonSpacePane = new LayoutPanel();
                buttonSpacePane.setWidth(buttonSpace + "px");
                buttonSpacePane.setHeight(buttonSpace + "px");
                Button floorButton = new Button("" + i);
                floorButton.addClickHandler(new FloorButtonClickHandler(i));
                buttonSpacePane.add(floorButton);
                buttonSpacePane.setWidgetLeftWidth(floorButton, 5, Style.Unit.PX, buttonSize, Style.Unit.PX);
                buttonSpacePane.setWidgetTopHeight(floorButton, 5,
                        Style.Unit.PX, buttonSize, Style.Unit.PX);
                buttonsBorder.add(buttonSpacePane);
                buttonsBorder.setWidgetLeftWidth(buttonSpacePane, j * buttonSpace, Style.Unit.PX,
                        buttonSpace, Style.Unit.PX);
                buttonsBorder.setWidgetTopHeight(buttonSpacePane, borderHeight - i / columnsCount * buttonSpace - buttonSpace,
                        Style.Unit.PX, buttonSpace, Style.Unit.PX);
                i++;
            }
        }
        buttonsBorder.setStyleName("buttonsBorder");
        buttonsPane.setWidgetLeftWidth(buttonsBorder, 300, Style.Unit.PX, buttonsBorderWidth + 7, Style.Unit.PX);
        buttonsPane.setWidgetTopHeight(buttonsBorder, 100, Style.Unit.PX,
                borderHeight + 7, Style.Unit.PX);

    }

    public void setEnterButtonState(boolean enabled, boolean isDown) {
        enterButton.setEnabled(enabled);
        enterButton.setDown(isDown);
    }

    public void setButtonsPaneState(boolean visible, boolean enabled) {
        if (visible) {
            setDoorsOpenConstraints();
        } else {
            setDoorsClosedConstraints();
        }
        // performance optimization
        if (visible != doorIsOpen) {
            buttonsPane.animate(2000);
        }
        doorIsOpen = visible;
    }

    public void indicateFloor(int floorNumber) {
        indicatorPane.indicateFloor(floorNumber);
    }

    public void confirmedMovingTo(int floorNumber) {
        System.out.println("confirmed moving to: " + floorNumber);
    }

    private class FloorButtonClickHandler implements ClickHandler {
        private final int i;

        public FloorButtonClickHandler(int i) {
            this.i = i;
        }

        public void onClick(ClickEvent event) {
            GwtLiftForm.this.controller.floorSelected(i);
        }
    }
}
