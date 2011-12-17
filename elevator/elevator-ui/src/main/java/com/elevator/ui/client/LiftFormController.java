package com.elevator.ui.client;

public class LiftFormController implements FormController {
    private LiftServiceAsync liftServiceAsync;
    private ScreenFlowManager screenFlowManager;
    private LiftForm form;
    private final ServerUpdater updater;
    private boolean insideCabin;
    private boolean isMyFloor;
    private Messages messages;
    private boolean doorIsOpen;
    private int previousFloor = -1;
    private int currentFloor;

    public LiftFormController(LiftServiceAsync liftServiceAsync,
                              ScreenFlowManager screenFlowManager,
                              final LiftForm form, ServerUpdater updater, Messages messages) {
        this.liftServiceAsync = liftServiceAsync;
        this.screenFlowManager = screenFlowManager;
        this.form = form;
        this.updater = updater;
        this.messages = messages;
    }

    public LiftFormController(LiftServiceAsync liftServiceAsync,
                              ScreenFlowManager screenFlowManager,
                              ServerUpdater updater, Messages messages) {
        this(liftServiceAsync, screenFlowManager, null, updater, messages);
    }

    public void callButtonPressed() {
        liftServiceAsync.call(currentFloor, new DefaultAsyncCallback<Void>(screenFlowManager) {
            public void onSuccess(Void result) {
                form.liftCalled();
            }
        });
    }

    public void synchronize(boolean doorIsOpen, int floorNumber, int doorSpeed) {
        if (previousFloor != floorNumber) {
            form.indicateFloor(floorNumber);
        }
        if (insideCabin) {
            currentFloor = floorNumber;
            form.setCurrentFloor(currentFloor);
        }
        this.doorIsOpen = doorIsOpen;
        isMyFloor = floorNumber == currentFloor;
        form.setWaitPanelVisible(false);
        form.setEnterButtonState(doorIsOpen && (isMyFloor || insideCabin), insideCabin);
        form.setButtonsPaneState(isMyFloor && doorIsOpen || insideCabin, insideCabin);
        previousFloor = floorNumber;
        form.setDoorSpeed(doorSpeed);
    }

    public void onHide() {
        updater.removeListener(this);
    }

    public void onShow() {
        form.setEnterButtonState(false, false);
        form.setCallButtonEnabled(true);
        form.setCurrentFloor(0);
        form.setWaitPanelVisible(true);
        liftServiceAsync.getFloorsCount(new DefaultAsyncCallback<Integer>(screenFlowManager) {
            public void onSuccess(Integer floorsCount) {
                form.buildIndicatorPane(floorsCount);
                form.buildButtonsPane(floorsCount);
            }
        });
        this.updater.addListener(this);
    }

    public void enterButtonClicked() {
        if (!isMyFloor || !doorIsOpen) {
            screenFlowManager.showUserError(messages.unexpectedClickOnDisabledEnterButton());
            return;
        }
        insideCabin = !insideCabin;
        form.setCallButtonEnabled(!insideCabin);
        form.setEnterButtonState(true, insideCabin);
        form.setButtonsPaneState(true, insideCabin);
    }

    public void floorSelected(final int floorNumber) {
        if (!insideCabin) {
            screenFlowManager.showUserError(messages.unexpectedClickFloorSelection());
        }
        liftServiceAsync.moveTo(floorNumber, new DefaultAsyncCallback<Void>(screenFlowManager) {
            public void onSuccess(Void result) {
                form.confirmedMovingTo(floorNumber);
            }
        });
    }

    public void setForm(LiftForm form) {
        this.form = form;
    }
}
