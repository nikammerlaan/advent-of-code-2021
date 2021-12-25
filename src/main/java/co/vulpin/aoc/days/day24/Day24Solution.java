package co.vulpin.aoc.days.day24;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day24Solution extends AbstractDayParallelSolution<List<Day24Solution.Stage>> {

    @Override
    protected Object solvePart1(List<Stage> stages) {
        return solve0(stages, true);
    }

    @Override
    protected Object solvePart2(List<Stage> stages) {
        return solve0(stages, false);
    }

    private String solve0(List<Stage> stages, boolean max) {
        var digits = new int[stages.size()];
        var stack = new LinkedList<Stage>();
        for(var stage : stages) {
            if(stage.a == 1) {
                stack.add(stage);
            } else {
                var pair = stack.pollLast();

                int diff = pair.c + stage.b;

                int pairValue, stageValue;

                if(max) {
                    if(diff > 0) {
                        pairValue = 9 - diff;
                        stageValue = 9;
                    } else {
                        pairValue = 9;
                        stageValue = 9 + diff;
                    }
                } else {
                    if(diff > 0) {
                        pairValue = 1;
                        stageValue = 1 + diff;
                    } else {
                        pairValue = 1 - diff;
                        stageValue = 1;
                    }
                }

                digits[pair.index] = pairValue;
                digits[stage.index] = stageValue;
            }
        }

        return Arrays.stream(digits)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining());
    }

    @Override
    protected List<Stage> parseInput(String rawInput) {
        var instructions = rawInput.lines()
            .map(this::parseInstruction)
            .toList();

        return IntStream.range(0, 14)
            .mapToObj(i -> {
                var stageInstructions = instructions.subList(i * 18, (i + 1) * 18);
                return parseStage(i, stageInstructions);
            })
            .toList();
    }

    private Stage parseStage(int index, List<Instruction> instructions) {
        var a = Integer.parseInt(instructions.get(4).b());
        var b = Integer.parseInt(instructions.get(5).b());
        var c = Integer.parseInt(instructions.get(15).b());
        return new Stage(index, a, b, c);
    }

    private Instruction parseInstruction(String input) {
        var parts = input.split(" ");
        return new Instruction(
            parts[0],
            parts[1],
            parts.length > 2 ? parts[2] : null
        );
    }

    record Stage(int index, int a, int b, int c) {}
    record Instruction(String type, String a, String b) {}

}
