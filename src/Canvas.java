import controlstate.LineState;
import controlstate.NGonState;
import controlstate.State;

import rasterdata.Raster;
import rasterdata.RasterAdapter;
import rasterops.Liner;
import rasterops.Polygoner;
import rasterops.TrivialLiner;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 *
 * @author PGRF FIM UHK
 * @version 2020
 */
public class Canvas {
    private JFrame frame;
    private final JPanel panel;
    private final Raster raster;
    private Liner liner;
    private int latestPolygonIndex = -1;
    private Polygoner polygoner;
    private ArrayList<Object> objects;
    private State state;

    public Canvas(int width, int height) {
        liner = new TrivialLiner();
        objects = new ArrayList<>();
        polygoner = new Polygoner();
        raster = new RasterAdapter(width, height);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                raster.present(g);
            }
        };

        LineState lineState = new LineState(raster, panel, polygoner, liner);
        NGonState nGonState = new NGonState(raster, panel, latestPolygonIndex, polygoner, liner);
        this.state = lineState;

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_X:
                        state = state instanceof LineState ? nGonState : lineState;
                        break;
                    default:
                        //
                        break;
                }

                state.keyPressed(e, objects);
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                state.mousePressed(e, objects);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                state.mouseReleased(e, objects);
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                state.mouseDragged(e, objects);
            }
        });

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void start() {
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(600, 600).start());
    }
}
