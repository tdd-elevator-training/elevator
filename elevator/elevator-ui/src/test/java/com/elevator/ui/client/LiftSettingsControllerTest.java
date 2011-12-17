package com.elevator.ui.client;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

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
        fillFormValues("10", "100", "200");

        controller.sendButtonClicked();

        assertLiftParams(10, 100, 200);
    }

    @Test
    public void shouldSayOkWhenCreateSucceed() {
        fillFormValues("9", "100", "200");

        controller.sendButtonClicked();

        assertTrue(liftSettingsForm.liftCreatedCalled);
        assertEquals(9, liftService.floorsCount.intValue());
        assertEquals(ScreenFlowManager.Form.LIFT_FORM, screenFlowManager.getNextScreen());
    }

    private void fillFormValues(String floorsCount, String delayBetweenFloors, String doorSpeed) {
        liftSettingsForm.fieldValues.put("floorsCount", floorsCount);
        liftSettingsForm.fieldValues.put("delayBetweenFloors", delayBetweenFloors);
        liftSettingsForm.fieldValues.put("doorSpeed", doorSpeed);
    }

    @Test
    public void shouldValidateNonDigitFloorCountInputWhenSendButtonPressed() {
        fillFormValues("lala", "100", "200");

        controller.sendButtonClicked();

        assertFieldIsInvalid("floorsCount");
    }

    @Test
    public void shouldValidateNonDigitDelayBetweenFloorInputWhenSendButtonPressed() {
        fillFormValues("1", "lala", "200");

        controller.sendButtonClicked();

        assertFieldIsInvalid("delayBetweenFloors");
    }

    @Test
    public void shouldNotCallServiceOnDelayBetweenFloorsValidationFailure() {
        fillFormValues("1", "invalid", "200");

        controller.sendButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldValidateNonDigitDoorSpeedInputWhenSendButtonPressed() {
        fillFormValues("1", "100", "lala");

        controller.sendButtonClicked();

        assertFieldIsInvalid("doorSpeed");
    }

    @Test
    public void shouldNotCallServiceOnDoorSpeedValidationFailure() {
        fillFormValues("1", "100", "invalid");

        controller.sendButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldNotCallServiceOnValidationFailure() {
        fillFormValues("invalid", "100", "200");

        controller.sendButtonClicked();

        assertServiceNotCalled();
    }

    private void assertFieldIsInvalid(String invalidFieldName) {
        assertEquals(invalidFieldName, liftSettingsForm.invalidFieldName);
    }

    private void assertServiceNotCalled() {
        assertNull(liftService.floorsCount);
    }

    @Test
    public void shouldRejectNegativeNumbersWhenSendButtonPressed() {
        fillFormValues("-1", "100", "200");

        controller.sendButtonClicked();

        assertTrue(liftSettingsForm.negativeIntegerValidation);
    }

    @Test
    public void shouldNotCallServiceWhenNegativeNumberEntered() {
        fillFormValues("-2", "100", "200");

        controller.sendButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldSayErrorWhenCallOnServerFailed() {
        IllegalArgumentException raisedException = new IllegalArgumentException();
        liftService.serverFailure = raisedException;
        fillFormValues("1", "100", "200");

        controller.sendButtonClicked();

        assertEquals(raisedException, screenFlowManager.serverCallFailed);
    }

    private void assertLiftParams(int floorsCount, int delayBetweenFloors, int doorSpeed) {
        assertEquals(floorsCount, liftService.floorsCount.intValue());
        assertEquals(delayBetweenFloors, liftService.delayBetweenFloors);
        assertEquals(doorSpeed, liftService.doorSpeed);
    }

    private class MockLiftSettingsForm implements LiftSettingsForm {
        private boolean liftCreatedCalled;
        private String invalidFieldName;
        public boolean negativeIntegerValidation;
        private final HashMap<String,String> fieldValues = new HashMap<String, String>();

        public String getFloorsCount() {
            return fieldValues.get("floorsCount");
        }

        public void liftCreated() {
            liftCreatedCalled = true;
        }

        public void invalidInteger(String fieldName) {
            invalidFieldName = fieldName;
        }

        public void negativeInteger() {
            negativeIntegerValidation = true;
        }

        public String getFieldValue(String fieldName) {
            return fieldValues.get(fieldName);
        }

    }
}
