package models;

import objectdata.Point2D;

public class FillPoint {
    private final Integer fillColor;
    private final Integer controlColor;
    private final Point2D fillStartPoint;
    private final boolean isLimitedByEdge;

    public FillPoint(int fillColor, int controlColor, Point2D fillStartPoint, boolean isLimitedByEdge) {
        this.fillColor = fillColor;
        this.fillStartPoint = fillStartPoint;
        this.controlColor = controlColor;
        this.isLimitedByEdge = isLimitedByEdge;
    }

    public int getFillColor() {
        return fillColor;
    }

    public int getControlColor() {
        return controlColor;
    }

    public Point2D getFillStartPoint() {
        return fillStartPoint;
    }

    public boolean isLimitedByEdge() {
        return isLimitedByEdge;
    }
}
