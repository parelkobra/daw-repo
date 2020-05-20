package org.ioc.daw.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.ioc.daw.db.DBConnection;

public class UserDAO {
    private DBConnection dBConnection;
    private Connection connection;

    public UserDAO(DBConnection dBConnection) {
	this.dBConnection = dBConnection;
    }

    public List<User> findAllUsers() throws SQLException {
	String qry = "select id, username, password, name, email, rank, active, created_on from users";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	List<User> users = executeQuery(preparedStatement);
	return users;
    }

    public User findUserByEmail(String userEmail) throws Exception {
	String qry = "select * from users where email = ?";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	preparedStatement.setString(1, userEmail);
	return findUniqueResult(preparedStatement);
    }

    public User findUserByUsername(String username) throws Exception {
	String qry = "select * from users where username =?";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	preparedStatement.setString(1, username);
	return findUniqueResult(preparedStatement);
    }

    public List<User> findActiveUsers() throws SQLException {
	String qry = "SELECT id, username, password, name, email, rank, active, created_on FROM users WHERE active = true";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	List<User> users = executeQuery(preparedStatement);
	return users;
    }

    public User createUser(String username, String password, String name, String email) throws Exception {
	String qry = "INSERT INTO users (username, password, name, email) VALUES (?, ?, ?, ?)";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	preparedStatement.setString(1, username);
	preparedStatement.setString(2, password);
	preparedStatement.setString(3, name);
	preparedStatement.setString(4, email);
	return createOrUpdateUser(username, preparedStatement);
    }

    public User updateUserEmail(User user, String newEmail) throws Exception {
	String qry = "UPDATE users SET email = ? WHERE id = ?";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	preparedStatement.setString(1, newEmail);
	preparedStatement.setInt(2, user.getUserId());
	return createOrUpdateUser(user.getUsername(), preparedStatement);
    }

    public User updateUserPassword(User user, String newPassword) throws Exception {
	String qry = "UPDATE users SET password = ? WHERE id = ?";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	preparedStatement.setString(1, newPassword);
	preparedStatement.setInt(2, user.getUserId());
	return createOrUpdateUser(user.getUsername(), preparedStatement);
    }

    public User updateUserRanking(User user, int newRanking) throws Exception {
	String qry = "UPDATE users SET rank = ? WHERE id = ?";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	preparedStatement.setInt(1, newRanking);
	preparedStatement.setInt(2, user.getUserId());
	return createOrUpdateUser(user.getUsername(), preparedStatement);
    }

    private User createOrUpdateUser(String username, PreparedStatement preparedStatement) throws Exception {
	int result = executeUpdateQuery(preparedStatement);
	if (result == 0) {
	    throw new Exception("Error creating user");
	}
	return findUserByUsername(username);
    }

    public User deleteUser(User user) throws Exception {
	String qry = "UPDATE users SET active = false WHERE id = ?";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	preparedStatement.setInt(1, user.getUserId());
	return createOrUpdateUser(user.getUsername(), preparedStatement);
    }

    public void deleteTestUsers() throws SQLException {
	String qry = "DELETE FROM users WHERE id NOT LIKE 1 && id NOT LIKE 2";
	PreparedStatement preparedStatement = getPreparedStatement(qry);
	preparedStatement.executeUpdate(qry);
    }

    private User findUniqueResult(PreparedStatement preparedStatement) throws Exception {
	List<User> users = executeQuery(preparedStatement);
	if (users.isEmpty()) {
	    return null;
	}
	if (users.size() > 1) {
	    throw new Exception("Only one result expected");
	}
	return users.get(0);
    }

    private List<User> executeQuery(PreparedStatement preparedStatement) {
	List<User> users = new ArrayList<>();

	try (ResultSet rs = preparedStatement.executeQuery()) {
	    while (rs.next()) {
		User user = buildUserFromResultSet(rs);
		users.add(user);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return users;
    }

    private PreparedStatement getPreparedStatement(String query) throws SQLException {
	if (getConnection() == null) {
	    try {
		setConnection(dBConnection.getConnection());
	    } catch (SQLException | IOException e) {
		e.printStackTrace();
	    }
	}
	return getConnection().prepareStatement(query);
    }

    private int executeUpdateQuery(PreparedStatement preparedStatement) {
	int result = 0;
	if (getConnection() == null) {
	    try {
		setConnection(dBConnection.getConnection());
	    } catch (SQLException | IOException e) {
		e.printStackTrace();
	    }
	}
	try {
	    result = preparedStatement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }

    private User buildUserFromResultSet(ResultSet rs) throws SQLException {
	int userId = rs.getInt("id");
	String username = rs.getString("username");
	String password = rs.getString("password");
	String name = rs.getString("name");
	String email = rs.getString("email");
	int rank = rs.getInt("rank");
	boolean active = rs.getBoolean("active");
	Timestamp timestamp = rs.getTimestamp("created_on");
	User user = new User(userId, username, password, name, email, rank, timestamp, active);
	return user;
    }

    public Connection getConnection() {
	return connection;
    }

    public void setConnection(Connection connection) {
	this.connection = connection;
    }
}
