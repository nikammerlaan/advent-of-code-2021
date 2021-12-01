package co.vulpin.aoc.data;

public record Result(
    TimeResult<?> parseResult,
    TimeResult<?> part1Result,
    TimeResult<?> part2Result
) {}
