package com.globallogic.training;

import java.awt.font.OpenType;

public class Lift {
    private boolean open;

    public void pressButton() {
        open = true;
    }

    public boolean doorIsOpen() {
        return open;
    }

    public void gotoFloor(int floor) {
        open = false;
    }
}
