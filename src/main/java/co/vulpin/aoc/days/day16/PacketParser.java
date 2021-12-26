package co.vulpin.aoc.days.day16;

import java.util.ArrayList;

public class PacketParser {

    private final String input;
    private int i;

    public PacketParser(String input) {
        this.input = input;
        this.i = 0;
    }

    public Packet parsePacket() {
        var version = readInt(3);
        var typeId = readInt(3);

        if(typeId == 4) {
            return parseLiteralPacket(version, typeId);
        } else {
            return parseOperatorPacket(version, typeId);
        }
    }

    private Packet parseOperatorPacket(int version, int typeId) {
        var lengthTypeId = readInt(1);
        if(lengthTypeId == 0) {
            return parseLengthOperatorPacket(version, typeId);
        } else {
            return parseCountOperatorPacket(version, typeId);
        }
    }

    private Packet parseLengthOperatorPacket(int version, int typeId) {
        var totalChildrenLength = readInt(15);
        var endIndex = i + totalChildrenLength;
        var children = new ArrayList<Packet>();
        while(i < endIndex) {
            var child = parsePacket();
            children.add(child);
        }
        return new Packet(version, typeId, children);
    }

    private Packet parseCountOperatorPacket(int version, int typeId) {
        var childCount = readInt(11);
        var children = new ArrayList<Packet>();
        for(int i = 0; i < childCount; i++) {
            var child = parsePacket();
            children.add(child);
        }
        return new Packet(version, typeId, children);
    }

    private Packet parseLiteralPacket(int version, int typeId) {
        long value = 0;

        while(true) {
            var cont = readBoolean();
            var segment = readInt(4);
            value <<= 4;
            value += segment;
            if(!cont) {
                break;
            }
        }

        return new Packet(version, typeId, value);
    }

    private boolean readBoolean() {
        return read(1).equals("1");
    }

    private int readInt(int size) {
        return Integer.parseInt(read(size), 2);
    }

    private String read(int size) {
        var substring = input.substring(i, i + size);
        i += size;
        return substring;
    }

}
