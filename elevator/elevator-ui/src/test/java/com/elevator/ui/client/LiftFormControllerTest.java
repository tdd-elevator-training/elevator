package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class LiftFormControllerTest {


    private MockLiftServiceAsync service;
    private MockScreenFlowManager screenFlowManager;
    private LiftFormController controller;
    private MockLiftForm form;

    @Before
    public void setUp() throws Exception {
        service = new MockLiftServiceAsync();
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

    private class MockLiftForm implements LiftForm {
        private boolean liftCalled;

        public void liftCalled() {
           liftCalled = true;
        }
    }
}
