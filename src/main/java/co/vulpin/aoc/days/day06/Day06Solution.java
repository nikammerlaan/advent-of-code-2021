package co.vulpin.aoc.days.day06;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.Arrays;
import java.util.List;

public class Day06Solution extends AbstractDaySolution<List<Integer>> {

    @Override
    protected Object solvePart1(List<Integer> input) {
        return solve(input, 80);
    }

    @Override
    protected Object solvePart2(List<Integer> input) {
        return solve(input, 256);
    }

    private long solve(List<Integer> input, int days) {
        long[] counts = new long[9];


        for(int i : input) {
            counts[i]++;
        }

        for(int i = 0; i < days; i++) {
            long[] newCounts = new long[9];

            System.arraycopy(counts, 1, newCounts, 0, 8);

            newCounts[6] += counts[0];
            newCounts[8] += counts[0];

            counts = newCounts;
        }

        return Arrays.stream(counts).sum();
    }

    @Override
    protected List<Integer> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split(","))
            .map(Integer::parseInt)
            .toList();
    }

}
