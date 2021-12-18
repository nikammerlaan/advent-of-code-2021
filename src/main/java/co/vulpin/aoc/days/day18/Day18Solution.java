package co.vulpin.aoc.days.day18;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.Arrays;
import java.util.List;

public class Day18Solution extends AbstractDayParallelSolution<List<SnailfishPair>> {

    @Override
    protected Object solvePart1(List<SnailfishPair> input) {
        var added = input.stream()
            .reduce(SnailfishPair::add)
            .orElseThrow();
        return added.getMagnitude();
    }

    @Override
    protected Object solvePart2(List<SnailfishPair> input) {
        return input.stream()
            .flatMapToInt(a -> input.stream()
                .map(a::add)
                .mapToInt(SnailfishPair::getMagnitude)
            )
            .max()
            .orElseThrow();
    }

    @Override
    protected List<SnailfishPair> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n"))
            .map(this::parseExpression)
            .map(n -> (SnailfishPair) n)
            .toList();
    }

    private SnailfishNumber parseExpression(String input) {
        try {
            int value = Integer.parseInt(input);
            return new SnailfishLiteral(value);
        } catch (NumberFormatException e) {}

        var depth = 0;
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if(c == '[') {
                depth++;
            } else if(c == ']') {
                depth--;
            } else if(c == ',' && depth == 1) {
                var left = parseExpression(input.substring(1, i));
                var right = parseExpression(input.substring(i + 1, input.length() - 1));
                var parent = new SnailfishPair(left, right);
                left.setParent(parent);
                right.setParent(parent);
                return parent;
            }
        }

        throw new IllegalStateException();
    }

}
