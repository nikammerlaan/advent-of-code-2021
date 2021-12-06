package co.vulpin.aoc.days.day01;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01Solution extends AbstractDaySolution<List<Integer>> {

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
            .toList();

        return getIncreasingCount(windows);
    }

    private int getIncreasingCount(List<Integer> list) {
        var iter = list.iterator();
        if(!iter.hasNext()) {
            return 0;
        }

        int count = 0;
        int previous = iter.next();
        while(iter.hasNext()) {
            int current = iter.next();
            if(current > previous) {
                count++;
            }
            previous = current;
        }

        return count;
    }

    @Override
    protected List<Integer> parseInput(String input) {
        return Arrays.stream(input.split("\n"))
            .map(Integer::parseInt)
            .collect(Collectors.toCollection(ArrayList::new));
    }

}
