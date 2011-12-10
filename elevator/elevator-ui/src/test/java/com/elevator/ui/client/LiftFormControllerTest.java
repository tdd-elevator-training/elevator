package com.elevator.ui.client;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class LiftFormControllerTest {


    private MockLiftServiceAsync service;
    private MockScreenFlowManager screenFlowManager;
    private LiftFormController controller;
    private MockLiftForm form;
    private ServerUpdater serverUpdater;

    @Before
    public void setUp() throws Exception {
        service = new MockLiftServiceAsync();
        service.floorsCount = 0;
        screenFlowManager = new MockScreenFlowManager();
        form = new MockLiftForm();
        serverUpdater = new ServerUpdater();
        controller = new LiftFormController(service, screenFlowManager, form, serverUpdater,
                EasyMock.createMock(Messages.class));
        controller.onShow();
    }

    @Test
    public void shouldCallLiftWhenButtonPressed() {
        controller.callPressed();

        assertEquals(0, service.fromFloor);
    }

    @Test
    public void shouldNotifyFormWhenCallOk() {
        controller.callPressed();

        assertTrue(form.liftCalled);
    }

    @Test
    public void shouldBeOutsideWhenInitialized() {
        assertFormState(0, false, true, true);
    }

    @Test
    public void shouldBuildIndicationPaneWhenInitialized(){
        service.floorsCount = 10;

        LiftFormController controller = new LiftFormController(service, screenFlowManager, form, serverUpdater, EasyMock.createMock(Messages.class));
        controller.onShow();
        
        assertEquals(10, form.indicatorBuiltNumber);
    }

    @Test
    public void shouldBuildButtonsPaneWhenInitialized(){
        service.floorsCount = 11;

        LiftFormController controller = new LiftFormController(service, screenFlowManager, form, serverUpdater, EasyMock.createMock(Messages.class));
        controller.onShow();
        
        assertEquals(11, form.buttonsPaneBuiltNumber);
    }

    @Test
    public void shouldHideWaitPaneWhenFirstSynchronized() {
        controller.synchronize(true, 0);

        assertFalse(form.waitPanelEnabled);
    }

    @Test
    public void shouldRegisterForServerUpdatesWhenInitialized(){
        assertSame(controller, serverUpdater.listener);
    }

    @Test
    public void shouldRemoveServerUpdateListenerWhenHidden() {
        controller.onHide();
        
        assertNull(serverUpdater.listener);
    }

    @Test
    public void shouldBeOutsideWhenInitializeAfterHidden() {
        controller.synchronize(true, 0);
        controller.onHide();

        controller.onShow();

        assertFormState(0, false, true, true);
    }

    @Test
    public void shouldInitializeServerListnerWhenShowAfterHide() {
        controller.synchronize(true, 0);
        controller.onHide();

        controller.onShow();

        assertSame(controller, serverUpdater.listener);
    }

    @Test
    public void shouldBeAbleToEnterAndCallWhenDoorIsOpenOnMyFloor(){
        controller.synchronize(true, 0);

        assertTrue(form.callButtonEnabled);
        assertEnterButton(true, false);
        assertButtonsPane(true, false);
    }

    @Test
    public void shouldBeOnlyAbleToCallWhenNotMyFloorOutside() {
        controller.synchronize(true, 0 + 1);

        assertTrue(form.callButtonEnabled);
        assertEnterButton(false, false);
        assertButtonsPane(false, false);
    }

    @Test
    public void shouldBeAbleSelectFloorAndExitWhenInsideAndDoorIsOpen(){
        controller.synchronize(true, 0);

        controller.enterButtonClicked();

        assertFalse(form.callButtonEnabled);
        assertEnterButton(true, true);
        assertButtonsPane(true, true);
    }

    @Test
    public void shouldExitWhenExitButtonPressed(){
        controller.synchronize(true, 0);
        controller.enterButtonClicked();
        
        controller.enterButtonClicked();
        
        assertTrue(form.callButtonEnabled);
        assertEnterButton(true, false);
        assertButtonsPane(true, false);
    } 
    
    @Test
    public void shouldSayErrorWhenClickEnterButton_And_Outside(){
        controller.synchronize(true, 0 +1);
        
        controller.enterButtonClicked();

        assertTrue(screenFlowManager.userMessageShown);
    }
    
    @Test
    public void shouldSayErrorWhenClickEnterButton_And_DoorIsClosed() {
        controller.synchronize(false, 0);
        
        controller.enterButtonClicked();

        assertTrue(screenFlowManager.userMessageShown);
    }

    @Test
    public void shouldBeOnlyAbleToCallWhenDoorIsClosed_Outside(){
        controller.synchronize(false, 0);

        assertTrue(form.callButtonEnabled);
        assertEnterButton(false, false);
        assertButtonsPane(false, false);
    } 
    
    @Test
    public void shouldBeOnlyAbleSelectFloorWhenInsideAndClosedDoor(){
        controller.synchronize(true, 0);
        controller.enterButtonClicked();
        
        controller.synchronize(false, 0);

        assertFalse(form.callButtonEnabled);
        assertEnterButton(false, true);
        assertButtonsPane(true, true);
    } 

    private void assertButtonsPane(boolean visible, boolean enabled) {
        assertEquals("Buttons pane visible", visible, form.buttonsPaneVisible);
        assertEquals("Buttons pane enabled", enabled, form.buttonsPaneEnabled);
    }

    private void assertEnterButton(boolean enabled, boolean isDown) {
        assertEquals("Enter button enabled", enabled,  form.enterButtonEnabled);
        assertEquals("Enter button is down", isDown, form.enterButtonIsDown);
    }

    private void assertFormState(int expectedCurrentFloor, boolean enterButtonIsDown, boolean callButtonEnabled, boolean waitPanelEnabled) {
        assertEquals("Enter button is down", enterButtonIsDown, form.enterButtonIsDown);
        assertEquals("Call button enabled", callButtonEnabled, form.callButtonEnabled);
        assertEquals("Current floor", expectedCurrentFloor, form.currentFloor);
        assertEquals("Wait pane enabled", waitPanelEnabled, form.waitPanelEnabled);
    }

    private class MockLiftForm implements LiftForm {
        private boolean liftCalled;
        public boolean enterButtonIsDown = true;
        public boolean callButtonEnabled;
        public int currentFloor = -1;
        public boolean waitPanelEnabled;
        public int indicatorBuiltNumber;
        public int buttonsPaneBuiltNumber;
        public boolean enterButtonEnabled;
        public boolean buttonsPaneVisible;
        public boolean buttonsPaneEnabled;

        public void liftCalled() {
           liftCalled = true;
        }

        public void setCallButtonEnabled(boolean enabled) {
            callButtonEnabled = enabled;
        }

        public void setCurrentFloor(int floorNumber) {
            currentFloor = floorNumber;
        }

        public void setWaitPanelVisible(boolean visible) {
            waitPanelEnabled = visible;
        }

        public void buildIndicatorPane(int floorsCount) {
            indicatorBuiltNumber = floorsCount;
        }

        public void buildButtonsPane(int floorsCount) {
            buttonsPaneBuiltNumber = floorsCount;
        }

        public void setEnterButtonState(boolean enabled, boolean isDown) {
            enterButtonEnabled = enabled;
            enterButtonIsDown = isDown;
        }

        public void setButtonsPaneState(boolean visible, boolean enabled) {
            buttonsPaneVisible = visible;
            buttonsPaneEnabled = enabled;
        }
    }
}
