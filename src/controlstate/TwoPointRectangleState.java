package controlstate;

import models.TwoPointRectangle;
import objectdata.Point2D;
import rasterdata.Raster;
import rasterops.Liner;
import rasterops.Polygoner;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;

public class TwoPointRectangleState extends BaseState {
    private Optional<Point2D> firstPoint = Optional.empty();

    public TwoPointRectangleState(Raster raster, JPanel panel, Polygoner polygoner, Liner liner) {
        super(raster, panel, polygoner, liner);
    }

    @Override
    public void mousePressed(MouseEvent e, ArrayList<Object> objects) throws Exception {
        if (makeNewColorFills(new Point2D(e.getX(), e.getY()), objects)) {
            return;
        }

        if (firstPoint.isPresent()) {
            objects.add(new TwoPointRectangle(new Point2D(firstPoint.get().getX(), firstPoint.get().getY()), new Point2D(e.getX(), e.getY()), 3));

            clear();
            drawObjects(objects);
            panel.repaint();

            firstPoint = Optional.empty();
            return;
        }

        firstPoint = Optional.of(new Point2D(e.getX(), e.getY()));
    }
}
