package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class ElevatorSettingsControllerTest {

    private MockElevatorServiceAsync elevatorService;
    private MockElevatorSettingsForm elevatorSettingsForm;
    private ElevatorSettingsController controller;

    @Before
    public void setUp() throws Exception {
        elevatorService = new MockElevatorServiceAsync();
        elevatorSettingsForm = new MockElevatorSettingsForm();
        controller = new ElevatorSettingsController(elevatorService, elevatorSettingsForm);
    }

    @Test
    public void shouldCallServiceWhenCreateButtonClicked() {
        elevatorSettingsForm.setFloorsCount("10");

        controller.sendButtonClicked();

        assertEquals(10, elevatorService.floorsCount.intValue());
    }

    @Test
    public void shouldSayOkWhenCreateSucceed() {
        elevatorSettingsForm.setFloorsCount("9");

        controller.sendButtonClicked();

        assertTrue(elevatorSettingsForm.elevatorCreatedCalled);
        assertEquals(9, elevatorService.floorsCount.intValue());
    }

    @Test
    public void shouldValidateNonDigitInputWhenSendButtonPressed() {
        elevatorSettingsForm.setFloorsCount("lala");

        controller.sendButtonClicked();

        assertTrue(elevatorSettingsForm.invalidIntegerValidation);
    }

    @Test
    public void shouldNotCallServiceOnValidationFailure() {
        elevatorSettingsForm.setFloorsCount("invalid");

        controller.sendButtonClicked();

        assertNull(elevatorService.floorsCount);
    }

    @Test
    public void shouldRejectNegativeNumbersWhenSendButtonPressed() {
        elevatorSettingsForm.setFloorsCount("-1");

        controller.sendButtonClicked();

        assertTrue(elevatorSettingsForm.negativeIntegerValidation);
    }

    @Test
    public void shouldNotCallServiceWhenNegativeNumberEntered() {
        elevatorSettingsForm.setFloorsCount("-2");

        controller.sendButtonClicked();

        assertNull(elevatorService.floorsCount);
    }

    @Test
    public void shouldSayErrorWhenCallOnServerFailed() {
        IllegalArgumentException raisedException = new IllegalArgumentException();
        elevatorService.serverFailure = raisedException;
        elevatorSettingsForm.setFloorsCount("1");

        controller.sendButtonClicked();

        assertEquals(raisedException, elevatorSettingsForm.serverCallFailed);
    }

    private static class MockElevatorServiceAsync implements ElevatorServiceAsync {
        private Integer floorsCount;
        public Throwable serverFailure;

        public void createElevator(int floorsCount, AsyncCallback<Void> callback) {
            this.floorsCount = floorsCount;
            if (serverFailure == null) {
                callback.onSuccess(null);
            } else {
                callback.onFailure(serverFailure);
            }
        }
    }

    private class MockElevatorSettingsForm implements ElevatorSettingsForm {
        private String floorsCount;
        private boolean elevatorCreatedCalled;
        private boolean invalidIntegerValidation;
        public boolean negativeIntegerValidation;
        public Throwable serverCallFailed;

        public void setFloorsCount(String floorsCount) {
            this.floorsCount = floorsCount;
        }

        public String getFloorsCount() {
            return floorsCount;
        }

        public void elevatorCreated() {
            elevatorCreatedCalled = true;
        }

        public void invalidInteger() {
            invalidIntegerValidation = true;
        }

        public void negativeInteger() {
            negativeIntegerValidation = true;
        }

        public void serverCallFailed(Throwable caught) {
            serverCallFailed = caught;
        }
    }
}