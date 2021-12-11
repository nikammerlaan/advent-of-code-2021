package co.vulpin.aoc.misc;

import java.time.Duration;
import java.time.Instant;

public class Timer {

    private Instant start;

    public Timer() {
        this.start = null;
    }

    public void start() {
        if(start != null) {
            throw new IllegalStateException("Timer is already started!");
        }
        start = Instant.now();
    }

    public Duration stop() {
        if(start == null) {
            throw new IllegalStateException("Timer is already stopped!");
        }
        var stop = Instant.now();
        var duration = Duration.between(start, stop);
        start = null;
        return duration;
    }

}
