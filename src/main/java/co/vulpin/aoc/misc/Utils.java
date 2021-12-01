package co.vulpin.aoc.misc;

import co.vulpin.aoc.data.TimeResult;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class Utils {

    public static <T> TimeResult<T> timeExecution(Supplier<T> supplier) {
        var start = Instant.now();
        var t = supplier.get();
        var end = Instant.now();
        return new TimeResult<>(t, Duration.between(start, end));
    }

}
