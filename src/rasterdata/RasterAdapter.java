package rasterdata;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class RasterAdapter implements Raster {
    private final BufferedImage image;

    public RasterAdapter(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public boolean setColor(int x, int y, int color) {
        if (!isWithinBufferedImageBounds(x, y)) {
            return false;
        }

        image.setRGB(x, y, color);
        return true;
    }

    @Override
    public Optional<Integer> getColor(int x, int y) {
        if (!isWithinBufferedImageBounds(x, y)) {
            return Optional.empty();
        }

        return Optional.of(image.getRGB(x, y));
    }

    public  void present(Graphics graphics) {
        if (graphics != null) {
            graphics.drawImage(image, 0, 0, null);
        }
    }

    public void clear(int background) {
        Graphics graphics = image.getGraphics();

        if (graphics == null) {
            return;
        }

        graphics.setColor(new Color(background));
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    private boolean isWithinBufferedImageBounds(int x, int y) {
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
            return true;
        }

        return false;
    }
}
