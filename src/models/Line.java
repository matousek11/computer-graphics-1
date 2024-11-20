package models;

import objectdata.Point2D;

import java.util.Optional;

public class Line {
    private final int xStart;
    private final int yStart;
    private final int xEnd;
    private final int yEnd;
    private final int color;
    private final int thickness;

    public Line(int xStart, int yStart, int xEnd, int yEnd, int color, int thickness) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.color = color;
        this.thickness = thickness;
    }

    public Line (Point2D startPoint, Point2D endPoint, int color, int thickness) {
        this.xStart = (int) startPoint.getX();
        this.yStart = (int) startPoint.getY();
        this.xEnd = (int) endPoint.getX();
        this.yEnd = (int) endPoint.getY();
        this.color = color;
        this.thickness = thickness;
    }

    public int getXStart() {
        return xStart;
    }

    public int getYStart() {
        return yStart;
    }

    public int getXEnd() {
        return xEnd;
    }

    public int getYEnd() {
        return yEnd;
    }

    public int getColor() {
        return color;
    }

    public int getThickness() {
        return thickness;
    }

    public Line getReverseLine() {
        return new Line(xEnd, yEnd, xStart, yStart, color, thickness);
    }

    public boolean isHorizontal() {
        return yStart == yEnd;
    }

    public boolean hasYIntercept(int y) {
        if (yStart > yEnd) {
            return getReverseLine().hasYIntercept(y);
        }

        return y >= yStart && y <= yEnd;
    }

    public Optional<Integer> yIntercept(int y) {
        if (!hasYIntercept(y)) {
            return Optional.empty();
        }

        // calculates how big proportion on y axis is took by selected y and interpolates it into proportion of x coordinate
        int x = xStart + ((y - yStart) * (xEnd - xStart)) / (yEnd - yStart);
        return Optional.of(x);
    }

    /**
     * Creates vector from line
     *
     * @return vector of line
     */
    public Point2D toVector() {
        return new Point2D(xEnd - yStart, yEnd - yStart);
    }

}