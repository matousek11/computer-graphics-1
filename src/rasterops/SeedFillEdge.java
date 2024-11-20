package rasterops;

import objectdata.Point2D;
import rasterdata.Raster;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Seed fill algorithm that is stopped by edge color.
 */
public class SeedFillEdge {
    public void fill(Point2D startingPoint, int fillColor, int edgeColor, Raster raster) {
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
            if (currentPixelColor.isEmpty() || (currentPixelColor.get() & 0xffffff) == (edgeColor & 0xffffff)) {
                continue;
            }

            // if pixel is already filled with new color
            if ((currentPixelColor.get() & 0xffffff) == (fillColor & 0xffffff)) {
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
