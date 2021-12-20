package co.vulpin.aoc.misc;

public record Range(int start, int end) implements Comparable<Integer> {

    public boolean isInRange(int value) {
        return value >= start && value <= end;
    }

    @Override
    public int compareTo(Integer o) {
        if (o < start) {
            return -1;
        } else if (o > end) {
            return 1;
        } else {
            return 0;
        }
    }
}
