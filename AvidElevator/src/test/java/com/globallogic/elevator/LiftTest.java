package com.globallogic.elevator;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LiftTest {
    @Test
    public void shouldLiftComeWhenICallit() {
        Lift lift = new Lift(5);

        lift.callTo(3);

        assertEquals(3, lift.getPosition());
    }
}
