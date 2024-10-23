package models;

import objectdata.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point2D> points;

    public Polygon(List<Point2D> points) {
        this.points = new ArrayList<>(points);
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
}
