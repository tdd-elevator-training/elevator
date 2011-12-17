package com.elevator.ui.client;

import com.elevator.ui.shared.LiftSettings;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static com.elevator.ui.client.LiftSettingsForm.*;
import static com.elevator.ui.client.LiftSettingsForm.FieldName.*;
import static junit.framework.Assert.*;

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
        fillFormValues("10", "100", "200", "300");

        controller.createButtonClicked();

        assertLiftParams(10, 100, 200, 300);
    }

    @Test
    public void shouldSayOkWhenCreateSucceed() {
        fillFormValues("9", "100", "200", "200");

        controller.createButtonClicked();

        assertTrue(liftSettingsForm.liftCreatedCalled);
        assertEquals(9, liftService.floorsCount.intValue());
        assertEquals(ScreenFlowManager.Form.LIFT_FORM, screenFlowManager.getNextScreen());
    }

    private void fillFormValues(String floorsCount, String delayBetweenFloors, String doorSpeed, String delayAfterOpen) {
        liftSettingsForm.fieldValues.put(FieldName.floorsCount, floorsCount);
        liftSettingsForm.fieldValues.put(FieldName.delayBetweenFloors, delayBetweenFloors);
        liftSettingsForm.fieldValues.put(FieldName.doorSpeed, doorSpeed);
        liftSettingsForm.fieldValues.put(FieldName.delayAfterOpen, delayAfterOpen);
    }

    @Test
    public void shouldValidateNonDigitFloorCountInputWhenSendButtonPressed() {
        fillFormValues("lala", "100", "200", "200");

        controller.createButtonClicked();

        assertFieldIsInvalid(floorsCount);
    }

    @Test
    public void shouldValidateNonDigitDelayBetweenFloorInputWhenSendButtonPressed() {
        fillFormValues("1", "lala", "200", "200");

        controller.createButtonClicked();

        assertFieldIsInvalid(delayBetweenFloors);
    }

    @Test
    public void shouldNotCallServiceOnDelayBetweenFloorsValidationFailure() {
        fillFormValues("1", "invalid", "200", "200");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldValidateNonDigitDoorSpeedInputWhenSendButtonPressed() {
        fillFormValues("1", "100", "lala", "200");

        controller.createButtonClicked();

        assertFieldIsInvalid(doorSpeed);
    }

    @Test
    public void shouldNotCallServiceOnDoorSpeedValidationFailure() {
        fillFormValues("1", "100", "invalid", "200");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldValidateNonDigitDelayAfterOpenInputWhenSendButtonPressed() {
        fillFormValues("1", "0", "0", "bubu");

        controller.createButtonClicked();

        assertFieldIsInvalid(delayAfterOpen);
    }

    @Test
    public void shouldNotCallServiceOnDelayAfterOpenValidationFailure() {
        fillFormValues("1", "0", "0", "invalid");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldNotCallServiceOnValidationFailure() {
        fillFormValues("invalid", "100", "200", "200");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }


    @Test
    public void shouldRejectNegativeNumbersWhenSendButtonPressed() {
        fillFormValues("-1", "100", "200", "200");

        controller.createButtonClicked();

        assertTrue(liftSettingsForm.negativeIntegerValidation);
    }

    @Test
    public void shouldNotCallServiceWhenNegativeNumberEntered() {
        fillFormValues("-2", "100", "200", "200");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldSayErrorWhenCallOnServerFailed() {
        IllegalArgumentException raisedException = new IllegalArgumentException();
        liftService.serverFailure = raisedException;
        fillFormValues("1", "100", "200", "200");

        controller.createButtonClicked();

        assertEquals(raisedException, screenFlowManager.serverCallFailed);
    }

    @Test
    public void shouldGetLiftParamsWhenShow() {
        liftService.liftSettings = new LiftSettings(10, 200, 300, 400);

        controller.onShow();

        assertFormValues("10", "200", "300", "400");
    }

    @Test
    public void shouldSetDefaultLiftParamsWhenLiftIsNotInstalled() {
        controller.onShow();

        assertFormValues("1", "0", "0", "0");
    }


    private void assertFormValues(String expectedFloorsCount,
                                  String expectedDelayBetweenFloors,
                                  String expectedDoorspeed,
                                  String expectedDelayAfterOpen) {
        assertEquals(expectedFloorsCount, liftSettingsForm.getFieldValue(floorsCount));
        assertEquals(expectedDelayBetweenFloors, liftSettingsForm.getFieldValue(delayBetweenFloors));
        assertEquals(expectedDoorspeed, liftSettingsForm.getFieldValue(doorSpeed));
        assertEquals(expectedDelayAfterOpen, liftSettingsForm.getFieldValue(delayAfterOpen));
    }

    private void assertLiftParams(int floorsCount, int delayBetweenFloors, int doorSpeed, int delayAfterOpen) {
        assertEquals(floorsCount, liftService.floorsCount.intValue());
        assertEquals(delayBetweenFloors, liftService.delayBetweenFloors);
        assertEquals(doorSpeed, liftService.doorSpeed);
        assertEquals(delayAfterOpen, liftService.delayAfterOpen);
    }

    private void assertFieldIsInvalid(FieldName invalidFieldName) {
        assertEquals(invalidFieldName, liftSettingsForm.invalidFieldName);
    }

    private void assertServiceNotCalled() {
        assertNull(liftService.floorsCount);
    }

    private class MockLiftSettingsForm implements LiftSettingsForm {
        private boolean liftCreatedCalled;
        private FieldName invalidFieldName;
        public boolean negativeIntegerValidation;
        private final HashMap<FieldName,String> fieldValues = new HashMap<FieldName, String>();

        public void setFieldValue(FieldName fieldName, String value) {
            fieldValues.put(fieldName, value);
        }

        public void liftCreated() {
            liftCreatedCalled = true;
        }

        public void invalidInteger(FieldName fieldName) {
            invalidFieldName = fieldName;
        }

        public void negativeInteger() {
            negativeIntegerValidation = true;
        }

        public String getFieldValue(FieldName fieldName) {
            return fieldValues.get(fieldName);
        }

    }
}
