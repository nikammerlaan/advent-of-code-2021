package co.vulpin.aoc.misc;

import java.nio.file.Files;
import java.nio.file.Paths;

public class InputUtils {

    private static final String INPUT_PATH_FORMAT = "/inputs/day%02d/%s.txt";

    public static String getRealInput(int dayNumber) {
        return getInput(dayNumber, "real");
    }

    public static String getExampleInput(int dayNumber) {
        return getInput(dayNumber, "example");
    }

    public static String getInput(int dayNumber, String name) {
        try {
            var path0 = String.format(INPUT_PATH_FORMAT, dayNumber, name);
            var path = Paths.get(InputUtils.class.getResource(path0).toURI());
            return Files.readString(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
