package com.globallogic.training;

import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.*;

public class SimpleLiftTest {

    private final static int SOME_FLOOR = 2;
    public static final int FLOOR_COUNT = 99;
    private MockDoor door;
    private Lift lift;
    private FloorListenerFixture floorListenerFixture;
    private FloorListener floorListener;
    private Capture<Integer> atFloorCapture;

    @Before
    public void setUp() throws Exception {
        door = new MockDoor();
        floorListenerFixture = new FloorListenerFixture().setUp();
        floorListener = floorListenerFixture.getFloorListener();
        atFloorCapture = floorListenerFixture.getAtFloorCapture();
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
    public void shouldPickAllUsersDown() throws ElevatorException {
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

    @Test
    public void shouldPickAllUsersUp() throws ElevatorException {
        givenLiftWithClosedDoor(1);

        // when
        lift.call(3);
        lift.call(2);
        lift.moveTo(4);
        lift.processQueue();

        // then
        door.assertWasOpened(2);
        door.assertWasClosed();
        door.assertWasOpened(3);
        door.assertWasClosed();
        door.assertWasOpened(4);
        door.assertIsOpen();
    }

    @Test
    public void shouldLiftGoesDownFirstly() throws ElevatorException {
        givenLiftWithClosedDoor(4);

        // when
        lift.call(2);
        lift.call(6);
        lift.moveTo(1);
        lift.processQueue();

        // then
        door.assertWasOpened(2);
        door.assertWasClosed();
        door.assertWasOpened(1);
        door.assertWasClosed();
        door.assertWasOpened(6);
        door.assertIsOpen();
    }

    @Test
    public void shouldLiftGoesUpFirsltyThenPickAllUsersDown() throws ElevatorException {
        givenLiftWithClosedDoor(4);

        // when
        lift.call(9);
        lift.call(2);
        lift.moveTo(1);
        lift.processQueue();

        // then
        door.assertWasOpened(9);
        door.assertWasClosed();
        door.assertWasOpened(2);
        door.assertWasClosed();
        door.assertWasOpened(1);
        door.assertIsOpen();
    }

    @Test
    public void shouldLiftGoesDownFirsltyThenPickAllUsersUp() throws ElevatorException {
        givenLiftWithClosedDoor(3);

        // when
        lift.call(2);
        lift.call(9);
        lift.moveTo(4);
        lift.processQueue();

        // then
        door.assertWasOpened(2);
        door.assertWasClosed();
        door.assertWasOpened(4);
        door.assertWasClosed();
        door.assertWasOpened(9);
        door.assertIsOpen();
    }

    @Test
    public void shouldLiftMoveToSameFloor() throws ElevatorException {
        givenLiftWithClosedDoor(3);

        // when
        lift.call(4);
        lift.moveTo(3);
        lift.processQueue();

        // then
        door.assertWasOpened(3);
        door.assertWasClosed();
        door.assertWasOpened(4);
        door.assertIsOpen();
    }

    @Test
    public void shouldLiftCallToSameFloor() throws ElevatorException {
        givenLiftWithOpenDoor(3);

        // when
        lift.moveTo(4);
        lift.call(3);
        lift.processQueue();

        // then
        door.assertWasClosed();
        door.assertWasOpened(4);
        door.assertIsOpen();
    }

    @Test
    public void shouldCallIndicatorWhenMovingUp() {
        givenLiftWithClosedDoor(1);

        lift.setFloorListener(floorListener);
        lift.call(4);
        lift.processQueue();

        assertLiftWasOnFloors(atFloorCapture, 1, 2, 3, 4);
    } 

    @Test
    public void shouldCallIndicatorWhenMovingDown() {
        givenLiftWithClosedDoor(10);

        lift.setFloorListener(floorListener);
        lift.call(7);
        lift.processQueue();

        assertLiftWasOnFloors(atFloorCapture, 10, 9, 8, 7);
    }
    
    @Test
    public void shouldCloseDoorWhenMoving() {
        givenLiftWithOpenDoor(1);

        MockFloorListener floorListener = new MockFloorListener();
        lift.setFloorListener(floorListener);
        lift.call(2);
        lift.processQueue();
        
        assertDoorClosedAt(floorListener, 1);
    }

    private void assertDoorClosedAt(MockFloorListener floorListener, int floorNumber) {
        assertNotNull("Door should be closed at floor: " + floorNumber, floorListener.doorTrack.get(floorNumber));
        assertFalse("Door should be closed at floor: " + floorNumber, floorListener.doorTrack.get(floorNumber));
    }

    private void assertLiftWasOnFloors(Capture<Integer> atFloorCapture, Integer... expectedFloors) {
        Assert.assertEquals(Arrays.asList(expectedFloors),
                atFloorCapture.getValues());
    }
    
    private void givenLiftWithOpenDoor(int position) {
        lift = new Lift(position, FLOOR_COUNT, door);
        door.isOpen = true;
    }

    private void givenLiftWithClosedDoor(int position) {
        lift = new Lift(position, FLOOR_COUNT, door);
    }

    private class FloorListenerFixture {
        private FloorListener floorListener;
        private Capture<Integer> atFloorCapture;

        public FloorListener getFloorListener() {
            return floorListener;
        }

        public Capture<Integer> getAtFloorCapture() {
            return atFloorCapture;
        }

        public FloorListenerFixture setUp() {
            floorListener = EasyMock.createMock(FloorListener.class);
            atFloorCapture = new Capture<Integer>(CaptureType.ALL);
            floorListener.atFloor(EasyMock.capture(atFloorCapture));
            EasyMock.expectLastCall().anyTimes();
            EasyMock.replay(floorListener);
            return this;
        }
    }

    private class MockFloorListener implements FloorListener {
        private Map<Integer, Boolean> doorTrack = new HashMap<Integer, Boolean>();
        
        @Override
        public void atFloor(int floorNumber) {
            doorTrack.put(floorNumber, door.isOpen);
        }
    }
}
