import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day12.Day12Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test extends DayTest {

    public Day12Test() {
        super(new Day12Solution(), 12);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(10, result.part1Result().result());
        assertEquals(36, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(5178, result.part1Result().result());
        assertEquals(130094, result.part2Result().result());
    }

}
