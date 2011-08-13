package com.globallogic.elevator;

import org.easymock.internal.matchers.EndsWith;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class LiftTest {
    @Test
    public void shouldLiftComeWhenICallit() {
        Lift lift = new Lift(5);

        lift.callTo(3);

        assertEquals(3, lift.getPosition());
    }

    @Test
    public void shouldLiftBeOnTheSameFloorWhenCalledFromThisFloor() {  // TODO same as shouldLiftComeWhenICallit
        Lift lift = new Lift(7);

        lift.callTo(7);

        assertEquals(7, lift.getPosition());
    }

    @Test
    public void shouldDoorBeOpenWhenLiftComes() {
        Lift lift = new Lift(5);

        lift.callTo(4);

        assertTrue(lift.isOpenDoor());
    }
}
