import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A styled date field that shows a popup calendar when clicked.
 * Usage: CalendarPicker picker = new CalendarPicker();
 *        String date = picker.getFormattedDate(); // "dd/MM/yyyy"
 */
public class CalendarPicker extends JPanel {

    private final JTextField displayField;
    private Calendar selected;
    private JWindow popup;
    private Calendar viewing; // month/year being viewed in popup

    private static final Color CAL_BG       = new Color(18, 22, 48);
    private static final Color CAL_CARD     = new Color(24, 30, 60);
    private static final Color CAL_BORDER   = new Color(50, 60, 110);
    private static final Color CAL_HEADER   = new Color(30, 38, 75);
    private static final Color TODAY_BG     = new Color(64, 156, 255, 60);
    private static final Color SEL_BG       = new Color(64, 156, 255);
    private static final Color HOVER_BG     = new Color(64, 156, 255, 35);
    private static final Color TEXT_MAIN    = new Color(230, 235, 255);
    private static final Color TEXT_MUTED   = new Color(100, 115, 165);
    private static final Color TEXT_OTHER   = new Color(60, 75, 120);
    private static final Font  FONT_HDR     = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font  FONT_DAY     = new Font("Segoe UI", Font.BOLD, 11);
    private static final Font  FONT_NUM     = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font  FONT_SEL_NUM = new Font("Segoe UI", Font.BOLD, 12);

