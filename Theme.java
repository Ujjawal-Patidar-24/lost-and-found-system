import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;

public class Theme {

    // === COLOR PALETTE ===
    public static final Color BG_DARK       = new Color(13, 17, 35);
    public static final Color BG_CARD       = new Color(22, 28, 55);
    public static final Color BG_CARD2      = new Color(28, 36, 68);
    public static final Color ACCENT_BLUE   = new Color(64, 156, 255);
    public static final Color ACCENT_PURPLE = new Color(140, 82, 255);
    public static final Color ACCENT_TEAL   = new Color(0, 210, 190);
    public static final Color ACCENT_ORANGE = new Color(255, 140, 50);
    public static final Color ACCENT_RED    = new Color(255, 75, 95);
    public static final Color ACCENT_GREEN  = new Color(50, 210, 120);
    public static final Color TEXT_PRIMARY  = new Color(230, 235, 255);
    public static final Color TEXT_MUTED    = new Color(120, 135, 175);
    public static final Color BORDER_COLOR  = new Color(45, 55, 95);

    // === FONTS ===
    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font FONT_H2     = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_H3     = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_LABEL  = new Font("Segoe UI", Font.BOLD, 12);

    // === GRADIENT PANEL ===
    public static JPanel gradientPanel(Color c1, Color c2, boolean vertical) {
        return new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = vertical
                    ? new GradientPaint(0, 0, c1, 0, getHeight(), c2)
                    : new GradientPaint(0, 0, c1, getWidth(), 0, c2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    // === STYLED BUTTON ===
    public static JButton styledButton(String text, Color c1, Color c2) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent e) { hovered = true; repaint(); }
                    public void mouseExited(java.awt.event.MouseEvent e)  { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color a = hovered ? c2 : c1;
                Color b = hovered ? c1 : c2;
                GradientPaint gp = new GradientPaint(0, 0, a, getWidth(), getHeight(), b);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(TEXT_PRIMARY);
                g2.setFont(FONT_BUTTON);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(super.getPreferredSize().width + 30, 42); }
        };
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(TEXT_PRIMARY);
        btn.setFont(FONT_BUTTON);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // === STYLED TEXT FIELD ===
    public static JTextField styledField(String placeholder) {
        JTextField field = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD2);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.setColor(BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(ACCENT_BLUE);
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        field.setPreferredSize(new Dimension(250, 38));
        return field;
    }

    // === STYLED COMBO BOX ===
    public static JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBackground(BG_CARD2);
        combo.setForeground(TEXT_PRIMARY);
        combo.setFont(FONT_BODY);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        combo.setPreferredSize(new Dimension(250, 38));
        return combo;
    }

    // === STYLED TEXT AREA ===
    public static JTextArea styledTextArea() {
        JTextArea area = new JTextArea(3, 20);
        area.setBackground(BG_CARD2);
        area.setForeground(TEXT_PRIMARY);
        area.setCaretColor(ACCENT_BLUE);
        area.setFont(FONT_BODY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return area;
    }

    // === LABEL ===
    public static JLabel label(String text, Font font, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(color);
        return lbl;
    }

    // === CARD PANEL ===
    public static JPanel cardPanel() {
        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
                g2.setColor(BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    // === STATUS BADGE ===
    public static JLabel statusBadge(String status) {
        JLabel badge = new JLabel("  " + status + "  ") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = status.equals("LOST") ? new Color(255,75,95,60)
                         : status.equals("FOUND") ? new Color(50,210,120,60)
                         : new Color(64,156,255,60);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        Color fg = status.equals("LOST") ? ACCENT_RED
                 : status.equals("FOUND") ? ACCENT_GREEN : ACCENT_BLUE;
        badge.setForeground(fg);
        badge.setFont(FONT_LABEL);
        badge.setOpaque(false);
        return badge;
    }
}
