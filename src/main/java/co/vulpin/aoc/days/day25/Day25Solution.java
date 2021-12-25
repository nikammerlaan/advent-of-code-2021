package co.vulpin.aoc.days.day25;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.Arrays;

public class Day25Solution extends AbstractDayParallelSolution<char[][]> {

    @Override
    protected Object solvePart1(char[][] grid) {
        for(int i = 1; true; i++) {
            var newGrid = iterate(grid);

            if(Arrays.deepEquals(grid, newGrid)) {
                return i;
            } else {
                grid = newGrid;
            }
        }
    }

    private char[][] iterate(char[][] grid) {
        grid = iterate(grid, true);
        grid = iterate(grid, false);
        return grid;
    }

    private char[][] iterate(char[][] grid, boolean east) {
        var newGrid = cloneGrid(grid);
        for(int x = 0; x < grid.length; x++) {
            var row = grid[x];
            for(int y = 0; y < row.length; y++) {
                var value = row[y];

                if(value != (east ? '>' : 'v')) {
                    continue;
                }

                int nextX = x, nextY = y;
                if(east) {
                    nextY++;
                    nextY %= grid[x].length;
                } else {
                    nextX++;
                    nextX %= grid.length;
                }

                if(grid[nextX][nextY] == '.') {
                    newGrid[nextX][nextY] = value;
                    newGrid[x][y] = '.';
                } else {
                    newGrid[x][y] = value;
                }
            }
        }
        return newGrid;
    }

    private char[][] cloneGrid(char[][] grid) {
        var clone = new char[grid.length][];
        for(int i = 0; i < grid.length; i++) {
            clone[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return clone;
    }

    @Override
    protected Object solvePart2(char[][] input) {
        return null;
    }

    @Override
    protected char[][] parseInput(String rawInput) {
        return rawInput.lines()
            .map(String::strip)
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }

}
