package co.vulpin.aoc.days;

import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.data.SolveResult;
import co.vulpin.aoc.misc.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class AbstractJoinedDaySolution<E> implements DaySolution {

    @Override
    public Result calculateAnswers() {
        var rawInput = retrieveRawInput();
        var parseTimeResult = Utils.timeExecution(() -> parseInput(rawInput));
        var input = parseTimeResult.result();

        var solveResult = solve(input);

        return new Result(parseTimeResult, solveResult.part1(), solveResult.part2());
    }

    protected abstract SolveResult solve(E input);

    protected abstract E parseInput(String rawInput);

    public String retrieveRawInput() {
        try {
            var path = "src/main/java/" + getClass().getPackageName().replace(".", "/") + "/input.txt";
            return Files.readString(new File(path).toPath());
        } catch(IOException e) {
            throw new RuntimeException("Error while reading input", e);
        }
    }

}
