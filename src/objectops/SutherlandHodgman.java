package objectops;

import models.Line;
import models.Polygon;
import objectdata.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a polygon cutting algorithm
 */
public class SutherlandHodgman {
    public Polygon cut(Polygon cuttingPolygon, Polygon cutPolygon) {
        List<Line> cuttingLines = cuttingPolygon.getLines();
        List<Point2D> outputList = new ArrayList<>(cutPolygon.getPoints());

        // Iterate through each edge of the cutting polygon
        for (Line cuttingLine : cuttingLines) {
            List<Point2D> inputList = makeDeepCopy(outputList);
            outputList.clear();

            for (int i = 0; i < inputList.size(); i++) {
                Point2D start = inputList.get(i);
                Point2D end = inputList.get((i + 1) % inputList.size());
                boolean startInside = cuttingLine.isInside(start);
                boolean endInside = cuttingLine.isInside(end);

                if (startInside && endInside) {
                    // Case 1: Both points are inside
                    outputList.add(end);
                } else if (startInside) {
                    // Case 2: Start is inside, end is outside
                    Optional<Point2D> intersection = cuttingLine.interceptionWithAnotherLine(new Line(start, end, 0xffffff, 1));
                    intersection.ifPresent(outputList::add);
                } else if (endInside) {
                    // Case 3: Start is outside, end is inside
                    Optional<Point2D> intersection = cuttingLine.interceptionWithAnotherLine(new Line(start, end, 0xffffff, 1));
                    intersection.ifPresent(outputList::add);
                    outputList.add(end);
                }
                // Case 4: Both points are outside -> Do nothing
            }
        }

        return new Polygon(outputList, 0xffffff);
    }

    private List<Point2D> makeDeepCopy(List<Point2D> points) {
        List<Point2D> copyPoints = new ArrayList<>(points);
        for (Point2D point : points) {
            copyPoints.add(new Point2D(point.getX(), point.getY()));
        }

        return copyPoints;
    }
}
