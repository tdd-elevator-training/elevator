package com.globallogic.training;

import java.util.LinkedList;
import java.util.Queue;

import static junit.framework.Assert.*;

class MockDoor implements Door {
    private Queue<LiftState> stateStack;
    boolean isOpen;
    private static final int FLOOR_NOT_CHECKS = 111;  // TODO remove me

    MockDoor() {
        isOpen = false;
        stateStack = new LinkedList<LiftState>();
    }

    public void open(int floor) {
        isOpen = true;
        stateStack.add(new LiftState(true, floor));
    }

    public void close() {
        isOpen = false;
        stateStack.add(new LiftState(false, FLOOR_NOT_CHECKS));
    }

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
        assertEquals("opened at:", floor, state.floor);
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
