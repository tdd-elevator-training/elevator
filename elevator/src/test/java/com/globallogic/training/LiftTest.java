package com.globallogic.training;

import org.junit.Test;

import java.util.ListIterator;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;


public class LiftTest {

	@Test
	public void shouldOpenDoorWhenClickButton() {
        Lift lift = new Lift();

        lift.pressButton();

        assertTrue(lift.doorIsOpen());
    }
}
