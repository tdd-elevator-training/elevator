package com.elevator.ui.server;

import com.elevator.ui.shared.ElevatorPersistenceException;
import com.globallogic.training.Lift;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ElevatorServiceImplTest {
    @Test
    public void shouldCallDaoWhenCreate() throws ElevatorPersistenceException {
        ElevatorDao dao = EasyMock.createMock(ElevatorDao.class);
        Capture<Lift> liftCapture = new Capture<Lift>();
        dao.store(EasyMock.capture(liftCapture));
        EasyMock.replay(dao);

        ElevatorServiceImpl service = new ElevatorServiceImpl(dao);

        service.createElevator(5);

        Lift storedLift = liftCapture.getValue();

        assertEquals(5, storedLift.getFloorsCount());
    }

}
