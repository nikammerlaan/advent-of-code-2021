package co.vulpin.aoc.days;

import co.vulpin.aoc.data.SolveResult;
import co.vulpin.aoc.misc.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ForkJoinPool;

public abstract class AbstractSeparateDaySolution<E> extends AbstractJoinedDaySolution<E> {

    @Override
    public SolveResult solve(E input) {
        var part1Future = ForkJoinPool.commonPool()
            .submit(() -> Utils.timeExecution(() -> solvePart1(input)));
        var part2Future = ForkJoinPool.commonPool()
            .submit(() -> Utils.timeExecution(() -> solvePart2(input)));

        var part1TimeResult = part1Future.join();
        var part2TimeResult = part2Future.join();

        return new SolveResult(part1TimeResult, part2TimeResult);
    }

    protected abstract Object solvePart1(E input);

    protected abstract Object solvePart2(E input);

    public String retrieveRawInput() {
        try {
            var path = "src/main/java/" + getClass().getPackageName().replace(".", "/") + "/input.txt";
            return Files.readString(new File(path).toPath());
        } catch(IOException e) {
            throw new RuntimeException("Error while reading input", e);
        }
    }

}
