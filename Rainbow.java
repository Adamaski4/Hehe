import javax.swing.*;
import java.awt.*;

/**
 * Simple Swing program that draws a rainbow using concentric arcs.
 *
 * Usage:
 *   javac Rainbow.java
 *   java Rainbow
 */
public class Rainbow extends JPanel {
    private static final int PADDING = 20;

    // Number of color steps for a smooth rainbow (more steps -> smoother bands)
    private static final int RAINBOW_STEPS = 56;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Determine largest circle that fits
            int size = Math.min(w, h) - PADDING * 2;
            int centerX = w / 2;
            int centerY = h / 2 + size / 8; // nudge downwards for a sky-like feel

            // Generate a smooth rainbow using HSB interpolation from red->violet
            Color[] colors = new Color[RAINBOW_STEPS];
            for (int i = 0; i < RAINBOW_STEPS; i++) {
                // Hue ranges roughly from 0.0 (red) through ~0.75 (violet)
                float hue = 0.0f + (0.75f * i) / (RAINBOW_STEPS - 1);
                // Slightly boost saturation and brightness for vivid colors
                colors[i] = Color.getHSBColor(hue, 0.9f, 0.95f);
            }

            int bands = colors.length;
            int bandThickness = Math.max(6, size / (bands * 2)); // ensure visible bands

            // Draw sky background
            g2.setColor(new Color(135, 206, 235)); // sky blue
            g2.fillRect(0, 0, w, h);

            // Draw layered translucent clouds for depth
            drawCloud(g2, centerX - size/2 - 40, centerY - size/4, 140, 70, 0.95f);
            drawCloud(g2, centerX + size/6, centerY - size/4 - 10, 180, 90, 0.9f);
            drawCloud(g2, centerX - size/3, centerY - size/4 + 10, 200, 80, 0.92f);
            // Add a few small wispy clouds
            drawCloud(g2, centerX - size/4, centerY - size/4 - 30, 100, 50, 0.85f);

            // Draw concentric arcs from outer (largest) to inner
            for (int i = 0; i < bands; i++) {
                int outer = size - i * bandThickness * 2;
                int x = centerX - outer / 2;
                int y = centerY - outer / 2;

                // Slightly vary stroke for a softer look
                float strokeWidth = bandThickness - (i * 0.02f);
                g2.setStroke(new BasicStroke(Math.max(1f, strokeWidth), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(colors[i]);

                // draw arc from 0 to 180 degrees (semi-circle)
                g2.drawArc(x, y, outer, outer, 0, 180);
            }

            // Optional: draw grassy ground
            g2.setColor(new Color(34, 139, 34));
            g2.fillRect(0, centerY + size/4, w, h - (centerY + size/4));

        } finally {
            g2.dispose();
        }
    }

    /**
     * Draws a layered, slightly translucent cloud using multiple overlapping ovals.
     * alpha is a value between 0.0 and 1.0 controlling base opacity.
     */
    private void drawCloud(Graphics2D g2, int x, int y, int w, int h, float alpha) {
        Composite old = g2.getComposite();
        // draw multiple overlapping ellipses with decreasing opacity and slight offsets
        for (int i = 0; i < 5; i++) {
            float layerAlpha = Math.max(0f, (alpha - i * 0.12f));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, layerAlpha));
            int dx = (int) (Math.cos(i * 1.2) * (w * 0.06));
            int dy = (int) (Math.sin(i * 0.9) * (h * 0.05));
            int ww = w - i * (w / 12);
            int hh = h - i * (h / 12);
            g2.fillOval(x + dx - ww/8, y + dy - hh/8, ww, hh);
        }

        // fine highlight
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(0.25f, alpha)));
        g2.setColor(new Color(1f, 1f, 1f, 0.18f));
        g2.fillOval(x + w/6, y - h/10, w/2, h/2);

        g2.setComposite(old);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rainbow");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Rainbow panel = new Rainbow();
            panel.setPreferredSize(new Dimension(800, 600));
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
