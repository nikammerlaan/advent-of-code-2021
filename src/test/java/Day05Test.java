import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day05.Day05Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test extends DayTest {

    public Day05Test() {
        super(new Day05Solution(), 5);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(5L, result.part1Result().result());
        assertEquals(12L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(4826L, result.part1Result().result());
        assertEquals(16793L, result.part2Result().result());
    }

}
