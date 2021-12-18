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
            if(explodeAny(input, input, 0)) {
                continue;
            }

            if(!splitAny(input)) {
                break;
            }
        }
    }

    private boolean explodeAny(SnailfishPair root, SnailfishPair input, int depth) {
        if(depth == 4) {
            explode(root, input);
            return true;
        }

        if(input.getLeft() instanceof SnailfishPair pair) {
            var reduced = explodeAny(root, pair, depth + 1);
            if(reduced) {
                return true;
            }
        }

        if(input.getRight() instanceof SnailfishPair pair) {
            var reduced = explodeAny(root, pair, depth + 1);
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
                split(literal);
                return true;
            }
        }

        return false;
    }

    private void split(SnailfishLiteral literal) {
        var parent = literal.getParent();

        var replacementLeft = new SnailfishLiteral(literal.getValue() / 2);
        var replacementRight = new SnailfishLiteral(literal.getValue() / 2 + literal.getValue() % 2);

        var replacement = new SnailfishPair(replacementLeft, replacementRight);
        replacement.setParent(parent);
        replacementLeft.setParent(replacement);
        replacementRight.setParent(replacement);

        if(parent.getLeft() == literal) {
            parent.setLeft(replacement);
        } else {
            parent.setRight(replacement);
        }
    }

    private void explode(SnailfishPair root, SnailfishPair pair) {
        var flattened = root.flatten();

        var leftIndex = flattened.indexOf((SnailfishLiteral) pair.getLeft());
        var rightIndex = leftIndex + 1;

        if(leftIndex > 0) {
            var moreLeft = flattened.get(leftIndex - 1);
            moreLeft.setValue(moreLeft.getValue() + ((SnailfishLiteral) pair.getLeft()).getValue());
        }

        if(rightIndex < flattened.size() - 1) {
            var moreRight = flattened.get(rightIndex + 1);
            moreRight.setValue(moreRight.getValue() + ((SnailfishLiteral) pair.getRight()).getValue());
        }

        var parent = pair.getParent();
        var replacement = new SnailfishLiteral(0);
        replacement.setParent(parent);
        if(parent.getLeft() == pair) {
            parent.setLeft(replacement);
        } else {
            parent.setRight(replacement);
        }
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

        SnailfishNumber left = null;

        var depth = 0;
        int commaPosition = -1;
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if(c == '[') {
                depth++;
            } else if(c == ']') {
                depth--;
            } else if(c == ',' && depth == 1) {
                commaPosition = i;
                left = parseExpression(input.substring(1, commaPosition));
                break;
            }
        }

        var right = parseExpression(input.substring(commaPosition + 1, input.length() - 1));

        var parent = new SnailfishPair(left, right);
        left.setParent(parent);
        right.setParent(parent);

        return parent;
    }

}
