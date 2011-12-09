package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

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

        assertEquals(10, elevatorService.floorsCount);
    }

    @Test
    public void shouldSayOkWhenCreateSucceed() {
        elevatorSettingsForm.setFloorsCount("9");

        controller.sendButtonClicked();

        assertTrue(elevatorSettingsForm.elevatorCreatedCalled);
        assertEquals(9, elevatorService.floorsCount);
    }

    @Test
    @Ignore
    public void shouldValidateNonDigitInputWhenSendButtonPressed() {

    }

    @Test
    @Ignore
    public void shouldSayErrorWhenCallOnServerFailed() {

    }

    private static class MockElevatorServiceAsync implements ElevatorServiceAsync {
        private int floorsCount;

        public void createElevator(int floorsCount, AsyncCallback<Void> callback) {
            this.floorsCount = floorsCount;
            callback.onSuccess(null);
        }
    }

    private class MockElevatorSettingsForm implements ElevatorSettingsForm {
        private String floorsCount;
        private boolean elevatorCreatedCalled;

        public void setFloorsCount(String floorsCount) {
            this.floorsCount = floorsCount;
        }

        public String getFloorsCount() {
            return floorsCount;
        }

        public void elevatorCreated() {
            elevatorCreatedCalled = true;
        }
    }
}
