import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day01.Day01Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test extends DayTest {

    public Day01Test() {
        super(new Day01Solution(), 1);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(7, result.part1Result().result());
        assertEquals(5, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(1759, result.part1Result().result());
        assertEquals(1805, result.part2Result().result());
    }

}
