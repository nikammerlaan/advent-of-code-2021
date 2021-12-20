package co.vulpin.aoc.days.day20;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Box;
import co.vulpin.aoc.misc.Point;
import co.vulpin.aoc.misc.Range;

import java.util.HashSet;
import java.util.Set;

public class Day20Solution extends AbstractDayParallelSolution<Day20Solution.Input> {

    @Override
    protected Object solvePart1(Input input) {
        return iterate(input.points(), input.algo(), 2).size();
    }

    @Override
    protected Object solvePart2(Input input) {
        return iterate(input.points(), input.algo(), 50).size();
    }

    private Set<Point> iterate(Set<Point> points, boolean[] algo, int steps) {
        var border = new Box(new Range(0, 0), new Range(0, 0));
        for(int i = 0; i < steps; i++) {
            var output = runIteration(points, algo, i, border);
            border = output.border();
            points = output.points();
        }
        return points;
    }

    private IterationOutput runIteration(Set<Point> points, boolean[] algo, int i, Box border) {
        var xStats  = points.stream()
            .mapToInt(Point::x)
            .summaryStatistics();
        var yStats = points.stream()
            .mapToInt(Point::y)
            .summaryStatistics();
        var newPoints = new HashSet<Point>();
        for(int x = xStats.getMin() - 1; x <= xStats.getMax() + 1; x++) {
            for(int y = yStats.getMin() - 1; y <= yStats.getMax() + 1; y++) {
                var point = new Point(x, y);
                if(shouldBeOn(point, points, i, border, algo)) {
                    newPoints.add(point);
                }
            }
        }
        var newBorder = new Box(
            new Range(xStats.getMin() - 1, xStats.getMax() + 1),
            new Range(yStats.getMin() - 1, yStats.getMax() + 1)
        );
        return new IterationOutput(newPoints, newBorder);
    }

    private boolean shouldBeOn(Point point, Set<Point> points, int i, Box border, boolean[] algo) {
        int index = 0;
        for(var p : getAdjacentPoints(point)) {
            boolean isOn = isOn(p, points, i, border, algo);
            index = (index << 1) + (isOn ? 1 : 0);
        }
        return algo[index];
    }

    private boolean isOn(Point point, Set<Point> points, int step, Box border, boolean[] algo) {
        return points.contains(point) || (algo[0] && step % 2 == 1 && !border.isInBox(point));
    }

    private Point[] getAdjacentPoints(Point point) {
        int x = point.x(), y = point.y();
        return new Point[] {
            new Point(x - 1, y - 1),
            new Point(x - 1, y    ),
            new Point(x - 1, y + 1),
            new Point(x,     y - 1),
            new Point(x,     y    ),
            new Point(x,     y + 1),
            new Point(x + 1, y - 1),
            new Point(x + 1, y    ),
            new Point(x + 1, y + 1)
        };
    }

    @Override
    protected Input parseInput(String rawInput) {
        var parts = rawInput.split("\n\n");

        var algo = parseBooleanArrayLine(parts[0]);

        var points = new HashSet<Point>();
        var rows = parts[1].split("\n");
        for(int x = 0; x < rows.length; x++) {
            var row = rows[x];
            for(int y = 0; y < row.length(); y++) {
                if(row.charAt(y) == '#') {
                    points.add(new Point(x, y));
                }
            }
        }

        return new Input(algo, points);
    }

    private boolean[] parseBooleanArrayLine(String input) {
        boolean[] arr = new boolean[input.length()];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = input.charAt(i) == '#';
        }
        return arr;
    }

    record Input(boolean[] algo, Set<Point> points) {}
    record IterationOutput(Set<Point> points, Box border) {}

}
