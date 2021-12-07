package co.vulpin.aoc.days.day07;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day07Solution extends AbstractDaySolution<List<Integer>> {

    @Override
    protected Object solvePart1(List<Integer> input) {
        var target = input.get(input.size() / 2);

        return getTotalFuelUsed(input, this::getFuelUsedPart1, target);
    }

    @Override
    protected Object solvePart2(List<Integer> input) {
        var average = input.stream()
            .mapToInt(i -> i)
            .average()
            .orElseThrow();

        // On the test case, it wants rounding up. On my input, it wants rounding down.
        // Just calculate both and then find which one is better.
        var down = getTotalFuelUsed(input, this::getFuelUsedPart2, (int) Math.floor(average));
        var up = getTotalFuelUsed(input, this::getFuelUsedPart2, (int) Math.ceil(average));

        return Math.min(down, up);
    }

    private int getTotalFuelUsed(List<Integer> inputs, BiFunction<Integer, Integer, Integer> usedFuelFunction, int target) {
        return inputs.stream()
            .mapToInt(i -> usedFuelFunction.apply(i, target))
            .sum();
    }

    private int getFuelUsedPart1(int i, int target) {
        return Math.abs(i - target);
    }

    private int getFuelUsedPart2(int i, int target) {
        var diff = Math.abs(i - target);
        return (diff * diff + diff) / 2;
    }

    @Override
    protected List<Integer> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split(","))
            .map(Integer::parseInt)
            .sorted()
            .toList();
    }

}
