package co.vulpin.aoc.days.day10;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.*;

public class Day10Solution extends AbstractDaySolution<List<Day10Solution.Row>> {

    private static final Map<Character, Character> PAIRS = Map.of(
        '(', ')',
        '[', ']',
        '{', '}',
        '<', '>'
    );

    private static final Map<Character, Integer> ERROR_SCORES = Map.of(
        ')', 3,
        ']', 57,
        '}', 1197,
        '>', 25137
    );

    private static final Map<Character, Integer> AUTOCOMPLETE_SCORES = Map.of(
        '(', 1,
        '[', 2,
        '{', 3,
        '<', 4
    );

    @Override
    protected Object solvePart1(List<Row> input) {
        return input.stream()
            .filter(row -> row.errorChar() != null)
            .map(Row::errorChar)
            .mapToInt(ERROR_SCORES::get)
            .sum();
    }

    @Override
    protected Object solvePart2(List<Row> input) {
        var scores = input.stream()
            .filter(row -> row.errorChar() == null)
            .map(row -> {
                var stack = new LinkedList<>(row.remainingStack());

                long score = 0;
                while(!stack.isEmpty()) {
                    score *= 5;
                    score += AUTOCOMPLETE_SCORES.get(stack.pollLast());
                }
                return score;
            })
            .sorted()
            .toList();
        return scores.get(scores.size() / 2);
    }

    @Override
    protected List<Row> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n"))
            .map(this::parseRow)
            .toList();
    }

    private Row parseRow(String row) {
        var stack = new LinkedList<Character>();
        Character unexpectedChar = null;

        for(char c : row.toCharArray()) {
            if(PAIRS.containsKey(c)) {
                stack.add(c);
            } else {
                var last = stack.pollLast();

                var expected = PAIRS.get(last);

                if(expected != c) {
                    unexpectedChar = c;
                    break;
                }
            }
        }

        return new Row(stack, unexpectedChar);
    }

    record Row(LinkedList<Character> remainingStack, Character errorChar) {}

}
