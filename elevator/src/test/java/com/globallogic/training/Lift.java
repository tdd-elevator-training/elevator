package com.globallogic.training;

public class Lift {
    private int position;
    private final Door door;
    private int floorsCount;

    public Lift(int position, int floorsCount, Door door) {
        this.position = position;
        this.floorsCount = floorsCount;
        this.door = door;
    }

    public void call() {
        if (door.isOpen()) {
            return;
        }
        door.open();
    }

    public void call(int floor) {
        if (door.isOpen()) {
            return;
        }
        position = floor;
        door.open();

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
        door.close();
        this.position = floor;
        door.open();
    }

    public int getPosition() {
        return position;
    }
}
