package rasterops;

import models.Line;
import objectdata.Point2D;
import rasterdata.Raster;

/**
 * DDA algorithm (Digital Differential Analyzer) for line rasterization
 * pros:
 * - easy to implement
 * - doesn't have problems with different slopes unlike Trivial liner
 * cons:
 * - it's slower because of dividing
 * - Bresenham’s algorithm is faster
 * Basically it's good for small applications that doesn't require high performance code
 */
public class DDALiner implements Liner {
    private final FillInCircler fillInCircler;

    public DDALiner() {
        fillInCircler = new FillInCircler();
    }

    @Override
    public void draw(Raster raster, double xStart, double yStart, double xEnd, double yEnd, int color, int width) {
        double deltaX = xEnd - xStart;
        double deltaY = yEnd - yStart;

        double steps = Math.max(Math.abs(deltaX), Math.abs(deltaY));

        double xIncrement = deltaX / steps;
        double yIncrement = deltaY / steps;

        double x = xStart;
        double y = yStart;

        int radius = width / 2;
        for (int i = 0; i < steps; i++) {
            fillInCircler.draw(raster, new Point2D(x, y), color, radius);
            x += xIncrement;
            y += yIncrement;
        }
    }

    @Override
    public void draw(Raster raster, Line line) {
        draw(raster, line.getXStart(), line.getYStart(), line.getXEnd(), line.getYEnd(), line.getColor(), line.getThickness());
    }
}
