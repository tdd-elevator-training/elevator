package com.globallogic.training;

public class Lift {
    private int position;
    private final Door door;
    private int floorsCount;


    public Lift(int maxFloorcount, Door door) {
        this.floorsCount = maxFloorcount;
        this.door = door;
    }

    public void call() {
        if (door.isOpen()) {
            return;
        }
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
