package server.database;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import server.persistence.RelationalDBGameManagerDAO;
import server.persistence.RelationalDBUserAccountsDAO;


public class Database
{
	private static final String DATABASE_DIRECTORY = "db";
	private static final String DATABASE_FILE = "database.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:"
			+ DATABASE_DIRECTORY + File.separator + DATABASE_FILE;
	private static Logger logger;
	private static final String CREATE_TABLE_USER="CREATE TABLE IF NOT EXISTS User("+ 
												"id integer not null primary key,"+
												"username text not null,"+
												"password text not null)";
	private static final String CREATE_TABLE_GAMES="CREATE TABLE IF NOT EXISTS Games(" +
												"id integer not null primary key,"+
												"gamemodel text not null)";
	private static final String CREATE_TABLE_PLAYERS="CREATE TABLE IF NOT EXISTS Players("+
	        									"userid integer not null,"+
	        									"gameid integer not null,"+
	        									"color text not null,"+
	        									"foreign key(userid) references User(id),"+
	        									"foreign key(gameid) references Games(id))";
	private static final String CREATE_TABLE_COMMANDS="CREATE TABLE IF NOT EXISTS Commands(" +
	        											"gameid integer not null,"+
	        											"command text not null," + 
	    	        									"foreign key(gameid) references Games(id))";
	
	
	static
	{
		logger = Logger.getLogger("Catan");
	}
	
	public static void initialize() throws DatabaseException
	{
		try
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		} catch (ClassNotFoundException e)
		{
			
			DatabaseException serverEx = new DatabaseException(
					"Could not load database driver", e);
			
			logger.throwing("server.database.Database", "initialize", serverEx);
			
			throw serverEx;
		}
	}
	
//	private RelationalDBGameManagerDAO gameManagerDAO;  
//	private RelationalDBUserAccountsDAO UserAccountsDAO;
	
	private Connection connection;
	
	public Database()
	{
//		gameManagerDAO = new RelationalDBGameManagerDAO(this);  
//		UserAccountsDAO = new RelationalDBUserAccountsDAO(this);
		connection = null;
	}
	
	public Connection getConnection()
	{
		if(connection == null)
			System.out.println("DEDD");
		return connection;
	}
	
	public void createTables(){
		Statement stmt = null;
		try {
			
			stmt = DriverManager.getConnection(DATABASE_URL).createStatement();
			stmt.executeUpdate(CREATE_TABLE_USER);
			stmt = DriverManager.getConnection(DATABASE_URL).createStatement();
			stmt.executeUpdate(CREATE_TABLE_GAMES);
			stmt = DriverManager.getConnection(DATABASE_URL).createStatement();
			stmt.executeUpdate(CREATE_TABLE_PLAYERS);
			stmt = DriverManager.getConnection(DATABASE_URL).createStatement();
			stmt.executeUpdate(CREATE_TABLE_COMMANDS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startTransaction() throws DatabaseException
	{
		try
		{
			// assert (connection == null);
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Could not connect to database. Make sure " + DATABASE_FILE
							+ " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}
	
	public void endTransaction(boolean commit)
	{
		if (connection != null)
		{
			try
			{
				if (commit)
				{
					connection.commit();
				} else
				{
					connection.rollback();
				}
			} catch (SQLException e)
			{
				System.out.println("Could not end transaction");
				e.printStackTrace();
			} finally
			{
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (SQLException e)
			{
				// ...
			}
		}
	}
	
	public static void safeClose(Statement stmt)
	{
		if (stmt != null)
		{
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				// ...
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt)
	{
		if (stmt != null)
		{
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			} catch (SQLException e)
			{
				// ...
			}
		}
	}
	
	/*CREATE TABLE Model
	(
		blob model not null
	)

	CREATE TABLE Games
	(
	        id integer not null primary key,
		blob gamemodel not null,
		foreign key(gamemodel) references Model(model)
	)

	CREATE TABLE Players
	(
	        userid integer not null,
	        gameid integer not null,
		color text not null,
		foreign key(userid) references User(id),
		foreign key(gameid) references Games(id)
	)

	CREATE TABLE User
	(
		id integer not null primary key,
		username text not null,
		password text not null
		//foreign key(currentBatch) references Batch(id)
	)*/
}
