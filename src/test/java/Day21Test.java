import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day21.Day21Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Test extends DayTest {

    public Day21Test() {
        super(new Day21Solution(), 21);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(739785, result.part1Result().result());
        assertEquals(444356092776315L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(802452, result.part1Result().result());
        assertEquals(270005289024391L, result.part2Result().result());
    }

}
