package rasterops;

import rasterdata.Raster;

public class TrivialLiner implements Liner {

    @Override
    public void draw(Raster raster, double xStart, double yStart, double xEnd, double yEnd, int color) {
        // if line is vertical => to avoid division by 0
        if (xStart == xEnd) {
            if (yEnd < yStart) {
                double swap = yStart;
                yStart = yEnd;
                yEnd = swap;
            }

            for (int y = (int)yStart; y < (int)yEnd; y++) {
                raster.setColor((int)xStart, y, color);
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
                raster.setColor(x, y, color);
            }
        } else {
            // switch points if line is heading to down
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
                raster.setColor(x, y, color);
            }
        }
    }
}
