package co.vulpin.aoc.days.day04;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day04Solution extends AbstractDayParallelSolution<String> {

    @Override
    protected Object solvePart1(String rawInput) {
        var input = parseInput0(rawInput);

        for(var num : input.numbers()) {
            num.mark();

            for(var board : input.boards()) {
                if(board.hasWon()) {
                    return board.getScore(num.value());
                }
            }
        }

        throw new IllegalStateException();
    }

    @Override
    protected Object solvePart2(String rawInput) {
        var input = parseInput0(rawInput);

        var boards = new ArrayList<>(input.boards());

        for(var num : input.numbers()) {
            num.mark();

            var newBoards = new ArrayList<Board>();

            for(var board : boards) {
                if(!board.hasWon()) {
                    newBoards.add(board);
                }
            }

            if(newBoards.isEmpty()) {
                return boards.get(0).getScore(num.value());
            }

            boards = newBoards;
        }

        throw new IllegalStateException();
    }

    @Override
    protected String parseInput(String input) {
        return input;
    }

    protected Input parseInput0(String input) {
        var blocks = input.split("\n\n");

        var calledNumbers = Arrays.stream(blocks[0].split(","))
            .map(Integer::parseInt)
            .map(Number::new)
            .toList();

        var numbers = calledNumbers.stream()
            .collect(Collectors.toMap(Number::value, Function.identity()));

        var boards = Arrays.stream(blocks, 1, blocks.length)
            .map(rawBoard -> Arrays.stream(rawBoard.split("\n"))
                .map(rawRow -> Arrays.stream(rawRow.strip().split("\\s+"))
                    .map(Integer::parseInt)
                    .map(numbers::get)
                    .toArray(Number[]::new)
                )
                .toArray(Number[][]::new))
            .map(Board::new)
            .toList();

        return new Input(calledNumbers, boards);
    }

    record Board(Number[][] numbers) {

        public int getScore(int number) {
            return number * Arrays.stream(numbers)
                .flatMap(Arrays::stream)
                .filter(i -> !i.marked())
                .mapToInt(Number::value)
                .sum();
        }

        public boolean hasWon() {
            for(int i = 0; i < 5; i++) {
                if(hasWonRow(i)) {
                    return true;
                }
            }

            for(int i = 0; i < 5; i++) {
                if(hasWonColumn(i)) {
                    return true;
                }
            }

            return false;
        }

        private boolean hasWonRow(int row) {
            for(int i = 0; i < 5; i++) {
                if(!numbers[row][i].marked()) {
                    return false;
                }
            }
            return true;
        }

        private boolean hasWonColumn(int column) {
            for(int i = 0; i < 5; i++) {
                if(!numbers[i][column].marked()) {
                    return false;
                }
            }
            return true;
        }

    }

    private static class Number {

        private final int value;
        private boolean marked;

        public Number(int value) {
            this.value = value;
            this.marked = false;
        }

        public int value() {
            return value;
        }

        public boolean marked() {
            return marked;
        }

        public void mark() {
            this.marked = true;
        }
    }

    record Input(List<Number> numbers, List<Board> boards) {}

}
