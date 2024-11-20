package models;

import objectdata.Point2D;

import java.util.ArrayList;
import java.util.List;

public class NGon extends Polygon {
    /**
     * Draws NGon based on center point, start angle, radius and number of vertexes.
     *
     * @param centerPoint Center of new NGon
     * @param startAngle Angle by which NGon will be rotated
     * @param radius Distance between center of NGon and points of NGon
     * @param numberOfVertexes Number of vertexes that NGon will have
     * @param thickness Thickness of lines on NGon
     */
    public NGon(Point2D centerPoint, double startAngle, int radius, int numberOfVertexes, int thickness) {
        super(new ArrayList<>(), thickness);
        List<Point2D> vertices = new ArrayList<>();

        // find diff pi angle
        double diffAngle = 2 * Math.PI / numberOfVertexes;

        Point2D startPoint = new Point2D(centerPoint.getX(), centerPoint.getY() - radius);
        for (int i = 0; i < numberOfVertexes; i++) {
            Point2D pointTranslatedToOrigin = new Point2D(startPoint.getX() - centerPoint.getX(), startPoint.getY() - centerPoint.getY());
            Point2D rotatedPoint = pointTranslatedToOrigin.rotate(startAngle + i * diffAngle);
            vertices.add(new Point2D(rotatedPoint.getX() + centerPoint.getX(), rotatedPoint.getY() + centerPoint.getY()));
        }

        points = vertices;
    }
}
