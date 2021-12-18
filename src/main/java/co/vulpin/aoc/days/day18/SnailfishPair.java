package co.vulpin.aoc.days.day18;

import java.util.ArrayList;
import java.util.List;

public final class SnailfishPair extends SnailfishNumber {

    private SnailfishNumber left;
    private SnailfishNumber right;

    public SnailfishPair(SnailfishNumber left, SnailfishNumber right) {
        this.left = left;
        this.right = right;
    }

    public SnailfishNumber getLeft() {
        return left;
    }

    public SnailfishNumber getRight() {
        return right;
    }

    public void setLeft(SnailfishNumber left) {
        this.left = left;
    }

    public void setRight(SnailfishNumber right) {
        this.right = right;
    }

    @Override
    public List<SnailfishLiteral> flatten() {
        var list = new ArrayList<SnailfishLiteral>();

        list.addAll(left.flatten());
        list.addAll(right.flatten());

        return list;
    }

    @Override
    public int getMagnitude() {
        return 3 * left.getMagnitude() + 2 * right.getMagnitude();
    }

    @Override
    public SnailfishPair makeClone() {
        var leftClone = left.makeClone();
        var rightClone = right.makeClone();

        var clone = new SnailfishPair(leftClone, rightClone);
        leftClone.setParent(clone);
        rightClone.setParent(clone);

        return clone;
    }

    @Override
    public String toString() {
        return "[" + left + "," + right + "]";
    }

}
