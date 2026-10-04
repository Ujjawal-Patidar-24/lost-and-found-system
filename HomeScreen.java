import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class HomeScreen extends JFrame {

    public HomeScreen() {
        setTitle("Lost & Found System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(buildContent());
    }

    private JPanel buildContent() {
        JPanel root = Theme.gradientPanel(Theme.BG_DARK, new Color(18, 10, 40), true);
        root.setLayout(new BorderLayout());

        // === HEADER ===
        JPanel header = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Glowing orb top-left
                RadialGradientPaint orb1 = new RadialGradientPaint(
                    new Point2D.Float(100, 60), 120,
                    new float[]{0f, 1f},
                    new Color[]{new Color(64,156,255,60), new Color(64,156,255,0)});
                g2.setPaint(orb1);
                g2.fillOval(-20, -30, 240, 180);
                // Glowing orb right
                RadialGradientPaint orb2 = new RadialGradientPaint(
                    new Point2D.Float(800, 30), 100,
                    new float[]{0f, 1f},
                    new Color[]{new Color(140,82,255,50), new Color(140,82,255,0)});
                g2.setPaint(orb2);
                g2.fillOval(700, -40, 200, 140);
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(900, 160));
        header.setLayout(new GridBagLayout());

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));

        // Icon row
        JLabel icon = new JLabel("🔍");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("LOST & FOUND");
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Gradient text simulation via compound label
        JLabel subtitle = new JLabel("Connect the missing with the found");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(Theme.TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleBox.add(icon);
        titleBox.add(Box.createVerticalStrut(6));
        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(subtitle);
        header.add(titleBox);

        // === CARDS ROW ===
        JPanel cardsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 10));
        cardsRow.setOpaque(false);

        cardsRow.add(buildOptionCard(
            "📦", "I Lost Something",
            "Register your lost item\nand track its status",
            Theme.ACCENT_ORANGE, new Color(255, 80, 50),
            () -> new ReportLostScreen().setVisible(true)
        ));

        cardsRow.add(buildOptionCard(
            "🎁", "I Found Something",
            "Register an item you\nhave found for others",
            Theme.ACCENT_TEAL, Theme.ACCENT_BLUE,
            () -> new ReportFoundScreen().setVisible(true)
        ));

        cardsRow.add(buildOptionCard(
            "🔎", "Check My Status",
            "See if your lost item\nhas been found yet",
            Theme.ACCENT_PURPLE, new Color(200, 80, 255),
            () -> new CheckStatusScreen().setVisible(true)
        ));

        // === FOOTER ===
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setPreferredSize(new Dimension(900, 55));
        JLabel footerLbl = Theme.label("© 2025 Lost & Found System  •  Helping reunite people with their belongings",
            Theme.FONT_SMALL, Theme.TEXT_MUTED);
        footer.add(footerLbl);

        root.add(header, BorderLayout.NORTH);
        root.add(cardsRow, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        return root;
    }

    private JPanel buildOptionCard(String emoji, String title, String desc,
                                    Color c1, Color c2, Runnable action) {
        JPanel card = new JPanel() {
            private boolean hovered = false;
            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hovered = true; repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                    public void mouseExited(java.awt.event.MouseEvent e)  { hovered = false; repaint(); }
                    public void mouseClicked(java.awt.event.MouseEvent e) { action.run(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Card background
                Color bgCol = hovered ? new Color(35, 45, 85) : Theme.BG_CARD;
                g2.setColor(bgCol);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 22, 22);
                // Top gradient stripe
                GradientPaint stripe = new GradientPaint(0, 0, c1, getWidth(), 0, c2);
                g2.setPaint(stripe);
                g2.fillRoundRect(0, 0, getWidth()-1, 5, 5, 5);
                // Border
                Color borderCol = hovered ? c1 : Theme.BORDER_COLOR;
                g2.setColor(borderCol);
                g2.setStroke(new BasicStroke(hovered ? 1.5f : 1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 22, 22);
                // Glow if hovered
                if (hovered) {
                    g2.setColor(new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 25));
                    g2.fillRoundRect(3, 3, getWidth()-7, getHeight()-7, 18, 18);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(240, 270));
        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Emoji circle
        JPanel iconCircle = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2.setPaint(gp);
                g2.fillOval(0, 0, getWidth()-1, getHeight()-1);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconCircle.setOpaque(false);
        iconCircle.setPreferredSize(new Dimension(72, 72));
        iconCircle.setLayout(new GridBagLayout());
        JLabel emojiLbl = new JLabel(emoji);
        emojiLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        iconCircle.add(emojiLbl);

        JPanel circleWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        circleWrap.setOpaque(false);
        circleWrap.add(iconCircle);

        JLabel titleLbl = new JLabel("<html><center>" + title + "</center></html>");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLbl.setForeground(Theme.TEXT_PRIMARY);
        titleLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel descLbl = new JLabel("<html><center>" + desc.replace("\n","<br>") + "</center></html>");
        descLbl.setFont(Theme.FONT_BODY);
        descLbl.setForeground(Theme.TEXT_MUTED);
        descLbl.setHorizontalAlignment(SwingConstants.CENTER);

        // Open button
        JButton btn = Theme.styledButton("Open →", c1, c2);
        btn.addActionListener(e -> action.run());
        btn.setPreferredSize(new Dimension(160, 40));
        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnWrap.setOpaque(false);
        btnWrap.add(btn);

        gbc.gridy = 0; card.add(circleWrap, gbc);
        gbc.gridy = 1; card.add(titleLbl, gbc);
        gbc.gridy = 2; card.add(descLbl, gbc);
        gbc.gridy = 3; card.add(btnWrap, gbc);

        return card;
    }
}
