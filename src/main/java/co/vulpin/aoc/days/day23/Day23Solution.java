package co.vulpin.aoc.days.day23;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Point;

import java.util.*;

public class Day23Solution extends AbstractDayParallelSolution<char[][]> {

    private final Map<String, Integer> costCache;

    public Day23Solution() {
        this.costCache = new HashMap<>();
    }

    @Override
    protected Object solvePart1(char[][] input) {
        var copy = new char[input.length][];

        for(int i = 0; i < input.length; i++) {
            copy[i] = Arrays.copyOf(input[i], input[i].length);
        }

        return solve0(copy);
    }

    @Override
    protected Object solvePart2(char[][] input) {
        char[][] newInput = new char[input.length + 2][];

        for(int i = 0; i <= 2; i++) {
            newInput[i] = Arrays.copyOf(input[i], input[i].length);
        }
        newInput[3] = "  #D#C#B#A#".toCharArray();
        newInput[4] = "  #D#B#A#C#".toCharArray();
        for(int i = 5; i <= 6; i++) {
            newInput[i] = Arrays.copyOf(input[i - 2], input[i - 2].length);
        }
        newInput[5] = Arrays.copyOf(input[3], input[3].length);
        newInput[6] = Arrays.copyOf(input[4], input[4].length);

        return solve0(newInput);
    }

    private Integer solve0(char[][] input) {
        String string = toString(input);
        if(costCache.containsKey(string)) {
            return costCache.get(string);
        }

        Integer minCost = null;
        boolean allInPlace = true;
        for(int x = 1; x < input.length; x++) {
            for(int y = 0; y < input[x].length; y++) {
                char value = input[x][y];

                if(Character.isAlphabetic(value) && !isInPlace(x, y, value, input)) {
                    allInPlace = false;

                    for(var destination : getDestinations(new Point(x, y), input)) {
                        int toX = destination.point().x(), toY = destination.point().y();
                        input[toX][toY] = value;
                        input[x][y] = '.';

                        var cost = solve0(input);
                        if(cost != null) {
                            cost += destination.cost() * getMovementCost(value);
                            if(minCost == null || cost < minCost) {
                                minCost = cost;
                            }
                        }

                        input[x][y] = value;
                        input[toX][toY] = '.';
                    }
                }

            }
        }

        if(allInPlace) {
            minCost = 0;
        }

        costCache.put(string, minCost);

        return minCost;
    }

    private List<Destination> getDestinations(Point from, char[][] input) {
        var seen = new HashSet<Point>();

        var queue = new LinkedList<Point>();
        queue.add(from);

        var costs = new HashMap<Point, Integer>();
        costs.put(from, 0);
        while(!queue.isEmpty()) {
            var point = queue.pop();
            var cost = costs.get(point);
            for(var neighbor : getNeighbors(point, input)) {
                if(input[neighbor.x()][neighbor.y()] != '.' || seen.contains(neighbor)) {
                    continue;
                }
                queue.add(neighbor);
                costs.put(neighbor, cost + 1);
            }
            seen.add(point);
        }

        int fromX = from.x(), fromY = from.y();
        var destinations = costs.entrySet().stream()
            .map(entry -> new Destination(entry.getKey(), entry.getValue()))
            .filter(dest -> !dest.point().equals(from));
        if(fromX == 1) {
            destinations = destinations
                .filter(dest -> {
                    var point = dest.point();
                    return isInPlace(point.x(), point.y(), input[fromX][fromY], input);
                })
                .filter(dest -> {
                    var point = dest.point();
                    int toX = point.x(), toY = point.y();
                    for(int i = 1; true; i++) {
                        char value = input[toX + i][toY];
                        if(value != input[fromX][fromY]) {
                            return value == '#';
                        }
                    }
                })
                .sorted(Comparator.<Destination>comparingInt(entry -> entry.point().x()).reversed())
                .limit(1);
        } else {
            destinations = destinations
                .filter(dest -> {
                    var point = dest.point();
                    int x = point.x(), y = point.y();
                    return x == 1 && y != 3 && y != 5 && y != 7 && y != 9;
                });
        }
        return destinations.toList();
    }

    private List<Point> getNeighbors(Point point, char[][] input) {
        int x = point.x(), y = point.y();
        var points = new ArrayList<Point>();

        if(x - 1 >= 0) {
            points.add(new Point(x - 1, y));
        }

        if(x + 1 < input.length) {
            points.add(new Point(x + 1, y));
        }

        if(y - 1 >= 0) {
            points.add(new Point(x, y - 1));
        }

        if(y + 1 < input[x].length) {
            points.add(new Point(x, y + 1));
        }

        return points;
    }

    private String toString(char[][] c) {
        var builder = new StringBuilder();
        for(var row : c) {
            for(var col : row) {
                builder.append(col);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    private boolean isInPlace(int x, int y, char c, char[][] input) {
        if(x == 1) {
            return false;
        }

        if(y != getTargetColumn(c)) {
            return false;
        }

        for(int i = 1; true; i++) {
            char value = input[x + i][y];
            if(value == '#') {
                return true;
            } else if(value == '.' || value == c) {
                continue;
            } else {
                return false;
            }
        }
    }

    private int getMovementCost(char c) {
        return switch(c) {
            case 'A' -> 1;
            case 'B' -> 10;
            case 'C' -> 100;
            case 'D' -> 1000;
            default -> throw new IllegalArgumentException();
        };
    }

    private int getTargetColumn(char c) {
        return switch(c) {
            case 'A' -> 3;
            case 'B' -> 5;
            case 'C' -> 7;
            case 'D' -> 9;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    protected char[][] parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n"))
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }


    private record Destination(Point point, int cost) {}

}
