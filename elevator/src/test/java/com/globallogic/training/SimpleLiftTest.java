package com.globallogic.training;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SimpleLiftTest extends LiftTest {

    private final static int SOME_FLOOR = 2;
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

    @Test
    public void shouldDoorBeClosedAfterSelectingFloor(){
        Lift lift = getLiftWithOpenDoor();

        lift.gotoFloor(SOME_FLOOR);

        assertFalse(lift.doorIsOpen());
    }

//    @Test
//    public void shouldDoorBeOpenIfSameFloorIsSelected(){
//
//    }

    protected Lift getLiftWithOpenDoor() {
        lift.pressButton();
        return lift;
    }

    protected Lift getLiftWithClosedDoor() {
        return lift;
    }
}
