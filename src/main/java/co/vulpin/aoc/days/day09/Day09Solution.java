package co.vulpin.aoc.days.day09;

import co.vulpin.aoc.days.AbstractDaySolution;
import co.vulpin.aoc.misc.Point;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day09Solution extends AbstractDaySolution<int[][]> {

    @Override
    protected Object solvePart1(int[][] board) {
        int risk = 0;
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                if(isLowSpot(board, x, y)) {
                    risk += board[x][y] + 1;
                }
            }
        }
        return risk;
    }

    @Override
    protected Object solvePart2(int[][] board) {
        var flow = new HashMap<Point, Point>();

        // Calculate the adjacent point for which every point flows to
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                int value = board[x][y];

                if(value == 9) {
                    continue;
                }

                var flowsTo = getAdjacentPoints(board, x, y).stream()
                    .filter(point -> board[point.x()][point.y()] < value)
                    .findFirst()
                    .orElse(null);

                if(flowsTo != null) {
                    flow.put(new Point(x, y), flowsTo);
                }
            }
        }

        // Simplify flow
        // ie, a->b->c->d to a->d
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                var point = new Point(x, y);

                if(!flow.containsKey(point)) {
                    continue;
                }

                var flowsTo = point;
                while(flow.get(flowsTo) != null) {
                    flowsTo = flow.get(flowsTo);
                }
                flow.put(point, flowsTo);
            }
        }

        // Group by destination and cont
        return flow.values().stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .values().stream()
            .sorted(Collections.reverseOrder())
            .map(i -> i + 1) // Add one for the low point because it technically doesn't "flow" anywhere
            .limit(3)
            .reduce(1L, (a, b) -> a * b);
    }

    private List<Point> getAdjacentPoints(int[][] board, int x, int y) {
        var points = new LinkedList<Point>();

        if(x - 1 >= 0) {
            points.add(new Point(x - 1, y));
        }

        if(x + 1 < board.length) {
            points.add(new Point(x +1, y));
        }

        if(y - 1 >= 0) {
            points.add(new Point(x, y - 1));
        }

        if(y + 1 < board[x].length) {
            points.add(new Point(x, y + 1));
        }

        return points;
    }

    private boolean isLowSpot(int[][] board, int x, int y) {
        int value = board[x][y];

        return getAdjacentPoints(board, x, y).stream()
            .allMatch(point -> board[point.x()][point.y()] > value);
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
