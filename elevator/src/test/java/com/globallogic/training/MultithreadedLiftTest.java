package com.globallogic.training;

import com.google.testing.threadtester.*;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MultithreadedLiftTest {

    private ThreadedTestRunner runner = new ThreadedTestRunner();

    @Test
    public void testThreadedTests() {
      runner.runTests(getClass(), Lift.class);
    }

    @ThreadedTest
    public void runConcurrentInvocation() {
        CodePosition beforeDoorOpenedAt5 = makeBreakpoint();

        RunResult result = InterleavedRunner.interleave(new MainThread(),
                new SecondaryThread(), Arrays.asList(beforeDoorOpenedAt5));
        result.throwExceptionsIfAny();
    }

    private CodePosition makeBreakpoint() {
        MethodRecorder<Lift> recorder = new MethodRecorder<Lift>(Lift.class);
        Lift lift = recorder.getControl();
        lift.moveLift(5);
        recorder.inLastMethod();
        Door door = recorder.createTarget(Door.class);
        door.open(5);
        return recorder.beforeCallingLastMethod().position();
    }

    private static class SecondaryThread extends SecondaryRunnableImpl<Lift, MainThread> {
        private Lift lift;

        @Override
        public void initialize(MainThread main) throws Exception {
            lift = main.getMainObject();
        }

        @Override
        public void run() throws Exception {
            lift.call(6);
            lift.call(7);
            lift.stop();
        }
    }

    private class MainThread extends MainRunnableImpl<Lift> {
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
            openDoorCapture = setupDoorOpenCapture();
            lift = new Lift(7, 10, door);

            EasyMock.replay(door);
        }

        private Capture<Integer> setupDoorOpenCapture() {
            Capture<Integer> capture = new Capture<Integer>(CaptureType.ALL);
            door.open(EasyMock.capture(capture));
            EasyMock.expectLastCall().anyTimes();
            EasyMock.expect(door.isOpen()).andReturn(false).anyTimes();

            return capture;
        }

        @Override
        public void run() throws Exception {
            lift.call(5);
            lift.call(3);
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
