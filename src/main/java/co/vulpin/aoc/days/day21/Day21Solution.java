package co.vulpin.aoc.days.day21;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.HashMap;
import java.util.Map;

public class Day21Solution extends AbstractDayParallelSolution<Day21Solution.Input> {

    private static final int PART_1_TARGET = 1000;
    private static final int PART_1_DIE_SIZE = 100;
    private static final int PART_2_TARGET = 21;

    private final Map<GameState, SimulateOutput> OUTPUT_CACHE = new HashMap<>();

    @Override
    protected Object solvePart1(Input input) {
        var a = new PlayerState(input.a(), 0);
        var b = new PlayerState(input.b(), 0);

        int turn = 0;
        var die = new DeterministicDieIterator(PART_1_DIE_SIZE);
        for(; a.score() < PART_1_TARGET && b.score() < PART_1_TARGET; turn++) {
            int amount = die.next() + die.next() + die.next();
            if(turn % 2 == 0) {
                a = a.roll(amount);
            } else {
                b = b.roll(amount);
            }
        }

        return Math.min(a.score(), b.score()) * turn * 3;
    }

    @Override
    protected Object solvePart2(Input input) {
        var a = new PlayerState(input.a(), 0);
        var b = new PlayerState(input.b(), 0);
        var state = new GameState(a, b, true);
        var output = simulate(state);
        return Math.max(output.aWins(), output.bWins());
    }

    private SimulateOutput simulate(GameState state) {
        if(OUTPUT_CACHE.containsKey(state)) {
            return OUTPUT_CACHE.get(state);
        }

        var a = state.a();
        var b = state.b();
        var aTurn = state.aTurn();

        if(a.score() >= PART_2_TARGET) {
            return new SimulateOutput(1, 0);
        } else if(b.score() >= PART_2_TARGET) {
            return new SimulateOutput(0, 1);
        }

        long aWins = 0;
        long bWins = 0;
        for(int x = 1; x <= 3; x++) {
            for(int y = 1; y <= 3; y++){
                for(int z = 1; z <= 3; z++) {
                    int amount = x + y + z;
                    var newA = aTurn ? a.roll(amount) : a;
                    var newB = aTurn ? b : b.roll(amount);
                    var newState = new GameState(newA, newB, !aTurn);
                    var output = simulate(newState);
                    aWins += output.aWins();
                    bWins += output.bWins();
                }
            }
        }

        var output = new SimulateOutput(aWins, bWins);
        OUTPUT_CACHE.put(state, output);
        return output;
    }

    @Override
    protected Input parseInput(String input) {
        var parts = input.split("\n");
        return new Input(
            parseLine(parts[0]),
            parseLine(parts[1])
        );
    }

    private int parseLine(String input) {
        var parts = input.split(": ");
        return Integer.parseInt(parts[1]);
    }
    
    record Input(int a, int b) {}
    record SimulateOutput(long aWins, long bWins) {}
    record PlayerState(int position, int score) {

        public PlayerState roll(int amount) {
            int newPosition = (position + amount - 1) % 10 + 1;
            int newScore = score + newPosition;
            return new PlayerState(newPosition, newScore);
        }

    }
    record GameState(PlayerState a, PlayerState b, boolean aTurn) {



    }

}
