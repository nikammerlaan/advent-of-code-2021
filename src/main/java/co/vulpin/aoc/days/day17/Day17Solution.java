package co.vulpin.aoc.days.day17;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Point;

import java.util.Iterator;

public class Day17Solution extends AbstractDayParallelSolution<Day17Solution.Box> {

    @Override
    protected Object solvePart1(Box input) {
        int max = 0;
        for(int xVel = 0; xVel < 500; xVel++) {
            for(int yVel = max + 1; yVel < 500; yVel++) {
                var iter = new PointIterator(xVel, yVel, input);
                int iterMax = 0;
                boolean hitsBox = false;
                while(iter.hasNext()) {
                    var point = iter.next();
                    if(point.y() > iterMax) {
                        iterMax = point.y();
                    }
                    if(input.isInBox(point)) {
                        hitsBox = true;
                        break;
                    }
                }
                if(hitsBox && iterMax > max) {
                    max = iterMax;
                }
            }
        }
        return max;
    }

    @Override
    protected Object solvePart2(Box input) {
        int count = 0;
        for(int xVel = 0; xVel < 250; xVel++) {
            for(int yVel = -250; yVel < 250; yVel++) {
                var iter = new PointIterator(xVel, yVel, input);
                int iterMax = 0;
                boolean hitsBox = false;
                while(iter.hasNext()) {
                    var point = iter.next();
                    if(point.y() > iterMax) {
                        iterMax = point.y();
                    }
                    if(input.isInBox(point)) {
                        hitsBox = true;
                        break;
                    }
                }
                if(hitsBox) {
                    count++;
                }
            }
        }
        return count;
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
            if(yVelocity <= 0 && target.yRange().compareTo(y) < 0) {
                return false;
            } else if(xVelocity == 0 && !target.xRange().isInRange(x)) {
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
