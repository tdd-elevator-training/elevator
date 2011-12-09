package com.elevator.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ElevatorSettingsControllerTest {

    @Test
    public void shouldCallServiceWhenCreateButtonClicked() {
        MockElevatorServiceAsync elevatorService = new MockElevatorServiceAsync();
        MockElevatorSettingsForm elevatorSettingsForm = new MockElevatorSettingsForm();
        ElevatorSettingsController controller = new ElevatorSettingsController(elevatorService, elevatorSettingsForm);
        elevatorSettingsForm.setFloorsCount("10");

        controller.sendButtonClicked();

        assertEquals(10, elevatorService.floorsCount);
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

        public void setFloorsCount(String floorsCount) {
            this.floorsCount = floorsCount;
        }

        public String getFloorsCount() {
            return floorsCount;
        }
    }
}
