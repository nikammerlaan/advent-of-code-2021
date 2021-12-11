import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day02.Day02Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test extends DayTest {

    public Day02Test() {
        super(new Day02Solution(), 2);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(150, result.part1Result().result());
        assertEquals(900, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(2102357, result.part1Result().result());
        assertEquals(2101031224, result.part2Result().result());
    }

}
