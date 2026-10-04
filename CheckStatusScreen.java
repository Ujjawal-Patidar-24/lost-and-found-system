import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class CheckStatusScreen extends JFrame {

    private JTextField searchField;
    private JPanel resultsPanel;
    private JLabel resultsLabel;

    public CheckStatusScreen() {
        setTitle("Check My Status");
        setSize(620, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(buildContent());
    }

    private JPanel buildContent() {
        JPanel root = Theme.gradientPanel(Theme.BG_DARK, new Color(10, 5, 35), true);
        root.setLayout(new BorderLayout());

        // HEADER
        JPanel header = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0,0, new Color(140,82,255,80), getWidth(),0, new Color(200,80,255,40));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(620, 80));
        header.setLayout(new GridBagLayout());
        JPanel hBox = new JPanel();
        hBox.setOpaque(false);
        hBox.setLayout(new BoxLayout(hBox, BoxLayout.Y_AXIS));
        JLabel hIcon = new JLabel("🔎  Check Item Status");
        hIcon.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        hIcon.setForeground(Theme.TEXT_PRIMARY);
        hIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel hSub = new JLabel("Enter your contact info to check your registered items");
        hSub.setFont(Theme.FONT_BODY);
        hSub.setForeground(Theme.TEXT_MUTED);
        hSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        hBox.add(hIcon);
        hBox.add(Box.createVerticalStrut(4));
        hBox.add(hSub);
        header.add(hBox);

        // SEARCH BAR
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 14));
        searchBar.setOpaque(false);
        searchField = Theme.styledField("Enter your registered phone...");
        searchField.setPreferredSize(new Dimension(340, 40));
        JButton searchBtn = Theme.styledButton("🔍 Search", Theme.ACCENT_PURPLE, new Color(200,80,255));
        searchBtn.setPreferredSize(new Dimension(130, 40));
        searchBtn.addActionListener(e -> doSearch());
        searchField.addActionListener(e -> doSearch());
        searchBar.add(searchField);
        searchBar.add(searchBtn);

        // RESULTS AREA
        resultsLabel = Theme.label("", Theme.FONT_H3, Theme.TEXT_MUTED);
        resultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel resultsLabelWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultsLabelWrap.setOpaque(false);
        resultsLabelWrap.add(resultsLabel);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(resultsPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Empty state
        showEmptyState();

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(searchBar, BorderLayout.NORTH);
        centerPanel.add(resultsLabelWrap, BorderLayout.CENTER);

        JPanel scrollWrap = new JPanel(new BorderLayout());
        scrollWrap.setOpaque(false);
        scrollWrap.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        scrollWrap.add(scroll, BorderLayout.CENTER);

        JPanel mainCenter = new JPanel(new BorderLayout());
        mainCenter.setOpaque(false);
        mainCenter.add(centerPanel, BorderLayout.NORTH);
        mainCenter.add(scrollWrap, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(mainCenter, BorderLayout.CENTER);
        return root;
    }

    private void doSearch() {
        String contact = searchField.getText().trim();
        if (contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your registered phone",
                "Empty Search", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<DataStore.Item> results = DataStore.searchByContact(contact);
        resultsPanel.removeAll();

        if (results.isEmpty()) {
            resultsLabel.setText("No items found for: " + contact);
            resultsLabel.setForeground(Theme.TEXT_MUTED);
            showEmptyState();
        } else {
            resultsLabel.setText(results.size() + " item(s) found");
            resultsLabel.setForeground(Theme.ACCENT_BLUE);
            for (DataStore.Item item : results) {
                resultsPanel.add(buildItemCard(item));
                resultsPanel.add(Box.createVerticalStrut(12));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void showEmptyState() {
        resultsPanel.removeAll();
        JPanel empty = new JPanel(new GridBagLayout());
        empty.setOpaque(false);
        empty.setPreferredSize(new Dimension(560, 280));
        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        JLabel emj = new JLabel("🔎");
        emj.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
        emj.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel msg = Theme.label("Search using your registered phone ", Theme.FONT_H3, Theme.TEXT_MUTED);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sub = Theme.label("to see all items you have registered", Theme.FONT_BODY, new Color(80,90,130));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        col.add(emj);
        col.add(Box.createVerticalStrut(12));
        col.add(msg);
        col.add(Box.createVerticalStrut(4));
        col.add(sub);
        empty.add(col);
        resultsPanel.add(empty);
    }

    private JPanel buildItemCard(DataStore.Item item) {
        JPanel card = Theme.cardPanel();
        card.setLayout(new GridBagLayout());
        card.setMaximumSize(new Dimension(560, 200));
        card.setPreferredSize(new Dimension(556, 165));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 14, 5, 14);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Top row: item name + status badge
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        JLabel itemName = Theme.label(item.itemName, Theme.FONT_H3, Theme.TEXT_PRIMARY);
        JLabel badge = Theme.statusBadge(item.status);
        topRow.add(itemName, BorderLayout.WEST);
        topRow.add(badge, BorderLayout.EAST);

        // Info rows
        JLabel typeRow = Theme.label("Type: " + item.status + "  •  Category: " + item.category, Theme.FONT_SMALL, Theme.TEXT_MUTED);
        JLabel locRow  = Theme.label("📍 " + (item.location.isEmpty() ? "Location not specified" : item.location), Theme.FONT_BODY, new Color(150,165,210));
        JLabel dateRow = Theme.label("📅 " + (item.date.isEmpty() ? "Date not specified" : item.date) + "    🆔 Ref: " + item.id, Theme.FONT_SMALL, Theme.TEXT_MUTED);

        gbc.gridy = 0; card.add(topRow, gbc);
        gbc.gridy = 1; card.add(typeRow, gbc);
        gbc.gridy = 2; card.add(locRow, gbc);
        gbc.gridy = 3; card.add(dateRow, gbc);

        if (item.status.equals("MATCHED")) {
            JPanel matchBox = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(50,210,120,30));
                    g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,10,10);
                    g2.setColor(new Color(50,210,120,80));
                    g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,10,10);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            matchBox.setOpaque(false);
            matchBox.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 4));
            DataStore.Item matched = findById(item.matchedWith);
            String matchInfo = matched != null
                ? "🎉  MATCH FOUND! Contact: " + matched.ownerName + " — " + matched.ownerContact
                : "🎉  Match found! ID: " + item.matchedWith;
            JLabel matchLbl = Theme.label(matchInfo, Theme.FONT_LABEL, Theme.ACCENT_GREEN);
            matchBox.add(matchLbl);
            gbc.gridy = 4;
            gbc.insets = new Insets(4, 14, 8, 14);
            card.add(matchBox, gbc);
        }

        return card;
    }

    private DataStore.Item findById(String id) {
        if (id == null) return null;
        for (DataStore.Item i : DataStore.getAllItems()) {
            if (i.id.equals(id)) return i;
        }
        return null;
    }
}
