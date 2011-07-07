package com.globallogic.training;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

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

        assertDoorIsOpen(liftWithClosedDoor);
    }

    private void assertDoorIsOpen(Lift lift) {
        assertTrue(lift.doorIsOpen());
    }

    private void assertDoorIsClosed(Lift lift) {
        assertFalse(lift.doorIsOpen());
    }

    @Test
	public void shouldOpenDoorWhenClickButtonAgain() {
        liftWithOpenDoor.pressButton();

        assertDoorIsOpen(liftWithOpenDoor);
    }

    @Test
    public void shouldDoorBeClosedAfterSelectingFloor(){
        liftWithOpenDoor.pressButton();

        liftWithOpenDoor.gotoFloor(SOME_FLOOR);

        assertDoorIsClosed(liftWithOpenDoor);
    }

//    @Test
//    public void shouldDoorBeOpenIfSameFloorIsSelected(){
//        final int SAME_FLOOR = 4;       // TODO extract me
//        liftWithOpenDoor.gotoFloor(SAME_FLOOR);
//
//        liftWithOpenDoor.gotoFloor(SAME_FLOOR);
//        assertDoorIsOpen(liftWithOpenDoor);
//    }

    @Test
    public void shouldDoorOpenOnSpecifiedFloor(){
        final int FLOOR = 34;

        liftWithOpenDoor.gotoFloor(FLOOR);

        assertEquals(FLOOR, liftWithOpenDoor.getCurrentFloor());
//TODO implement this
//        assertDoorIsOpen(liftWithOpenDoor);
    }

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
