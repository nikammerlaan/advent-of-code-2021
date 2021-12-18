package co.vulpin.aoc.days.day18;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18Solution extends AbstractDayParallelSolution<List<Day18Solution.ShellfishPair>> {

    @Override
    protected Object solvePart1(List<ShellfishPair> input) {
        var added = input.stream()
            .reduce(this::add)
            .orElseThrow();
        return added.getMagnitude();
    }

    @Override
    protected Object solvePart2(List<ShellfishPair> input) {
        return input.stream()
            .flatMapToInt(a -> input.stream()
                .map(b -> add(a, b))
                .mapToInt(ShellfishPair::getMagnitude)
            )
            .max()
            .orElseThrow();
    }

    private ShellfishPair add(ShellfishPair a, ShellfishPair b) {
        var aClone = a.makeClone();
        var bClone = b.makeClone();
        var pair = new ShellfishPair(aClone, bClone);
        aClone.setParent(pair);
        bClone.setParent(pair);
        reduce(pair);
        return pair;
    }

    private void reduce(ShellfishPair input) {
        while(true) {
            if(explodeAny(input, input, 0)) {
                continue;
            }

            if(!splitAny(input)) {
                break;
            }
        }
    }

    private boolean explodeAny(ShellfishPair root, ShellfishPair input, int depth) {
        if(depth == 4) {
            explode(root, input);
            return true;
        }

        if(input.getLeft() instanceof ShellfishPair pair) {
            var reduced = explodeAny(root, pair, depth + 1);
            if(reduced) {
                return true;
            }
        }

        if(input.getRight() instanceof ShellfishPair pair) {
            var reduced = explodeAny(root, pair, depth + 1);
            if(reduced) {
                return true;
            }
        }

        return false;
    }

    private boolean splitAny(ShellfishNumber input) {
        if(input instanceof ShellfishPair pair) {
            var splitLeft = splitAny(pair.getLeft());
            if(splitLeft) {
                return true;
            }

            var splitRight = splitAny(pair.getRight());
            if(splitRight) {
                return true;
            }
        } else if(input instanceof ShellfishLiteral literal) {
            if(literal.getValue() >= 10) {
                split(literal);
                return true;
            }
        }

        return false;
    }

    private void split(ShellfishLiteral literal) {
        var parent = literal.getParent();

        var replacementLeft = new ShellfishLiteral(literal.getValue() / 2);
        var replacementRight = new ShellfishLiteral(literal.getValue() / 2 + literal.getValue() % 2);

        var replacement = new ShellfishPair(replacementLeft, replacementRight);
        replacement.setParent(parent);
        replacementLeft.setParent(replacement);
        replacementRight.setParent(replacement);

        if(parent.getLeft() == literal) {
            parent.setLeft(replacement);
        } else {
            parent.setRight(replacement);
        }
    }

    private void explode(ShellfishPair root, ShellfishPair pair) {
        var flattened = flatten(root);

        var leftIndex = flattened.indexOf((ShellfishLiteral) pair.getLeft());
        var rightIndex = leftIndex + 1;

        if(leftIndex > 0) {
            var moreLeft = flattened.get(leftIndex - 1);
            moreLeft.setValue(moreLeft.getValue() + ((ShellfishLiteral) pair.getLeft()).getValue());
        }

        if(rightIndex < flattened.size() - 1) {
            var moreRight = flattened.get(rightIndex + 1);
            moreRight.setValue(moreRight.getValue() + ((ShellfishLiteral) pair.getRight()).getValue());
        }

        var parent = pair.getParent();
        var replacement = new ShellfishLiteral(0);
        replacement.setParent(parent);
        if(parent.getLeft() == pair) {
            parent.setLeft(replacement);
        } else {
            parent.setRight(replacement);
        }
    }

    private List<ShellfishLiteral> flatten(ShellfishPair pair) {
        var list = new ArrayList<ShellfishLiteral>();

        if(pair.getLeft() instanceof ShellfishLiteral literal) {
            list.add(literal);
        } else if(pair.getLeft() instanceof ShellfishPair leftPair) {
            list.addAll(flatten(leftPair));
        }

        if(pair.getRight() instanceof ShellfishLiteral literal) {
            list.add(literal);
        } else if(pair.getRight() instanceof ShellfishPair rightPair) {
            list.addAll(flatten(rightPair));
        }

        return list;
    }

    @Override
    protected List<ShellfishPair> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n"))
            .map(this::parseExpression)
            .map(n -> (ShellfishPair) n)
            .toList();
    }

    private ShellfishNumber parseExpression(String input) {
        try {
            int value = Integer.parseInt(input);
            return new ShellfishLiteral(value);
        } catch (NumberFormatException e) {}

        ShellfishNumber left = null;

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

        var parent = new ShellfishPair(left, right);
        left.setParent(parent);
        right.setParent(parent);

        return parent;
    }

    interface ShellfishNumber {

        void setParent(ShellfishPair parent);
        int getMagnitude();
        ShellfishNumber makeClone();

    }

    public static class ShellfishPair implements ShellfishNumber {

        private ShellfishPair parent;

        private ShellfishNumber left;
        private ShellfishNumber right;

        public ShellfishPair(ShellfishNumber left, ShellfishNumber right) {
            this.left = left;
            this.right = right;
        }

        public ShellfishPair getParent() {
            return parent;
        }

        public void setParent(ShellfishPair parent) {
            this.parent = parent;
        }

        public ShellfishNumber getLeft() {
            return left;
        }

        public ShellfishNumber getRight() {
            return right;
        }

        public void setLeft(ShellfishNumber left) {
            this.left = left;
        }

        public void setRight(ShellfishNumber right) {
            this.right = right;
        }

        @Override
        public int getMagnitude() {
            return 3 * left.getMagnitude() + 2 * right.getMagnitude();
        }

        @Override
        public ShellfishPair makeClone() {
            ShellfishNumber leftClone = left.makeClone();
            ShellfishNumber rightClone = right.makeClone();

            ShellfishPair clone = new ShellfishPair(leftClone, rightClone);
            leftClone.setParent(clone);
            rightClone.setParent(clone);

            return clone;
        }

        @Override
        public String toString() {
            return "[" + left + "," + right + "]";
        }

    }

    public static class ShellfishLiteral implements ShellfishNumber {

        private int value;

        private ShellfishPair parent;

        public ShellfishLiteral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public ShellfishPair getParent() {
            return parent;
        }

        public void setParent(ShellfishPair parent) {
            this.parent = parent;
        }

        @Override
        public ShellfishNumber makeClone() {
            return new ShellfishLiteral(value);
        }

        @Override
        public int getMagnitude() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

    }
    
}
