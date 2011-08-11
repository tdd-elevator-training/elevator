package com.globallogic.training;

public class Lift {
	private int position;
    private final Door door;
    private int floorsCount;
    private final FloorQueue floorQueue;

    public Lift(int position, int floorsCount, Door door) {
        this.position = position;
        this.floorsCount = floorsCount;
        this.door = door;
        this.floorQueue = new FloorQueue();
    }

    public void call(int floor) {
        if (door.isOpen() && floor == position) {
            return;
        }

        floorQueue.addFloor(floor);
    }
    
    void processQueue() {
        while (!floorQueue.isEmpty()) {
            moveLift(floorQueue.getNextFloor(position));
        }
    }

    public void moveTo(int floor) throws ElevatorException {
        if (floor < 0 || floor > floorsCount) {
            throw new ElevatorException(floor, position);
        }

        if (floor == position && floorQueue.isEmpty()) {
            return;
        }

        floorQueue.addFloor(floor);
    }

    private void moveLift(int floor) {
        if (door.isOpen()) {
            door.close();
        }
        position = floor;
        door.open(position);
    }
}
