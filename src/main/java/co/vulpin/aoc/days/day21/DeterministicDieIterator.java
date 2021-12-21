package co.vulpin.aoc.days.day21;

import java.util.Iterator;

class DeterministicDieIterator implements Iterator<Integer> {

    private int i;
    private final int max;

    public DeterministicDieIterator(int max) {
        this.i = 0;
        this.max = max;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        i = (i + 1) % max;
        return i;
    }

}
