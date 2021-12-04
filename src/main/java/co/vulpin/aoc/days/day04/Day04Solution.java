package co.vulpin.aoc.days.day04;

import co.vulpin.aoc.days.DaySolution;

import java.util.*;
import java.util.stream.Collectors;

public class Day04Solution extends DaySolution<Day04Solution.Input> {

    @Override
    protected Object solvePart1(Input input) {
        var calledNums = new HashSet<Integer>();

        for(int num : input.numbers()) {
            calledNums.add(num);

            for(var board : input.boards()) {
                if(hasWon(board, calledNums)) {
                    return num * Arrays.stream(board)
                        .flatMapToInt(Arrays::stream)
                        .filter(i -> !calledNums.contains(i))
                        .sum();
                }
            }
        }

        throw new IllegalStateException();
    }

    @Override
    protected Object solvePart2(Input input) {
        var calledNums = new HashSet<Integer>();
        var boards = new ArrayList<>(input.boards());

        for(int num : input.numbers()) {
            calledNums.add(num);

            var newBoards = new ArrayList<int[][]>();

            for(var board : boards) {
                if(!hasWon(board, calledNums)) {
                    newBoards.add(board);
                }
            }

            if(newBoards.isEmpty()) {
                return num * Arrays.stream(boards.get(0))
                    .flatMapToInt(Arrays::stream)
                    .filter(i -> !calledNums.contains(i))
                    .sum();
            }

            boards = newBoards;
        }

        throw new IllegalStateException();
    }

    private boolean hasWon(int[][] board, Set<Integer> nums) {
        for(int i = 0; i < 5; i++) {
            if(hasWonRow(board, i, nums)) {
                return true;
            }
        }

        for(int i = 0; i < 5; i++) {
            if(hasWonColumn(board, i, nums)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasWonRow(int[][] board, int row, Set<Integer> nums) {
        for(int i = 0; i < 5; i++) {
            if(!nums.contains(board[row][i])) {
                return false;
            }
        }
        return true;
    }

    private boolean hasWonColumn(int[][] board, int column, Set<Integer> nums) {
        for(int i = 0; i < 5; i++) {
            if(!nums.contains(board[i][column])) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Input parseInput(String input) {
        var lines = input.lines()
            .collect(Collectors.toCollection(LinkedList::new));

        var numbers = Arrays.stream(lines.pop().split(","))
            .map(Integer::parseInt)
            .toList();

        var boards = Arrays.stream(String.join("\n", lines).split("\n\n"))
            .map(String::trim)
            .map(rawBoard -> Arrays.stream(rawBoard.split("\n"))
                .map(s -> Arrays.stream(s.split("\\s+"))
                    .filter(s0 -> !s0.isBlank())
                    .mapToInt(Integer::parseInt)
                    .toArray()
                )
                .toArray(int[][]::new)
            )
            .toList();

        return new Input(numbers, boards);
    }

    record Input(List<Integer> numbers, List<int[][]> boards) {}

}
