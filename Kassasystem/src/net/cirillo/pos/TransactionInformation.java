package net.cirillo.pos;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TransactionInformation extends JFrame {
 
 private MysqlConnection con = new MysqlConnection();
 private JTextArea text = new JTextArea();

 public TransactionInformation(int x, int y, int transvestiteId) {
  setTitle("Transaction details");
  setSize(new Dimension(400, 500));
  setLocationRelativeTo(null);
  setAlwaysOnTop(true);
  
  con.connect();
  
  add(text);
  
  //Ska joinas med items och customer för att få ut textsträngar, kommer i senare version
  String query = "SELECT * FROM transactions WHERE transactionid='" + transvestiteId + "';"; 
  ResultSet rs = con.select(query); 

  try {
   rs.beforeFirst();
   while (rs.next()) { 
       String textLine = rs.getString("itemid"); 
       text.append(textLine + " ");
   }
  } catch (SQLException e1) {
   // TODO Auto-generated catch block
   e1.printStackTrace();
  } 

  try {
   rs.close();
  } catch (SQLException e1) {
   // TODO Auto-generated catch block
   e1.printStackTrace();
  } 
  
 }
}