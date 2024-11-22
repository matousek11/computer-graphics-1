package models;

import objectdata.Point2D;

import java.util.ArrayList;
import java.util.List;
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

    public boolean isVertical() {
        return xStart == xEnd;
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

    /**
     * Check whether point is inside of line (line from start of line to point have positive angle to line)
     *
     * @param point Point which will be evaluated
     * @return true if point is inside
     */
    public boolean isInside(Point2D point) {
        double lineVectorX = xEnd - xStart;
        double lineVectorY = yEnd - yStart;

        double pointVectorX = point.getX() - xStart;
        double pointVectorY = point.getY() - yStart;

        // if positive, point lies on the left, if 0 point lies on line and if negative point lies on the right
        double crossProduct = lineVectorX * pointVectorY - lineVectorY * pointVectorX;

        return crossProduct <= 0;
    }

    /**
     * Find first interception of line with line from parameter
     *
     * @param otherLine line which will be used to calculate interception with current line
     * @return point of interception
     */
    public Optional<Point2D> interceptionWithAnotherLine(Line otherLine) {
        double denominator = (xStart - xEnd) * (otherLine.getYStart() - otherLine.getYEnd()) -
                (yStart - yEnd) * (otherLine.getXStart() - otherLine.getXEnd());

        // Check for parallel lines (denominator is 0)
        if (denominator == 0) {
            return Optional.empty(); // Lines are parallel or coincident
        }

        double xInterception = (xStart * yEnd - xEnd * yStart) * (otherLine.getXStart() - otherLine.getXEnd());
        xInterception -= (otherLine.getXStart() * otherLine.getYEnd() - otherLine.getXEnd() * otherLine.getYStart()) * (xStart - xEnd);
        xInterception /= (xStart - xEnd) * (otherLine.getYStart() - otherLine.getYEnd()) - (yStart - yEnd) * (otherLine.getXStart() - otherLine.getXEnd());

        double yInterception = (xStart * yEnd - xEnd * yStart) * (otherLine.getYStart() - otherLine.getYEnd());
        yInterception -= (otherLine.getXStart() * otherLine.getYEnd() - otherLine.getXEnd() * otherLine.getYStart()) * (yStart - yEnd);
        yInterception /= (xStart - xEnd) * (otherLine.getYStart() - otherLine.getYEnd()) - (yStart - yEnd) * (otherLine.getXStart() - otherLine.getXEnd());

        return Optional.of(new Point2D(xInterception, yInterception));
    }

    public double length () {
        double deltaX = xEnd - xStart;
        double deltaY = yEnd - yStart;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public double angle() {
        double deltaY = yEnd - yStart;
        double deltaX = xEnd - xStart;

        return Math.atan2(deltaY, deltaX);
    }

    public List<Point2D> getPoints() {
        List<Point2D> points = new ArrayList<>();
        points.add(new Point2D(xStart, yStart));
        points.add(new Point2D(xEnd, yEnd));
        return points;
    }
}