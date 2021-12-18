package co.vulpin.aoc.days.day18;

import java.util.List;

public abstract sealed class SnailfishNumber permits SnailfishPair, SnailfishLiteral {

    protected SnailfishPair parent;

    public void setParent(SnailfishPair parent) {
        this.parent = parent;
    }

    public SnailfishPair getParent() {
        return parent;
    }

    public abstract int getMagnitude();

    public abstract SnailfishNumber makeClone();

    public abstract List<SnailfishLiteral> flatten();

    public SnailfishPair getRoot() {
        if(parent != null) {
            return parent.getRoot();
        } else {
            return (SnailfishPair) this;
        }
    }

}
