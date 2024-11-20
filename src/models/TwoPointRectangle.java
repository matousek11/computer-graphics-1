package models;

import objectdata.Point2D;

import java.util.ArrayList;

public class TwoPointRectangle extends Polygon {

    public TwoPointRectangle(Point2D firstPoint, Point2D secondPoint, int thickness) {
        super(new ArrayList<>(), thickness);

        addRemainingPoints(firstPoint, secondPoint);
    }

    /**
     * Calculates remaining two points for rectangle and adds them to points array
     */
    private void addRemainingPoints(Point2D firstPoint, Point2D secondPoint) {
        this.points.add(new Point2D(firstPoint.getX(), firstPoint.getY()));
        this.points.add(new Point2D(firstPoint.getX(), secondPoint.getY()));
        this.points.add(new Point2D(secondPoint.getX(), secondPoint.getY()));
        this.points.add(new Point2D(secondPoint.getX(), firstPoint.getY()));
    }
}
