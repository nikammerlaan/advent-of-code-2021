import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day17.Day17Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test extends DayTest {

    public Day17Test() {
        super(new Day17Solution(), 17);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(45, result.part1Result().result());
        assertEquals(112, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(5886, result.part1Result().result());
        assertEquals(1806, result.part2Result().result());
    }


}
