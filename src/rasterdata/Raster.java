package rasterdata;

import java.awt.*;
import java.util.Optional;

/**
 * Represent a two dimensional grid with pixels of type int
 */
public interface Raster {
    /**
     * Returns number of columns in raster
     * @return int
     */
    int getWidth();

    /**
     * Returns number of rows in raster
     * @return int
     */
    int getHeight();

    /**
     * Set color to selected pixel
     * @param x horizontal position of pixel
     * @param y vertical position of pixel
     * @param color color of pixel to be set
     */
    boolean setColor(int x, int y, int color);

    /**
     * Read color of selected pixel
     * @param x horizontal position of pixel
     * @param y vertical position of pixel
     * @return returns Optional of color if address of pixel is valid
     */
    Optional<Integer> getColor(int x, int y);

    /**
     * Draw new image on element
     * @param graphics element on which image will be rendered
     */
    void present(Graphics graphics);

    /**
     * Redraw whole image with selected color
     * @param background color by which element will be repainted
     */
    void clear(int background);
}
