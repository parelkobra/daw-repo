package org.ioc.daw.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DBConnectionTest {
    DBConnection dBConnection;
    Connection connection;
    private String connectionProperties = "db.properties";

    @Before
    public void setUp() {
	dBConnection = new DBConnection(connectionProperties);
    }

    @After
    public void cleanUp() throws SQLException {
	if (connection != null) {
	    connection.close();
	}
    }

    @Test
    public void dbConnection() throws IOException, SQLException {
	connection = dBConnection.getConnection();
	Assert.assertEquals("MySQL Connector/J", connection.getMetaData().getDriverName());
	Assert.assertEquals("test", connection.getCatalog());
    }

    @Test
    public void dbConnectionWrongDriver() throws IOException, SQLException {
	dBConnection = new DBConnection("db_wrong_driver.properties");
	connection = dBConnection.getConnection();
	Assert.assertNull(connection);
    }
}
