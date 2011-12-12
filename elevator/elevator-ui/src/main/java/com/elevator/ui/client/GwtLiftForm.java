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


    public GwtLiftForm(LiftFormController controller) {
        this.controller = controller;
        mainPane = new DockLayoutPanel(Style.Unit.PX);
        initWidget(mainPane);

        mainPane.setWidth("600px");
        mainPane.setHeight("800px");

        FlowPanel indicatorPane = new FlowPanel();
        Label floor1 = new Label("1");
        indicatorPane.add(floor1);
        indicatorPane.setStyleName("indicatorPane");
        mainPane.addNorth(indicatorPane, 30);

        LayoutPanel callButtonPane = new LayoutPanel();
        callbutton = new ToggleButton("Call");
        callbutton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                GwtLiftForm.this.controller.callPressed();
            }
        });
        callButtonPane.add(callbutton);
        callButtonPane.setWidgetLeftWidth(callbutton, 25, Style.Unit.PX, 50.0, Style.Unit.PX);
        callButtonPane.setWidgetBottomHeight(callbutton, 375, Style.Unit.PX, 50.0, Style.Unit.PX);
        mainPane.addEast(callButtonPane, 100);

        buttonsPane = new LayoutPanel();
        Button floorButton1 = new Button("Floor 1");
        floorButton1.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                GwtLiftForm.this.controller.floorSelected(1);
            }
        });
        buttonsPane.add(floorButton1);
        buttonsPane.setWidgetLeftWidth(floorButton1, 200, Style.Unit.PX, 50.0, Style.Unit.PX);
        buttonsPane.setWidgetTopHeight(floorButton1, 10.0, Style.Unit.PX, 50.0, Style.Unit.PX);

        Button floorButton2 = new Button("Floor 2");
        floorButton2.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                GwtLiftForm.this.controller.floorSelected(2);
            }
        });
        buttonsPane.add(floorButton2);
        buttonsPane.setWidgetLeftWidth(floorButton2, 250, Style.Unit.PX, 50.0, Style.Unit.PX);
        buttonsPane.setWidgetTopHeight(floorButton2, 10.0, Style.Unit.PX, 50.0, Style.Unit.PX);

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

    }

    public void buildButtonsPane(int floorsCount) {

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
        System.out.println("indicate: " + floorNumber);
    }

    public void confirmedMovingTo(int floorNumber) {
        System.out.println("confirmed moving to: " + floorNumber);
    }
}
