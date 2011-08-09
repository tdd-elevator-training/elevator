package com.globallogic.training;

import org.junit.Before;
import org.junit.Ignore;
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
        lift.processQueue();

        // then
        door.assertWasOpened(SOME_FLOOR);
        door.assertIsOpen();
    }

    @Test
    public void shouldOpenDoorWhenClickButtonAgain() {
        givenLiftWithOpenDoor(SOME_FLOOR);

        // when
        lift.call(SOME_FLOOR);
        lift.processQueue();

        // then
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorBeClosedAfterSelectingFloor() throws ElevatorException {
        givenLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.moveTo(SOME_FLOOR);
        lift.processQueue();

        //then
        door.assertWasClosed();
        door.assertWasOpened(SOME_FLOOR);
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorBeOpenIfSameFloorIsSelected( ) throws ElevatorException {
        givenLiftWithOpenDoor(SOME_FLOOR);

        //when
        lift.moveTo(SOME_FLOOR);
        lift.processQueue();

        //then
        door.assertWasNotChanged();
        door.assertIsOpen();
    }

    @Test
    public void shouldDoorOpenOnSpecifiedFloor() throws ElevatorException {
        givenLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.moveTo(SOME_FLOOR);
        lift.processQueue();

        // then
        door.assertWasClosed();
        door.assertWasOpened(SOME_FLOOR);
        door.assertIsOpen();
    }

    @Test
    public void shouldBeExceprtionWhenFloorNumberIsOutOfRange() throws ElevatorException {
        givenLiftWithOpenDoor(FLOOR_COUNT - 1);

        try {
            // when
            lift.moveTo(FLOOR_COUNT + 1);
            lift.processQueue();

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
            lift.processQueue();

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
        lift.processQueue();

        // then
        door.assertWasOpened(SOME_FLOOR);
        door.assertIsOpen();
    }

    @Test
    public void shouldLiftMovedWithClosedDoorIfCallIt() {
        givenLiftWithOpenDoor(FLOOR_COUNT);

        // when
        lift.call(SOME_FLOOR);
        lift.processQueue();

        // then
        door.assertWasClosed();
        door.assertWasOpened(SOME_FLOOR);
        door.assertIsOpen();
    }

    @Test
    public void shouldPickAllUsers() throws ElevatorException {
        givenLiftWithClosedDoor(4);

        // when
        lift.call(2);
        lift.call(3);
        lift.moveTo(1);
        lift.processQueue();

        // then
        door.assertWasOpened(3);
        door.assertWasClosed();
        door.assertWasOpened(2);
        door.assertWasClosed();
        door.assertWasOpened(1);
        door.assertIsOpen();
    }

    private void givenLiftWithOpenDoor(int position) {
        lift = new Lift(position, FLOOR_COUNT, door);
        door.isOpen = true;
    }

    private void givenLiftWithClosedDoor(int position) {
        lift = new Lift(position, FLOOR_COUNT, door);
    }

    private static class LiftState {

        boolean isOpen;
        int floor;

        LiftState(boolean isOpen, int floor) {
            this.isOpen = isOpen;
            this.floor = floor;
        }

    }

    private static class MockDoor implements Door {
        private Queue<LiftState> stateStack;
        private boolean isOpen;
        private static final int FLOOR_NOT_CHECKS = 111;  // TODO remove me

        private MockDoor() {
            isOpen = false;
            stateStack = new LinkedList<LiftState>();
        }

        @Override
        public void open(int floor) {
            isOpen = true;
            stateStack.add(new LiftState(true, floor));
        }

        @Override
        public void close() {
            isOpen = false;
            stateStack.add(new LiftState(false, FLOOR_NOT_CHECKS));
        }

        @Override
        public boolean isOpen() {
            return isOpen;
        }

        void assertIsOpen() {
            assertTrue("Expected door is open but was close", isOpen);
        }

        void assertWasOpened(int floor) {
            if (stateStack.size() == 0) {
                fail("Expected door was opened but was no changes");
            }
            LiftState state = stateStack.poll();
            assertTrue("Expected door was opened but was closed", state.isOpen);
            assertEquals("Expected door was opened but was closed", floor, state.floor);
        }

        void assertWasClosed() {
            if (stateStack.size() == 0) {
                fail("Expected door was closed but was no changes");
            }
            assertFalse("Expected door was closed but was opened", stateStack.poll().isOpen);
        }

        void assertWasNotChanged() {
            assertEquals("Expected door has no changes", 0, stateStack.size());
        }
    }
}
