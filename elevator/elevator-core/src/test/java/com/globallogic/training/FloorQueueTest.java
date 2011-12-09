package com.globallogic.training;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import org.junit.Test;

public class FloorQueueTest {

    public FloorQueue queue;

    @Test
	public void test1() {
        createQueue(Direction.UNDEFINED, 2, 3, 1);

        assertNextFloor(3, 4);
    }

    @Test
	public void test2() {
        createQueue(Direction.DOWN, 3, 1);

        assertNextFloor(1, 2);
    }
	
	@Test
	public void test3() {
        createQueue(Direction.UP, 3, 1);

        assertNextFloor(3, 2);
    }
	
	@Test
	public void test4() {
		createQueue(Direction.UNDEFINED, 1,3);

        assertNextFloor(1, 2);
    }
	
	@Test
	public void test5() {
		createQueue(Direction.UNDEFINED, 7, 1);

        assertNextFloor(7, 6);
    }
	
	@Test
	public void test6() {
        createQueue(Direction.DOWN, 3, 4);

        assertNextFloor(3, 1);
    }

    @Test
	public void test7() {
        createQueue(Direction.UP, 3, 4);

        assertNextFloor(4, 6);
    }

	@Test
	public void test8() {
		createQueue(Direction.DOWN, 3);

        assertNextFloor(3, 2);
    }

	@Test
	public void test9() {
		createQueue(Direction.UP, 3);

        assertNextFloor(3, 4);
    }

	@Test
	public void test10() {
		createQueue(Direction.UNDEFINED, 3);

		assertFalse(queue.isEmpty());
	}

	@Test
	public void test11() {
		FloorQueue queue = new FloorQueue();
		assertTrue(queue.isEmpty());
	}

	@Test
	public void test12() {
		createQueue(Direction.UNDEFINED, 3,5,3);

        assertNextFloor(5, queue.getNextFloor(1));
        assertTrue(queue.isEmpty());
	}

	@Test
	public void test13() {
		createQueue(Direction.UNDEFINED, 3,4,2);

        assertNextFloor(2, 1);
    }

    @Test
    public void test14(){
        queue = new FloorQueue();
        queue.addFloor(1);
        assertNextFloor(1, 3);
    }

    private void createQueue(Direction direction, int... floors) {
        queue = new FloorQueue(direction);
        for (int floor : floors) {
            queue.addFloor(floor);
        }
    }
    
    private void assertNextFloor(int expectedNextFloor, int fromFloor) {
        assertEquals(expectedNextFloor, queue.getNextFloor(fromFloor));
    }
}
