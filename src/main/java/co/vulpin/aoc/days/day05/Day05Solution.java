package co.vulpin.aoc.days.day05;

import co.vulpin.aoc.days.AbstractSeparateDaySolution;
import co.vulpin.aoc.misc.Point;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day05Solution extends AbstractSeparateDaySolution<List<Day05Solution.Input>> {

    @Override
    protected Object solvePart1(List<Input> input) {
        return solve(input, false);
    }

    @Override
    protected Object solvePart2(List<Input> input) {
        return solve(input, true);
    }

    private long solve(List<Input> input, boolean diagonals) {
        int xLength = 1 + input.stream()
            .flatMap(i -> Stream.of(i.from(), i.to()))
            .mapToInt(Point::x)
            .max()
            .orElseThrow();
        int yLength = 1 + input.stream()
            .flatMap(i -> Stream.of(i.from(), i.to()))
            .mapToInt(Point::y)
            .max()
            .orElseThrow();

        int[][] board = new int[xLength][yLength];


        for(var line : input) {
            var from = line.from();
            var to = line.to();

            if(from.x() == to.x()) {
                for(int i = Math.min(from.y(), to.y()); i <= Math.max(from.y(), to.y()); i++) {
                    board[from.x()][i]++;
                }
            } else if(from.y() == to.y()) {
                for(int i = Math.min(from.x(), to.x()); i <= Math.max(from.x(), to.x()); i++) {
                    board[i][from.y()]++;
                }
            } else if(diagonals){
                boolean xAscending = from.x() < to.x();
                boolean yAscending = from.y() < to.y();

                int x = from.x();
                int y = from.y();

                while(x != to.x() && y != to.y()) {
                    board[x][y]++;

                    if(xAscending) {
                        x++;
                    } else {
                        x--;
                    }
                    if(yAscending) {
                        y++;
                    } else {
                        y--;
                    }
                }

                board[x][y]++;
            }
        }

        return Arrays.stream(board)
            .flatMapToInt(Arrays::stream)
            .filter(i -> i >= 2)
            .count();
    }


    @Override
    protected List<Input> parseInput(String input) {
        return Arrays.stream(input.split("\n"))
            .map(this::parseInputLine)
            .toList();
    }

    private Input parseInputLine(String input) {
        var parts = input.split(" -> ");
        return new Input(parseInputPoint(parts[0]), parseInputPoint(parts[1]));
    }

    private Point parseInputPoint(String input) {
        var parts = input.split(",");
        return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    record Input(Point from, Point to) {}

}
