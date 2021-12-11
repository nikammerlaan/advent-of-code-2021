package co.vulpin.aoc.days.day11;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.Arrays;

public class Day11Solution extends AbstractDaySolution<int[][]> {

    @Override
    protected Object solvePart1(int[][] input) {
        int[][] board = copy2dArray(input);

        int flashes = 0;
        for(int i = 1; i <= 100; i++) {
            flashes += runIteration(board);
        }

        return flashes;
    }

    @Override
    protected Object solvePart2(int[][] input) {
        int[][] board = copy2dArray(input);

        for(int i = 1; true; i++) {
            int flashes = runIteration(board);

            if(flashes == board.length * board.length) {
                return i;
            }
        }
    }

    private int runIteration(int[][] input) {
        int flashCount = 0;
        boolean[][] flashed = new boolean[10][10];
        for(int x = 0; x < input.length; x++) {
            var row = input[x];
            for(int y = 0; y < row.length; y++) {
                flashCount += increment(input, flashed, x, y);
            }
        }
        return flashCount;
    }

    private int increment(int[][] board, boolean[][] flashed, int x, int y) {
        if(flashed[x][y]) {
            return 0;
        }

        board[x][y]++;

        int flashCount = 0;

        if(board[x][y] > 9) {
            board[x][y] = 0;
            flashed[x][y] = true;
            flashCount++;

            for(int xA = x - 1; xA <= x + 1; xA++) {
                if(xA < 0 || xA >= board.length) {
                    continue;
                }
                for(int yA = y - 1; yA <= y + 1; yA++) {
                    if(yA < 0 || yA >= board[xA].length || (xA == x && yA == y) || flashed[xA][yA]) {
                        continue;
                    }
                    flashCount += increment(board, flashed, xA, yA);
                }
            }
        }

        return flashCount;
    }

    private int[][] copy2dArray(int[][] array) {
        int[][] arrayCopy = new int[array.length][];
        for(int i = 0; i < array.length; i++) {
            arrayCopy[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return arrayCopy;
    }

    @Override
    protected int[][] parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n"))
            .map(s -> s.chars()
                .map(i -> i - '0')
                .toArray()
            )
            .toArray(int[][]::new);
    }

}
