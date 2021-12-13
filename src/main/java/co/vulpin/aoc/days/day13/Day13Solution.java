package co.vulpin.aoc.days.day13;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Point;

import java.util.Arrays;
import java.util.List;

public class Day13Solution extends AbstractDayParallelSolution<Day13Solution.Input> {

    @Override
    protected Object solvePart1(Input input) {
        var grid = buildGrid(input.points());

        var firstFold = input.folds().get(0);
        grid = fold(grid, firstFold);

        int count = 0;
        for(var row : grid) {
            for(var b : row) {
                if(b) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    protected Object solvePart2(Input input) {
        var grid = buildGrid(input.points());

        for(var fold : input.folds()) {
            grid = fold(grid, fold);
        }

        return gridToString(grid);
    }

    private boolean[][] buildGrid(List<Point> points) {
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

        boolean[][] grid = new boolean[maxX + 1][maxY + 1];

        for(var point : points) {
            grid[point.x()][point.y()] = true;
        }

        return grid;
    }

    private String gridToString(boolean[][] grid) {
        var stringBuilder = new StringBuilder();
        for(int y = 0; y < grid[0].length; y++) {
            for(int x = 0; x < grid.length; x++) {
                stringBuilder.append(grid[x][y] ? '#' : '.');
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private boolean[][] fold(boolean[][] grid, Fold fold) {
        int newXSize = grid.length;
        int newYSize = grid[0].length;

        if(fold.axis() == 'x') {
            newXSize = fold.value();
        } else {
            newYSize = fold.value();
        }

        boolean[][] newGrid = new boolean[newXSize][newYSize];

        for(int x = 0; x < grid.length; x++) {
            var row = grid[x];

            if(fold.axis() == 'x' && fold.value() == x) {
                continue;
            }

            int newX = x;
            if(fold.axis() == 'x' && newX > fold.value()) {
                newX = fold.value() - (x - fold.value());
            }

            for(int y = 0; y < row.length; y++) {
                if(fold.axis() == 'y' && fold.value() == y) {
                    continue;
                }

                int newY = y;
                if(fold.axis() == 'y' && y > fold.value()) {
                    newY = fold.value() - (y - fold.value());
                }

                newGrid[newX][newY] = newGrid[newX][newY] || grid[x][y];
            }
        }
        return newGrid;
    }

    @Override
    protected Input parseInput(String rawInput) {
        var parts = rawInput.split("\n\n");

        var points = Arrays.stream(parts[0].split("\n"))
            .map(this::parsePoint)
            .toList();

        var folds = Arrays.stream(parts[1].split("\n"))
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
