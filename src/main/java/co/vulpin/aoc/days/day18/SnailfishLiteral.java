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
