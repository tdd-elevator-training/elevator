package com.globallogic.training;

public class LiftPool implements Runnable {

    private Thread thread;
    private Lift lift;

    public LiftPool(int position, int floorsCount, Door door) {
        lift = new Lift(position, floorsCount, door);

        thread = new Thread(this);
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        lift.setStarted(true);
        lift.run();
    }

    public void call(int floor) {
        lift.call(floor);
    }

    public void moveTo(int floor) throws ElevatorException {
        lift.moveTo(floor);
    }

    public void stop() {
        lift.setStarted(false);
    }

}
