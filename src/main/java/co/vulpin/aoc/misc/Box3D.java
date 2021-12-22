package co.vulpin.aoc.misc;

public record Box3D(Range x, Range y, Range z, long volume) {

    public Box3D(Range x, Range y, Range z) {
        this(x, y, z, x.size() * y.size() * z.size());
    }

    public boolean intersects(Box3D other) {
        return x.intersects(other.x()) && y.intersects(other.y()) && z.intersects(other.z());
    }

    public boolean contains(Box3D other) {
        return x.contains(other.x()) && y.contains(other.y()) && z.contains(other.z());
    }

}
