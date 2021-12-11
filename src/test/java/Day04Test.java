import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day04.Day04Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test extends DayTest {

    public Day04Test() {
        super(new Day04Solution(), 4);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(4512, result.part1Result().result());
        assertEquals(1924, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(10374, result.part1Result().result());
        assertEquals(24742, result.part2Result().result());
    }

}
