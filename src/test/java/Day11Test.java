import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day11.Day11Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test extends DayTest {

    public Day11Test() {
        super(new Day11Solution(), 11);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(1656, result.part1Result().result());
        assertEquals(195, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(1747, result.part1Result().result());
        assertEquals(505, result.part2Result().result());
    }

}
