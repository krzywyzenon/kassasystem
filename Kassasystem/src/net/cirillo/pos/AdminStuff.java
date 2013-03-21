package net.cirillo.pos;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AdminStuff {
	
	JLabel statusBar;
	MysqlConnection db;
	ResultSet results = null;
	
	public AdminStuff(MysqlConnection c, JLabel t){
		db = c;
		statusBar = t;
		
	}
	
	public void adminLogin(){
		MysqlConnection.setAdmin(true);
		MysqlConnection.resetPwReq();
		db.adminLogin();
		if(MysqlConnection.getAdmin()){
			statusBar.setText(statusBar.getText() + " You are logged in as admin ");
		}
	}
	
	public void adminAuthCheck(){
		db.connect();
		results = db.selectStaff("select admin from staff where staffid = " + MysqlConnection.getLoggedUserID());
		try {
			results.next();
			if(results.getShort("admin")==1){
				adminLogin();
			}else{
				JOptionPane.showMessageDialog(null, "You are not authorized for this operation", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
	}
	
	public void adminLogout(){
		if(MysqlConnection.getAdmin()){
			MysqlConnection.setAdmin(false);
			MysqlConnection.resetAdminPw();
			MysqlConnection.resetAdminPasswordCheck();
			JOptionPane.showMessageDialog(null, "You are no longer logged in as admin");
			db.getDs().setUser("kassa_user");
			db.getDs().setPassword("user");
			db.connect();
			results = db.selectStaff("select name from staff where staffid = " + MysqlConnection.getLoggedUserID());
			try {
				results.next();
				statusBar.setText("Welcome " + results.getString("name") + "!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
