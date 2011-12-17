package com.globallogic.training;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class Lift implements Serializable {
    private static final long serialVersionUID = 4646859136612305054L;

    private int position;
    private final Door door;
    private transient CurrentThread currentThread;
    private int floorsCount;
    private transient FloorQueue queue;
    private transient boolean started;
    private transient FloorListener floorListener;
    private int moveBetweenFloorsDelay;
    private int delayAfterOpen;

    public Lift(int position, int floorsCount, Door door, CurrentThread currentThread) {
        this.position = position;
        this.floorsCount = floorsCount;
        this.door = door;
        this.currentThread = currentThread;
        this.queue = new FloorQueue();
    }
    public Lift(int position, int floorsCount, Door door) {
        this(position, floorsCount, door, new NativeCurrentThread());
    }

    public void call(int floor) {
        if (door.isOpen() && floor == position) {
            notifyListener();
            return;
        }
        queue.addFloor(floor);
    }

    protected void processQueue() {
        while (!queue.isEmpty()) {
            int nextFloor = queue.getNextFloor(position);
            int increment = position < nextFloor ? 1 : -1;
            moveIncrementaly(nextFloor, increment);
            moveLift();
        }
    }

    private void moveIncrementaly(int nextFloor, int increment) {
        if (door.isOpen()) {
            door.close();
        }
        notifyListener();
        while (position != nextFloor) {
            position += increment;
            notifyListener();
            currentThread.sleep(moveBetweenFloorsDelay);
        }
    }

    private void notifyListener() {
        if (floorListener != null) {
            floorListener.atFloor(position);
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

    protected void moveLift() {
        door.open(position);
        currentThread.sleep(delayAfterOpen);
    }

    public void run() {
        while (started) {
            processQueue();
            //this should go away with actors implementation. for now have to deal with sleeps...
            currentThread.sleep(50);
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

    public void setFloorListener(FloorListener floorListener) {
        this.floorListener = floorListener;
    }

    public void setMoveBetweenFloorsDelay(int miliseconds) {
        this.moveBetweenFloorsDelay = miliseconds;
    }

    public int getMoveBetweenFloorsDelay() {
        return moveBetweenFloorsDelay;
    }

    private Object readResolve() throws ObjectStreamException {
        currentThread = new NativeCurrentThread(); //warn: not tested
        queue = new FloorQueue();
        return this;
    }

    public Door getDoor() {
        return door;
    }
            
    public void setDelayAfterOpen(int delayAfterOpen) {
        this.delayAfterOpen = delayAfterOpen;
    }
}
