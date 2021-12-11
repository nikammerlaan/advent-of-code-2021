package co.vulpin.aoc.days;

import co.vulpin.aoc.data.Answer;
import co.vulpin.aoc.misc.Utils;

import java.util.concurrent.ForkJoinPool;

public abstract class AbstractDayParallelSolution<E> extends AbstractDaySolution<E> {

    @Override
    protected Answer solve(E input) {
        var part1Future = ForkJoinPool.commonPool()
            .submit(() -> Utils.timeExecution(() -> solvePart1(input)));
        var part2Future = ForkJoinPool.commonPool()
            .submit(() -> Utils.timeExecution(() -> solvePart2(input)));

        var part1TimeResult = part1Future.join();
        var part2TimeResult = part2Future.join();

        return new Answer(part1TimeResult, part2TimeResult);
    }

    protected abstract Object solvePart1(E input);

    protected abstract Object solvePart2(E input);

}