    public CalendarPicker() {
        setLayout(new BorderLayout());
        setOpaque(false);

        selected = Calendar.getInstance();
        viewing  = Calendar.getInstance();

        displayField = Theme.styledField("Select a date...");
        displayField.setEditable(false);
        displayField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        displayField.setText(formatDate(selected));

        // Calendar icon button
        JButton calBtn = new JButton("📅") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.BG_CARD2);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Theme.BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        calBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        calBtn.setFocusPainted(false);
        calBtn.setBorderPainted(false);
        calBtn.setContentAreaFilled(false);
        calBtn.setPreferredSize(new Dimension(40, 38));
        calBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ActionListener showPopup = e -> togglePopup();
        calBtn.addActionListener(showPopup);
        displayField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { togglePopup(); }
        });

        add(displayField, BorderLayout.CENTER);
        add(calBtn, BorderLayout.EAST);
    }

    // ── public API ──────────────────────────────────────────────────────────

    public String getFormattedDate() {
        return formatDate(selected);
    }

    public void reset() {
        selected = Calendar.getInstance();
        viewing  = (Calendar) selected.clone();
        displayField.setText(formatDate(selected));
        if (popup != null) popup.setVisible(false);
    }

    // ── popup ────────────────────────────────────────────────────────────────

    private void togglePopup() {
        if (popup != null && popup.isVisible()) {
            popup.setVisible(false);
            return;
        }
        viewing = (Calendar) selected.clone();
        showPopup();
    }

    private void showPopup() {
        Window owner = SwingUtilities.getWindowAncestor(this);
        popup = new JWindow(owner);
        popup.setBackground(new Color(0, 0, 0, 0));

        JPanel cal = buildCalendarPanel();
        popup.add(cal);
        popup.pack();

        Point loc = displayField.getLocationOnScreen();
        int x = loc.x;
        int y = loc.y + displayField.getHeight() + 4;

        // Keep on screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        if (x + popup.getWidth() > screen.width)  x = screen.width - popup.getWidth() - 8;
        if (y + popup.getHeight() > screen.height) y = loc.y - popup.getHeight() - 4;

        popup.setLocation(x, y);
        popup.setVisible(true);

        // Close when clicking outside
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent) {
                    MouseEvent me = (MouseEvent) event;
                    if (me.getID() == MouseEvent.MOUSE_PRESSED) {
                        if (!popup.getBounds().contains(me.getLocationOnScreen())) {
                            popup.setVisible(false);
                            Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                        }
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }

    private JPanel buildCalendarPanel() {
        JPanel root = new JPanel(new BorderLayout(0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CAL_BG);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
                g2.setColor(CAL_BORDER);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
                g2.dispose();
            }
        };
        root.setOpaque(false);
        root.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        root.setPreferredSize(new Dimension(300, 310));

        // ── Month/Year header ───────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JButton prevBtn = navButton("‹");
        JButton nextBtn = navButton("›");

        String[] months = {"January","February","March","April","May","June",
                           "July","August","September","October","November","December"};
        JLabel monthYearLbl = new JLabel(
            months[viewing.get(Calendar.MONTH)] + "  " + viewing.get(Calendar.YEAR),
            SwingConstants.CENTER);
        monthYearLbl.setFont(FONT_HDR);
        monthYearLbl.setForeground(TEXT_MAIN);

        prevBtn.addActionListener(e -> {
            viewing.add(Calendar.MONTH, -1);
            rebuildPopup();
        });
        nextBtn.addActionListener(e -> {
            viewing.add(Calendar.MONTH, 1);
            rebuildPopup();
        });

        header.add(prevBtn, BorderLayout.WEST);
        header.add(monthYearLbl, BorderLayout.CENTER);
        header.add(nextBtn, BorderLayout.EAST);

        // ── Day-of-week labels ──────────────────────────────────────────────
        JPanel dayNames = new JPanel(new GridLayout(1, 7, 4, 0));
        dayNames.setOpaque(false);
        dayNames.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        String[] days = {"Mo","Tu","We","Th","Fr","Sa","Su"};
        for (String d : days) {
            JLabel lbl = new JLabel(d, SwingConstants.CENTER);
            lbl.setFont(FONT_DAY);
            lbl.setForeground(TEXT_MUTED);
            dayNames.add(lbl);
        }

        // ── Date grid ───────────────────────────────────────────────────────
        JPanel grid = new JPanel(new GridLayout(6, 7, 4, 4));
        grid.setOpaque(false);

        Calendar today = Calendar.getInstance();
        Calendar first = (Calendar) viewing.clone();
        first.set(Calendar.DAY_OF_MONTH, 1);

        // Monday-based: Mon=0 … Sun=6
        int startDow = first.get(Calendar.DAY_OF_WEEK); // Sun=1..Sat=7
        int offset = (startDow == Calendar.SUNDAY) ? 6 : startDow - Calendar.MONDAY;

        // Fill leading days from previous month
        Calendar prev = (Calendar) viewing.clone();
        prev.add(Calendar.MONTH, -1);
        int prevMax = prev.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = offset - 1; i >= 0; i--) {
            grid.add(dayCell(prevMax - i, false, false, false, true));
        }

        int maxDay = viewing.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int d = 1; d <= maxDay; d++) {
            boolean isSel   = (d == selected.get(Calendar.DAY_OF_MONTH)
                               && viewing.get(Calendar.MONTH) == selected.get(Calendar.MONTH)
                               && viewing.get(Calendar.YEAR)  == selected.get(Calendar.YEAR));
            boolean isToday = (d == today.get(Calendar.DAY_OF_MONTH)
                               && viewing.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                               && viewing.get(Calendar.YEAR)  == today.get(Calendar.YEAR));
            final int day = d;
            JPanel cell = dayCell(d, isSel, isToday, true, false);
            cell.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selected = (Calendar) viewing.clone();
                    selected.set(Calendar.DAY_OF_MONTH, day);
                    displayField.setText(formatDate(selected));
                    popup.setVisible(false);
                }
            });
            grid.add(cell);
        }

        // Fill trailing days
        int total = offset + maxDay;
        int trailing = (total % 7 == 0) ? 0 : 7 - (total % 7);
        for (int i = 1; i <= trailing; i++) {
            grid.add(dayCell(i, false, false, false, true));
        }

        // ── Clear / Today buttons ───────────────────────────────────────────
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton clearBtn = footerButton("CLEAR", false);
        JButton todayBtn = footerButton("TODAY", true);

        clearBtn.addActionListener(e -> {
            selected = Calendar.getInstance();
            displayField.setText(formatDate(selected));
            popup.setVisible(false);
        });
        todayBtn.addActionListener(e -> {
            selected = Calendar.getInstance();
            viewing  = (Calendar) selected.clone();
            displayField.setText(formatDate(selected));
            popup.setVisible(false);
        });

        footer.add(clearBtn);
        footer.add(todayBtn);

        JPanel body = new JPanel(new BorderLayout(0, 0));
        body.setOpaque(false);
        body.add(dayNames, BorderLayout.NORTH);
        body.add(grid,     BorderLayout.CENTER);
        body.add(footer,   BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);
        root.add(body,   BorderLayout.CENTER);
        return root;
    }

    private void rebuildPopup() {
        popup.getContentPane().removeAll();
        popup.add(buildCalendarPanel());
        popup.pack();
        popup.revalidate();
        popup.repaint();
    }

    // ── Cell builder ─────────────────────────────────────────────────────────

    private JPanel dayCell(int day, boolean selected, boolean today, boolean active, boolean faded) {
        JPanel cell = new JPanel(new GridBagLayout()) {
            private boolean hovered = false;
            {
                if (active && !selected) {
                    addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) { hovered = true; repaint(); setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
                        public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                    });
                }
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                int d = Math.min(w, h) - 4;
                int cx = (w - d) / 2, cy = (h - d) / 2;
                if (selected) {
                    g2.setColor(SEL_BG);
                    g2.fillOval(cx, cy, d, d);
                } else if (today) {
                    g2.setColor(TODAY_BG);
                    g2.fillOval(cx, cy, d, d);
                    g2.setColor(SEL_BG);
                    g2.setStroke(new BasicStroke(1.2f));
                    g2.drawOval(cx, cy, d-1, d-1);
                } else if (hovered) {
                    g2.setColor(HOVER_BG);
                    g2.fillOval(cx, cy, d, d);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        cell.setOpaque(false);
        cell.setPreferredSize(new Dimension(34, 34));

        JLabel lbl = new JLabel(String.valueOf(day), SwingConstants.CENTER);
        lbl.setFont(selected ? FONT_SEL_NUM : FONT_NUM);
        lbl.setForeground(selected ? Color.WHITE : faded ? TEXT_OTHER : active ? TEXT_MAIN : TEXT_MUTED);
        cell.add(lbl);
        return cell;
    }

    // ── Nav button ────────────────────────────────────────────────────────────

    private JButton navButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true; repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hov) { g2.setColor(new Color(64,156,255,40)); g2.fillOval(2,2,getWidth()-4,getHeight()-4); }
                g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                g2.setColor(hov ? SEL_BG : TEXT_MUTED);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(32, 32));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── Footer button ─────────────────────────────────────────────────────────

    private JButton footerButton(String text, boolean filled) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true; repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (filled) {
                    g2.setColor(hov ? SEL_BG.darker() : SEL_BG);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    g2.setColor(Color.WHITE);
                } else {
                    g2.setColor(hov ? new Color(64,156,255,20) : new Color(0,0,0,0));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    g2.setColor(TEXT_MUTED);
                }
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(90, 32));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private String formatDate(Calendar cal) {
        return new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
    }
}
