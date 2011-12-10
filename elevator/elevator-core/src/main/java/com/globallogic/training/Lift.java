package com.globallogic.training;

import java.io.Serializable;

public class Lift implements Serializable {
    private static final long serialVersionUID = 4646859136612305054L;

    private int position;
    private final Door door;
    private int floorsCount;
    private transient final FloorQueue queue;
    private transient boolean started;

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

    public void moveLift(int floor) {
        if (door.isOpen()) {
            door.close();
        }
        position = floor;
        door.open(position);
    }

    public void run() {
        while (started) {
            processQueue();
        }
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public boolean isStarted() {
        return started;
    }
}
