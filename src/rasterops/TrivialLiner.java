package rasterops;

import models.Line;
import objectdata.Point2D;
import rasterdata.Raster;

public class TrivialLiner implements Liner {
    private final FillInCircler fillInCircler;

    public TrivialLiner() {
        fillInCircler = new FillInCircler();
    }

    @Override
    public void draw(Raster raster, double xStart, double yStart, double xEnd, double yEnd, int color, int width) {
        float radius = (float) width / 2;

        // if line is vertical => to avoid division by 0
        if (xStart == xEnd) {
            if (yEnd < yStart) {
                double swap = yStart;
                yStart = yEnd;
                yEnd = swap;
            }

            for (int y = (int)yStart; y < (int)yEnd; y++) {
                if (width > 1) {
                    fillInCircler.draw(raster, new Point2D(xStart, y), color, (int) radius);
                } else {
                    raster.setColor((int) xStart, y, color);
                }
            }

            return;
        }

        double slope = (yEnd - yStart) / (xEnd - xStart);

        if (Math.abs(slope) < 1) {
            // switch points if line is heading to the left
            if (xEnd < xStart) {
                double swap = xStart;
                xStart = xEnd;
                xEnd = swap;

                yStart = yEnd;
            }
            double q = yStart - slope * xStart;

            // raster line
            for (int x = (int)xStart; x < (xEnd); x++) {
                int y = (int) (slope * x + q);

                if (width > 1) {
                    fillInCircler.draw(raster, new Point2D(x, y), color, (int) radius);
                } else {
                    raster.setColor(x, y, color);
                }
            }
        } else {
            // switch points if line is heading down
            if (yEnd < yStart) {
                double swap = xStart;
                xStart = xEnd;
                xEnd = swap;

                swap = yStart;
                yStart = yEnd;
                yEnd = swap;
            }

            slope = (xEnd - xStart) / (yEnd - yStart);
            double q = xStart - slope * yStart;


            // raster line
            for (int y = (int)yStart; y < (yEnd); y++) {
                int x = (int) (slope * y + q);

                if (width > 1) {
                    fillInCircler.draw(raster, new Point2D(x, y), color, (int) radius);
                } else {
                    raster.setColor(x, y, color);
                }
            }
        }
    }

    @Override
    public void draw(Raster raster, Line line) {
        draw(raster, line.getXStart(), line.getYStart(), line.getXEnd(), line.getYEnd(), line.getColor(), line.getThickness());
    }
}
