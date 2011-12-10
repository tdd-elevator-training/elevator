package com.elevator.ui.client;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ElevatorFormControllerTest {


    @Test
    public void shouldCallLiftWhenButtonPressed() {
        MockElevatorServiceAsync service = new MockElevatorServiceAsync();
        ElevatorFormController controller = new ElevatorFormController(service, new MockScreenFlowManager());

        controller.callPressed();

        assertEquals(0, service.fromFloor);
    }
}
