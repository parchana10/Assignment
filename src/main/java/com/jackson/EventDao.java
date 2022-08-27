package com.jackson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jackson.model.EventData;
import com.jackson.utils.JDBCUtils;

public class EventDao {
	private static final String createTableSQL = "create table Event6 (\r\n" + "  id  varchar(20) primary key,\r\n"
			+ "duration int,\r\n" + "alert boolean \r\n" + ");";

	private static final String INSERT_USERS_SQL = "INSERT INTO Event6" + "  (id, duration, alert) VALUES "
			+ " (?, ?, ?);";
	private static final Logger logger = LogManager.getLogger(EventDao.class);

	public void createTable() throws SQLException {

		logger.info(createTableSQL);
		// Step 1: Establishing a Connection
		try (Connection connection = JDBCUtils.getConnection();
				// Step 2:Create a statement using connection object
				Statement statement = connection.createStatement();) {

			// Step 3: Execute the query or update query
			statement.execute(createTableSQL);
		} catch (SQLException e) {
			// print SQL exception information
			JDBCUtils.printSQLException(e);
		}
	}

	public void insertRecord(EventData eventData) throws SQLException {

		logger.info(INSERT_USERS_SQL);
		// Step 1: Establishing a Connection
		try (Connection connection = JDBCUtils.getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, eventData.getId());
			preparedStatement.setInt(2, eventData.getDuration());
			preparedStatement.setBoolean(3, eventData.getAlert());

			logger.info(preparedStatement);
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// print SQL exception information
			JDBCUtils.printSQLException(e);
		}

		// Step 4: try-with-resource statement will auto close the connection.
	}

}
