package controlstate;

import models.FillPoint;
import models.Line;
import models.NGon;
import models.Polygon;
import objectdata.Point2D;
import rasterdata.Raster;
import rasterops.Liner;
import rasterops.Polygoner;
import rasterops.SeedFill4Bg;
import rasterops.SeedFillEdge;

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
    protected SeedFill4Bg seedFillBg;
    protected SeedFillEdge seedFillEdge;
    protected boolean shiftIsPressed = false;
    protected boolean fWasPressed = false;
    protected boolean eWasPressed = false;
    protected int numberOfClicks = 0;
    protected int edgeColor = 0xffffff;
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
        this.seedFillBg = new SeedFill4Bg();
        this.seedFillEdge = new SeedFillEdge();
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
    public void keyPressed(KeyEvent e, ArrayList<Object> objects) throws Exception {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C:
                objects.clear();
                clear();
                panel.repaint();
                break;
            case KeyEvent.VK_F:
                fWasPressed = true;
                eWasPressed = false;
                numberOfClicks = 0;
                break;
            case KeyEvent.VK_E:
                eWasPressed = true;
                fWasPressed = false;
                numberOfClicks = 0;
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

    /**
     * Draw already added objects
     *
     * @param objects objects to be drawn
     * @throws Exception Is thrown when renderer doesn't know object
     */
    protected void drawObjects(ArrayList<Object> objects) throws Exception {
        for (Object object : objects) {
            switch (object) {
                case NGon nGon:
                    polygoner.draw(raster, nGon, liner, defaultObjectColor);
                    break;
                case Polygon polygon:
                    polygoner.draw(raster, polygon, liner, defaultObjectColor);
                    break;
                case Line line:
                    liner.draw(raster, line);
                    break;
                case FillPoint fillPoint:
                    if (fillPoint.isLimitedByEdge()) {
                        seedFillEdge.fill(fillPoint.getFillStartPoint(), fillPoint.getFillColor(), fillPoint.getControlColor(), raster);
                        continue;
                    }

                    seedFillBg.fill(fillPoint.getFillStartPoint(), fillPoint.getFillColor(), fillPoint.getControlColor(), raster);
                    break;
                default:
                    throw new Exception("Unexpected object");
            }
        }
    }

    /**
     * Seed fill algorithm driven by background color
     *
     * @param startPoint Point where algorithm will start
     * @param objects Array where new seed fill will be added
     */
    protected void fillAreaByBg(Point2D startPoint, ArrayList<Object> objects) {
        Optional<Integer> bgColor = raster.getColor((int) startPoint.getX(), (int) startPoint.getY());

        if (bgColor.isPresent()) {
            seedFillBg.fill(new Point2D(startPoint.getX(), startPoint.getY()), defaultFillInColor, bgColor.get(), raster);
            objects.add(new FillPoint(defaultFillInColor, bgColor.get(), new Point2D(startPoint.getX(), startPoint.getY()), false));
            panel.repaint();
        }
    }

    /**
     * Seed fill algorithm driven by edge color
     *
     * @param startPoint Point where algorithm will start
     * @param objects Array where new seed fill will be added
     */
    protected void fillAreaByEdge(Point2D startPoint, ArrayList<Object> objects) {
        seedFillEdge.fill(new Point2D(startPoint.getX(), startPoint.getY()), defaultFillInColor, this.edgeColor, raster);
        objects.add(new FillPoint(defaultFillInColor, this.edgeColor, new Point2D(startPoint.getX(), startPoint.getY()), true));
        panel.repaint();
    }

    /**
     * Checks whether new color fill should be added, if new color fill should be added it will add it
     * @return true if color fill was made or is closer to be done
     */
    protected boolean makeNewColorFills (Point2D fillPoint, ArrayList<Object> objects) {
        if (fWasPressed) {
            fWasPressed = false;
            fillAreaByBg(new Point2D(fillPoint.getX(), fillPoint.getY()), objects);
            return true;
        }

        if (eWasPressed) {
            numberOfClicks++;
            if (numberOfClicks == 1) {
                Optional<Integer> edgeColor = raster.getColor((int)fillPoint.getX(), (int)fillPoint.getY());
                edgeColor.ifPresent(color -> this.edgeColor = color);
            }

            if (numberOfClicks == 2) {
                fillAreaByEdge(new Point2D(fillPoint.getX(), fillPoint.getY()), objects);
                numberOfClicks = 0;
                eWasPressed = false;
            }
            return true;
        }

        return false;
    }

    /**
     * Clears raster, draw objects from objects array and repaint it on panel.
     *
     * @param objects Object that will be on panel
     * @throws Exception When unknown object was expected to be rendered
     */
    protected void repaintObjects(ArrayList<Object> objects) throws Exception {
        clear();
        drawObjects(objects);
        panel.repaint();
    }

    /**
     * Clear raster and fill it with default background color.
     */
    protected void clear() {
        raster.clear(defaultBgColor);
    }
}
