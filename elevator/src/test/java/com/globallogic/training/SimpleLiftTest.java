package com.globallogic.training;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static junit.framework.Assert.*;

public class SimpleLiftTest {

    private final static int SOME_FLOOR = 2;
    public static final int FLOOR_COUNT = 99;
    private MockDoor door;

    @Before
    public void setUp() throws Exception {
        door = new MockDoor();
    }

    @Test
    public void shouldOpenDoorWhenClickButton() {
        // given
        Lift lift = getLiftWithClosedDoor();

        // when
        lift.pressButton();

        // then
        assertDoorWasOpened();
        assertDoorIsOpen();
    }

    @Test
    public void shouldOpenDoorWhenClickButtonAgain() {
        // given
        Lift lift = getLiftWithOpenDoor();

        // when
        lift.pressButton();

        // then
        assertDoorWasNotChanged();
        assertDoorIsOpen();
    }

    @Test
    public void shouldDoorBeClosedAfterSelectingFloor() throws ElevatorException {
        //given
        Lift lift = getLiftWithOpenDoor();

        // when
        lift.gotoFloor(SOME_FLOOR);

        //then
        assertDoorWasClosed();
        assertDoorWasOpened();
        assertDoorIsOpen();
    }

    @Test
    public void shouldDoorBeOpenIfSameFloorIsSelected( ) throws ElevatorException {
        //given
        Lift lift = getLiftWithOpenDoor();

        lift.gotoFloor(SOME_FLOOR);
        assertDoorWasClosed();
        assertDoorWasOpened();

        //when
        lift.gotoFloor(SOME_FLOOR);

        //then
        assertDoorWasNotChanged();
        assertDoorIsOpen();
    }

    @Test
    public void shouldDoorOpenOnSpecifiedFloor() throws ElevatorException {
        // given
        Lift lift = getLiftWithOpenDoor();

        // when
        lift.gotoFloor(SOME_FLOOR);

        // then
        assertDoorWasClosed();
        assertDoorWasOpened();
        assertDoorIsOpen();

        assertEquals(SOME_FLOOR, lift.getCurrentFloor());
        assertDoorWasNotChanged();
        assertDoorIsOpen();
    }

    @Test
    public void shouldThrowExceprtionWhenFloorNumberIsOutOfRange() throws ElevatorException {
        // given
        Lift lift = getLiftWithOpenDoor(FLOOR_COUNT);
        lift.gotoFloor(FLOOR_COUNT - 1);

        try {
            // when
            lift.gotoFloor(FLOOR_COUNT + 1);

            fail("Expected exception");
        } catch (ElevatorException exception) {
            // then
            assertEquals(FLOOR_COUNT + 1, exception.getSelectedFloor());
            assertEquals(FLOOR_COUNT - 1, exception.getCurrentFloor());
        }
    }

    @Test
    public void shouldThrowExceptionWhenSelectingNegativeFloor() throws ElevatorException {
        // given
        Lift lift = getLiftWithOpenDoor();
        lift.gotoFloor(SOME_FLOOR);

        try {
            // when
            lift.gotoFloor(-1);

            fail("Expected exception");
        } catch (ElevatorException exception) {
            // then
            assertEquals(-1, exception.getSelectedFloor());
            assertEquals(SOME_FLOOR, exception.getCurrentFloor());
        }

    }

    private Lift getLiftWithOpenDoor(int maxFloorCount) {
        Lift lift = new Lift(maxFloorCount, door);
        lift.pressButton();
        door.clearStates();
        return lift;
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
        return getLiftWithOpenDoor(FLOOR_COUNT);
    }

    private Lift getLiftWithClosedDoor() {
        Lift lift = new Lift(FLOOR_COUNT, door);
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
