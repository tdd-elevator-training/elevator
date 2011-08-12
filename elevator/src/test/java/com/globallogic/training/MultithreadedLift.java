package com.globallogic.training;

public class MultithreadedLift extends Lift implements Runnable {

    private Thread thread;
    private boolean started;

    public MultithreadedLift(int position, int floorsCount, Door door) {
        super(position, floorsCount, door);

        thread = new Thread(this);
    }

    public void start() {
        started = true;
        thread.start();
    }

    @Override
    public void run() {
        while (started) {
            sleep(100);
            processQueue();
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
