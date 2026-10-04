import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DataStore — SQLite-backed permanent storage for Lost & Found items.
 * Database file: lost_and_found.db (created automatically in the app folder)
 * Driver:        SQLite JDBC  →  sqlite-jdbc-*.jar on classpath
 */
public class DataStore {

    private static final String DB_URL = "jdbc:sqlite:lost_and_found.db";

    // ── Item model ────────────────────────────────────────────────────────────
    public static class Item {
        public String id;
        public String ownerName;
        public String ownerContact;
        public String itemName;
        public String description;
        public String location;
        public String date;
        public String category;
        public String status;       // LOST | FOUND | MATCHED
        public String matchedWith;  // ref-id of paired item (nullable)

        public Item() {}

        public Item(String ownerName, String ownerContact, String itemName,
                    String description, String location, String date,
                    String category, String status) {
            this.id           = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            this.ownerName    = ownerName;
            this.ownerContact = ownerContact;
            this.itemName     = itemName;
            this.description  = description;
            this.location     = location;
            this.date         = date;
            this.category     = category;
            this.status       = status;
            this.matchedWith  = null;
        }
    }

    // ── Init — call once in Main before launching UI ──────────────────────────
    public static void init() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS items (
                    id            TEXT PRIMARY KEY,
                    owner_name    TEXT NOT NULL,
                    owner_contact TEXT NOT NULL,
                    item_name     TEXT NOT NULL,
                    description   TEXT,
                    location      TEXT,
                    date          TEXT,
                    category      TEXT,
                    status        TEXT NOT NULL,
                    matched_with  TEXT
                )
            """);

        } catch (SQLException e) {
            showError("DB init failed:\n" + e.getMessage());
        }
    }

    // ── Add item + auto-match ─────────────────────────────────────────────────
    public static void addItem(Item item) {
        tryMatch(item); // may flip item.status to MATCHED

        String sql = """
            INSERT INTO items
              (id, owner_name, owner_contact, item_name, description,
               location, date, category, status, matched_with)
            VALUES (?,?,?,?,?,?,?,?,?,?)
        """;
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,  item.id);
            ps.setString(2,  item.ownerName);
            ps.setString(3,  item.ownerContact);
            ps.setString(4,  item.itemName);
            ps.setString(5,  item.description);
            ps.setString(6,  item.location);
            ps.setString(7,  item.date);
            ps.setString(8,  item.category);
            ps.setString(9,  item.status);
            ps.setString(10, item.matchedWith);
            ps.executeUpdate();

        } catch (SQLException e) {
            showError("Insert failed:\n" + e.getMessage());
        }
    }

    // ── Queries ───────────────────────────────────────────────────────────────
    public static List<Item> getItemsByStatus(String status) {
        return query("SELECT * FROM items WHERE status = ?", status);
    }

    public static List<Item> searchByContact(String contact) {
        return query("SELECT * FROM items WHERE LOWER(owner_contact) = LOWER(?)", contact.trim());
    }

    public static List<Item> getAllItems() {
        return query("SELECT * FROM items ORDER BY rowid DESC", null);
    }

    public static Item findById(String id) {
        List<Item> list = query("SELECT * FROM items WHERE id = ?", id);
        return list.isEmpty() ? null : list.get(0);
    }

    // ── Internals ─────────────────────────────────────────────────────────────
    private static List<Item> query(String sql, String param) {
        List<Item> result = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (param != null) ps.setString(1, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item it       = new Item();
                it.id         = rs.getString("id");
                it.ownerName  = rs.getString("owner_name");
                it.ownerContact = rs.getString("owner_contact");
                it.itemName   = rs.getString("item_name");
                it.description = rs.getString("description");
                it.location   = rs.getString("location");
                it.date       = rs.getString("date");
                it.category   = rs.getString("category");
                it.status     = rs.getString("status");
                it.matchedWith = rs.getString("matched_with");
                result.add(it);
            }
        } catch (SQLException e) {
            showError("Query failed:\n" + e.getMessage());
        }
        return result;
    }

    private static void tryMatch(Item newItem) {
        String opposite = newItem.status.equals("LOST") ? "FOUND" : "LOST";
        List<Item> candidates = query(
            "SELECT * FROM items WHERE status = ? AND matched_with IS NULL", opposite);

        for (Item existing : candidates) {
            if (keywordsMatch(newItem.itemName,    existing.itemName) ||
                keywordsMatch(newItem.description, existing.description)) {

                updateStatus(existing.id, "MATCHED", newItem.id);
                newItem.status      = "MATCHED";
                newItem.matchedWith = existing.id;
                return;
            }
        }
    }

    private static void updateStatus(String id, String status, String matchedWith) {
        String sql = "UPDATE items SET status = ?, matched_with = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, matchedWith);
            ps.setString(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            showError("Update failed:\n" + e.getMessage());
        }
    }

    private static boolean keywordsMatch(String a, String b) {
        if (a == null || b == null || a.isBlank() || b.isBlank()) return false;
        for (String word : a.toLowerCase().split("\\s+")) {
            if (word.length() > 3 && b.toLowerCase().contains(word)) return true;
        }
        return false;
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void showError(String msg) {
        javax.swing.JOptionPane.showMessageDialog(
            null, msg, "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
