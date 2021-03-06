package co.vulpin.aoc.days.day08;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.*;
import java.util.stream.Collectors;

public class Day08Solution extends AbstractDayParallelSolution<List<Day08Solution.Display>> {

    @Override
    protected Object solvePart1(List<Display> input) {
        return input.stream()
            .map(Display::outputs)
            .flatMap(List::stream)
            .map(Set::size)
            .filter(Set.of(2,3,4,7)::contains)
            .count();
    }

    @Override
    protected Object solvePart2(List<Display> input) {
        return input.stream()
            .mapToInt(this::getOutput)
            .sum();
    }

    private int getOutput(Display display) {
        var eight = display.patterns().stream()
            .filter(s -> s.size() == 7)
            .findFirst()
            .orElseThrow();
        var one = display.patterns().stream()
            .filter(s -> s.size() == 2)
            .findFirst()
            .orElseThrow();
        var seven = display.patterns().stream()
            .filter(s -> s.size() == 3)
            .findFirst()
            .orElseThrow();
        var four = display.patterns().stream()
            .filter(s -> s.size() == 4)
            .findFirst()
            .orElseThrow();

        var temp = new HashSet<>(seven);
        temp.removeAll(one);
        var a = temp.iterator().next();

        temp = display.patterns().stream()
            .filter(pattern -> pattern.size() == 6)
            .map(pattern -> {
                var temp2 = new HashSet<>(eight);
                temp2.removeAll(pattern);
                return temp2.iterator().next();
            })
            .collect(Collectors.toCollection(HashSet::new));
        temp.retainAll(one);
        var c = temp.iterator().next();

        temp = new HashSet<>(one);
        temp.remove(c);
        var f = temp.iterator().next();

        var two = display.patterns().stream()
            .filter(s -> s.size() == 5)
            .filter(s -> !s.contains(f))
            .findFirst()
            .orElseThrow();

        temp = new HashSet<>(eight);
        temp.removeAll(one);
        temp.removeAll(two);
        var b = temp.iterator().next();

        var five = display.patterns().stream()
            .filter(s -> s.size() == 5)
            .filter(s -> s.contains(b))
            .findFirst()
            .orElseThrow();

        temp = new HashSet<>(four);
        temp.removeAll(seven);
        temp.remove(b);
        var d = temp.iterator().next();

        temp = new HashSet<>(five);
        temp.removeAll(seven);
        temp.removeAll(four);
        var g = temp.iterator().next();

        temp = new HashSet<>(eight);
        temp.remove(a);
        temp.remove(b);
        temp.remove(c);
        temp.remove(d);
        temp.remove(f);
        temp.remove(g);
        var e = temp.iterator().next();

        var zero = Set.of(a, b, c, e, f, g);
        var three = Set.of(a, c, d, f, g);
        var six = Set.of(a, b, d, e, f, g);

        var rawOutput = display.outputs().stream()
            .map(set -> {
                if(set.equals(zero)) {
                    return 0;
                } else if(set.equals(one)) {
                    return 1;
                } else if(set.equals(two)) {
                    return 2;
                } else if(set.equals(three)) {
                    return 3;
                } else if(set.equals(four)) {
                    return 4;
                } else if(set.equals(five)) {
                    return 5;
                } else if(set.equals(six)) {
                    return 6;
                } else if(set.equals(seven)) {
                    return 7;
                } else if(set.equals(eight)) {
                    return 8;
                } else {
                    return 9;
                }
            })
            .map(String::valueOf)
            .collect(Collectors.joining());

        return Integer.parseInt(rawOutput);
    }

    @Override
    protected List<Display> parseInput(String rawInput) {
        return rawInput.lines()
            .map(s -> {
                var parts = s.split(" \\| ");
                var patterns = parseMultiple(parts[0]);
                var outputs = parseMultiple(parts[1]);
                return new Display(patterns, outputs);
            })
            .toList();
    }

    private List<Set<Character>> parseMultiple(String s) {
        return Arrays.stream(s.split(" "))
            .map(this::parse)
            .toList();
    }

    private Set<Character> parse(String ssd) {
        return ssd.chars()
            .mapToObj(i -> (char) i)
            .collect(Collectors.toSet());
    }
    
    record Display(List<Set<Character>> patterns, List<Set<Character>> outputs) {}
    
}
