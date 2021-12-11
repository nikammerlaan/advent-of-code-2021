import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day06.Day06Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test extends DayTest {

    public Day06Test() {
        super(new Day06Solution(), 6);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(5934L, result.part1Result().result());
        assertEquals(26984457539L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(372300L, result.part1Result().result());
        assertEquals(1675781200288L, result.part2Result().result());
    }

}
