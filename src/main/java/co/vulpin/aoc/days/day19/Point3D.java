package co.vulpin.aoc.days.day19;

public record Point3D(
    int x,
    int y,
    int z
) {

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
