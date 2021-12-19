package co.vulpin.aoc.days.day19;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.*;
import java.util.stream.Collectors;

public class Day19Solution extends AbstractDayParallelSolution<List<Scanner>> {

    private final Map<NormalizeCacheKey, Scanner> normalizeCache;

    public Day19Solution() {
        this.normalizeCache = new HashMap<>();
    }

    @Override
    protected Object solvePart1(List<Scanner> input) {
        var fixedScanners = new ArrayList<Scanner>();
        var unfixedScanners = new LinkedList<>(input);
        fixedScanners.add(unfixedScanners.remove(0));

        while(!unfixedScanners.isEmpty()) {
            var unfixedIter = unfixedScanners.iterator();
            while(unfixedIter.hasNext()) {
                var unfixed = unfixedIter.next();
                Scanner normalized = null;
                var fixedIter = fixedScanners.iterator();
                while(normalized == null && fixedIter.hasNext()) {
                    var fixed = fixedIter.next();
                    normalized = normalize(fixed, unfixed);
                }
                if(normalized != null) {
                    unfixedIter.remove();
                    fixedScanners.add(normalized);
                }
            }
        }

        return fixedScanners.stream()
            .map(Scanner::points)
            .flatMap(Set::stream)
            .distinct()
            .count();
    }

    @Override
    protected Object solvePart2(List<Scanner> input) {
        var fixedScanners = new ArrayList<Scanner>();
        var unfixedScanners = new LinkedList<>(input);

        var zero = unfixedScanners.remove(0);
        fixedScanners.add(new Scanner(new Point3D(0, 0, 0), zero.points()));

        while(!unfixedScanners.isEmpty()) {
            for(var unfixedIter = unfixedScanners.iterator(); unfixedIter.hasNext();) {
                var unfixed = unfixedIter.next();
                Scanner normalized = null;
                for(var fixedIter = fixedScanners.iterator(); normalized == null && fixedIter.hasNext();) {
                    var fixed = fixedIter.next();
                    normalized = normalize(fixed, unfixed);
                }
                if(normalized != null) {
                    unfixedIter.remove();
                    fixedScanners.add(normalized);
                }
            }
        }

        return fixedScanners.stream()
            .flatMapToInt(a -> fixedScanners.stream()
                .mapToInt(b -> {
                    int xDistance = Math.abs(a.position().x() - b.position().x());
                    int yDistance = Math.abs(a.position().y() - b.position().y());
                    int zDistance = Math.abs(a.position().z() - b.position().z());
                    return xDistance + yDistance + zDistance;
                })
            )
            .max()
            .orElseThrow();
    }

    private Scanner normalize(Scanner a, Scanner b) {
        var cacheKey = new NormalizeCacheKey(a, b);
        if(normalizeCache.containsKey(cacheKey)) {
            return normalizeCache.get(cacheKey);
        } else {
            var normalized = normalize0(a, b);
            normalizeCache.put(cacheKey, normalized);
            return normalized;
        }
    }

    private Scanner normalize0(Scanner a, Scanner b) {
        for(int xRotation = 0; xRotation < 4; xRotation++) {
            for(int yRotation = 0; yRotation < 4; yRotation++) {
                for(int zRotation = 0; zRotation < 4; zRotation++) {
                    if(xRotation == 0 && yRotation == 0 && zRotation == 0) {
                        continue;
                    }

                    for(var aPoint : a.points()) {
                        for(var bPoint : b.points()) {
                            var bPointRotated = rotatePoint(bPoint, xRotation, yRotation, zRotation);
                            var bPosition = offsetPointSubtractive(aPoint, bPointRotated);
                            int count = 0;

                            var normalizedPoints = new HashSet<Point3D>();
                            for(var point : b.points()) {
                                var rotated = rotatePoint(point, xRotation, yRotation, zRotation);
                                var offset = offsetPointAdditive(rotated, bPosition);
                                if(a.points().contains(offset)) {
                                    count++;
                                }
                                normalizedPoints.add(offset);
                            }

                            if(count >= 12) {
                                return new Scanner(bPosition, normalizedPoints);
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private Point3D offsetPointAdditive(Point3D point, Point3D offset) {
        return new Point3D(
            point.x() + offset.x(),
            point.y() + offset.y(),
            point.z() + offset.z()
        );
    }

    private Point3D offsetPointSubtractive(Point3D point, Point3D offset) {
        return new Point3D(
            point.x() - offset.x(),
            point.y() - offset.y(),
            point.z() - offset.z()
        );
    }

    private Point3D rotatePoint(Point3D point, int xRotation, int yRotation, int zRotation) {
        int x = point.x(), y = point.y(), z = point.z();

        for(int i = xRotation; i > 0; i--) {
            var temp = y;
            y = -z;
            z = temp;
        }

        for(int i = yRotation; i > 0; i--) {
            var temp = x;
            x = -z;
            z = temp;
        }

        for(int i = zRotation; i > 0; i--) {
            var temp = x;
            x = -y;
            y = temp;
        }

        return new Point3D(x, y, z);
    }

    @Override
    protected List<Scanner> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n\n"))
            .map(this::parseScanner)
            .toList();
    }

    private Scanner parseScanner(String input) {
        var points = Arrays.stream(input.split("\n"))
            .skip(1)
            .map(this::parsePoint)
            .collect(Collectors.toSet());
        return new Scanner(new Point3D(0, 0, 0), points);
    }

    private Point3D parsePoint(String input) {
        var parts = input.split(",");
        var x = Integer.parseInt(parts[0]);
        var y = Integer.parseInt(parts[1]);
        var z = Integer.parseInt(parts[2]);
        return new Point3D(x, y, z);
    }

    private record NormalizeCacheKey(Scanner a, Scanner b) {}

}
