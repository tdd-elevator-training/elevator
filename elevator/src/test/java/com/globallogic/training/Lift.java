package com.globallogic.training;

public class Lift {
	private int position;
    private final Door door;
    private int floorsCount;
    private final FloorQueue queue;

    public Lift(int position, int floorsCount, Door door) {
        this.position = position;
        this.floorsCount = floorsCount;
        this.door = door;
        this.queue = new FloorQueue();
    }

    public void call(int floor) {
        if (door.isOpen() && floor == position) {
            return;
        }

        queue.addFloor(floor);
    }

    protected void processQueue() {
        while (!queue.isEmpty()) {
            moveLift(queue.getNextFloor(position));
        }
    }

    public void moveTo(int floor) throws ElevatorException {
        if (floor < 0 || floor > floorsCount) {
            throw new ElevatorException(floor, position);
        }

        if (floor == position && queue.isEmpty()) {
            return;
        }

        queue.addFloor(floor);
    }

    private void moveLift(int floor) {
        if (door.isOpen()) {
            door.close();
        }
        position = floor;
        door.open(position);
    }
}
