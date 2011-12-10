package com.globallogic.training;

public class MockCurrentThread implements CurrentThread {
    public long totalSleepTime;

    @Override
    public void sleep(long miliseconds) {
        totalSleepTime += miliseconds;
    }
}
