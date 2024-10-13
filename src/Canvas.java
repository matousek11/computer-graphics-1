import rasterdata.Raster;
import rasterdata.RasterAdapter;
import rasterops.Liner;
import rasterops.TrivialLiner;

import java.awt.*;
import java.awt.event.*;

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
    private int positionX = 0;
    private int positionY = 0;

    public Canvas(int width, int height) {
        frame = new JFrame();

        liner = new TrivialLiner();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterAdapter(width, height);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                raster.present(g);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                positionX = e.getX();
                positionY = e.getY();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                clear();
                liner.draw(raster, positionX, positionY, e.getX(), e.getY(), 0xffff00);
                panel.repaint();
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

    public void clear() {
        raster.clear(0x000000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(600, 600).start());
    }
}
