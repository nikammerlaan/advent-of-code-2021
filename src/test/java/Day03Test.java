import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day03.Day03Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test extends DayTest {

    public Day03Test() {
        super(new Day03Solution(), 3);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(198, result.part1Result().result());
        assertEquals(230, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(1071734, result.part1Result().result());
        assertEquals(6124992, result.part2Result().result());
    }

}
