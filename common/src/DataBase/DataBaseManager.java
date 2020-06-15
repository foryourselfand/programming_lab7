package DataBase;

import DataBase.CredentialsGetter.CredentialsGetter;
import DataBase.UrlGetter.UrlGetter;
import Input.Coordinates;
import Input.Flat;
import Input.House;
import Input.Transport;
import Session.User;
import Utils.Characters;
import Utils.PasswordEncoder;
import Utils.RandomSequenceGenerator;

import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.CopyOnWriteArraySet;
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
	
	public boolean containsUserName(User user) {
		try {
			PreparedStatement statement = connection.prepareStatement("select * from users where username = ?");
			statement.setString(1, user.getUsername());
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
	
	public boolean containsUser(User user) {
		try {
			PreparedStatement statement = connection.prepareStatement("select * from users where username = ?");
			statement.setString(1, user.getUsername());
			ResultSet resultSet = statement.executeQuery();
			if (! resultSet.next()) return false;
			String salt = resultSet.getString("salt");
			String hash = PasswordEncoder.getHash(user.getPassword(), salt);
			statement = connection.prepareStatement("select * from users where username = ? and password = ? and salt = ?");
			statement.setString(1, user.getUsername());
			statement.setString(2, hash);
			statement.setString(3, salt);
			return statement.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public CopyOnWriteArraySet<Flat> getCollectionFromDatabase() {
		CopyOnWriteArraySet<Flat> collection = new CopyOnWriteArraySet<>();
		
		try {
			PreparedStatement statement = connection.prepareStatement("select * from flats");
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String flatName = resultSet.getString("flatName");
				
				float x = resultSet.getFloat("x");
				Double y = resultSet.getDouble("y");
				Coordinates coordinates = new Coordinates(x, y);
				
				LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
				int area = resultSet.getInt("area");
				int numberOfRooms = resultSet.getInt("numberOfRooms");
				Integer height = resultSet.getInt("height");
				Boolean isNew = resultSet.getBoolean("isNew");
				Transport transport = Transport.valueOf(resultSet.getString("transport"));
				
				String houseName = resultSet.getString("houseName");
				Integer year = resultSet.getInt("year");
				Long numberOfFloors = resultSet.getLong("numberOfFloors");
				long numberOfLifts = resultSet.getLong("numberOfLifts");
				House house = new House(houseName, year, numberOfFloors, numberOfLifts);
				
				Flat flat = new Flat(id, flatName, coordinates, creationDate, area, numberOfRooms, height, isNew, transport, house);
				
				collection.add(flat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return collection;
	}
}
