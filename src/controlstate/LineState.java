package controlstate;

import helpers.SnapLine;
import models.Line;
import objectdata.Point2D;
import rasterdata.Raster;
import rasterops.Liner;
import rasterops.Polygoner;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LineState extends BaseState {
    private int positionX, positionY;

    public LineState(Raster raster, JPanel panel, Polygoner polygoner, Liner liner) {
        super(raster, panel, polygoner, liner);
    }

    @Override
    public void mousePressed(MouseEvent e, ArrayList<Object> objects) throws Exception {
        if (makeNewColorFills(new Point2D(e.getX(), e.getY()), objects)) {
            return;
        }

        positionX = e.getX();
        positionY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e, ArrayList<Object> objects) throws Exception {
        if (fWasPressed) {
            fWasPressed = false;
            return;
        }

        Point2D endPoint = shiftIsPressed
                ? SnapLine.snapLine(new Point2D(positionX, positionY), new Point2D(e.getX(), e.getY()))
                : new Point2D(e.getX(), e.getY());

        Line line = new Line(positionX, positionY, (int) endPoint.getX(), (int) endPoint.getY(), defaultObjectColor, oldLineThickness);
        objects.add(line);

        repaintObjects(objects);
    }

    @Override
    public void mouseDragged(MouseEvent e, ArrayList<Object> objects) throws Exception {
        if (fWasPressed) {
            fWasPressed = false;
            return;
        }

        clear();
        drawObjects(objects);

        Point2D endPoint = shiftIsPressed
                ? SnapLine.snapLine(new Point2D(positionX, positionY), new Point2D(e.getX(), e.getY()))
                : new Point2D(e.getX(), e.getY());
        liner.draw(raster, positionX, positionY, endPoint.getX(), endPoint.getY(), defaultObjectColor, newLineThickness);
        panel.repaint();
    }
}
