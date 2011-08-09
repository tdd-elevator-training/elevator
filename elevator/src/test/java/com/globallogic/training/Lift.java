package com.globallogic.training;

import java.util.*;

public class Lift {

    private int position;
    private final Door door;
    private int floorsCount;
    private List<Integer> floorQueue;

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
    }

    void processQueue() {
        final boolean direction = getDirection();

        Collections.sort(floorQueue, new Comparator<Integer>() {

            @Override
            public int compare(Integer from, Integer to) {
                return (direction) ? (to - from) : (from - to);
            }

        });
        for (Integer floor : floorQueue) {
            if (door.isOpen()) {
                door.close();
            }
            position = floor;
            door.open(position);
        }
    }

    private boolean getDirection() {
        for (Integer floor : floorQueue) {
            if (floor < position) {
                return true;
            }
        }
        return false;
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
    }

}
