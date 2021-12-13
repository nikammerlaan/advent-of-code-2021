import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day13.Day13Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test extends DayTest {

    public Day13Test() {
        super(new Day13Solution(), 13);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(17L, result.part1Result().result());
        assertEquals("""
            #####
            #...#
            #...#
            #...#
            #####
            """, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(653L, result.part1Result().result());
        assertEquals("""
            #....#..#.###..####.###..###..###..#..#
            #....#.#..#..#.#....#..#.#..#.#..#.#.#.
            #....##...#..#.###..###..#..#.#..#.##..
            #....#.#..###..#....#..#.###..###..#.#.
            #....#.#..#.#..#....#..#.#....#.#..#.#.
            ####.#..#.#..#.####.###..#....#..#.#..#
            """, result.part2Result().result());
    }


}
