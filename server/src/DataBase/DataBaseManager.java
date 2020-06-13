package DataBase;

import DataBase.UrlGetters.UrlGetter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseManager {
	public static final Logger logger = Logger.getLogger(DataBaseManager.class.getName());
	
	static {
		logger.info("Connection to PostgreSQL JDBC");
		try {
			Class.forName("org.postgresql.Driver");
			logger.info("PostgreSQL JDBC Driver successfully connected");
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "PostgreSQL JDBC Driver is not found. Include it in your library path", e);
		}
	}
	
	private Connection connection;
	
	public DataBaseManager(UrlGetter urlGetter, String user, String pass) {
		try {
			String url = urlGetter.getUrl();
			connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException throwables) {
			System.out.println("Connection to database failed");
			throwables.printStackTrace();
		}
	}
}
