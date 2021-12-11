import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day10.Day10Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test extends DayTest {

    public Day10Test() {
        super(new Day10Solution(), 10);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(26397, result.part1Result().result());
        assertEquals(288957L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(388713, result.part1Result().result());
        assertEquals(3539961434L, result.part2Result().result());
    }

}
