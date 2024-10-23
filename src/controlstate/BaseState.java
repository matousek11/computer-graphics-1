package controlstate;

import models.Line;
import models.Polygon;
import rasterdata.Raster;
import rasterops.Liner;
import rasterops.Polygoner;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BaseState implements State {
    protected JPanel panel;
    protected Raster raster;
    protected Polygoner polygoner;
    protected Liner liner;

    public BaseState(Raster raster, JPanel panel, Polygoner polygoner, Liner liner) {
        this.raster = raster;
        this.panel = panel;
        this.polygoner = polygoner;
        this.liner = liner;
    }

    @Override
    public void mousePressed(MouseEvent e, ArrayList<Object> objects) {

    }

    @Override
    public void mouseReleased(MouseEvent e, ArrayList<Object> objects) {

    }

    @Override
    public void mouseDragged(MouseEvent e, ArrayList<Object> objects) {

    }

    @Override
    public void keyPressed(KeyEvent e, ArrayList<Object> objects) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C:
                objects.clear();
                clear();
                panel.repaint();
                break;
        }
    }

    protected void clear() {
        raster.clear(0x000000);
    }

    protected void drawObjects(ArrayList<Object> objects) {
        for (Object object : objects) {
            switch (object) {
                case Polygon polygon:
                    polygoner.draw(raster, polygon, liner, 0xffff00);
                    break;
                case Line line:
                    liner.draw(raster, line);
                    break;
                default:
                    //throw new Exception();
                    break;
            }
        }
    }
}
