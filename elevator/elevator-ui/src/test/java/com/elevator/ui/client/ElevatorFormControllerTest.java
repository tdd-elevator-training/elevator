package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ElevatorFormControllerTest {


    private MockElevatorServiceAsync service;
    private MockScreenFlowManager screenFlowManager;
    private ElevatorFormController controller;
    private MockElevatorForm form;

    @Before
    public void setUp() throws Exception {
        service = new MockElevatorServiceAsync();
        screenFlowManager = new MockScreenFlowManager();
        form = new MockElevatorForm();
        controller = new ElevatorFormController(service, screenFlowManager, form);
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

    private class MockElevatorForm implements ElevatorForm {
        private boolean liftCalled;

        public void liftCalled() {
           liftCalled = true;
        }
    }
}
