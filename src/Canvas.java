import rasterdata.Raster;
import rasterdata.RasterAdapter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
    private int positionX = 0;
    private int positionY = 0;

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterAdapter(width, height);

        // anonymní třída
        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                raster.present(g);
            }
        };

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        positionY -= 20;
                        break;
                    case KeyEvent.VK_DOWN:
                        positionY += 20;
                        break;
                    case KeyEvent.VK_LEFT:
                        positionX -= 20;
                        break;
                    case KeyEvent.VK_RIGHT:
                        positionX += 20;
                        break;
                }

                raster.clear(0x000000);
                raster.setColor(positionX, positionY, 0xffff00);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(600, 600).start());
    }
}
