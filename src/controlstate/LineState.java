package controlstate;

import models.Line;
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
    public void mousePressed(MouseEvent e, ArrayList<Object> objects) {
        positionX = e.getX();
        positionY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e, ArrayList<Object> objects) {
        Line line = new Line(positionX, positionY, e.getX(), e.getY(), 0xffff00);
        objects.add(line);

        clear();
        drawObjects(objects);
        panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e, ArrayList<Object> objects) {
        clear();
        drawObjects(objects);
        liner.draw(raster, positionX, positionY, e.getX(), e.getY(), 0xffff00);
        panel.repaint();
    }
}
