import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day15.Day15Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test extends DayTest {

    public Day15Test() {
        super(new Day15Solution(), 15);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(40, result.part1Result().result());
        assertEquals(315, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(621, result.part1Result().result());
        assertEquals(2904, result.part2Result().result());
    }


}
