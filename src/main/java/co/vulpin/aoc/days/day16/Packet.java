package co.vulpin.aoc.days.day16;

import java.util.Collections;
import java.util.List;

public record Packet(int version, int type, long value, List<Packet> children) {

    // Constructor for operator packets
    public Packet(int version, int type, List<Packet> children) {
        this(
            version,
            type,
            computeValue(type, children),
            children
        );
    }

    // Constructor for literal packets
    public Packet(int version, int type, long value) {
        this(version, type, value, Collections.emptyList());
    }

    public int getSummedVersion() {
        return version + children.stream()
            .mapToInt(Packet::getSummedVersion)
            .sum();
    }

    private static long computeValue(int type, List<Packet> children) {
        return switch (type) {
            case 0 -> children.stream()
                .mapToLong(Packet::value)
                .sum();
            case 1 -> children.stream()
                .mapToLong(Packet::value)
                .reduce(1, (a, b) -> a * b);
            case 2 -> children.stream()
                .mapToLong(Packet::value)
                .min()
                .orElseThrow();
            case 3 -> children.stream()
                .mapToLong(Packet::value)
                .max()
                .orElseThrow();
            case 5 -> {
                var first = children.get(0);
                var second = children.get(1);
                yield first.value() > second.value() ? 1 : 0;
            }
            case 6 -> {
                var first = children.get(0);
                var second = children.get(1);
                yield first.value() < second.value() ? 1 : 0;
            }
            case 7 -> {
                var first = children.get(0);
                var second = children.get(1);
                yield first.value() == second.value() ? 1 : 0;
            }
            default -> throw new IllegalStateException();
        };
    }

}
