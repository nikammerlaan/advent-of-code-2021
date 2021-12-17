package co.vulpin.aoc.days.day17;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Point;

import java.util.Iterator;

public class Day17Solution extends AbstractDayParallelSolution<Day17Solution.Box> {

    @Override
    protected Object solvePart1(Box input) {
        int maxY = -1;
        for(int xVel = 0; xVel < 250; xVel++) {
            for(int yVel = maxY + 1; yVel < 250; yVel++) {
                if(hitsBox(input, xVel, yVel)) {
                    maxY = getMaxY(yVel);
                }
            }
        }
        return maxY;
    }

    @Override
    protected Object solvePart2(Box target) {
        int count = 0;
        for(int xVel = 0; xVel < 250; xVel++) {
            for(int yVel = -250; yVel < 250; yVel++) {
                if(hitsBox(target, xVel, yVel)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean hitsBox(Box box, int xVelocity, int yVelocity) {
        var iter = new PointIterator(xVelocity, yVelocity, box);
        while(iter.hasNext()) {
            var point = iter.next();
            if(box.isInBox(point)) {
                return true;
            }
        }
        return false;
    }

    private int getMaxY(int yVelocity) {
        if(yVelocity > 0) {
            return (yVelocity * yVelocity + yVelocity) / 2;
        } else {
            return 0;
        }
    }

    @Override
    protected Box parseInput(String input) {
        input = input.split(": ")[1];
        var parts = input.split(", ");

        var xRange = parseRange(parts[0].split("=")[1]);
        var yRange = parseRange(parts[1].split("=")[1]);

        return new Box(xRange, yRange);
    }

    private Range parseRange(String part) {
        var parts = part.split("\\.\\.");
        var start = Integer.parseInt(parts[0]);
        var end = Integer.parseInt(parts[1]);
        return new Range(start, end);
    }

    private static class PointIterator implements Iterator<Point> {

        private int x;
        private int y;
        private int xVelocity;
        private int yVelocity;

        private final Box target;

        public PointIterator(int xVelocity, int yVelocity, Box target) {
            this.x = 0;
            this.y = 0;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;

            this.target = target;
        }

        @Override
        public boolean hasNext() {
            // If you're below the box and have a Y velocity <=0, then you'll never hit the box
            if(yVelocity <= 0 && target.yRange().compareTo(y) < 0) {
                return false;
            }

            // If you're to the right of the box moving right
            if(xVelocity > 0 && target.xRange().compareTo(x) > 0) {
                return false;
            }

            // If you are not above/below the box and have 0 X velocity, then you'll never hit the box
            if(xVelocity == 0 && !target.xRange().isInRange(x)) {
                return false;
            }

            return true;
        }

        @Override
        public Point next() {
            x += xVelocity;
            y += yVelocity;

            var point =  new Point(x, y);

            if(xVelocity != 0) {
                if(xVelocity > 0) {
                    xVelocity--;
                } else {
                    xVelocity++;
                }
            }
            yVelocity--;

            return point;
        }
    }

    record Box(Range xRange, Range yRange) {

        boolean isInBox(Point point) {
            return xRange.isInRange(point.x()) && yRange.isInRange(point.y());
        }

    }

    record Range(int start, int end) implements Comparable<Integer> {

        boolean isInRange(int value) {
            return value >= start && value <= end;
        }

        @Override
        public int compareTo(Integer o) {
            if(o < start) {
                return -1;
            } else if(o > end) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
