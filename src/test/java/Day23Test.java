import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day23.Day23Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day23Test extends DayTest {

    public Day23Test() {
        super(new Day23Solution(), 23);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(12521, result.part1Result().result());
        assertEquals(44169, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(15385, result.part1Result().result());
        assertEquals(49803, result.part2Result().result());
    }

}
