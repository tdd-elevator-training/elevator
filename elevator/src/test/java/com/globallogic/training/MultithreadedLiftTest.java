package com.globallogic.training;

import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MultithreadedLiftTest {

    private int countOpened = 0;
    public MultithreadedLift lift;
    public Door door;
    public Capture<Integer> openDoorCapture;

    @Before
    public void init() {
        door = EasyMock.createMock(Door.class);
        openDoorCapture = new Capture<Integer>(CaptureType.ALL);
    }

    @Test
    public void testConcurrentInvocation() {
        lift = new MultithreadedLift(7, 10, door);

        door.open(EasyMock.capture(openDoorCapture));
        EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                sleep(100);

                if (countOpened == 0) {
                    casualCall(6);
                    casualCall(7);
                }

                countOpened++;
                return null;
            }
        }).anyTimes();

        EasyMock.expect(door.isOpen()).andReturn(false).anyTimes();
        EasyMock.replay(door);

        lift.start();

        casualCall(3);
        casualCall(5);

        int counter = 0;
        while (counter < 10) {
            sleep(100);
//            lift.processQueue();
            counter++;
        }

        assertListWasAtFloors(5, 3, 6, 7);
    }

    private void assertListWasAtFloors(Integer... expectedFloors) {
        assertEquals(Arrays.asList(expectedFloors),
                openDoorCapture.getValues());
    }

    private void casualCall(final int floor) {
        sleep(10);
        new Thread(new Runnable() {

            @Override
            public void run() {
                sleep(10);
                lift.call(floor);
            }

        }).start();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
