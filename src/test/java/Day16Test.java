import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day16.Day16Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Test extends DayTest {

    public Day16Test() {
        super(new Day16Solution(), 16);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(8, result.part1Result().result());
        assertEquals(54L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(957, result.part1Result().result());
        assertEquals(744953223228L, result.part2Result().result());
    }


}
