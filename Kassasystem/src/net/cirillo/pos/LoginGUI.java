package net.cirillo.pos;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginGUI {
	JFrame myFrame;
	MysqlConnection db = null;
	JTextField userName;
	JTextField password;
	

	LoginGUI(){
		myFrame = new JFrame("Kassa hanterare");
		myFrame.setVisible(true);
		myFrame.setSize(300, 300);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel userNameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		userName = new JTextField(20);
		password = new JTextField(20);
		JButton logIn = new JButton("Log in");
		
		myFrame.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		myFrame.add(userNameLabel, gc);
		gc.gridx = 1;
		gc.gridy = 0;
		myFrame.add(userName, gc);
		gc.gridx = 0;
		gc.gridy = 1;
		myFrame.add(passwordLabel, gc);
		gc.gridx = 1;
		gc.gridy = 1;
		myFrame.add(password, gc);
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 2;
		myFrame.add(logIn, gc);
		myFrame.pack();
		
		logIn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				db = new MysqlConnection();
				db.connect();
//				ResultSet res = db.selectStaff("select * from staff where username = '" + userName.getText() + "' and password = '" + password.getText() + "'");
				ResultSet res = db.selectStaff("select * from staff");
				try {
							
						while(res.next()){
							try {
								if(userName.getText().equals(res.getString("username")) && password.getText().equals(res.getString("password"))){
									System.out.println("Welcome " + res.getString("name"));
									if(res.getShort("admin") == 1 ){
										System.out.println("You are logged in as admin");
										MysqlConnection.setAdmin(true);
									}
									MysqlConnection.setLoggedUserID(res.getShort("staffid"));
									Gui gui = new Gui();
									gui.setVisible(true);
									myFrame.dispose();
									return;
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					
						System.out.println("login incorrect");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
	}
	
	
}
