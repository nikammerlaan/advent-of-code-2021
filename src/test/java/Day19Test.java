import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day19.Day19Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day19Test extends DayTest {

    public Day19Test() {
        super(new Day19Solution(), 19);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(79L, result.part1Result().result());
        assertEquals(3621, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(414L, result.part1Result().result());
        assertEquals(13000, result.part2Result().result());
    }

}
