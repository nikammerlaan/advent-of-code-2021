package co.vulpin.aoc.days;

import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.misc.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ForkJoinPool;

public abstract class DaySolution<E> {

    public Result calculateAnswers() {
        var rawInput = retrieveRawInput();
        var parseTimeResult = Utils.timeExecution(() -> parseInput(rawInput));
        var input = parseTimeResult.result();

        var part1Future = ForkJoinPool.commonPool()
            .submit(() -> Utils.timeExecution(() -> solvePart1(input)));
        var part2Future = ForkJoinPool.commonPool()
            .submit(() -> Utils.timeExecution(() -> solvePart2(input)));

        var part1TimeResult = part1Future.join();
        var part2TimeResult = part2Future.join();

        return new Result(parseTimeResult, part1TimeResult, part2TimeResult);
    }

    protected abstract Object solvePart1(E input);

    protected abstract Object solvePart2(E input);

    protected abstract E parseInput(String input);

    public String retrieveRawInput() {
        try {
            var path = "src/main/java/" + getClass().getPackageName().replace(".", "/") + "/input.txt";
            return Files.readString(new File(path).toPath());
        } catch(IOException e) {
            throw new RuntimeException("Error while reading input", e);
        }
    }

}
