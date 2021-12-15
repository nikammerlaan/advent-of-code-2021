package co.vulpin.aoc.days.day15;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Point;

import java.util.*;

public class Day15Solution extends AbstractDayParallelSolution<int[][]> {

    @Override
    protected Object solvePart1(int[][] input) {
        return solve0(input);
    }

    @Override
    protected Object solvePart2(int[][] input) {
        var len = input.length;
        var grid = new int[len * 5][len * 5];

        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid.length; y++) {
                int value = input[x % len][y % len] + x / len + y / len;
                if(value >= 10) {
                    value = value % 10 + 1;
                }
                grid[x][y] = value;
            }
        }

        return solve0(grid);
    }

    private int solve0(int[][] grid) {
        var len = grid.length;

        var shortestPath = new int[len][len];
        for(var row : shortestPath) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        shortestPath[0][0] = 0;

        var visited = new boolean[len][len];

        var queue = new LinkedList<Point>();
        queue.add(new Point(0, 0));

        while(!queue.isEmpty()) {
            var point = queue.pollFirst();
            var x = point.x();
            var y = point.y();

            if(visited[x][y]) {
                continue;
            }

            var neighbors = getNeighbors(grid, point);

            for(var neighbor : neighbors) {
                var existingShortestPath  = shortestPath[neighbor.x()][neighbor.y()];
                var neighborRisk = grid[neighbor.x()][neighbor.y()];
                var newPath = shortestPath[point.x()][point.y()] + neighborRisk;
                if(newPath < existingShortestPath) {
                    shortestPath[neighbor.x()][neighbor.y()] = newPath;
                    visited[neighbor.x()][neighbor.y()] = false;
                    queue.add(neighbor);
                } else if(!visited[neighbor.x()][neighbor.y()]) {
                    queue.add(neighbor);
                }
            }

            visited[x][y] = true;
        }

        return shortestPath[len - 1][len - 1];
    }

    private List<Point> getNeighbors(int[][] grid, Point point) {
        int x = point.x();
        int y = point.y();
        var neighbors = new LinkedList<Point>();
        if(x - 1 >= 0) {
            neighbors.add(new Point(x - 1, y));
        }
        if(x + 1 < grid.length) {
            neighbors.add(new Point(x + 1, y));
        }
        if(y - 1 >= 0) {
            neighbors.add(new Point(x, y - 1));
        }
        if(y + 1 < grid[x].length) {
            neighbors.add(new Point(x, y + 1));
        }
        return neighbors;
    }

    @Override
    protected int[][] parseInput(String rawInput) {
        var lines = rawInput.split("\n");
        var grid = new int[lines.length][];

        for(int i = 0; i < lines.length; i++) {
            var line = lines[i];
            grid[i] = line.chars()
                .map(c -> c - '0')
                .toArray();
        }

        return grid;
    }
}
