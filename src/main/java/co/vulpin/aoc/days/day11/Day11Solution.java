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
        for(int i = 0; i < input.length; i++) {
            var row = input[i];
            for(int j = 0; j < row.length; j++) {
                row[j]++;
            }
        }

        int flashCount = 0;
        boolean[][] flashed = new boolean[10][10];

        boolean cont = true;
        while(cont) {
            cont = false;
            for(int i = 0; i < input.length; i++) {
                var row = input[i];
                for(int j = 0; j < row.length; j++) {
                    if(row[j] > 9) {
                        row[j] = 0;
                        flashCount++;
                        flashed[i][j] = true;
                        cont = true;

                        for(int iA = i - 1; iA <= i + 1; iA++) {
                            if(iA < 0 || iA >= input.length) {
                                continue;
                            }
                            for(int jA = j - 1; jA <= j + 1; jA++) {
                                if(jA < 0 || jA >= input[iA].length || (iA == i && jA == j) || flashed[iA][jA]) {
                                    continue;
                                }
                                input[iA][jA]++;
                            }
                        }
                    }
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
