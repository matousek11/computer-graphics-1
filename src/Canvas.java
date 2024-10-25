import controlstate.LineState;
import controlstate.NGonState;
import controlstate.State;

import rasterdata.Raster;
import rasterdata.RasterAdapter;
import rasterops.DDALiner;
import rasterops.Liner;
import rasterops.Polygoner;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Main class for painting on canvas
 */
public class Canvas {
    private final JFrame frame;
    private final JPanel panel;
    private final Raster raster;
    private final Liner liner;
    private final int latestPolygonIndex = -1;
    private final Polygoner polygoner;
    private final ArrayList<Object> objects;
    private State state;

    public Canvas(int width, int height) {
        liner = new DDALiner();
        objects = new ArrayList<>();
        polygoner = new Polygoner();
        raster = new RasterAdapter(width, height);

        panel = new JPanel() {
            @Serial
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
        frame.setTitle("UHK FIM PGRF 1 : Lukáš Matoušek");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_X:
                        state = state instanceof LineState ? nGonState : lineState;
                        break;
                    case KeyEvent.VK_SHIFT:

                        break;
                    default:
                        //
                        break;
                }

                state.keyPressed(e, objects);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                state.keyReleased(e, objects);
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    state.mousePressed(e, objects);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    state.mouseReleased(e, objects);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                try {
                    state.mouseDragged(e, objects);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
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
