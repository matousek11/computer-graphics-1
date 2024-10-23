package controlstate;

import models.Polygon;
import objectdata.Point2D;
import rasterdata.Raster;
import rasterops.Liner;
import rasterops.Polygoner;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NGonState extends BaseState {
    private int latestPolygonIndex;

    public NGonState(Raster raster, JPanel panel, int latestPolygonIndex, Polygoner polygoner, Liner liner) {
        super(raster, panel, polygoner, liner);

        this.latestPolygonIndex = latestPolygonIndex;
        this.polygoner = polygoner;
        this.liner = liner;
    }

    @Override
    public void mousePressed(MouseEvent e, ArrayList<Object> objects) {
        if (latestPolygonIndex == -1) {
            objects.add(new Polygon(new ArrayList<>()));
            latestPolygonIndex = objects.size() - 1;
        }

        Polygon latestPolygon = (Polygon) objects.get(latestPolygonIndex);
        latestPolygon.addPoint(new Point2D(e.getX(), e.getY()));

        clear();
        drawObjects(objects);
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e, ArrayList<Object> objects) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                objects.add(new Polygon(new ArrayList<>()));
                latestPolygonIndex = objects.size() - 1;
                break;
            case KeyEvent.VK_C:
                latestPolygonIndex = -1;
                super.keyPressed(e, objects);
                break;
        }
    }
}
