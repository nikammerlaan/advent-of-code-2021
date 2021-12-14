import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day14.Day14Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test extends DayTest {

    public Day14Test() {
        super(new Day14Solution(), 14);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(1588L, result.part1Result().result());
        assertEquals(2188189693529L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(2375L, result.part1Result().result());
        assertEquals(1976896901756L, result.part2Result().result());
    }


}
