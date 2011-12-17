package com.elevator.ui.client;

import com.elevator.ui.shared.LiftSettings;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import static com.elevator.ui.client.LiftSettingsForm.*;
import static com.elevator.ui.client.LiftSettingsForm.FieldName.*;
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

        controller.createButtonClicked();

        assertLiftParams(10, 100, 200);
    }

    @Test
    public void shouldSayOkWhenCreateSucceed() {
        fillFormValues("9", "100", "200");

        controller.createButtonClicked();

        assertTrue(liftSettingsForm.liftCreatedCalled);
        assertEquals(9, liftService.floorsCount.intValue());
        assertEquals(ScreenFlowManager.Form.LIFT_FORM, screenFlowManager.getNextScreen());
    }

    private void fillFormValues(String floorsCount, String delayBetweenFloors, String doorSpeed) {
        liftSettingsForm.fieldValues.put(FieldName.floorsCount, floorsCount);
        liftSettingsForm.fieldValues.put(FieldName.delayBetweenFloors, delayBetweenFloors);
        liftSettingsForm.fieldValues.put(FieldName.doorSpeed, doorSpeed);
    }

    @Test
    public void shouldValidateNonDigitFloorCountInputWhenSendButtonPressed() {
        fillFormValues("lala", "100", "200");

        controller.createButtonClicked();

        assertFieldIsInvalid(floorsCount);
    }

    @Test
    public void shouldValidateNonDigitDelayBetweenFloorInputWhenSendButtonPressed() {
        fillFormValues("1", "lala", "200");

        controller.createButtonClicked();

        assertFieldIsInvalid(delayBetweenFloors);
    }

    @Test
    public void shouldNotCallServiceOnDelayBetweenFloorsValidationFailure() {
        fillFormValues("1", "invalid", "200");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldValidateNonDigitDoorSpeedInputWhenSendButtonPressed() {
        fillFormValues("1", "100", "lala");

        controller.createButtonClicked();

        assertFieldIsInvalid(doorSpeed);
    }

    @Test
    public void shouldNotCallServiceOnDoorSpeedValidationFailure() {
        fillFormValues("1", "100", "invalid");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldNotCallServiceOnValidationFailure() {
        fillFormValues("invalid", "100", "200");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }


    @Test
    public void shouldRejectNegativeNumbersWhenSendButtonPressed() {
        fillFormValues("-1", "100", "200");

        controller.createButtonClicked();

        assertTrue(liftSettingsForm.negativeIntegerValidation);
    }

    @Test
    public void shouldNotCallServiceWhenNegativeNumberEntered() {
        fillFormValues("-2", "100", "200");

        controller.createButtonClicked();

        assertServiceNotCalled();
    }

    @Test
    public void shouldSayErrorWhenCallOnServerFailed() {
        IllegalArgumentException raisedException = new IllegalArgumentException();
        liftService.serverFailure = raisedException;
        fillFormValues("1", "100", "200");

        controller.createButtonClicked();

        assertEquals(raisedException, screenFlowManager.serverCallFailed);
    }

    @Test
    public void shouldGetLiftParamsWhenShow() {
        liftService.liftSettings = new LiftSettings(10, 200, 300);

        controller.onShow();
        
        assertEquals("10", liftSettingsForm.getFieldValue(floorsCount));
        assertEquals("200", liftSettingsForm.getFieldValue(delayBetweenFloors));
        assertEquals("300", liftSettingsForm.getFieldValue(doorSpeed));
    }

    private void assertLiftParams(int floorsCount, int delayBetweenFloors, int doorSpeed) {
        assertEquals(floorsCount, liftService.floorsCount.intValue());
        assertEquals(delayBetweenFloors, liftService.delayBetweenFloors);
        assertEquals(doorSpeed, liftService.doorSpeed);
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
