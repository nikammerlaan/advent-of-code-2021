import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day20.Day20Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day20Test extends DayTest {

    public Day20Test() {
        super(new Day20Solution(), 20);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(35, result.part1Result().result());
        assertEquals(3351, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(5786, result.part1Result().result());
        assertEquals(16757, result.part2Result().result());
    }

}
