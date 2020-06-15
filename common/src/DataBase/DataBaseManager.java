package DataBase;

import DataBase.CredentialsGetter.CredentialsGetter;
import DataBase.UrlGetter.UrlGetter;
import Input.Coordinates;
import Input.Flat;
import Input.House;
import Input.Transport;
import Session.SessionServerClient;
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
			statement.executeUpdate();
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
				String userName = resultSet.getString("userName");
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
				
				Flat flat = new Flat(id, userName, flatName, coordinates, creationDate, area, numberOfRooms, height, isNew, transport, house);
				
				collection.add(flat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return collection;
	}
	
	public long generateId() {
		try {
			PreparedStatement statement = connection.prepareStatement("select nextval('generate_id')");
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getLong("nextval");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean addFlat(Flat flat, SessionServerClient session) {
		try {
			flat.setUserName(session.getUser().getUsername());
			
			PreparedStatement statement = connection.prepareStatement("insert into flats values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, cast (? as transport), ?, ?, ?, ?)");
			
			long id = generateId();
			String userName = flat.getUserName();
			String flatName = flat.getFlatName();
			
			Coordinates coordinates = flat.getCoordinates();
			float x = coordinates.getX();
			Double y = coordinates.getY();
			
			LocalDate creationDate = flat.getCreationDate();
			int area = flat.getArea();
			int numberOfRooms = flat.getNumberOfRooms();
			Integer height = flat.getHeight();
			Boolean isNew = flat.getIsNew();
			Transport transport = flat.getTransport();
			
			House house = flat.getHouse();
			String houseName = house.getHouseName();
			Integer year = house.getYear();
			Long numberOfFloors = house.getNumberOfFloors();
			long numberOfLifts = house.getNumberOfLifts();
			
			statement.setLong(1, id);
			statement.setString(2, userName);
			statement.setString(3, flatName);
			statement.setFloat(4, x);
			statement.setDouble(5, y);
			statement.setDate(6, Date.valueOf(creationDate));
			statement.setInt(7, area);
			statement.setInt(8, numberOfRooms);
			statement.setInt(9, height);
			statement.setBoolean(10, isNew);
			statement.setObject(11, transport.toString());
			statement.setString(12, houseName);
			statement.setInt(13, year);
			statement.setLong(14, numberOfFloors);
			statement.setLong(15, numberOfLifts);
			
			flat.setId(id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int removeFlatById(long id) {
		try {
			PreparedStatement statement = connection.prepareStatement("delete from flats where id = ?");
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public boolean removeFlatsByUser(User user) {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE from flats where \"userName\"=?");
			statement.setString(1, user.getUsername());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateFlatById(long id, Flat flat) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"update flats set \"flatName\"=?, x=? , y=?, \"creationDate\"=?, area=?, \"numberOfRooms\"=?," +
							" height=?, \"isNew\"=?, transport=cast (? as transport), \"houseName\"=?, year=?," +
							" \"numberOfFloors\"=?, \"numberOfLifts\"=?" +
							" where id = ?");
			
			statement.setString(1, flat.getFlatName());
			statement.setFloat(2, flat.getX());
			statement.setDouble(3, flat.getY());
			statement.setDate(4, Date.valueOf(flat.getCreationDate()));
			statement.setInt(5, flat.getArea());
			statement.setInt(6, flat.getNumberOfRooms());
			statement.setInt(7, flat.getHeight());
			statement.setBoolean(8, flat.getIsNew());
			statement.setObject(9, flat.getTransport().toString());
			statement.setString(10, flat.getHouseName());
			statement.setInt(11, flat.getYear());
			statement.setLong(12, flat.getNumberOfFloors());
			statement.setLong(13, flat.getNumberOfLifts());
			statement.setLong(14, id);
			
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
