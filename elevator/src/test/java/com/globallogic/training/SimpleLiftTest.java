package com.globallogic.training;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class SimpleLiftTest extends LiftTest {
    private Lift lift;

    @Before
    public void setUp() throws Exception {
        lift = new Lift();
    }

    @Test
	public void shouldOpenDoorWhenClickButton() {
        Lift lift = getLiftWithClosedDoor();

        lift.pressButton();

        assertTrue(lift.doorIsOpen());
    }

    @Test
	public void shouldOpenDoorWhenClickButtonAgain() {
        Lift lift = getLiftWithOpenDoor();

        lift.pressButton();

        assertTrue(lift.doorIsOpen());
    }

    protected Lift getLiftWithOpenDoor() {
        lift.pressButton();
        return lift;
    }

    protected Lift getLiftWithClosedDoor() {
        return lift;
    }
}
