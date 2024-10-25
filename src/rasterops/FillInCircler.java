package rasterops;

import objectdata.Point2D;
import rasterdata.Raster;

public class FillInCircler {
    void draw(Raster raster, Point2D centerPoint, int color, int radius) {
        // Draw a circle brush around each pixel
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                // Calculate the distance from the center with use of circle formula (x-x_0)^2 + (y-y_0)^2 <= r^2
                if (dx * dx + dy * dy <= radius * radius) {
                    raster.setColor((int) centerPoint.getX() + dx, (int) centerPoint.getY() + dy, color);
                }
            }
        }
    }
}
