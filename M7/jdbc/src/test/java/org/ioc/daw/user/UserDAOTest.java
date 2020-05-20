package org.ioc.daw.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.ioc.daw.db.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {

    private DBConnection dBConnection;
    private String connectionProperties = "db_test.properties";
    UserDAO userDAO;

    @Before
    public void setUp() {
	dBConnection = new DBConnection(connectionProperties);
	userDAO = new UserDAO(dBConnection);
    }

    @After
    public void tearDown() throws IOException, SQLException {
	userDAO.deleteTestUsers();
	userDAO.getConnection().close();
    }

    @Test
    public void findAllUsers() throws Exception {
	List<User> users = userDAO.findAllUsers();
	Assert.assertEquals(0, users.size());

	users.add(userDAO.createUser("Josh", "123", "Josh", "josh@email.com"));
	Assert.assertEquals(1, users.size());
    }

    @Test
    public void findActiveUsers() throws Exception {
	User user = userDAO.createUser("Josh", "123", "Josh", "josh@email.com");

	List<User> users = userDAO.findActiveUsers();
	Assert.assertEquals(1, users.size());
	userDAO.deleteUser(user);

	List<User> activeUsers = userDAO.findActiveUsers();
	Assert.assertEquals(0, activeUsers.size());
    }

    @Test
    public void findUserByEmail() throws Exception {
	String name = "Josh";
	String email = "josh@email.com";
	userDAO.createUser("Josh", "123", name, email);

	User res = userDAO.findUserByEmail(email);
	Assert.assertNotNull(res);
	Assert.assertEquals(name, res.getName());

	res = userDAO.findUserByEmail("nonExisting@email.com");
	Assert.assertNull(res);
    }

    @Test
    public void findUserByUsername() throws Exception {
	String username = "Josh";
	String name = "Josh";
	userDAO.createUser(username, "123", name, "josh@email.com");

	User res = userDAO.findUserByUsername(username);
	Assert.assertNotNull(res);
	Assert.assertEquals(name, res.getName());

	res = userDAO.findUserByUsername("nonExistingUsername");
	Assert.assertNull(res);
    }

    @Test
    public void createUser() throws Exception {
	String username = "testUser";
	User user = userDAO.createUser(username, "123", "Test", "test@email.com");

	Assert.assertNotNull(user);
	Assert.assertEquals(username, user.getUsername());
	Assert.assertNotEquals(0, user.getUserId());
    }

    public void createUserEscapesSQLCharacters() throws Exception {
	User createdUser = userDAO.createUser("sl','sls", "123", "Pete Test", "pete@email.com");

	Assert.assertNotNull(createdUser);
    }

    @Test
    public void updateUserEmail() throws Exception {
	String email = "pete@email.com";
	User createdUser = userDAO.createUser("testUser", "123", "Pete Test", email);

	Assert.assertNotNull(createdUser);
	Assert.assertEquals(email, createdUser.getEmail());

	User updatedUser = userDAO.updateUserEmail(createdUser, "new@email.com");

	Assert.assertEquals(createdUser.getUserId(), updatedUser.getUserId());
	Assert.assertEquals("new@email.com", updatedUser.getEmail());
    }

    @Test
    public void updateUserPassword() throws Exception {
	User createdUser = userDAO.createUser("testUser", "123", "test", "pete@email.com");

	Assert.assertNotNull(createdUser);

	User updatedUser = userDAO.updateUserPassword(createdUser, "543");

	Assert.assertEquals(createdUser.getUserId(), updatedUser.getUserId());
	Assert.assertEquals("543", updatedUser.getPassword());
    }

    @Test
    public void updateUserRanking() throws Exception {
	User createdUser = userDAO.createUser("testUser", "123", "Pete Test", "pete@email.com");

	Assert.assertNotNull(createdUser);
	Assert.assertEquals(0, createdUser.getRank());

	User updatedUser = userDAO.updateUserRanking(createdUser, 5);

	Assert.assertNotNull(updatedUser);
	Assert.assertEquals(createdUser.getUserId(), updatedUser.getUserId());
	Assert.assertEquals(5, updatedUser.getRank());
    }

    @Test
    public void deleteUser() throws Exception {
	User createdUser = userDAO.createUser("testUser", "123", "Pete Test", "pete@email.com");

	Assert.assertNotNull(createdUser);
	Assert.assertEquals(true, createdUser.isActive());

	User deletedUser = userDAO.deleteUser(createdUser);

	Assert.assertNotNull(createdUser);
	Assert.assertEquals(false, deletedUser.isActive());
    }
}
