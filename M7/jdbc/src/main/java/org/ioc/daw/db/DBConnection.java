package org.ioc.daw.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private String connectionFile;

    public DBConnection(String connectionFile) {
	this.connectionFile = connectionFile;
    }

    public Connection getConnection() throws SQLException, IOException {
	Properties props = new Properties();
	InputStream resourceAsStream = null;
	Connection con = null;
	try {
	    ClassLoader classLoader = getClass().getClassLoader();
	    URL urlResource = classLoader.getResource(connectionFile);
	    if (urlResource != null) {
		resourceAsStream = urlResource.openStream();
		props.load(resourceAsStream);
		Class.forName(props.getProperty("DB_DRIVER_CLASS"));
		con = DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("DB_USERNAME"),
			props.getProperty("DB_PASSWORD"));
	    }
	} catch (IOException | ClassNotFoundException | SQLException e) {
	    e.printStackTrace();
	} finally {
	    if (resourceAsStream != null) {
		resourceAsStream.close();
	    }
	}
	return con;
    }
}
