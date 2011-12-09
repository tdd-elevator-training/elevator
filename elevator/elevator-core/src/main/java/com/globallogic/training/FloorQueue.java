package com.globallogic.training;

import java.util.LinkedList;
import java.util.List;

public class FloorQueue {

    private List<Integer> queue = new LinkedList<Integer>();
	private Direction direction = Direction.UNDEFINED;
	private int fromFloor = 0;

	public FloorQueue() {
	}

	FloorQueue(Direction direction) {
		this.direction = direction;
	}
	
	public void addFloor(int floor) {
		if (queue.indexOf(floor) == -1) {
			queue.add(floor);
		}
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public int getNextFloor(int fromFloor) {
		this.fromFloor = fromFloor;
        calculateDirection();

		return process(getNearest());
	}

	private int getNearest() {		
		if (queue.size() == 1) {
			return queue.get(0);
		}
		
		if (direction.equals(Direction.UP)) {
			return getUpNearest();
		} else {
			return getDownNearest();
		}
	}

	private int process(int floor) {
		queue.remove(queue.indexOf(floor));

		if (queue.isEmpty()) {
			direction = Direction.UNDEFINED;
		}

		return floor;
	}

	private int getDownNearest() {
		Integer nearest = null;
		for (int floor : queue) {
			if (floor > fromFloor) {
				continue;
			}

			if (nearest == null || floor > nearest) {
				nearest = floor;
			}
		}

		if (nearest == null) {
			direction = Direction.UP;
			return getUpNearest();
		}

		return nearest;
	}

	private int getUpNearest() {
		Integer nearest = null;
		for (int floor : queue) {
			if (floor < fromFloor) {
				continue;
			}

			if (nearest == null || floor < nearest) {
				nearest = floor;
			}
		}

		if (nearest == null) {
			direction = Direction.DOWN;
			return getDownNearest();
		}

		return nearest;
	}

	private void calculateDirection() {
        if (direction.equals(Direction.UNDEFINED)) {
            direction = (queue.get(0) - fromFloor > 0) ? Direction.UP : Direction.DOWN;
        }
    }
}
