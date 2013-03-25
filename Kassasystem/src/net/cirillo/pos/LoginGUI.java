package net.cirillo.pos;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI {
	JFrame myFrame;
	MysqlConnection db = null;
	JTextField userName;
	JPasswordField password;
	JLabel loginStatus;
	

	LoginGUI(){
		myFrame = new JFrame("Kassa hanterare");
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
		myFrame.setSize(300, 300);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel userNameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		userName = new JTextField(20);
		password = new JPasswordField(20);
		JButton logIn = new JButton("Log in");
		loginStatus = new JLabel();
		loginStatus.setForeground(Color.red);
		
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
		myFrame.add(logIn, gc);
		gc.gridx = 1;
		gc.gridy = 2;
		myFrame.add(loginStatus, gc);
		myFrame.pack();
		
		logIn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				db = new MysqlConnection();
				db.connect();
				ResultSet res = db.selectStaff("select * from staff");
				try {
							
						while(res.next()){
							try {
								if(userName.getText().equals(res.getString("username")) && password.getText().equals(res.getString("password"))){
									if(res.getShort("admin") == 1 ){
										MysqlConnection.setAdmin(true);
									}
									MysqlConnection.setLoggedUserID(res.getShort("staffid"));
									db.close();
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
						loginStatus.setText("Login incorrect!");
						userName.setText("");
						password.setText("");
						db.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
	}
	
	
}
