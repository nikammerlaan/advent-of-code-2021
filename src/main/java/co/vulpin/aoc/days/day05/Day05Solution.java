package co.vulpin.aoc.days.day05;

import co.vulpin.aoc.data.SolveResult;
import co.vulpin.aoc.data.TimeResult;
import co.vulpin.aoc.days.AbstractJoinedDaySolution;
import co.vulpin.aoc.misc.Point;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Day05Solution extends AbstractJoinedDaySolution<List<Day05Solution.Line>> {

    @Override
    protected SolveResult solve(List<Line> input) {
        var start = Instant.now();

        var xLength = 1 + input.stream()
            .flatMap(i -> Stream.of(i.from(), i.to()))
            .mapToInt(Point::x)
            .max()
            .orElseThrow();
        var yLength = 1 + input.stream()
            .flatMap(i -> Stream.of(i.from(), i.to()))
            .mapToInt(Point::y)
            .max()
            .orElseThrow();

        var board = new int[xLength][yLength];

        var diagonals = new LinkedList<Line>();
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
            } else {
                diagonals.add(line);
            }
        }

        var part1 = new TimeResult<>(getOverlapCount(board), Duration.between(start, Instant.now()));

        for(var line : diagonals) {
            var from = line.from();
            var to = line.to();

            var xAscending = from.x() < to.x();
            var yAscending = from.y() < to.y();

            var x = from.x();
            var y = from.y();

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

        var part2 = new TimeResult<>(getOverlapCount(board), Duration.between(start, Instant.now()));

        return new SolveResult(part1, part2);
    }

    private long getOverlapCount(int[][] board) {
        return Arrays.stream(board)
            .flatMapToInt(Arrays::stream)
            .filter(i -> i >= 2)
            .count();
    }

    @Override
    protected List<Line> parseInput(String input) {
        return Arrays.stream(input.split("\n"))
            .map(this::parseInputLine)
            .toList();
    }

    private Line parseInputLine(String input) {
        var parts = input.split(" -> ");
        return new Line(parseInputPoint(parts[0]), parseInputPoint(parts[1]));
    }

    private Point parseInputPoint(String input) {
        var parts = input.split(",");
        return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    record Line(Point from, Point to) {}

}
