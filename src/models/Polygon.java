package models;

import objectdata.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Polygon {
    private List<Point2D> points;
    private Optional<Integer> fillColor;
    private Optional<Point2D> fillStart;
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

    public Optional<Integer> getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor, Point2D fillStart) {
        this.fillColor = Optional.of(fillColor);
        this.fillStart = Optional.of(fillStart);
    }

    public Optional<Point2D> getFillStart() {
        return fillStart;
    }

    public int getThickness() {
        return thickness;
    }
}
