package controlstate;

import models.FillPoint;
import models.Line;
import models.Polygon;
import objectdata.Point2D;
import rasterdata.Raster;
import rasterops.Liner;
import rasterops.Polygoner;
import rasterops.SeedFill4BG;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Base class for new control state implementation
 */
public class BaseState implements State {
    protected JPanel panel;
    protected Raster raster;
    protected Polygoner polygoner;
    protected Liner liner;
    protected SeedFill4BG seedFill;
    protected boolean shiftIsPressed = false;
    protected boolean fWasPressed = false;
    protected int defaultBgColor = 0x000000;
    protected int defaultObjectColor = 0xffff00;
    protected int defaultFillInColor = 0xffffff;
    protected int oldLineThickness = 3;
    protected int newLineThickness = 6;


    public BaseState(Raster raster, JPanel panel, Polygoner polygoner, Liner liner) {
        this.raster = raster;
        this.panel = panel;
        this.polygoner = polygoner;
        this.liner = liner;
        this.seedFill = new SeedFill4BG();
    }

    @Override
    public void mousePressed(MouseEvent e, ArrayList<Object> objects) throws Exception {

    }

    @Override
    public void mouseReleased(MouseEvent e, ArrayList<Object> objects) throws Exception {

    }

    @Override
    public void mouseDragged(MouseEvent e, ArrayList<Object> objects) throws Exception {

    }

    @Override
    public void keyPressed(KeyEvent e, ArrayList<Object> objects) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C:
                objects.clear();
                clear();
                panel.repaint();
                break;
            case KeyEvent.VK_F:
                fWasPressed = true;
                break;
            case KeyEvent.VK_SHIFT:
                shiftIsPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e, ArrayList<Object> objects) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                shiftIsPressed = false;
                break;
        }
    }

    protected void clear() {
        raster.clear(defaultBgColor);
    }

    /**
     * Draw already added objects
     *
     * @param objects objects to be drawn
     * @throws Exception Is thrown when renderer doesn't know object
     */
    protected void drawObjects(ArrayList<Object> objects) throws Exception {
        for (Object object : objects) {
            switch (object) {
                case Polygon polygon:
                    polygoner.draw(raster, polygon, liner, defaultObjectColor);
                    break;
                case Line line:
                    liner.draw(raster, line);
                    break;
                case FillPoint fillPoint:
                    seedFill.fill(fillPoint.getFillStartPoint(), fillPoint.getFillColor(), fillPoint.getBgColor(), raster);
                    break;
                default:
                    throw new Exception("Unexpected object");
            }
        }
    }

    protected void fillArea(Point2D startPoint, ArrayList<Object> objects) throws Exception {
        Optional<Integer> bgColor = raster.getColor((int) startPoint.getX(), (int) startPoint.getY());

        if (bgColor.isPresent()) {
            seedFill.fill(new Point2D(startPoint.getX(), startPoint.getY()), defaultFillInColor, bgColor.get(), raster);
            objects.add(new FillPoint(defaultFillInColor, bgColor.get(), new Point2D(startPoint.getX(), startPoint.getY())));
            panel.repaint();
        }
    }
}
