package co.vulpin.aoc.misc;

import co.vulpin.aoc.data.TimeResult;

import java.util.function.Supplier;

public class Utils {

    public static <T> TimeResult<T> timeExecution(Supplier<T> supplier) {
        var timer = new Timer();
        timer.start();
        var t = supplier.get();
        var duration = timer.stop();
        return new TimeResult<>(t, duration);
    }

}
