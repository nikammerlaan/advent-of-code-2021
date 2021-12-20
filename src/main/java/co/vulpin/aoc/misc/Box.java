package co.vulpin.aoc.misc;

public record Box(Range xRange, Range yRange) {

    public boolean isInBox(Point point) {
        return xRange.isInRange(point.x()) && yRange.isInRange(point.y());
    }

}
