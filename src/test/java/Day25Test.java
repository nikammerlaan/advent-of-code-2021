import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day25.Day25Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day25Test extends DayTest {

    public Day25Test() {
        super(new Day25Solution(), 25);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(42, result.part1Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(406, result.part1Result().result());
    }

}