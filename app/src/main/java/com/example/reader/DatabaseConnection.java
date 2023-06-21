package com.example.reader;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://skyflow-db.postgres.database.azure.com:5432/postgres?user=postgres&password=Password123&sslmode=require";


    public static Connection connection() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String read(Connection connection, String ticketId, String flightId) {
        String query = "SELECT * FROM tickets where ticket_id = '" + ticketId + "' and flight_id = '" + flightId + "'";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int status = rs.getInt("status");
                String statusQuery = "select * from statuses where status_id = " + status;
                ResultSet statusRs = stmt.executeQuery(statusQuery);
                if (statusRs.next()) {
                    return statusRs.getString("status");
                }
                return "Status Not Valid";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Ticket Not Found";
    }

    public static boolean update(Connection connection, String ticketId, String flightId) {
        String query = "update tickets set status = 2 where ticket_id = '" + ticketId + "' and flight_id = '" + flightId + "'";
        try (Statement stmt = connection.createStatement()) {
            if (stmt.executeUpdate(query) == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}

