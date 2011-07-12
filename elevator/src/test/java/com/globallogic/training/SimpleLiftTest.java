package com.globallogic.training;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static junit.framework.Assert.*;

public class SimpleLiftTest {

    private final static int SOME_FLOOR = 2;
    private Lift lift;
    private MockDoor door;

    @Before
    public void setUp() throws Exception {
        door = new MockDoor();
    }

    @Test
    public void shouldOpenDoorWhenClickButton() {
        lift = getLiftWithClosedDoor();

        lift.pressButton();
        assertDoorWasOpened();
        assertDoorIsOpen();
    }

    @Test
    public void shouldOpenDoorWhenClickButtonAgain() {
        lift = getLiftWithOpenDoor();

        lift.pressButton();

        assertDoorWasNotChanged();
        assertDoorIsOpen();
    }

    @Test
    public void shouldDoorBeClosedAfterSelectingFloor() throws ElevatorException {
        lift = getLiftWithOpenDoor();

        lift.gotoFloor(SOME_FLOOR);
        assertDoorWasClosed();
        assertDoorWasOpened();
        assertDoorIsOpen();
    }

    @Test
    public void shouldDoorBeOpenIfSameFloorIsSelected( ) throws ElevatorException {
        lift = getLiftWithOpenDoor();
        lift.gotoFloor(4);
        assertDoorWasClosed();
        assertDoorWasOpened();

        lift.gotoFloor(4);
        assertDoorWasNotChanged();
        assertDoorIsOpen();
    }

    @Test
    public void shouldDoorOpenOnSpecifiedFloor() throws ElevatorException {
        lift = getLiftWithOpenDoor();

        lift.gotoFloor(34);
        assertDoorWasClosed();
        assertDoorWasOpened();
        assertDoorIsOpen();

        assertEquals(34, lift.getCurrentFloor());
        assertDoorWasNotChanged();
        assertDoorIsOpen();
    }

    @Test
    public void shouldThrowExceprtionWhenFloorNumberIsOutOfRange() throws ElevatorException {
        lift = getLiftWithOpenDoor(Lift.FLOOR_COUNT);

        lift.gotoFloor(Lift.FLOOR_COUNT - 1);
        try {
            lift.gotoFloor(Lift.FLOOR_COUNT + 1);

            fail("Expected exception");
        } catch (ElevatorException exception) {
            assertEquals(Lift.FLOOR_COUNT, exception.getSelectedFloor());
            assertEquals(Lift.FLOOR_COUNT - 1, exception.getCurrentFloor());
        }
    }

    @Test
    public void shouldThrowExceptionWhenSelectingNegativeFloor() throws ElevatorException {
        lift = getLiftWithOpenDoor(Lift.FLOOR_COUNT);

        lift.gotoFloor(SOME_FLOOR);
        try {
            lift.gotoFloor(-1);

            fail("Expected exception");
        } catch (ElevatorException exception) {
            assertEquals(SOME_FLOOR, exception.getSelectedFloor());
            assertEquals(- 1, exception.getCurrentFloor());
        }

    }

    private Lift getLiftWithOpenDoor(int maxFloorcount) {
        Lift lift = new Lift(maxFloorcount, door);
        lift.pressButton();
        door.clearStates();
        return lift;
    }


    @Test
    public void test(){

    }

    private void assertDoorIsOpen() {
        assertTrue("Expected door is open but was close", door.isOpen);
    }

    private void assertDoorWasOpened() {
        assertTrue("Expected door was opened but was closed", door.popState());
    }

    private void assertDoorWasClosed() {
        assertFalse("Expected door was closed but was opened", door.popState());
    }

    private void assertDoorWasNotChanged() {
        assertEquals(0, door.stateStack.size());
    }

    private Lift getLiftWithOpenDoor() {
        return getLiftWithOpenDoor(Lift.FLOOR_COUNT);
    }

    private Lift getLiftWithClosedDoor() {
        Lift lift = new Lift(Lift.FLOOR_COUNT, door);
        door.clearStates();
        return lift;
    }

    private static class MockDoor implements Door {
        private Queue<Boolean> stateStack;
        private boolean isOpen;

        private MockDoor() {
            isOpen = false;
            stateStack = new LinkedList<Boolean>();
        }

        void clearStates() {
            stateStack.clear();
        }

        @Override
        public void open() {
            isOpen = true;
            stateStack.add(true);
        }

        @Override
        public void close() {
            isOpen = false;
            stateStack.add(false);
        }

        @Override
        public boolean isOpen() {
            return isOpen;
        }

        boolean popState() {
            return stateStack.poll();
        }
    }
}
