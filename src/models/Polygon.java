package models;

import objectdata.Point2D;
import transforms.Mat3;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    protected List<Point2D> points;
    private final int thickness;

    public Polygon(List<Point2D> points, int thickness) {
        this.points = new ArrayList<>(points);
        this.thickness = thickness;
    }

    public ArrayList<Point2D> getPoints() {
        return (ArrayList<Point2D>) points;
    }

    public void addPoint(Point2D point) {
        points.add(point);
    }

    public int getThickness() {
        return thickness;
    }

    public List<Line> getLines() {
        List<Line> lines = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            Point2D currentPoint = points.get(i);
            Point2D nextPoint = points.get((i + 1) % points.size());

            lines.add(new Line(currentPoint, nextPoint, 0xffffff, thickness));
        }

        return lines;
    }

    public Polygon translate(double dx, double dy) {
        ArrayList<Point2D> newPoints = new ArrayList<>();

        // Translate each point in the polygon by dx and dy
        for (Point2D point : points) {
            newPoints.add(point.translate(dx, dy));
        }

        return new Polygon(newPoints, thickness);
    }

    public Polygon rotate(double angle) {
        Point2D center = getCenterOfPolygon();
        ArrayList<Point2D> newPoints = new ArrayList<>();

        // Rotate each point around the center
        for (Point2D point : points) {
            Point2D translatedToOrigin = new Point2D(point.getX() - center.getX(), point.getY() - center.getY());
            Point2D rotated = translatedToOrigin.rotate(angle);
            newPoints.add(new Point2D(rotated.getX() + center.getX(), rotated.getY() + center.getY()));
        }

        return new Polygon(newPoints, thickness);
    }

    public Polygon scale(double scale) {
        Point2D center = getCenterOfPolygon();
        ArrayList<Point2D> newPoints = new ArrayList<>();

        // Scale each point around the center
        for (Point2D point : points) {
            Point2D pointTranslatedToOrigin = new Point2D(point.getX() - center.getX(), point.getY() - center.getY());
            Point2D scaledPoint = pointTranslatedToOrigin.scale(scale);
            newPoints.add(new Point2D(scaledPoint.getX() + center.getX(), scaledPoint.getY() + center.getY()));
        }

        return new Polygon(newPoints, thickness);
    }

    public Polygon transform(Mat3 transformation) {
        Point2D center = getCenterOfPolygon();
        ArrayList<Point2D> newPoints = new ArrayList<>();

        // Rotate each point around the center
        for (Point2D point : points) {
            transforms.Point2D translatedToOrigin = new transforms.Point2D(point.getX() - center.getX(), point.getY() - center.getY());
            transforms.Point2D rotated = translatedToOrigin.mul(transformation);
            newPoints.add(new Point2D(rotated.getX() + center.getX(), rotated.getY() + center.getY()));
        }

        return new Polygon(newPoints, thickness);
    }

    public Point2D getCenterOfPolygon() {
        double sumX = 0;
        double sumY = 0;

        for (Point2D point : points) {
            sumX += point.getX();
            sumY += point.getY();
        }

        // Average the sums to get centre of polygon
        double centerX = sumX / points.size();
        double centerY = sumY / points.size();

        return new Point2D(centerX, centerY);
    }
}