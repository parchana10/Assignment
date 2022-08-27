package com.jackson.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jackson.Application;

public class JDBCUtils {
	private static String jdbcURL = "jdbc:hsqldb:hsql://localhost:9999/testdb";
	private static String jdbcUsername = "SA";
	private static String jdbcPassword = "";
	private static final Logger logger = LogManager.getLogger(JDBCUtils.class);


	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				logger.info("SQLState: " + ((SQLException) e).getSQLState());
				logger.info("Error Code: " + ((SQLException) e).getErrorCode());
				logger.info("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					logger.info("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

}
