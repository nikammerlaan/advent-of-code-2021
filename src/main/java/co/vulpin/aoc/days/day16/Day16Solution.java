package co.vulpin.aoc.days.day16;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day16Solution extends AbstractDayParallelSolution<Day16Solution.Packet> {

    @Override
    protected Object solvePart1(Packet packet) {
        return packet.getSummedVersion();
    }

    @Override
    protected Object solvePart2(Packet packet) {
        return packet.getValue();
    }

    @Override
    protected Packet parseInput(String rawInput) {
        var input = new BigInteger("1" + rawInput, 16).toString(2).substring(1);
        return parsePackets(input, new AtomicInteger(0), 1).get(0);
    }

    private List<Packet> parsePackets(String input, AtomicInteger i, int limit) {
        var packetsParsed = 0;
        var packets = new ArrayList<Packet>();

        while((limit == -1 && i.get() < input.length()) || packetsParsed < limit) {
            var packet = parsePacket(input, i);
            packets.add(packet);
            packetsParsed++;
        }

        return packets;
    }

    private Packet parsePacket(String input, AtomicInteger i) {
        var packetVersion = getIntSubstring(input, i, 3);
        var packetTypeId = getIntSubstring(input, i, 3);

        if(packetTypeId != 4) {
            var lengthTypeId = getIntSubstring(input, i, 1);
            if(lengthTypeId == 0) {
                var totalBitLength = getIntSubstring(input, i, 15);
                var rawChildren = getSubstring(input, i, totalBitLength);
                var children = parsePackets(rawChildren, new AtomicInteger(0), -1);
                return new Packet(packetVersion, packetTypeId, null, children);
            } else {
                var childCount = getIntSubstring(input, i, 11);
                var children = parsePackets(input, i, childCount);
                return new Packet(packetVersion, packetTypeId, null, children);
            }
        } else {
            var dataBuilder = new StringBuilder();

            while(true) {
                var cont = getIntSubstring(input, i, 1);
                var segment = getSubstring(input, i, 4);
                dataBuilder.append(segment);
                if(cont == 0) {
                    break;
                }
            }

            var data = Long.parseLong(dataBuilder.toString(), 2);
            return new Packet(packetVersion, packetTypeId, data, Collections.emptyList());
        }
    }

    private int getIntSubstring(String s, AtomicInteger i, int length) {
        return Integer.parseInt(getSubstring(s, i, length), 2);
    }

    private String getSubstring(String s, AtomicInteger i, int length) {
        return s.substring(i.get(), i.addAndGet(length));
    }

    record Packet(int version, int type, Long data, List<Packet> children) {

        public long getValue() {
            return switch(type) {
                case 0 -> children.stream()
                    .mapToLong(Packet::getValue)
                    .sum();
                case 1 -> children.stream()
                    .mapToLong(Packet::getValue)
                    .reduce(1, (a, b) -> a * b);
                case 2 -> children.stream()
                    .mapToLong(Packet::getValue)
                    .min()
                    .orElseThrow();
                case 3 -> children.stream()
                    .mapToLong(Packet::getValue)
                    .max()
                    .orElseThrow();
                case 4 -> data;
                case 5 -> {
                    var first = children.get(0);
                    var second = children.get(1);
                    yield first.getValue() > second.getValue() ? 1 : 0;
                }
                case 6 -> {
                    var first = children.get(0);
                    var second = children.get(1);
                    yield first.getValue() < second.getValue() ? 1 : 0;
                }
                case 7 -> {
                    var first = children.get(0);
                    var second = children.get(1);
                    yield first.getValue() == second.getValue() ? 1 : 0;
                }
                default -> throw new IllegalStateException();
            };
        }

        public int getSummedVersion() {
            return version + children.stream()
                .mapToInt(Packet::getSummedVersion)
                .sum();
        }

    }

}
