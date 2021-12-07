package co.vulpin.aoc.days.day01;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Day01Solution extends AbstractDaySolution<List<Integer>> {

    @Override
    protected Object solvePart1(List<Integer> input) {
        return getIncreasingCount(input.iterator());
    }

    @Override
    protected Object solvePart2(List<Integer> input) {
        return getIncreasingCount(new RollingSumIterator(input, 3));
    }

    private int getIncreasingCount(Iterator<Integer> iter) {
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

    private static class RollingSumIterator implements Iterator<Integer> {

        private final Iterator<Integer> on;
        private final Iterator<Integer> off;

        private boolean first;
        private int current;

        public RollingSumIterator(List<Integer> source, int windowSize) {
            this.on = source.iterator();
            this.off = source.iterator();

            for(int i = 0; i < windowSize; i++) {
                if(on.hasNext()) {
                    this.current += on.next();
                } else {
                    throw new IllegalStateException("Source size is less than window size");
                }
            }

            this.first = true;
        }

        @Override
        public boolean hasNext() {
            return on.hasNext();
        }

        @Override
        public Integer next() {
            if(first) {
                first = false;
                return current;
            } else {
                current += on.next();
                current -= off.next();

                return current;
            }
        }

    }

}
