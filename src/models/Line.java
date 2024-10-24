package models;

public class Line {
    private final int xStart;
    private final int yStart;
    private final int xEnd;
    private final int yEnd;
    private final int color;

    public Line(int xStart, int yStart, int xEnd, int yEnd, int color) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.color = color;
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
}