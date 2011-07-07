package com.globallogic.training;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SimpleLiftTest extends LiftTest {

    private final static int SOME_FLOOR = 2;
    private Lift liftWithClosedDoor;
    private Lift liftWithOpenDoor;

    @Before
    public void setUp() throws Exception {
        liftWithClosedDoor = getLiftWithClosedDoor();
        liftWithOpenDoor = getLiftWithOpenDoor();
    }

    @Test
	public void shouldOpenDoorWhenClickButton() {
        liftWithClosedDoor.pressButton();

        assertTrue(liftWithClosedDoor.doorIsOpen());
    }

    @Test
	public void shouldOpenDoorWhenClickButtonAgain() {
        liftWithOpenDoor.pressButton();

        assertTrue(liftWithOpenDoor.doorIsOpen());
    }

    @Test
    public void shouldDoorBeClosedAfterSelectingFloor(){
        liftWithOpenDoor.pressButton();

        liftWithOpenDoor.gotoFloor(SOME_FLOOR);

        assertFalse(liftWithOpenDoor.doorIsOpen());
    }

//    @Test
//    public void shouldDoorBeOpenIfSameFloorIsSelected(){
//
//    }

    protected Lift getLiftWithOpenDoor() {
        Lift lift = new Lift();
        lift.pressButton();
        return lift;
    }

    protected Lift getLiftWithClosedDoor() {
        Lift lift = new Lift();
        return lift;
    }
}
