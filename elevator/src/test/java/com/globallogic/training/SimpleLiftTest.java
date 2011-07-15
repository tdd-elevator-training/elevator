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
    private Lift lift;

    @Before
    public void setUp() throws Exception {
        door = new MockDoor();
    }

    @Test
    public void shouldOpenDoorWhenClickButton() {
        givenLiftWithClosedDoor(SOME_FLOOR);

        // when
        lift.call(SOME_FLOOR);

        // then
        door.assertWasOpened();
        door.assertIsOpen();
    }

    @Test
    public void shouldOpenDoorWhenClickButtonAgain() {
        givenLiftWithOpenDoor(SOME_FLOOR);

        // when
        lift.call(SOME_FLOOR);

        // then
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorBeClosedAfterSelectingFloor() throws ElevatorException {
        givenLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.moveTo(SOME_FLOOR);

        //then
        door.assertWasClosed();
        door.assertWasOpened();
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorBeOpenIfSameFloorIsSelected( ) throws ElevatorException {
        givenLiftWithOpenDoor(SOME_FLOOR);

        //when
        lift.moveTo(SOME_FLOOR);

        //then
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorOpenOnSpecifiedFloor() throws ElevatorException {
        givenLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.moveTo(SOME_FLOOR);

        // then
        door.assertWasClosed();
        door.assertWasOpened();
        door.assertIsOpen();

        assertLiftAt(SOME_FLOOR);
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldBeExceprtionWhenFloorNumberIsOutOfRange() throws ElevatorException {
        givenLiftWithOpenDoor(FLOOR_COUNT - 1);

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
    public void shouldBeExceptionWhenSelectingNegativeFloor() throws ElevatorException {
        givenLiftWithOpenDoor(SOME_FLOOR);

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
    public void shouldLiftGoToFloorIfCallItFromAnotherFloor() {
        givenLiftWithClosedDoor(FLOOR_COUNT);

        // when
        lift.call(SOME_FLOOR);

        // then
        door.assertWasOpened();
        door.assertIsOpen();

        assertLiftAt(SOME_FLOOR);
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldLiftMovedWithClosedDoorIfCallIt() {
        givenLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.call(SOME_FLOOR);

        // then
        door.assertWasClosed();
        door.assertWasOpened();
        door.assertIsOpen();

        assertLiftAt(SOME_FLOOR);
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    private void assertLiftAt(int position) {
        assertEquals(position, lift.getPosition());
    }

    private void givenLiftWithOpenDoor(int position) {
        lift = new Lift(position, FLOOR_COUNT, door);
        door.isOpen = true;
    }

    private void givenLiftWithClosedDoor(int position) {
        lift = new Lift(position, FLOOR_COUNT, door);
    }

    private static class MockDoor implements Door {
        private Queue<Boolean> stateStack;
        private boolean isOpen;

        private MockDoor() {
            isOpen = false;
            stateStack = new LinkedList<Boolean>();
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
