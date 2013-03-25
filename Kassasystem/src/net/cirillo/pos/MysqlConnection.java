package net.cirillo.pos;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MysqlConnection {
	//Tomasz changes begin
	private static Boolean isAdmin = false;
	private static int loggedUserID;
	private static Boolean adminPasswordSet = false;
	private static String adminPw;
	private static String passwordRequired = "Please type in admin password";
	private JPasswordField passField = new JPasswordField(10);
	private static String user;
	private static String password;
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
		dbLogin();

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

			
		} catch (SQLException e) {
			System.err.println("SQL Error: " + e.getMessage());
		}
		
		return results;
	}
	//Tomasz method ends

	public void populateJList(JList list, String query, String column) throws SQLException
	{
	   DefaultListModel model = new DefaultListModel(); //create a new list model

	   Statement statement = conn.createStatement();
	   ResultSet resultSet = statement.executeQuery(query); //run your query

	   while (resultSet.next()) //go through each row that your query returns
	   {
	      String itemCode = resultSet.getString(column); //get the element in column
	       model.addElement(itemCode); //add each item to the model
	   }
	   list.setModel(model);

	   resultSet.close();
	   statement.close();

	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Unable to close mysql connection");
		}
		System.out.println("\nConnection closed");
	}
	
	//Tomasz logging to database
	public void dbLogin(){
			
			if(isAdmin == true && adminPasswordSet == true){
			
				setUser("kassa_admin");
				setPassword(adminPw);
				ds.setUser(user);
				ds.setPassword(password);
		}else 
		{
//			ds.setUser("kassa_user");
//			ds.setPassword("user");
			setUser("kassa_user");
			setPassword("user");
			ds.setUser(user);
			ds.setPassword(password);
		}
	}
	
	public void adminLogin(){
		setUser("kassa_admin");
		ds.setUser(user);
//		ds.setUser("kassa_admin");
		adminPw =JOptionPane.showInputDialog(null, passwordRequired, "Admin Login Tool", JOptionPane.WARNING_MESSAGE);
		if(adminPw == null){
			setUser("kassa_user");
			ds.setUser(user);
//			ds.setUser("kassa_user");
			setPassword("user");
//			ds.setPassword("user");
			ds.setPassword(password);
			MysqlConnection.setAdmin(false);
		}
		else{
			setPassword(adminPw);
			ds.setPassword(password);
			try {
				ds.getConnection();
				passwordRequired = "Please type in admin password";
				adminPasswordSet = true;
				isAdmin = true;
			} catch (SQLException e) {
				passwordRequired = "Incorrect. Please try again.";
				MysqlConnection.setAdmin(false);
				MysqlConnection.resetAdminPw();
				setPassword("user");
				adminLogin();
//				e.printStackTrace();
			}
		}
		
	}
	
	
	
	//Tomasz getters and setters
	public static void setAdmin(boolean admin){
		isAdmin = admin;
	}
	
	public static void resetAdminPasswordCheck(){
		adminPasswordSet = false;
	}
	
	public static void resetAdminPw(){
		adminPw = null;
	}
	
	public static void setLoggedUserID(int userId){
		loggedUserID = userId;
	}
	
	public static void setUser(String s){
		user = s;
	}
	
	public static void setPassword(String s){
		password = s;
	}
	
	public static String getPassword(){
		return password;
	}
	
	public static boolean getAdmin(){
		return isAdmin;
	}
	
	public static String getUser(){
		return user;
	}
	
	public static int getLoggedUserID(){
		return loggedUserID;
	}
	
	public static boolean getAdminPw(){
		return adminPasswordSet;
		
	}
	
	public static void resetPwReq(){
		passwordRequired = "Please type in admin password";
	}
	
	public MysqlDataSource getDs(){
		return ds;
		
	}

}
