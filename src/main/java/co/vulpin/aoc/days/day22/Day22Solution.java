package co.vulpin.aoc.days.day22;

import co.vulpin.aoc.days.AbstractDayParallelSolution;
import co.vulpin.aoc.misc.Box3D;
import co.vulpin.aoc.misc.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Day22Solution extends AbstractDayParallelSolution<List<Day22Solution.Instruction>> {

    @Override
    protected Object solvePart1(List<Day22Solution.Instruction> input) {
        return solve0(input.subList(0, 20));
    }

    @Override
    protected Object solvePart2(List<Day22Solution.Instruction> input) {
        return solve0(input);
    }

    private long solve0(List<Day22Solution.Instruction> input) {
        var boxes = new ArrayList<Box3D>();

        for(var instruction : input) {
            var box = instruction.box();

            var iter = boxes.listIterator();
            while(iter.hasNext()) {
                var other = iter.next();
                if(box.intersects(other)) {
                    iter.remove();
                    getOverlap(box, other).forEach(iter::add);
                }
            }

            if(instruction.on()) {
                boxes.add(box);
            }
        }

        return boxes.stream()
            .mapToLong(Box3D::volume)
            .sum();
    }

    private Stream<Box3D> getOverlap(Box3D box, Box3D other) {
        if(box.contains(other)) {
            return Stream.empty();
        } else if(!box.intersects(other)) {
            return Stream.of(other);
        }

        if(box.x().intersects(other.x()) && !box.x().contains(other.x())) {
            return getOverlap(box.x(), other.x()).stream()
                .map(newX -> new Box3D(newX, other.y(), other.z()))
                .flatMap(newBox -> getOverlap(box, newBox));
        }

        if(box.y().intersects(other.y()) && !box.y().contains(other.y())) {
            return getOverlap(box.y(), other.y()).stream()
                .map(newY -> new Box3D(other.x(), newY, other.z()))
                .flatMap(newBox -> getOverlap(box, newBox));
        }

        if(box.z().intersects(other.z()) && !box.z().contains(other.z())) {
            return getOverlap(box.z(), other.z()).stream()
                .map(newZ -> new Box3D(other.x(), other.y(), newZ))
                .flatMap(newBox -> getOverlap(box, newBox));
        }

        return Stream.of(other);
    }

    private List<Range> getOverlap(Range range, Range other) {
        if(range.contains(other)) {
            return Collections.singletonList(other);
        }

        var newRanges = new ArrayList<Range>();

        if(other.start() < range.start() && other.end() >= range.start()) {
            var split = new Range(other.start(), range.start() - 1);
            other = new Range(range.start(), other.end());
            newRanges.add(split);
        }

        if(other.end() > range.end() && other.start() <= range.end()) {
            var split = new Range(range.end() + 1, other.end());
            other = new Range(other.start(), range.end());
            newRanges.add(split);
        }

        newRanges.add(other);

        return newRanges;
    }

    @Override
    protected List<Day22Solution.Instruction> parseInput(String rawInput) {
        return rawInput.lines()
            .map(this::parseInstruction)
            .toList();
    }

    private Instruction parseInstruction(String input) {
        var parts = input.split(" ");
        var on = parts[0].equals("on");

        parts = parts[1].split(",");
        var xRange = parseRange(parts[0]);
        var yRange = parseRange(parts[1]);
        var zRange = parseRange(parts[2]);
        var box = new Box3D(xRange, yRange, zRange);
        return new Instruction(on, box);
    }

    private Range parseRange(String input) {
        var parts = input.split("(=|\\.\\.)");
        return new Range(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    record Instruction(boolean on, Box3D box) {}

}
