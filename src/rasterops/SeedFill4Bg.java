package rasterops;

import objectdata.Point2D;
import rasterdata.Raster;

import java.util.*;

/**
 * Seed fill algorithm for filling area stopped by different background color
 */
public class SeedFill4Bg {
    public void fill(Point2D startingPoint, int fillColor, int bgColor, Raster raster) {
        Deque<Point2D> stack = new LinkedList<>();
        stack.push(startingPoint);

        while (!stack.isEmpty()) {
            Point2D point = stack.pop();
            int x = (int) point.getX();
            int y = (int) point.getY();

            // Check bounds
            if (!raster.isWithinBufferedImageBounds(x, y)) {
                continue;
            }

            // Check color at the current pixel
            Optional<Integer> currentPixelColor = raster.getColor(x, y);
            if (currentPixelColor.isEmpty() || (currentPixelColor.get() & 0xffffff) != (bgColor & 0xffffff)) {
                continue;
            }

            // Fill the current pixel
            raster.setColor(x, y, fillColor);

            // Push neighboring pixels onto the stack
            stack.push(new Point2D(x + 1, y));
            stack.push(new Point2D(x - 1, y));
            stack.push(new Point2D(x, y + 1));
            stack.push(new Point2D(x, y - 1));
        }
    }
}
