package co.vulpin.aoc.days;

import co.vulpin.aoc.data.Answer;
import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.misc.Utils;

public abstract class AbstractDaySolution<E> implements DaySolution {

    @Override
    public Result calculateAnswers(String rawInput) {
        var parseTimeResult = Utils.timeExecution(() -> parseInput(rawInput));
        var input = parseTimeResult.result();

        var answer = solve(input);

        return new Result(parseTimeResult, answer.part1(), answer.part2());
    }

    protected abstract Answer solve(E input);

    protected abstract E parseInput(String rawInput);

}
