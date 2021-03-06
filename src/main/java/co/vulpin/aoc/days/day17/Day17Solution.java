package co.vulpin.aoc.days.day17;

import co.vulpin.aoc.misc.Box;
import co.vulpin.aoc.misc.Range;
import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.MathUtils;
import co.vulpin.aoc.misc.Point;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Day17Solution extends AbstractDayParallelSolution<Box> {

    private static final Pattern REGEX = Pattern.compile("target area: x=(-?[0-9]+)\\.\\.(-?[0-9]+), y=(-?[0-9]+)\\.\\.(-?[0-9]+)");

    @Override
    protected Object solvePart1(Box input) {
        return MathUtils.getTriangleNumber(Math.abs(input.yRange().start()) - 1);
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

    @Override
    protected Box parseInput(String input) {
        var matcher = REGEX.matcher(input);
        if(matcher.find()) {
            var xRange = new Range(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            var yRange = new Range(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
            return new Box(xRange, yRange);
        } else {
            throw new IllegalArgumentException();
        }
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

}
