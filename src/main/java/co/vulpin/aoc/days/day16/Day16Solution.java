package co.vulpin.aoc.days.day16;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.math.BigInteger;

public class Day16Solution extends AbstractDayParallelSolution<Packet> {

    @Override
    protected Object solvePart1(Packet packet) {
        return packet.getSummedVersion();
    }

    @Override
    protected Object solvePart2(Packet packet) {
        return packet.value();
    }

    @Override
    protected Packet parseInput(String rawInput) {
        var input = new BigInteger("1" + rawInput, 16).toString(2).substring(1);
        var parser = new PacketParser(input);
        return parser.parsePacket();
    }

}
