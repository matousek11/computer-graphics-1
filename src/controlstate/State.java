package controlstate;

import java.awt.event.MouseEvent;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Interface that represents state of control for type of rasterized object.
 * Interface offers events that can be used to react on user actions.
 */
public interface State {
    void mousePressed(MouseEvent e, ArrayList<Object> objects) throws Exception;

    void mouseReleased(MouseEvent e, ArrayList<Object> objects) throws Exception;

    void mouseDragged(MouseEvent e, ArrayList<Object> objects) throws Exception;

    void keyPressed(KeyEvent e, ArrayList<Object> objects) throws Exception;

    void keyReleased(KeyEvent e, ArrayList<Object> objects);
}
