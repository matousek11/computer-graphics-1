package models;

import objectdata.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point2D> points;
    private final int thickness;

    public Polygon(List<Point2D> points, int thickness) {
        this.points = new ArrayList<>(points);
        this.thickness = thickness;
    }

    public ArrayList<Point2D> getPoints() {
        return (ArrayList<Point2D>) points;
    }

    public Point2D getPoint(int id) {
        return points.get(id);
    }

    public void addPoint(Point2D point) {
        points.add(point);
    }

    public int getThickness() {
        return thickness;
    }
}
