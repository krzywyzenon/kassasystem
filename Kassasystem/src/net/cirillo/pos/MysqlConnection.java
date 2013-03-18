package net.cirillo.pos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MysqlConnection {
	//Tomasz changes begin
	static Boolean isAdmin = false;
	static int loggedUserID;
	//Tomasz changes end

	//Declare mysql datasource
	MysqlDataSource ds;

	//Declare connection;
	Connection conn;

	public MysqlConnection() {

		//Create new datasource
		ds = new MysqlDataSource();

		//Connection parameters
		ds.setServerName("localhost");
		ds.setDatabaseName("kassa");
		ds.setPort(3306);
		if(isAdmin == true){
			ds.setUser("kassa_admin");
			ds.setPassword("admin");
		}else
		{
			ds.setUser("kassa_user");
			ds.setPassword("user");
		}
			
			
	}

	public void connect() {
		//Set connection
		conn = null;

		//Try to connect to mysql server
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			System.out.println("Unable to connect to database: " + e.getMessage());
			return;
		}

		//Print message if connected
		System.out.println("Connected to database " + ds.getDatabaseName()+"@"+ds.getServerName());
	}


	public void update(String q) {
		Statement insertQuery = null;
		try {
			insertQuery = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Failed to create statement: " + e.getMessage());
			return;
		}

		try {
			insertQuery.executeUpdate(q);
		} catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			return;
		}
		System.out.println("Item added to database");
	}
	public void select(String q) {
		//Create a statement object for sending queries
				Statement query = null;
				try {
					query = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				} catch (SQLException e) {
					System.err.println("Create statement failed. " + e.getMessage());
					return;
				}

				//The result set will store the data returned by the query
				ResultSet results = null;
				try {
					results = query.executeQuery(q);
					//Print info from all rows
					while(results.next()){
						System.out.print(results.getString("itemid") + " ");
						System.out.print(results.getString("name") + " ");
						System.out.print(results.getString("price") + " ");
						System.out.print(results.getString("stockamount") + " ");
					}
					
				} catch (SQLException e) {
					System.err.println("SQL Error: " + e.getMessage());
				}
	}
	//Tomasz method
	public ResultSet selectStaff(String q) {
		//Create a statement object for sending queries
		Statement query = null;
		try {
			query = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.err.println("Create statement failed. " + e.getMessage());
		}
		
		//The result set will store the data returned by the query
		ResultSet results = null;
		try {
			results = query.executeQuery(q);
			//Print info from all rows
//			while(results.next()){
//				System.out.print(results.getString("staffid") + " ");
//				System.out.print(results.getString("name") + " ");
//				System.out.print(results.getString("username") + " ");
//				System.out.println(results.getString("password") + " ");
//			}
			
		} catch (SQLException e) {
			System.err.println("SQL Error: " + e.getMessage());
		}
		
		return results;
	}
	//Tomasz method ends

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Unable to close mysql connection");
		}
		System.out.println("\nConnection closed");
	}
	
	//Tomasz getters and setters
	public static void setAdmin(boolean admin){
		isAdmin = admin;
	}
	
	public static boolean getAdmin(){
		return isAdmin;
	}
	public static void setLoggedUserID(int userId){
		loggedUserID = userId;
	}
	
	public static int getLoggedUserID(){
		return loggedUserID;
	}

}
