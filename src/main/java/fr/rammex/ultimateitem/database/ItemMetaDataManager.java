package fr.rammex.ultimateitem.database;

import fr.rammex.ultimateitem.UltimateItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import static fr.rammex.ultimateitem.database.SQLiteManager.getSQLConnection;

public class ItemMetaDataManager {

    public static UUID getItemOwnerUUID(String itemID){
        try (Connection connection = getSQLConnection()) {
            String query = "SELECT * FROM itemMetaData WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, itemID);
                ps.executeQuery();
            }
        } catch (SQLException ex) {
            UltimateItem.instance.getLogger().log(Level.SEVERE, "Unable to get owner UUID", ex);
        }
        return null;
    }

    public static void insertNewOwnerItem(UUID ownerUUID, String itemMeta){
        try (Connection connection = getSQLConnection()) {
            String query = "INSERT OR REPLACE INTO itemMetaData (owner_uuid, itemMeta) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, ownerUUID.toString());
                ps.setString(2, itemMeta);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            UltimateItem.instance.getLogger().log(Level.SEVERE, "Unable to save item", ex);
        }
    }

    public static boolean isItemInTable(String itemID){
        try (Connection connection = getSQLConnection()) {
            String query = "SELECT * FROM itemMetaData WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, itemID);
                return ps.executeQuery().next();
            }
        } catch (SQLException ex) {
            UltimateItem.instance.getLogger().log(Level.SEVERE, "Unable to save item", ex);
        }
        return false;
    }

    public static String getNextItemID(){
        try (Connection connection = getSQLConnection()) {
            String query = "SELECT COUNT(*) AS count FROM itemMetaData";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return String.valueOf(rs.getInt("count") + 1);
                    }
                }
            }
        } catch (SQLException ex) {
            UltimateItem.instance.getLogger().log(Level.SEVERE, "Unable to get next item ID", ex);
        }
        return null;
    }

}
