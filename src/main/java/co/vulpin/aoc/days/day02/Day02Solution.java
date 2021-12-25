package co.vulpin.aoc.days.day02;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.List;

public class Day02Solution extends AbstractDayParallelSolution<List<Instruction>> {

    @Override
    protected Object solvePart1(List<Instruction> input) {
        int depth = 0;
        int position = 0;

        for(var instruction : input) {
            int amount = instruction.amount();
            switch(instruction.type()) {
                case FORWARD -> position += amount;
                case UP      -> depth -= amount;
                case DOWN    -> depth += amount;
            }
        }

        return depth * position;
    }

    @Override
    protected Object solvePart2(List<Instruction> input) {
        int aim = 0;
        int depth = 0;
        int position = 0;

        for(var instruction : input) {
            int amount = instruction.amount();
            switch(instruction.type()) {
                case FORWARD -> {
                    position += amount;
                    depth += aim * amount;
                }
                case UP      -> aim -= amount;
                case DOWN    -> aim += amount;
            }
        }

        return depth * position;
    }

    @Override
    protected List<Instruction> parseInput(String input) {
        return input.lines()
            .map(this::parseRow)
            .toList();
    }

    private Instruction parseRow(String input) {
        var parts = input.split(" ");
        var type = InstructionType.valueOf(parts[0].toUpperCase());
        var amount = Integer.parseInt(parts[1]);
        return new Instruction(type, amount);
    }

}
