package com.elevator.ui.client;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        assertFormState(true, enterBtn(true, false), buttonsPane(true, false));
    }

    @Test
    public void shouldBeOnlyAbleToCallWhenNotMyFloorOutside() {
        controller.synchronize(true, 0 + 1);

        assertFormState(true, enterBtn(false, false), buttonsPane(false, false));
    }

    @Test
    public void shouldBeAbleSelectFloorAndExitWhenInsideAndDoorIsOpen(){
        controller.synchronize(true, 0);

        controller.enterButtonClicked();

        assertFormState(false, enterBtn(true, true), buttonsPane(true, true));
    }

    @Test
    public void shouldExitWhenExitButtonPressed(){
        enterCabin();
        
        controller.enterButtonClicked();
        
        assertFormState(true, enterBtn(true, false), buttonsPane(true, false));
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

        assertFormState(true, enterBtn(false, false), buttonsPane(false, false));
    } 
    
    @Test
    public void shouldBeOnlyAbleSelectFloorWhenInsideAndClosedDoor(){
        enterCabin();
        
        controller.synchronize(false, 0);

        assertFormState(false, enterBtn(false, true), buttonsPane(true, true));
    }

    @Test
    public void shouldChangeCurrentFloorWhenMovingInsideCabin(){
        enterCabin();
        
        controller.synchronize(false, 1);

        assertEquals(1, form.currentFloor);
    }

    @Test
    public void shouldNotChangeFloorWhenOutsideCabin(){
        controller.synchronize(false, 1);

        assertEquals(0, form.currentFloor);
    }

    @Test
    public void shouldChangeFloorIndicationWhenCabinMoves() {
        controller.synchronize(false, 3);
        controller.synchronize(true, 2);
        
        assertIndication(3, 2);
    }

    @Test
    public void shouldNotFlashIndicatorWhenCabinDidNotMove(){
        controller.synchronize(false, 0);
        controller.synchronize(true, 0);
        controller.synchronize(false, 1);

        assertIndication(0, 1);
    }

    @Test
    public void shouldBeAbleToExitWhenCameToAnotherFloor(){
        enterCabin();
        
        controller.synchronize(true, 1);

        assertFormState(false, enterBtn(true, true), buttonsPane(true, true));
    } 
    
    @Test
    public void shouldBeAbleToComeInWhenExitOnSomeFloor(){
        enterCabin();
        controller.synchronize(true, 1);
        
        controller.enterButtonClicked();

        assertFormState(true, enterBtn(true, false), buttonsPane(true, false));
    } 
    
    @Test
    public void shouldSendMoveToRequestWhenFloorSelected(){
        enterCabin();

        controller.floorSelected(11);

        assertEquals(11, service.movingTo);
    } 
    
    @Test
    public void shouldConfirmMoveToRequestWhenFloorSelected(){
        enterCabin();
        
        controller.floorSelected(10);

        assertEquals(10, form.movingToConfirmation);
    } 
    
    @Test
    public void shouldSayErrorWhenFloorSelectedOutside(){
        controller.floorSelected(123);
        
        assertTrue("Error should be shown", screenFlowManager.userMessageShown);
    }

    private void assertIndication(Integer ... floors) {
        assertEquals(Arrays.asList(floors), form.indicationsHistory);
    }
    
    private void enterCabin() {
        controller.synchronize(true, 0);
        controller.enterButtonClicked();
    }
    
    private void assertButtonsPane(boolean visible, boolean enabled) {
        assertEquals("Buttons pane visible", visible, form.buttonsPaneVisible);
        assertEquals("Buttons pane enabled", enabled, form.buttonsPaneEnabled);
    }

    private void assertButtonsPane(ButtonsPane buttonsPane) {
        assertButtonsPane(buttonsPane.visible, buttonsPane.enabled);
    }

    private void assertEnterButton(EnterButtonState enterButton) {
        assertEnterButton(enterButton.enabled, enterButton.down);
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

    private void assertFormState(boolean callButtonEnabled, EnterButtonState enterButton, ButtonsPane buttonsPane) {
        assertEquals("Call button enabled", callButtonEnabled, form.callButtonEnabled);
        assertEnterButton(enterButton);
        assertButtonsPane(buttonsPane);
    }

    private ButtonsPane buttonsPane(boolean visible, boolean enabled) {
        return new ButtonsPane(visible, enabled);
    }

    private EnterButtonState enterBtn(boolean enabled, boolean down) {
        return new EnterButtonState(enabled, down);
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
        public List<Integer> indicationsHistory = new ArrayList<Integer>();
        public int movingToConfirmation;

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

        public void indicateFloor(int floorNumber) {
            indicationsHistory.add(floorNumber);
        }

        public void confirmedMovingTo(int floorNumber) {
            movingToConfirmation = floorNumber;
        }
    }

    private class EnterButtonState {
        private boolean enabled;
        private boolean down;

        public EnterButtonState(boolean enabled, boolean isDown) {
            this.enabled = enabled;
            down = isDown;
        }
    }

    private class ButtonsPane {
        private boolean visible;
        private boolean enabled;

        public ButtonsPane(boolean visible, boolean enabled) {
            this.visible = visible;
            this.enabled = enabled;
        }
    }
}
