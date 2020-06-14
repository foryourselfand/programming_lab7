package DataBase;

import DataBase.CredentialsGetter.CredentialsGetter;
import DataBase.UrlGetter.UrlGetter;
import Session.User;
import Utils.Characters;
import Utils.PasswordEncoder;
import Utils.RandomSequenceGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	public DataBaseManager(UrlGetter urlGetter, CredentialsGetter credentialsGetter) {
		try {
			String url = urlGetter.getUrl();
			connection = DriverManager.getConnection(url, credentialsGetter.getLogin(), credentialsGetter.getPassword());
		} catch (SQLException throwables) {
			System.out.println("Connection to database failed");
			throwables.printStackTrace();
		}
	}
	
	public void addUser(User user) {
		String salt = RandomSequenceGenerator.getRandomSequence(
				10,
				Characters.LOWERCASE,
				Characters.UPPERCASE,
				Characters.DIGITS,
				Characters.SPECIAL
		);
		
		String hash = PasswordEncoder.getHash(user.getPassword(), salt);
		try {
			PreparedStatement statement = connection.prepareStatement("insert into users values (?, ?, ?)");
			statement.setString(1, user.getUsername());
			statement.setString(2, hash);
			statement.setString(3, salt);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
