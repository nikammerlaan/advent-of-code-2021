import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day09.Day09Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test extends DayTest {

    public Day09Test() {
        super(new Day09Solution(), 9);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(15, result.part1Result().result());
        assertEquals(1134L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(502, result.part1Result().result());
        assertEquals(1330560L, result.part2Result().result());
    }

}
