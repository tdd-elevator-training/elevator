package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class LiftSettingsControllerTest {

    private MockLiftServiceAsync liftService;
    private MockLiftSettingsForm liftSettingsForm;
    private LiftSettingsController controller;
    private MockScreenFlowManager screenFlowManager;

    @Before
    public void setUp() throws Exception {
        liftService = new MockLiftServiceAsync();
        liftSettingsForm = new MockLiftSettingsForm();
        screenFlowManager = new MockScreenFlowManager();
        controller = new LiftSettingsController(liftService, liftSettingsForm, screenFlowManager);
    }

    @Test
    public void shouldCallServiceWhenCreateButtonClicked() {
        liftSettingsForm.setFloorsCount("10");

        controller.sendButtonClicked();

        assertEquals(10, liftService.floorsCount.intValue());
    }

    @Test
    public void shouldSayOkWhenCreateSucceed() {
        liftSettingsForm.setFloorsCount("9");

        controller.sendButtonClicked();

        assertTrue(liftSettingsForm.liftCreatedCalled);
        assertEquals(9, liftService.floorsCount.intValue());
        assertEquals(ScreenFlowManager.Form.LIFT_FORM, screenFlowManager.getNextScreen());
    }

    @Test
    public void shouldValidateNonDigitInputWhenSendButtonPressed() {
        liftSettingsForm.setFloorsCount("lala");

        controller.sendButtonClicked();

        assertTrue(liftSettingsForm.invalidIntegerValidation);
    }

    @Test
    public void shouldNotCallServiceOnValidationFailure() {
        liftSettingsForm.setFloorsCount("invalid");

        controller.sendButtonClicked();

        assertNull(liftService.floorsCount);
    }

    @Test
    public void shouldRejectNegativeNumbersWhenSendButtonPressed() {
        liftSettingsForm.setFloorsCount("-1");

        controller.sendButtonClicked();

        assertTrue(liftSettingsForm.negativeIntegerValidation);
    }

    @Test
    public void shouldNotCallServiceWhenNegativeNumberEntered() {
        liftSettingsForm.setFloorsCount("-2");

        controller.sendButtonClicked();

        assertNull(liftService.floorsCount);
    }

    @Test
    public void shouldSayErrorWhenCallOnServerFailed() {
        IllegalArgumentException raisedException = new IllegalArgumentException();
        liftService.serverFailure = raisedException;
        liftSettingsForm.setFloorsCount("1");

        controller.sendButtonClicked();

        assertEquals(raisedException, screenFlowManager.serverCallFailed);
    }

    private class MockLiftSettingsForm implements LiftSettingsForm {
        private String floorsCount;
        private boolean liftCreatedCalled;
        private boolean invalidIntegerValidation;
        public boolean negativeIntegerValidation;

        public void setFloorsCount(String floorsCount) {
            this.floorsCount = floorsCount;
        }

        public String getFloorsCount() {
            return floorsCount;
        }

        public void liftCreated() {
            liftCreatedCalled = true;
        }

        public void invalidInteger() {
            invalidIntegerValidation = true;
        }

        public void negativeInteger() {
            negativeIntegerValidation = true;
        }

    }
}
