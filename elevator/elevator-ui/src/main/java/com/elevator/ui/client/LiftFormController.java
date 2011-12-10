package com.elevator.ui.client;

public class LiftFormController {
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

    public void callPressed() {
        liftServiceAsync.call(0, new DefaultAsyncCallback<Void>(screenFlowManager) {
            public void onSuccess(Void result) {
                form.liftCalled();
            }
        });
    }

    public void synchronize(boolean doorIsOpen, int floorNumber) {
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
}
