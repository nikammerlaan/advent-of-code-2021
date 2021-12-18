package co.vulpin.aoc.days.day18;

import java.util.List;

public final class SnailfishLiteral extends SnailfishNumber {

    private int value;

    public SnailfishLiteral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean splitRecursively() {
        if(value >= 10) {
            split();
            return true;
        } else {
            return false;
        }
    }

    public void split() {
        var replacementLeft = new SnailfishLiteral(value / 2);
        var replacementRight = new SnailfishLiteral(value / 2 + value % 2);

        var replacement = new SnailfishPair(replacementLeft, replacementRight);
        replacement.setParent(parent);
        replacementLeft.setParent(replacement);
        replacementRight.setParent(replacement);

        if(parent.getLeft() == this) {
            parent.setLeft(replacement);
        } else {
            parent.setRight(replacement);
        }
    }

    @Override
    public SnailfishNumber makeClone() {
        return new SnailfishLiteral(value);
    }

    @Override
    public List<SnailfishLiteral> flatten() {
        return List.of(this);
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
