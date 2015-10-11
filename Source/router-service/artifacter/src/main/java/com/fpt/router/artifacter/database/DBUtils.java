package com.fpt.router.artifacter.database;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
import com.fpt.router.artifacter.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtils {

    public static Connection getConnection() {
        Connection conn;
        try {
            Class.forName(Config.JDBC_DRIVER);
            conn = (Connection) DriverManager.getConnection(Config.DB_URL, Config.USER, Config.PASS);
            return conn;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void deleteDatabase() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();

            String sql = "Truncate table MiddlePath";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM BusOrder";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM BusRoute";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM Station";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Connection conn = DBUtils.getConnection();
    }
}

