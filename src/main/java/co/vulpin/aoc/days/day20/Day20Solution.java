package co.vulpin.aoc.days.day20;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day20Solution extends AbstractDayParallelSolution<Day20Solution.Input> {

    @Override
    protected Object solvePart1(Input input) {
        var points = input.points();
        var xBorder = new Range(0, 0);
        var yBorder = new Range(0, 0);
        for(int i = 0; i < 2; i++) {
            var output = runIteration(points, input.algo(), i, xBorder, yBorder);
            xBorder = output.xBorder();
            yBorder = output.yBorder();
            points = output.points();
        }
        return points.size();
    }

    @Override
    protected Object solvePart2(Input input) {
        var points = input.points();
        var xBorder = new Range(0, 0);
        var yBorder = new Range(0, 0);
        for(int i = 0; i < 50; i++) {
            var output = runIteration(points, input.algo(), i, xBorder, yBorder);
            xBorder = output.xBorder();
            yBorder = output.yBorder();
            points = output.points();
        }
        return points.size();
    }

    private Output runIteration(Set<Point> points, boolean[] algo, int i, Range xBorder, Range yBorder) {
        var xStats  = points.stream()
            .mapToInt(Point::x)
            .summaryStatistics();
        var yStats = points.stream()
            .mapToInt(Point::y)
            .summaryStatistics();
        var newPoints = new HashSet<Point>();
        for(int x = xStats.getMin() - 3; x <= xStats.getMax() + 3; x++) {
            for(int y = yStats.getMin() - 3; y <= yStats.getMax() + 3; y++) {
                var point = new Point(x, y);
                if(shouldBeOn(point, points, i, xBorder, yBorder, algo)) {
                    newPoints.add(point);
                }
            }
        }
        return new Output(newPoints, new Range(xStats.getMin() - 3, xStats.getMax() + 3), new Range(yStats.getMin() - 3, yStats.getMax() + 3));
    }

    private boolean shouldBeOn(Point point, Set<Point> points, int i, Range xBorder, Range yBorder, boolean[] algo) {
        int index = 0;
        for(var p : getAdjacentPoints(point)) {
            boolean isOn = points.contains(p) || (algo[0] && i % 2 == 1 && (!xBorder.isInRange(p.x()) || !yBorder.isInRange(p.y())));
            index = (index << 1) + (isOn ? 1 : 0);
        }
        return algo[index];
    }

    private List<Point> getAdjacentPoints(Point point) {
        int x = point.x(), y = point.y();
        return List
            .of(
                new Point(x - 1, y - 1),
                new Point(x - 1, y),
                new Point(x - 1, y + 1),
                new Point(x, y - 1),
                new Point(x, y),
                new Point(x, y + 1),
                new Point(x + 1, y - 1),
                new Point(x + 1, y),
                new Point(x + 1, y + 1)
            );
    }

    @Override
    protected Input parseInput(String rawInput) {
        var parts = rawInput.split("\n\n");

        var algo = parseLine(parts[0]);

        var image = Arrays.stream(parts[1].split("\n"))
            .map(this::parseLine)
            .toArray(boolean[][]::new);

        var points = new HashSet<Point>();
        for(int x = 0; x < image.length; x++) {
            var row = image[x];
            for(int y = 0; y < row.length; y++) {
                if(row[y]) {
                    points.add(new Point(x, y));
                }
            }
        }

        return new Input(algo, points);
    }

    private boolean[] parseLine(String input) {
        boolean[] arr = new boolean[input.length()];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = input.charAt(i) == '#';
        }
        return arr;
    }

    record Input(boolean[] algo, Set<Point> points) {}
    record Output(Set<Point> points, Range xBorder, Range yBorder) {}
    record Range(int start, int end) {

        boolean isInRange(int value) {
            return value >= start && value <= end;
        }

    }
}
