package fr.rammex.arkaitems.database;

import fr.rammex.arkaitems.ArkaItems;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

public class SQLiteManager {
    private static String dbName;
    private static File dbFile;

    public SQLiteManager(String dbName, File dbFile) {
        SQLiteManager.dbName = dbName;
        SQLiteManager.dbFile = dbFile;
    }

    public void initialize() {
        try (Connection connection = getSQLConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM itemMetaData");
            ResultSet rs = ps.executeQuery();
            close(ps, rs);
        } catch (SQLException ex) {
            ArkaItems.instance.getLogger().log(Level.SEVERE, "Unable to retrieve connection", ex);
        }
    }

    public static Connection getSQLConnection() {
        File folder = new File(dbFile, dbName + ".db");
        if (!folder.getParentFile().exists()) {
            folder.getParentFile().mkdirs();
        }
        if (!folder.exists()) {
            try {
                folder.createNewFile();
            } catch (IOException e) {
                ArkaItems.instance.getLogger().log(Level.SEVERE, "File write error: " + dbName + ".db");
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + folder);
        } catch (SQLException | ClassNotFoundException ex) {
            ArkaItems.instance.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        }
        return null;
    }

    public void load() {
        try (Connection connection = getSQLConnection()) {
            Statement s = connection.createStatement();

            // Table for itemMetaData
            String itemMetaData = "CREATE TABLE IF NOT EXISTS itemMetaData (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`owner_uuid` VARCHAR(36) NOT NULL," +
                    "`itemMeta` TEXT" +
                    ");";



            s.executeUpdate(itemMetaData);

            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }

    public static void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ingored) {
        }
    }
}

