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
        Lift lift = getLiftWithClosedDoor(SOME_FLOOR);

        // when
        lift.call(SOME_FLOOR);

        // then
        door.assertWasOpened();
        door.assertIsOpen();
    }

    @Test
    public void shouldOpenDoorWhenClickButtonAgain() {
        // given
        Lift lift = getLiftWithOpenDoor(SOME_FLOOR);

        // when
        lift.call(SOME_FLOOR);

        // then
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorBeClosedAfterSelectingFloor() throws ElevatorException {
        //given
        Lift lift = getLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.moveTo(SOME_FLOOR);

        //then
        door.assertWasClosed();
        door.assertWasOpened();
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorBeOpenIfSameFloorIsSelected( ) throws ElevatorException {
        //given
        Lift lift = getLiftWithOpenDoor(SOME_FLOOR);

        //when
        lift.moveTo(SOME_FLOOR);

        //then
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorOpenOnSpecifiedFloor() throws ElevatorException {
        // given
        Lift lift = getLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.moveTo(SOME_FLOOR);

        // then
        door.assertWasClosed();
        door.assertWasOpened();
        door.assertIsOpen();

        assertEquals(SOME_FLOOR, lift.getPosition());
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldThrowExceprtionWhenFloorNumberIsOutOfRange() throws ElevatorException {
        // given
        Lift lift = getLiftWithOpenDoor(FLOOR_COUNT - 1);

        try {
            // when
            lift.moveTo(FLOOR_COUNT + 1);

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
        Lift lift = getLiftWithOpenDoor(SOME_FLOOR);

        try {
            // when
            lift.moveTo(-1);

            fail("Expected exception");
        } catch (ElevatorException exception) {
            // then
            assertEquals(-1, exception.getSelectedFloor());
            assertEquals(SOME_FLOOR, exception.getCurrentFloor());
        }

    }

    @Test
    public void shouldLiftGoToFloorIfCallItFromAnother_liftWithClosedDoor() {
        // given
        Lift lift = getLiftWithClosedDoor(FLOOR_COUNT);

        // when
        lift.call(SOME_FLOOR);

        // then
        door.assertWasOpened();
        door.assertIsOpen();

        assertEquals(SOME_FLOOR, lift.getPosition());
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

     @Test
    public void shouldLiftGoToFloorIfCallItFromAnother_liftWithOpenedDoor() {
        // given
        Lift lift = getLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.call(SOME_FLOOR);

        // then
        door.assertWasClosed();
        door.assertWasOpened();
        door.assertIsOpen();

        assertEquals(SOME_FLOOR, lift.getPosition());
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    private Lift getLiftWithOpenDoor(int position) {
        Lift lift = new Lift(position, FLOOR_COUNT, door);
        lift.call(position);
        door.clearStates();
        return lift;
    }

    private Lift getLiftWithClosedDoor(int position) {
        Lift lift = new Lift(position, FLOOR_COUNT, door);
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

        void assertIsOpen() {
            assertTrue("Expected door is open but was close", isOpen);
        }

        void assertWasOpened() {
            if (stateStack.size() == 0) {
                fail("Expected door was opened but was no changes");
            }
            assertTrue("Expected door was opened but was closed", stateStack.poll());
        }

        void assertWasClosed() {
            if (stateStack.size() == 0) {
                fail("Expected door was closed but was no changes");
            }
            assertFalse("Expected door was closed but was opened", stateStack.poll());
        }

        void assertWasNotChanged() {
            assertEquals("Expected door has no changes", 0, stateStack.size());
        }
    }
}
