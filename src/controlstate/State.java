package controlstate;

import java.awt.event.MouseEvent;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public interface State {
    void mousePressed(MouseEvent e, ArrayList<Object> objects);

    void mouseReleased(MouseEvent e, ArrayList<Object> objects);

    void mouseDragged(MouseEvent e, ArrayList<Object> objects);

    void keyPressed(KeyEvent e, ArrayList<Object> objects);
}
