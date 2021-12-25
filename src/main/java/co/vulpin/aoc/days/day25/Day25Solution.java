package co.vulpin.aoc.days.day25;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.Arrays;

public class Day25Solution extends AbstractDayParallelSolution<char[][]> {

    @Override
    protected Object solvePart1(char[][] grid) {
        for(int i = 0; true; i++) {
            var newGrid = iterate(grid);

            if(Arrays.deepEquals(grid, newGrid)) {
                return i;
            } else {
                grid = newGrid;
            }
        }
    }

    private char[][] iterate(char[][] grid) {
        return iterateSouth(iterateEast(grid));
    }

    private char[][] iterateSouth(char[][] grid) {
        var newGrid = cloneGrid(grid);
        for(int x = 0; x < grid.length; x++) {
            var row = grid[x];
            for(int y = 0; y < row.length; y++) {
                var value = row[y];

                if(value != 'v') {
                    continue;
                }

                int nextX = (x + 1) % grid.length;
                int nextY = y;

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

    private char[][] iterateEast(char[][] grid) {
        var newGrid = cloneGrid(grid);
        for(int x = 0; x < grid.length; x++) {
            var row = grid[x];
            for(int y = 0; y < row.length; y++) {
                var value = row[y];

                if(value != '>') {
                    continue;
                }

                int nextX = x;
                int nextY = (y + 1) % grid[x].length;

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

    private SeaCucumber parseSeaCucumber(char c) {
        return switch(c) {
            case 'v' -> new SeaCucumber(SeaCucumberType.SOUTH);
            case '>' -> new SeaCucumber(SeaCucumberType.EAST);
            default -> null;
        };
    }

    record Input(int x, int y, SeaCucumber[][] grid) {}

    public static class SeaCucumber {

        private final SeaCucumberType type;
        private int moves;
        
        public SeaCucumber(SeaCucumberType type) {
            this.type = type;
            this.moves = 0;
        }

        public SeaCucumberType getType() {
            return type;
        }

        public void incrementMoves() {
            moves++;
        }

        public boolean canMove() {
            return moves < 58;
        }

    }

    private enum SeaCucumberType { EAST, SOUTH}

}
