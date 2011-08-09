package com.globallogic.training;

import java.util.LinkedList;
import java.util.Queue;

public class Lift {

    private int position;
    private final Door door;
    private int floorsCount;
    private Queue<Integer> floorQueue;

    public Lift(int position, int floorsCount, Door door) {
        this.position = position;
        this.floorsCount = floorsCount;
        this.door = door;
        floorQueue = new LinkedList();
    }

    public void call(int floor) {
        if (door.isOpen() && floor == position) {
            return;
        }

        floorQueue.add(floor);
        processQueue();
    }

    private void processQueue() {
        for (Integer floor : floorQueue) {
            if (door.isOpen()) {
                door.close();
            }
            position = floor;
            door.open(position);
        }
    }

    public void moveTo(int floor) throws ElevatorException {
        if (floor < 0) {
            throw new ElevatorException(floor, position);
        }

        if (floor > floorsCount) {
            throw new ElevatorException(floor, position);
        }
        if (floor == position) {
            return;
        }

        floorQueue.add(floor);
        processQueue();
    }

}
