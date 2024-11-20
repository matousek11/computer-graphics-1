package rasterops;

import models.Polygon;
import objectdata.Point2D;
import rasterdata.Raster;

import java.util.ArrayList;

public class Polygoner {
    public void draw(Raster raster, Polygon polygon, Liner liner, int color) {
        ArrayList<Point2D> points = polygon.getPoints();
        if (points.size() > 2) {
            ScanLine scanLine = new ScanLine();
            scanLine.draw(polygon, raster);
        }

        for (int i = 0; i < points.size(); i++) {
            Point2D currentPoint = points.get(i);
            Point2D nextPoint = points.get((i + 1) % points.size());

            liner.draw(raster, currentPoint.getX(), currentPoint.getY(), nextPoint.getX(), nextPoint.getY(), color, polygon.getThickness());
        }
    }
}
