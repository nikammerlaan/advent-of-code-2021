package co.vulpin.aoc.days.day01;

import co.vulpin.aoc.days.AbstractSeparateDaySolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01Solution extends AbstractSeparateDaySolution<List<Integer>> {

    @Override
    protected Object solvePart1(List<Integer> input) {
        return getIncreasingCount(input);
    }

    @Override
    protected Object solvePart2(List<Integer> input) {
        var windows = IntStream.range(0, input.size() - 2)
            .map(i -> IntStream.rangeClosed(i, i + 2)
                .map(input::get)
                .sum()
            )
            .boxed()
            .collect(Collectors.toCollection(ArrayList::new));

        return getIncreasingCount(windows);
    }

    @Override
    protected List<Integer> parseInput(String input) {
        return Arrays.stream(input.split("\n"))
            .map(Integer::parseInt)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private int getIncreasingCount(List<Integer> list) {
        int count = 0;
        for(int i = 0; i < list.size() - 1; i++) {
            if(list.get(i) < list.get(i + 1)) {
                count++;
            }
        }
        return count;
    }

}
