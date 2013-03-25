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
			statusBar.setText(statusBar.getText() + " ADMIN ");
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
			MysqlConnection.setUser("kassa_user");
			MysqlConnection.setPassword("user");
//			db.getDs().setUser("kassa_user");
			db.getDs().setUser(MysqlConnection.getUser());
			db.getDs().setPassword(MysqlConnection.getPassword());
			db.connect();
			results = db.selectStaff("select name from staff where staffid = " + MysqlConnection.getLoggedUserID());
			try {
				results.next();
				statusBar.setText(results.getString("name"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
