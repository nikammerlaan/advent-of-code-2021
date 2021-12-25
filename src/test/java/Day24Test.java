import co.vulpin.aoc.days.day24.Day24Solution;
import co.vulpin.aoc.misc.InputUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24Test {

    private final Day24Solution solution;

    public Day24Test() {
        this.solution = new Day24Solution();
    }

    @Test
    public void testRealInput() {
        var input = InputUtils.getRealInput(24);
        var result = solution.calculateAnswers(input);
        assertEquals("99691891979938", result.part1Result().result());
        assertEquals("27141191213911", result.part2Result().result());
    }

}
