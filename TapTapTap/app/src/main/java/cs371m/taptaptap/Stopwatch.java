package cs371m.taptaptap;

import java.lang.System;

/**
 * Stop Watch class
 */

public class Stopwatch {

    private final long NANO_SECONDS = 1000000000;

    private long startTime;
    private long stopTime;
    private boolean stopped;

    public Stopwatch() {
        startTime = stopTime = 0;
        stopped = true;
    }

    public void startTimer() {
        stopped = false;
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        stopped = true;
        stopTime = System.nanoTime();
    }

    public void resetTimer() {
        startTime = stopTime = 0;
        stopped = true;
    }

    public double getTime() {
        double totalTime;
        if (!stopped)
            totalTime = (System.nanoTime() - startTime);
        else
            totalTime = (stopTime - startTime);

        return totalTime / NANO_SECONDS;
    }

    @Override
    public String toString() {
        double totalTime = getTime();
        StringBuilder sb = new StringBuilder();
        return sb.append(totalTime).toString();
    }
}