package rasterops;

import models.Line;
import models.OrientedLines;
import models.Polygon;
import objectdata.Point2D;
import rasterdata.Raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScanLine {
    void draw(Polygon polygon, Raster raster) {
        OrientedLines orientedLines = getOrientedLines(polygon);

        // TODO: remake for yMin and yMax calculated in getOrientedLines
        for (int y = orientedLines.getYMin(); y < orientedLines.getYMax(); y++) {
            List<Integer> xIntersections = getIntersectionPointsPerYAxe(y, orientedLines.getOrientedLines());
            // algorithm for painting a y line
            paintYLine(y, xIntersections, raster);
        }
    }

    private void paintYLine(int y, List<Integer> xIntersections, Raster raster) {
        boolean isAfterOddIntersection = false;
        for (int x = 0; x < raster.getWidth(); x++) {
            // if scanline is not painting
            if (!isAfterOddIntersection) {
                Optional<Integer> closestIntersection = findNextClosestIntersection(x, xIntersections);
                // if current intersection is last intersection
                if (closestIntersection.isEmpty()) {
                    break;
                }

                isAfterOddIntersection = true;
                x = closestIntersection.get();
                continue;
            }

            // when scanline is on intersection and is not after odd number
            if (xIntersections.contains(x)) {
                isAfterOddIntersection = false;
                continue;
            }

            raster.setColor(x, y, 0xff00ff);
        }
    }

    /**
     * Finds next closest intersection in sorted list
     * @param currentPosition Position to which closest position should be find
     * @param xIntersections Array in which to search closest position
     * @return closest bigger intersection value or empty optional
    */
    private Optional<Integer> findNextClosestIntersection(int currentPosition, List<Integer> xIntersections) {
        for (int intersection : xIntersections) {
            if (intersection > currentPosition) {
                return Optional.of(intersection);
            }
        }

        return Optional.empty();
    }

    private List<Integer> getIntersectionPointsPerYAxe(int y, List<Line> orientedLines) {
        List<Integer> intersectionPoints = new ArrayList<>();
        for (Line line : orientedLines) {
            if (line.isHorizontal()) {
                continue;
            }

            Optional<Integer> yInterception = line.yIntercept(y);
            yInterception.ifPresent(intersectionPoints::add);
        }

        intersectionPoints = intersectionPoints.stream().distinct().collect(Collectors.toList());

        Collections.sort(intersectionPoints);

        return removeIntersectionWhichDontHaveEmptySpaceBetweenLines(intersectionPoints);
    }

    private List<Integer> removeIntersectionWhichDontHaveEmptySpaceBetweenLines(List<Integer> intersectionPoints) {
        if (intersectionPoints.size() == 1) {
            return new ArrayList<>();
        }

        return intersectionPoints;
    }

    private OrientedLines getOrientedLines(Polygon polygon) {
        List<Point2D> points = polygon.getPoints();
        List<Line> orientedLines = new ArrayList<>();
        int yMin = 0;
        int yMax = 0;

        for (int i = 0; i < points.size(); i++) {
            Point2D currentPoint = points.get(i);
            Point2D nextPoint = points.get((i + 1) % points.size());

            Line line = new Line(currentPoint, nextPoint, 0xffffff, 1);

            // if line is horizontal no orientation is needed, will be skipped later
            if (line.isHorizontal()) {
                continue;
            }

            Line orientedLine = prepareLine(line);

            if (yMin > orientedLine.getYStart()) {
                yMin = orientedLine.getYStart();
            }

            if (yMax < orientedLine.getYEnd()) {
                yMax = orientedLine.getYEnd();
            }

            orientedLines.add(orientedLine);
        }

        yMin = yMin + 1;
        yMax = yMax - 1;
        return new OrientedLines(orientedLines, yMin, yMax);
    }

    private Line prepareLine(Line line) {
        if (line.getYStart() > line.getYEnd()) {
            return line.getReverseLine();
        }

        return line;
    }
}