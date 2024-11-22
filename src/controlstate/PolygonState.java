package controlstate;

import models.Polygon;
import objectdata.Point2D;
import rasterdata.Raster;
import rasterops.Liner;
import rasterops.Polygoner;
import transforms.Mat3Rot2D;
import transforms.Mat3Scale2D;
import transforms.Mat3Transl2D;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PolygonState extends BaseState {
    private int latestPolygonIndex;

    public PolygonState(Raster raster, JPanel panel, int latestPolygonIndex, Polygoner polygoner, Liner liner) {
        super(raster, panel, polygoner, liner);

        this.latestPolygonIndex = latestPolygonIndex;
        this.polygoner = polygoner;
        this.liner = liner;
    }

    @Override
    public void mousePressed(MouseEvent e, ArrayList<Object> objects) throws Exception {
        if (makeNewColorFills(new Point2D(e.getX(), e.getY()), objects)) {
            return;
        }

        if (latestPolygonIndex == -1) {
            objects.add(new Polygon(new ArrayList<>(), oldLineThickness));
            latestPolygonIndex = objects.size() - 1;
        }

        Polygon latestPolygon = (Polygon) objects.get(latestPolygonIndex);
        latestPolygon.addPoint(new Point2D(e.getX(), e.getY()));

        repaintObjects(objects);
    }

    @Override
    public void keyPressed(KeyEvent e, ArrayList<Object> objects) throws Exception {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                objects.add(new Polygon(new ArrayList<>(), oldLineThickness));
                latestPolygonIndex = objects.size() - 1;
                break;
            case KeyEvent.VK_C:
                latestPolygonIndex = -1;
                break;
            case KeyEvent.VK_S:
                for (int i = 0; i < objects.size(); i++) {
                    Object object = objects.get(i);
                    if (object instanceof Polygon polygon) {
                        objects.set(i, polygon.transform(new Mat3Scale2D(1.5)));
                    }
                }

                repaintObjects(objects);
                break;
            case KeyEvent.VK_R:
                for (int i = 0; i < objects.size(); i++) {
                    Object object = objects.get(i);
                    if (object instanceof Polygon polygon) {
                        objects.set(i, polygon.transform(new Mat3Rot2D(0.05)));
                    }
                }

                repaintObjects(objects);
                break;
            case KeyEvent.VK_T:
                for (int i = 0; i < objects.size(); i++) {
                    Object object = objects.get(i);
                    if (object instanceof Polygon polygon) {
                        objects.set(i, polygon.transform(new Mat3Transl2D(20, 20)));
                    }
                }

                repaintObjects(objects);
                break;
        }

        super.keyPressed(e, objects);
    }
}
