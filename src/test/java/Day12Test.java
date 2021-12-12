import co.vulpin.aoc.days.DaySolution;
import co.vulpin.aoc.days.day12.Day12Solution;
import co.vulpin.aoc.misc.InputUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {

    private final DaySolution daySolution;

    public Day12Test() {
        this.daySolution = new Day12Solution();
    }

    @Test
    public void test_exampleSmallInput() {
        var input = InputUtils.getInput(12, "exampleSmall");
        var result = daySolution.calculateAnswers(input);
        assertEquals(10, result.part1Result().result());
        assertEquals(36, result.part2Result().result());
    }

    @Test
    public void test_exampleMediumInput() {
        var input = InputUtils.getInput(12, "exampleMedium");
        var result = daySolution.calculateAnswers(input);
        assertEquals(19, result.part1Result().result());
        assertEquals(103, result.part2Result().result());
    }

    @Test
    public void test_exampleLargeInput() {
        var input = InputUtils.getInput(12, "exampleLarge");
        var result = daySolution.calculateAnswers(input);
        assertEquals(226, result.part1Result().result());
        assertEquals(3509, result.part2Result().result());
    }

    @Test
    public void test_realInput() {
        var input = InputUtils.getRealInput(12);
        var result = daySolution.calculateAnswers(input);
        assertEquals(5178, result.part1Result().result());
        assertEquals(130094, result.part2Result().result());
    }

}
