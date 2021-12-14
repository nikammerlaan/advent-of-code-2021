package co.vulpin.aoc.days.day14;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14Solution extends AbstractDayParallelSolution<Day14Solution.Input> {

    @Override
    protected Object solvePart1(Input input) {
        return solve(input, 10);
    }

    @Override
    protected Object solvePart2(Input input) {
        return solve(input, 40);
    }

    private long solve(Input input, int depth) {
        // Create rules map for easy pulling
        var rules = new HashMap<String, Rule>();
        for(var rule : input.rules()) {
            var joined = "" + rule.a() + rule.b();
            rules.put(joined, rule);
        }

        var ruleCounts = createEmptyRuleCountMap(input.rules());
        var charCounts = new HashMap<Character, Long>();

        // Fill in char counts map with 0s for all possible chars
        for(var rule : input.rules()) {
            charCounts.put(rule.a(), 0L);
            charCounts.put(rule.b(), 0L);
            charCounts.put(rule.c(), 0L);
        }

        // Setup initial state from template for rules
        var template = input.template();
        for(int i = 0; i < template.length() - 1; i++) {
            var pair = template.substring(i, i + 2);
            var rule = rules.get(pair);
            ruleCounts.compute(rule, (__, count) -> count + 1);
        }
        // Setup initial state from template for chars
        for(char c : template.toCharArray()) {
            charCounts.compute(c, (__, count) -> count + 1);
        }

        for(int i = 0; i < depth; i++) {
            var newRuleCounts = createEmptyRuleCountMap(input.rules());
            for(var rule : input.rules()) {
                var ruleCount = ruleCounts.getOrDefault(rule, 0L);
                var newRule1 = rules.get("" + rule.a() + rule.c());
                var newRule2 = rules.get("" + rule.c() + rule.b());
                newRuleCounts.compute(newRule1, (__, count) -> count + ruleCount);
                newRuleCounts.compute(newRule2, (__, count) -> count + ruleCount);
                charCounts.compute(rule.c(), (__, count) -> count + ruleCount);
            }
            ruleCounts = newRuleCounts;
        }

        var stats = charCounts.values().stream()
            .mapToLong(i -> i)
            .summaryStatistics();
        return stats.getMax() - stats.getMin();
    }

    private Map<Rule, Long> createEmptyRuleCountMap(List<Rule> rules) {
        var map = new HashMap<Rule, Long>();
        for(var rule : rules) {
            map.put(rule, 0L);
        }
        return map;
    }

    @Override
    protected Input parseInput(String rawInput) {
        var parts = rawInput.split("\n\n");

        var template = parts[0];

        var rules = Arrays.stream(parts[1].split("\n"))
            .map(this::parseRule)
            .toList();

        return new Input(template, rules);
    }

    private Rule parseRule(String raw) {
        var a = raw.charAt(0);
        var b = raw.charAt(1);
        var c = raw.charAt(6);
        return new Rule(a, b, c);
    }

    record Input(String template, List<Rule> rules) {}
    record Rule(char a, char b, char c) {}

}
