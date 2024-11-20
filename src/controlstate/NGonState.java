package controlstate;

import models.Line;
import models.NGon;
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
    private int positionX, positionY;

    public NGonState(Raster raster, JPanel panel, Polygoner polygoner, Liner liner) {
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

        Point2D startPoint = new Point2D(positionX, positionY);
        Point2D endPoint = new Point2D(e.getX(), e.getY());
        Line line = new Line(startPoint, endPoint, 0xffffff, 1);

        NGon nGon = new NGon(startPoint, line.angle(), (int)line.length(), 5, 1);
        objects.add(nGon);

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

        Point2D startPoint = new Point2D(positionX, positionY);
        Point2D endPoint = new Point2D(e.getX(), e.getY());
        Line line = new Line(startPoint, endPoint, 0xffffff, 1);

        NGon nGon = new NGon(startPoint, line.angle(), (int)line.length(), 5, 1);
        polygoner.draw(raster, nGon, liner, 0xffffff);
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e, ArrayList<Object> objects) throws Exception {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
                for (int i = 0; i < objects.size(); i++) {
                    Object object = objects.get(i);
                    if (object instanceof NGon) {
                        Polygon nGon = (NGon) object;
                        objects.set(i, nGon.scale(1.5));
                    }
                }
                repaintObjects(objects);
                break;
            case KeyEvent.VK_R:
                for (int i = 0; i < objects.size(); i++) {
                    Object object = objects.get(i);
                    if (object instanceof NGon) {
                        Polygon nGon = (NGon) object;
                        objects.set(i, nGon.rotate(0.05));
                    }
                }
                repaintObjects(objects);
                break;
            case KeyEvent.VK_T:
                for (int i = 0; i < objects.size(); i++) {
                    Object object = objects.get(i);
                    if (object instanceof NGon) {
                        Polygon nGon = (NGon) object;
                        objects.set(i, nGon.translate(20, 20));
                    }
                }
                repaintObjects(objects);
                break;
        }

        super.keyPressed(e, objects);
    }
}
