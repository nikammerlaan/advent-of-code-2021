package co.vulpin.aoc.days.day18;

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
    public int getMagnitude() {
        return 3 * left.getMagnitude() + 2 * right.getMagnitude();
    }

    @Override
    public SnailfishPair makeClone() {
        SnailfishNumber leftClone = left.makeClone();
        SnailfishNumber rightClone = right.makeClone();

        SnailfishPair clone = new SnailfishPair(leftClone, rightClone);
        leftClone.setParent(clone);
        rightClone.setParent(clone);

        return clone;
    }

    @Override
    public String toString() {
        return "[" + left + "," + right + "]";
    }

}
