package models;

import java.util.List;

public class OrientedLines {
    private final List<Line> orientedLines;
    private final int yMin;
    private final int yMax;

    public OrientedLines(List<Line> orientedLines, int yMin, int yMax) {
        this.orientedLines = orientedLines;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public List<Line> getOrientedLines() {
        return orientedLines;
    }

    public int getYMin() {
        return yMin;
    }

    public int getYMax() {
        return yMax;
    }
}
