package controlstate;

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
    public void mousePressed(MouseEvent e, ArrayList<Object> objects) {
        positionX = e.getX();
        positionY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e, ArrayList<Object> objects) throws Exception {
        Point2D endPoint = shiftIsPressed
                ? snapLine(new Point2D(positionX, positionY), new Point2D(e.getX(), e.getY()))
                : new Point2D(e.getX(), e.getY());

        Line line = new Line(positionX, positionY, (int) endPoint.getX(), (int) endPoint.getY(), defaultObjectColor, oldLineThickness);
        objects.add(line);

        clear();
        drawObjects(objects);
        panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e, ArrayList<Object> objects) throws Exception {
        clear();
        drawObjects(objects);

        Point2D endPoint = shiftIsPressed
                ? snapLine(new Point2D(positionX, positionY), new Point2D(e.getX(), e.getY()))
                : new Point2D(e.getX(), e.getY());
        liner.draw(raster, positionX, positionY, endPoint.getX(), endPoint.getY(), defaultObjectColor, newLineThickness);
        panel.repaint();
    }

    /**
     * Snaps line to horizontal, vertical or diagonal line based on angle of line between two passed Point2D.
     *
     * @param startPoint Point from which angle of line is calculated.
     * @param endPoint Point to which angle of line is calculated.
     * @return Recalculated Point2D endpoint for correct snap line is returned.
     */
    public Point2D snapLine(Point2D startPoint, Point2D endPoint) {
        double currentAngle = calculateAngle(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());

        double maxDegreesDistance = 22.5; // Half the size of each zone (360/16 = 22.5 degrees per half-zone)
        for (int targetLineDegrees = 0; targetLineDegrees < 360; targetLineDegrees += 45) {
            double upperBound = targetLineDegrees + maxDegreesDistance;
            double lowerBound = targetLineDegrees - maxDegreesDistance;

            // fix 4th quadrant horizontal line snap
            if (targetLineDegrees == 0 && currentAngle > 337.5) {
                upperBound = 360;
            }

            // Check if the current angle is within the target zone
            if (currentAngle < upperBound && currentAngle > lowerBound) {
                if (targetLineDegrees % 180 == 0) { // horizontal lines
                    return new Point2D(endPoint.getX(), startPoint.getY());
                } else if (targetLineDegrees % 90 == 0) { // vertical lines
                    return new Point2D(startPoint.getX(), endPoint.getY());
                } else if (targetLineDegrees / 45 == 3 || targetLineDegrees / 45 == 7) {
                    // diagonals in second and fourth quadrant
                    double q = startPoint.getY() - 1 * startPoint.getX();
                    int y = (int) (1 * endPoint.getX() + q);
                    return new Point2D(endPoint.getX(), y);
                } else {
                    // diagonals in first and third quadrant
                    double q = startPoint.getY() + startPoint.getX();
                    int y = (int) (-endPoint.getX() + q);
                    return new Point2D(endPoint.getX(), y);
                }
            }
        }

        return endPoint;
    }

    /**
     * Calculates angle of line selected by two 2D points
     *
     * @return returns angle of line in degrees
     */
    private double calculateAngle(double startX, double startY, double endX, double endY) {
        double deltaX = endX - startX;
        double deltaY = endY - startY;

        // Calculate the angle in radians
        double angleRadians = Math.atan2(deltaY, deltaX);
        // Convert radians to degrees (optional)
        double angleDegrees = Math.toDegrees(angleRadians);

        //+ 360 to get only positive degrees
        if (angleDegrees < 0) {
            angleDegrees += 360;
        }

        angleDegrees = 360 - angleDegrees;

        return angleDegrees;
    }
}
