import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day22.Day22Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day22Test extends DayTest {

    public Day22Test() {
        super(new Day22Solution(), 22);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(590784L, result.part1Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(655005L, result.part1Result().result());
        assertEquals(1125649856443608L, result.part2Result().result());
    }

}
