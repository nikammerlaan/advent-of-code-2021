import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day08.Day08Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test extends DayTest {

    public Day08Test() {
        super(new Day08Solution(), 8);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(26L, result.part1Result().result());
        assertEquals(61229, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(301L, result.part1Result().result());
        assertEquals(908067, result.part2Result().result());
    }

}
