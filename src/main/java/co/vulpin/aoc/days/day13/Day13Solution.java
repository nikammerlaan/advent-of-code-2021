package co.vulpin.aoc.days.day13;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Point;

import java.util.*;
import java.util.stream.Collectors;

public class Day13Solution extends AbstractDayParallelSolution<Day13Solution.Input> {

    @Override
    protected Object solvePart1(Input input) {
        var firstFold = input.folds().get(0);
        return input.points().stream()
            .map(point -> transformPoint(point, firstFold))
            .distinct()
            .count();
    }

    @Override
    protected Object solvePart2(Input input) {
        Set<Point> points = new HashSet<>(input.points());

        for(var fold : input.folds()) {
            points = points.stream()
                .map(point -> transformPoint(point, fold))
                .collect(Collectors.toSet());
        }

        return pointsToString(points);
    }

    private String pointsToString(Set<Point> points) {
        int maxX = 0;
        int maxY = 0;

        for(var point : points) {
            if(point.x() > maxX) {
                maxX = point.x();
            }
            if(point.y() > maxY) {
                maxY = point.y();
            }
        }

        var stringBuilder = new StringBuilder();
        for(int y = 0; y <= maxY; y++) {
            for(int x = 0; x <= maxX; x++) {
                var point = new Point(x, y);
                stringBuilder.append(points.contains(point) ? '#' : '.');
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private Point transformPoint(Point point, Fold fold) {
        if(fold.axis() == 'x') {
            if(point.x() > fold.value()) {
                int newX = fold.value() - (point.x() - fold.value());
                return new Point(newX, point.y());
            } else {
                return point;
            }
        } else {
            if(point.y() > fold.value()) {
                int newY = fold.value() - (point.y() - fold.value());
                return new Point(point.x(), newY);
            } else {
                return point;
            }
        }
    }

    @Override
    protected Input parseInput(String rawInput) {
        var parts = rawInput.split("\n\n");

        var points = parts[0].lines()
            .map(this::parsePoint)
            .toList();

        var folds = parts[1].lines()
            .map(this::parseFold)
            .toList();

        return new Input(points, folds);
    }

    private Point parsePoint(String raw) {
        var parts = raw.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new Point(x, y);
    }

    private Fold parseFold(String raw) {
        var parts = raw.split("[ =]");
        var axis = parts[2].charAt(0);
        var value = Integer.parseInt(parts[3]);
        return new Fold(axis, value);
    }
    
    record Input(List<Point> points, List<Fold> folds) {}
    record Fold(char axis, int value) {}

}
