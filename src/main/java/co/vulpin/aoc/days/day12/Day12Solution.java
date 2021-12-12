package co.vulpin.aoc.days.day12;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.*;

public class Day12Solution extends AbstractDayParallelSolution<Day12Solution.Node> {

    @Override
    protected Object solvePart1(Node input) {
        return solve0(input, new HashSet<>(), true);
    }

    @Override
    protected Object solvePart2(Node input) {
        return solve0(input, new HashSet<>(), false);
    }

    private int solve0(Node input, Set<Node> visitedNodes, boolean visitedTwice) {
        if(input.end()) {
            return 1;
        }

        int sum = 0;
        for(var connection : input.connections()) {
            // We cannot traverse back to the start 🙅‍
            if(connection.start()) {
                continue;
            }

            // If the cave is small AND the cave was already visited AND we've already visited a cave twice, continue
            if(!connection.big() && visitedNodes.contains(connection) && visitedTwice) {
                continue;
            }

            var newVisitedTwice = visitedTwice;
            var added = false;
            if(!connection.big()) {
                added = visitedNodes.add(connection);
                if(!added) {
                    newVisitedTwice = true;
                }
            }
            sum += solve0(connection, visitedNodes, newVisitedTwice);

            if(added) {
                visitedNodes.remove(connection);
            }
        }

        return sum;
    }

    @Override
    protected Node parseInput(String rawInput) {
        Map<String, Node> nodes = new HashMap<>();

        for(var row : rawInput.split("\n")) {
            var parts = row.split("-");
            var aName = parts[0];
            var bName = parts[1];

            var a = nodes.computeIfAbsent(aName, Node::new);
            var b = nodes.computeIfAbsent(bName, Node::new);

            a.connections().add(b);
            b.connections().add(a);
        }

        return nodes.get("start");
    }

    record Node(boolean big, boolean start, boolean end, List<Node> connections) {

        public Node(String name) {
            this(
                Character.isUpperCase(name.charAt(0)),
                name.equals("start"),
                name.equals("end"),
                new ArrayList<>()
            );
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }

    }

}
