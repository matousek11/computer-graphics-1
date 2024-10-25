package models;

import objectdata.Point2D;

public class FillPoint {
    private final Integer fillColor;
    private final Integer bgColor;
    private final Point2D fillStartPoint;

    public FillPoint(int fillColor, int bgColor, Point2D fillStartPoint) {
        this.fillColor = fillColor;
        this.fillStartPoint = fillStartPoint;
        this.bgColor = bgColor;
    }

    public int getFillColor() {
        return fillColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public Point2D getFillStartPoint() {
        return fillStartPoint;
    }
}
