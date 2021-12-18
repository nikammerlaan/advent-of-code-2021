package co.vulpin.aoc.days.day18;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.Arrays;
import java.util.List;

public class Day18Solution extends AbstractDayParallelSolution<List<SnailfishPair>> {

    @Override
    protected Object solvePart1(List<SnailfishPair> input) {
        var added = input.stream()
            .reduce(this::add)
            .orElseThrow();
        return added.getMagnitude();
    }

    @Override
    protected Object solvePart2(List<SnailfishPair> input) {
        return input.stream()
            .flatMapToInt(a -> input.stream()
                .map(b -> add(a, b))
                .mapToInt(SnailfishPair::getMagnitude)
            )
            .max()
            .orElseThrow();
    }

    private SnailfishPair add(SnailfishPair a, SnailfishPair b) {
        var aClone = a.makeClone();
        var bClone = b.makeClone();
        var pair = new SnailfishPair(aClone, bClone);
        aClone.setParent(pair);
        bClone.setParent(pair);
        reduce(pair);
        return pair;
    }

    private void reduce(SnailfishPair input) {
        while(true) {
            if(explodeAny(input, 0)) {
                continue;
            }

            if(!splitAny(input)) {
                break;
            }
        }
    }

    private boolean explodeAny(SnailfishPair input, int depth) {
        if(depth == 4) {
            input.explode();
            return true;
        }

        if(input.getLeft() instanceof SnailfishPair pair) {
            var reduced = explodeAny(pair, depth + 1);
            if(reduced) {
                return true;
            }
        }

        if(input.getRight() instanceof SnailfishPair pair) {
            var reduced = explodeAny(pair, depth + 1);
            if(reduced) {
                return true;
            }
        }

        return false;
    }

    private boolean splitAny(SnailfishNumber input) {
        if(input instanceof SnailfishPair pair) {
            var splitLeft = splitAny(pair.getLeft());
            if(splitLeft) {
                return true;
            }

            var splitRight = splitAny(pair.getRight());
            if(splitRight) {
                return true;
            }
        } else if(input instanceof SnailfishLiteral literal) {
            if(literal.getValue() >= 10) {
                literal.split();
                return true;
            }
        }

        return false;
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
