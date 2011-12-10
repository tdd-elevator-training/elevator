package com.globallogic.training;

public class NativeCurrentThread implements CurrentThread {
    @Override
    public void sleep(long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
