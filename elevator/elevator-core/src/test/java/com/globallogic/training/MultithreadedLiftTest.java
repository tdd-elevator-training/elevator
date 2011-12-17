package com.globallogic.training;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultithreadedLiftTest {

    private LiftPool lift;
    private Door door = new MockDoor();
    private boolean liftStoped = false;
    private List<Integer> wasAt = new LinkedList<Integer>();
    private Collection<OpenAction> actions = new LinkedList<OpenAction>();

    abstract class OpenAction {

        private int weAt;

        public OpenAction(int weAt) {
            this.weAt = weAt;
        }

        public void action(int floor) {
            if (floor == weAt) {
                open(floor);
            }
        }

        public abstract void open(int floor);
    }

    class MockDoor implements Door {

        @Override
        public void open(int floor) {
            wasAt.add(floor);
            for (OpenAction action : actions) {
                action.action(floor);
            }
        }

        @Override
        public void close() {
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public void setDoorSpeed(int milliseconds) {

        }

        @Override
        public int getDoorSpeed() {
            return 0;
        }
    }

    @Test
    @Ignore
    public void testConcurrentInvocation() {
        lift = new LiftPool(7, 10, door);

        callFrom(3);
        callFrom(5);

        whenWeAt(5).moveTo(7, 6);
        whenWeAt(7).stopLiftAndCheckThatWeWasAt(5, 3, 6, 7);

        lift.start();

        waitForListStops();
    }

    private void waitForListStops() {
        while (!liftStoped) {}
    }

    private Checker whenWeAt(int floor) {
        Checker checker = new Checker();
        checker.addWeAtFloor(floor);
        return checker;
    }

    class Checker {

        private int weAt;

        public void addWeAtFloor(int floor) {
            weAt = floor;
        }

        public void moveTo(final Integer... floors) {
            addAction(new OpenAction(weAt) {
                @Override
                public void open(int floor) {
                    for (int fl : floors) {
                        MultithreadedLiftTest.this.moveTo(fl);
                        sleep(10); // TODO если заремарить это то будет валиться тест, почему?
                    }
                }
            });
        }

        public void stopLiftAndCheckThatWeWasAt(final Integer... floors) {
            addAction(new OpenAction(weAt) {
                @Override
                public void open(int floor) {
                    lift.stop();
                    assertListWasAtFloors(floors);
                    liftStoped = true;
                }
            });
        }

    }

    private void addAction(OpenAction actions) {
        this.actions.add(actions);
    }

    private void assertListWasAtFloors(Integer... expectedFloors) {
        assertEquals(Arrays.asList(expectedFloors).toString(),
                wasAt.toString());
    }

    private void callFrom(final int floor) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lift.call(floor);
            }
        }).start();
    }

    private void moveTo(final int floor) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lift.moveTo(floor);
                } catch (ElevatorException e) {

                }
            }
        }).start();
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
