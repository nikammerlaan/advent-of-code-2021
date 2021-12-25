package co.vulpin.aoc;

import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.DaySolution;
import co.vulpin.aoc.misc.InputUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {

    private static final ZoneId AOC_ZONE_ID = ZoneId.of("America/New_York");
    private static final String CLASS_NAME_FORMAT = "co.vulpin.aoc.days.day%02d.Day%02dSolution";

    public static void main(String[] args) throws Exception {
        int dayNumber;
        if(args.length == 1) {
            dayNumber = Integer.parseInt(args[0]);
        } else {
            dayNumber = getCurrentDayNumber();
        }
        System.out.printf("Day %02d\n------\n\n", dayNumber);

        var daySolution = getDaySolution(dayNumber);
        var input = InputUtils.getRealInput(dayNumber);
        var result = daySolution.calculateAnswers(input);
        printResult(result);
    }

    private static void printResult(Result result) {
        var part1 = result.part1Result();
        var part2 = result.part2Result();
        var parse = result.parseResult();
        System.out.printf("Parsing\n\tDuration: %dms\n\n", parse.duration().toMillis());
        System.out.printf("Part 1\n\tAnswer: %s\n\tDuration: %,dms\n\n", part1.result(), part1.duration().toMillis());
        System.out.printf("Part 2\n\tAnswer: %s\n\tDuration: %,dms\n\n", part2.result(), part2.duration().toMillis());
    }

    private static DaySolution getDaySolution(int dayNumber) throws Exception {
        var clazz = getDaySolutionClass(dayNumber);
        var constructor = clazz.getConstructor();
        return constructor.newInstance();
    }

    private static Class<DaySolution> getDaySolutionClass(int dayNumber) throws Exception {
        var className = String.format(CLASS_NAME_FORMAT, dayNumber, dayNumber);
        return (Class<DaySolution>) Class.forName(className);
    }

    private static int getCurrentDayNumber() {
        return ZonedDateTime.now(AOC_ZONE_ID).getDayOfMonth();
    }

}
