package rasterops;

import models.Line;
import rasterdata.Raster;

/**
 * Represents an algorithm for drawing line segments
 */
public interface Liner {
    /**
     * Draws a line segment specified by two points onto the given Raster using provided color
     * @param raster raster where the line will be drawn
     * @param xStart x coordinate of start point of line
     * @param yStart y coordinate of start point of line
     * @param xEnd x coordinate of end point of line
     * @param yEnd y coordinate of end point of line
     * @param color color of line segment
     * @param width width of line segment
     */
    void draw(Raster raster, double xStart, double yStart, double xEnd, double yEnd, int color, int width);

    /**
     * Draws a line segment specified by Line onto the given Raster
     * @param raster raster where the line will be drawn
     * @param line line which will be rasterized
     */
    void draw(Raster raster, Line line);
}
