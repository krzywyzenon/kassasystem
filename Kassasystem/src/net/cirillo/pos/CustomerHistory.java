package net.cirillo.pos;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class CustomerHistory extends JFrame {
 private static final long serialVersionUID = 1L;
 // Declare database variables
 private MysqlDataSource ds;
 private Connection con = null;
 private Statement stmt = null;
 private ResultSet rs = null;
 private ResultSetMetaData md = null;

 private String[] columnNames = { "Transaktions-ID", "Säljare", "Produkt",
   "Antal" };

 // Declare table variables
 private DefaultTableModel dtm;
 private JTable itemTable;
 private JScrollPane scrollPane;
 // Declare vectors for tablemodel
 @SuppressWarnings("rawtypes")
 Vector data;
 @SuppressWarnings("rawtypes")
 Vector columns;
 @SuppressWarnings("rawtypes")
 Vector row;

 public CustomerHistory(int x, int y, int customerid, String customerName) {
  // Set JFrame parameters
  setTitle("Köphistorik - " + customerName);
  setSize(new Dimension(400, 500));
  setLocation(x - (getWidth() / 2), y - (getHeight() / 2));
  setAlwaysOnTop(true);
  // Create new jpanel for input componets and add to contentpane
  JPanel panel = new JPanel();
  getContentPane().add(panel, BorderLayout.NORTH);
  panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
  // Create new scrollpane for jtable
  scrollPane = new JScrollPane();
  getContentPane().add(scrollPane, BorderLayout.CENTER);
  // Create new Mysql datasource
  ds = new MysqlDataSource();
  // Mysql connection parameters
  ds.setServerName("localhost");
  ds.setPort(3306);
  ds.setUser(MysqlConnection.getUser());
  ds.setPassword(MysqlConnection.getPassword());
  ds.setDatabaseName("kassa");
  try {
   // Set connetion and statement
   con = ds.getConnection();
   stmt = con.createStatement();
   // Set Recordset and metadata
   rs = stmt
     .executeQuery("SELECT transactions.transactionid, staff.name, item.name, transactions.amountsold "
       + "FROM customer "
       + "INNER JOIN transactions on customer.customerid = transactions.customerid "
       + "LEFT JOIN item on item.itemid = transactions.itemid "
       + "RIGHT JOIN staff on staff.staffid = transactions.staffid "
       + "WHERE customer.customerid = " + customerid + "");

   md = rs.getMetaData();
   // Get number of columns
   int columnCount = md.getColumnCount();
   // Create new columns vector
   columns = new Vector(columnCount);
   // store column names
   for (int i = 0; i < columnCount; i++)
    columns.add(columnNames[i]);

   data = new Vector();
   // store row data
   while (rs.next()) {
    row = new Vector(columnCount);
    for (int i = 1; i <= columnCount; i++) {
     row.add(rs.getString(i));
    }
    data.add(row);
   }
   // Catch execption
  } catch (SQLException sqle) {
   System.out.println("SQL Error: " + sqle.getMessage());
  }
  // Create new table and tablemodell and add to scrollpane
  dtm = new DefaultTableModel(data, columns);
  itemTable = new JTable(dtm) {
   public boolean isCellEditable(int rowIndex, int colIndex) {
           return false;
   }
  };
  itemTable.setBackground(Color.white);
  scrollPane.setViewportView(itemTable);
  itemTable.addMouseListener(new MouseAdapter() {
   @Override
   public void mouseClicked(MouseEvent event) {
      int trow=itemTable.rowAtPoint(event.getPoint());
      int tcol= itemTable.columnAtPoint(event.getPoint());
      //int transID = Integer.parseInt(String.valueOf(itemTable.getValueAt(0, trow)));
      System.out.println(Integer.parseInt(String.valueOf(itemTable.getValueAt(trow, 0))));
      TransactionInformation transInfo = new TransactionInformation(getLocation().x,getLocation().y,Integer.parseInt(String.valueOf(itemTable.getValueAt(trow, 0))));
      transInfo.setVisible(true);
   }
  });
 }

}