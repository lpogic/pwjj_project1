package app.model;

import org.h2.Driver;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static String dbURL = "jdbc:h2:D:\\Users\\1\\db;user=test;password=test";
    private static String tableName = "TRACKS";

    private Connection connection = null;
    private Statement statement = null;

    public void createConnection()throws SQLException {
        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(dbURL);
    }

    public void createTrackTextTable()throws SQLException{
        statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "    ID int," +
                "    NAME varchar(255)," +
                "    TRACK varchar(255)," +
                "    RATE int," +
                "    LMT TIMESTAMP" + " )");
    }

    public List<TrackText> getAllTrackText()throws SQLException {
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM " + tableName);
        List<TrackText> entries = fetchAll(results);
        results.close();
        statement.close();
        return entries;
    }

    public List<TrackText> getTracksByNameLikeAndTrackLike(String name, String track)throws SQLException {
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM " + tableName + " WHERE NAME LIKE '%" +
                name + "%' AND TRACK LIKE '%" + track + "%'" );
        List<TrackText> entries = fetchAll(results);
        results.close();
        statement.close();
        return entries;
    }

    private List<TrackText> fetchAll(ResultSet results)throws SQLException{
        List<TrackText> entries = new ArrayList<>();
        while (results.next()) {
            int resId = results.getInt("ID");
            String resName = results.getString("NAME");
            String resTrack = results.getString("TRACK");
            int resRate = results.getInt("RATE");
            Timestamp lastModificationTime = results.getTimestamp("LMT");
            TrackText entry = new TrackText(resId, resName, resTrack,resRate,lastModificationTime);
            entries.add(entry);
        }
        return entries;
    }

    public void insertEntry(TrackText entry)throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.now();
        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO " + tableName + "(ID, NAME, TRACK, RATE, LMT) values (?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, localDateTime.hashCode());
        preparedStatement.setString(2, entry.getName());
        preparedStatement.setString(3, entry.getTrack());
        preparedStatement.setInt(4, entry.getRate());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(localDateTime));
        preparedStatement.executeUpdate();
    }

    public void updateEntry(TrackText entry)throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.now();
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE " + tableName + " SET NAME = ?, TRACK = ?, RATE = ?, LMT = ? WHERE ID = ?");
        preparedStatement.setString(1, entry.getName());
        preparedStatement.setString(2, entry.getTrack());
        preparedStatement.setInt(3, entry.getRate());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(localDateTime));
        preparedStatement.setInt(5, entry.getId());
        preparedStatement.executeUpdate();
    }

    public void saveEntry(TrackText entry)throws SQLException {
        if(entry.getId() == 0)insertEntry(entry);
        else updateEntry(entry);
    }

    public void deleteAllEntries()throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM " + tableName);
    }

    public void shutdown()throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            DriverManager.getConnection(dbURL + ";shutdown=true");
            connection.close();
        }
    }
}
