package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class LiftFormControllerTest {


    private MockLiftServiceAsync service;
    private MockScreenFlowManager screenFlowManager;
    private LiftFormController controller;
    private MockLiftForm form;

    @Before
    public void setUp() throws Exception {
        service = new MockLiftServiceAsync();
        service.floorsCount = 0;
        screenFlowManager = new MockScreenFlowManager();
        form = new MockLiftForm();
        controller = new LiftFormController(service, screenFlowManager, form);
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
        assertFalse(form.enterButtonIsDown);
        assertTrue(form.callButtonEnabled);
        assertEquals(0, form.currentFloor);
    }
    
    @Test
    public void shouldBeWaitMessageOnlyWhenInitializedAndNotSynchronized(){
        assertTrue(form.waitPanelEnabled);
    } 
    
    @Test
    public void shouldBuildIndicationPaneWhenInitialized(){
        service.floorsCount = 10;

        new LiftFormController(service, screenFlowManager, form);
        
        assertEquals(10, form.indicatorBuiltNumber);
    } 

    @Test
    public void shouldBuildButtonsPaneWhenInitialized(){
        service.floorsCount = 11;

        new LiftFormController(service, screenFlowManager, form);

        assertEquals(11, form.buttonsPaneBuiltNumber);
    }

    private class MockLiftForm implements LiftForm {
        private boolean liftCalled;
        public boolean enterButtonIsDown = true;
        public boolean callButtonEnabled;
        public int currentFloor = -1;
        public boolean waitPanelEnabled;
        public int indicatorBuiltNumber;
        public int buttonsPaneBuiltNumber;

        public void liftCalled() {
           liftCalled = true;
        }

        public void setEnterButtonDown(boolean down) {
            enterButtonIsDown = down;
        }

        public void setCallButtonEnabled(boolean enabled) {
            callButtonEnabled = enabled;
        }

        public void setCurrentFloor(int floorNumber) {
            currentFloor = floorNumber;
        }

        public void showWaitPanel() {
            waitPanelEnabled = true;
        }

        public void buildIndicatorPane(int floorsCount) {
            indicatorBuiltNumber = floorsCount;
        }

        public void buildButtonsPane(int floorsCount) {
            buttonsPaneBuiltNumber = floorsCount;
        }
    }
}
