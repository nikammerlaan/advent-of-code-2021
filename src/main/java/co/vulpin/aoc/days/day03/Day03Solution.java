package co.vulpin.aoc.days.day03;

import co.vulpin.aoc.days.AbstractSeparateDaySolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day03Solution extends AbstractSeparateDaySolution<List<String>> {

    @Override
    protected Object solvePart1(List<String> input) {
        int[] counts = new int[input.get(0).length()];

        for(String s : input) {
            for(int i = 0; i < s.length(); i++) {
                if(s.charAt(i) == '1') {
                    counts[i]++;
                }
            }
        }

        int gamma = 0;
        int epsilon = 0;

        for(int i : counts) {
            gamma <<= 1;
            epsilon <<= 1;
            double half = ((double) input.size()) / 2;
            if(i >= half) {
                gamma++;
            } else {
                epsilon++;
            }
        }

        return gamma * epsilon;
    }

    @Override
    protected Object solvePart2(List<String> input) {
        int oxygenGeneratorRating = getFilteredItem(input, true);
        int co2ScrubberRating = getFilteredItem(input, false);

        return oxygenGeneratorRating * co2ScrubberRating;
    }

    private int getFilteredItem(List<String> items, boolean preferCommon) {
        var current = new ArrayList<>(items);

        for(int i = 0; current.size() > 1; i++) {
            int count = 0;
            for(var s : current) {
                if(s.charAt(i) == '1') {
                    count++;
                }
            }

            int i0 = i;
            double half = ((double) current.size()) / 2;
            char charToRemove = preferCommon
                ? count >= half ? '1' : '0'
                : count >= half ? '0' : '1';
            current.removeIf(s -> s.charAt(i0) == charToRemove);
        }

        return Integer.parseInt(current.get(0), 2);
    }

    @Override
    protected List<String> parseInput(String input) {
        return Arrays.asList(input.split("\n"));
    }

}
