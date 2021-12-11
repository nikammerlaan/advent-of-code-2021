import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day07.Day07Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Test extends DayTest {

    public Day07Test() {
        super(new Day07Solution(), 7);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(37, result.part1Result().result());
        assertEquals(168, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(352331, result.part1Result().result());
        assertEquals(99266250, result.part2Result().result());
    }

}
