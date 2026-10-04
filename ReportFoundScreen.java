import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ReportFoundScreen extends JFrame {

    private JTextField nameField, contactField, itemField, locationField;
    private CalendarPicker datePicker;
    private JTextArea descArea;
    private JComboBox<String> categoryCombo;

    public ReportFoundScreen() {
        setTitle("Report Found Item");
        setSize(580, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(buildContent());
    }

    private JPanel buildContent() {
        JPanel root = Theme.gradientPanel(Theme.BG_DARK, new Color(5, 25, 25), true);
        root.setLayout(new BorderLayout());

        // HEADER
        JPanel header = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0,0, new Color(0,210,190,80), getWidth(),0, new Color(64,156,255,40));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(580, 80));
        header.setLayout(new GridBagLayout());
        JPanel hBox = new JPanel();
        hBox.setOpaque(false);
        hBox.setLayout(new BoxLayout(hBox, BoxLayout.Y_AXIS));
        JLabel hIcon = new JLabel("🎁  Report Found Item");
        hIcon.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        hIcon.setForeground(Theme.TEXT_PRIMARY);
        hIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel hSub = new JLabel("Help someone reunite with their belongings");
        hSub.setFont(Theme.FONT_BODY);
        hSub.setForeground(Theme.TEXT_MUTED);
        hSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        hBox.add(hIcon);
        hBox.add(Box.createVerticalStrut(4));
        hBox.add(hSub);
        header.add(hBox);

        // FORM CARD
        JPanel formCard = Theme.cardPanel();
        formCard.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;

        nameField     = Theme.styledField("Your full name");
        contactField  = Theme.styledField("Phone or Email");
        itemField     = Theme.styledField("e.g. Red Backpack, Silver Watch");
        locationField = Theme.styledField("Where did you find it?");
        descArea      = Theme.styledTextArea();
        categoryCombo = Theme.styledCombo(new String[]{
            "Select Category","Electronics","Clothing","Accessories",
            "Documents","Keys","Bags","Others"});
        datePicker    = new CalendarPicker();

        int row = 0;

        gbc.gridy = row++; gbc.insets = new Insets(14, 20, 2, 20);
        formCard.add(Theme.label("👤  Your Name", Theme.FONT_LABEL, Theme.TEXT_MUTED), gbc);
        gbc.gridy = row++; gbc.insets = new Insets(2, 20, 6, 20);
        formCard.add(nameField, gbc);

        gbc.gridy = row++; gbc.insets = new Insets(8, 20, 2, 20);
        formCard.add(Theme.label("📞  Contact Info", Theme.FONT_LABEL, Theme.TEXT_MUTED), gbc);
        gbc.gridy = row++; gbc.insets = new Insets(2, 20, 6, 20);
        formCard.add(contactField, gbc);

        gbc.gridy = row++; gbc.insets = new Insets(8, 20, 2, 20);
        formCard.add(Theme.label("🏷  Item Name", Theme.FONT_LABEL, Theme.TEXT_MUTED), gbc);
        gbc.gridy = row++; gbc.insets = new Insets(2, 20, 6, 20);
        formCard.add(itemField, gbc);

        gbc.gridy = row++; gbc.insets = new Insets(8, 20, 2, 20);
        formCard.add(Theme.label("📂  Category", Theme.FONT_LABEL, Theme.TEXT_MUTED), gbc);
        gbc.gridy = row++; gbc.insets = new Insets(2, 20, 6, 20);
        formCard.add(categoryCombo, gbc);

        gbc.gridy = row++; gbc.insets = new Insets(8, 20, 2, 20);
        formCard.add(Theme.label("📍  Found Location", Theme.FONT_LABEL, Theme.TEXT_MUTED), gbc);
        gbc.gridy = row++; gbc.insets = new Insets(2, 20, 6, 20);
        formCard.add(locationField, gbc);

        gbc.gridy = row++; gbc.insets = new Insets(8, 20, 2, 20);
        formCard.add(Theme.label("📅  Date Found", Theme.FONT_LABEL, Theme.TEXT_MUTED), gbc);
        gbc.gridy = row++; gbc.insets = new Insets(2, 20, 6, 20);
        formCard.add(datePicker, gbc);

        gbc.gridy = row++; gbc.insets = new Insets(8, 20, 2, 20);
        formCard.add(Theme.label("📝  Description", Theme.FONT_LABEL, Theme.TEXT_MUTED), gbc);
        gbc.gridy = row++; gbc.insets = new Insets(2, 20, 6, 20);
        descArea.setRows(5);
        JScrollPane scrollDesc = new JScrollPane(descArea);
        scrollDesc.setBorder(BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1, true));
        scrollDesc.setOpaque(false);
        scrollDesc.getViewport().setOpaque(false);
        scrollDesc.setPreferredSize(new Dimension(490, 100));
        scrollDesc.setMinimumSize(new Dimension(490, 100));
        formCard.add(scrollDesc, gbc);

        // BUTTON ROW
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnRow.setOpaque(false);
        JButton clearBtn  = Theme.styledButton("Clear", Theme.BG_CARD2, Theme.BORDER_COLOR);
        clearBtn.setPreferredSize(new Dimension(140, 44));
        JButton submitBtn = Theme.styledButton("Submit Report", Theme.ACCENT_TEAL, Theme.ACCENT_BLUE);
        submitBtn.setPreferredSize(new Dimension(200, 44));
        clearBtn.addActionListener(e -> clearForm());
        submitBtn.addActionListener(e -> submitForm());
        btnRow.add(clearBtn);
        btnRow.add(submitBtn);

        gbc.gridy = row; gbc.insets = new Insets(16, 20, 20, 20);
        formCard.add(btnRow, gbc);

        JPanel centerWrap = new JPanel();
        centerWrap.setLayout(new BoxLayout(centerWrap, BoxLayout.Y_AXIS));
        centerWrap.setOpaque(false);
        formCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.setMaximumSize(new Dimension(540, Integer.MAX_VALUE));
        JPanel hPad = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));
        hPad.setOpaque(false);
        hPad.add(formCard);
        centerWrap.add(hPad);

        JScrollPane outerScroll = new JScrollPane(centerWrap);
        outerScroll.setOpaque(false);
        outerScroll.getViewport().setOpaque(false);
        outerScroll.setBorder(BorderFactory.createEmptyBorder());
        outerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScroll.getVerticalScrollBar().setUnitIncrement(14);

        root.add(header, BorderLayout.NORTH);
        root.add(outerScroll, BorderLayout.CENTER);
        return root;
    }

    private void clearForm() {
        nameField.setText("");
        contactField.setText("");
        itemField.setText("");
        locationField.setText("");
        descArea.setText("");
        categoryCombo.setSelectedIndex(0);
        datePicker.reset();
    }

    private void submitForm() {
        String name     = nameField.getText().trim();
        String contact  = contactField.getText().trim();
        String item     = itemField.getText().trim();
        String location = locationField.getText().trim();
        String desc     = descArea.getText().trim();
        String cat      = (String) categoryCombo.getSelectedItem();
        String date     = datePicker.getFormattedDate();

        if (name.isEmpty() || contact.isEmpty() || item.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in Name, Contact, and Item Name.",
                "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DataStore.Item newItem = new DataStore.Item(name, contact, item, desc, location, date,
            cat.equals("Select Category") ? "Others" : cat, "FOUND");
        DataStore.addItem(newItem);

        String msg = newItem.status.equals("MATCHED")
            ? "🎉 A match was found! Someone reported this item as lost.\nReference ID: " + newItem.id + "\nPlease check status for owner contact info."
            : "✅ Found item registered!\nReference ID: " + newItem.id + "\nThank you for your kindness!";

        JOptionPane.showMessageDialog(this, msg, "Submitted", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }
}
