package com.globallogic.training;

import com.google.testing.threadtester.*;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import com.google.testing.threadtester.*;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ThredweaverMultithreadedLiftTest {

    ThreadedTestRunner runner = new ThreadedTestRunner();

    @Test
    public void testThreadedTests() {
      runner.runTests(getClass(), Lift.class);
    }

    @ThreadedTest
    public void testConcurrentInvocation2() {
        MethodRecorder<Lift> recorder = new MethodRecorder<Lift>(Lift.class);
        Lift lift = recorder.getControl();
        lift.moveLift();
        recorder.inLastMethod();
        Door door = recorder.createTarget(Door.class);
        door.open(5);
        CodePosition afterDoorOpenedAt5 = recorder.beforeCallingLastMethod().position();

        RunResult result = InterleavedRunner.interleave(new MultithreadedLiftMainRunnable(),
                new MultithreadedLifSecondaryRunnableImpl(), Arrays.asList(afterDoorOpenedAt5));
        result.throwExceptionsIfAny();
    }

    private static class MultithreadedLifSecondaryRunnableImpl extends SecondaryRunnableImpl<Lift, MultithreadedLiftMainRunnable> {
        private Lift lift;

        @Override
        public void initialize(MultithreadedLiftMainRunnable main) throws Exception {
            lift = main.getMainObject();
        }

        @Override
        public void run() throws Exception {
            lift.call(6);
            lift.call(7);
            lift.setStarted(false);
        }
    }

    private class MultithreadedLiftMainRunnable extends MainRunnableImpl<Lift> {
        private Lift lift;
        private Door door;
        public Capture<Integer> openDoorCapture;

        @Override
        public Class<Lift> getClassUnderTest() {
            return Lift.class;
        }

        @Override
        public Lift getMainObject() {
            return lift;
        }

        @Override
        public void initialize() throws Exception {
            door = EasyMock.createMock(Door.class);
            openDoorCapture = new Capture<Integer>(CaptureType.ALL);
            door.open(EasyMock.capture(openDoorCapture));
            EasyMock.expectLastCall().anyTimes();
            EasyMock.expect(door.isOpen()).andReturn(false).anyTimes();
            lift = new Lift(7, 10, door, new MockCurrentThread());
            EasyMock.replay(door);
        }

        @Override
        public void run() throws Exception {
            lift.call(5);
            lift.call(3);
            lift.setStarted(true);
            lift.run();
        }

        @Override
        public void terminate() throws Exception {
            assertListWasAtFloors(5, 3, 6, 7);
        }

        private void assertListWasAtFloors(Integer... expectedFloors) {
            assertEquals(Arrays.asList(expectedFloors),
                    openDoorCapture.getValues());
        }

    }
}