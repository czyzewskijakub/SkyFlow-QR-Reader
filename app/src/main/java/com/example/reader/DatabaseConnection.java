package com.example.reader;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://skyflow-db.postgres.database.azure.com:5432/postgres?user=postgres&password=Password123&sslmode=require";


    public static Connection connection ()  {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Log.d("DB", "connection: ");
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            Log.d("DB", drivers.nextElement().getClass().getName());
            Class.forName("org.postgresql.Driver");
            Log.d("DB", DriverManager.getDriver(URL).getClass().getName());
            Log.d("DB", URL);
           return DriverManager.getConnection(URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

