import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day18.Day18Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test extends DayTest {

    public Day18Test() {
        super(new Day18Solution(), 18);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(4140, result.part1Result().result());
        assertEquals(3993, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(4433, result.part1Result().result());
        assertEquals(4559, result.part2Result().result());
    }

}
