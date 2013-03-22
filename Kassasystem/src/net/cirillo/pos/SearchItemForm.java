package net.cirillo.pos;
 
import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
 
public class SearchItemForm extends JFrame implements ActionListener {
 
            private static final long serialVersionUID = 1L;
 
            // Declare database variables
            private MysqlDataSource ds;
            private Connection con = null;
            private Statement stmt = null;
            private ResultSet rs = null;
            private ResultSetMetaData md = null;
           
            // Declare table variables
            private DefaultTableModel dtm;
            private JTable itemTable;
            private JScrollPane scrollPane;
           
            // Declare input components
            private JTextField searchField;
            private JButton btnSearch;
 
            // Declare vectors for tablemodel
            @SuppressWarnings("rawtypes")
            Vector data;
            @SuppressWarnings("rawtypes")
            Vector columns;
            @SuppressWarnings("rawtypes")
            Vector row;
 
            public SearchItemForm(int x, int y) {
 
                         // Set JFrame parameters
                         setTitle("Sök artikel");
                         setSize(new Dimension(400, 500));
                         setLocation(x-(getWidth()/2), y-(getHeight()/2));
                         setAlwaysOnTop(true);
 
                         // Create new jpanel for input componets and add to contentpane
                         JPanel panel = new JPanel();
                         getContentPane().add(panel, BorderLayout.NORTH);
                         panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
 
                         // Create new input field and add to panel
                         searchField = new JTextField();
                         panel.add(searchField);
                         searchField.setColumns(10);
 
                         // Create new button for searchfield
                         btnSearch = new JButton("S\u00F6k");
                         btnSearch.setActionCommand("search");
                         btnSearch.addActionListener(this);
                         panel.add(btnSearch);
 
                         // Create new scrollpane for jtable
                         scrollPane = new JScrollPane();
                         getContentPane().add(scrollPane, BorderLayout.CENTER);
 
 
                         // Set default query to list all items
                         queryDB("SELECT * FROM item");
 
            }
            @SuppressWarnings({ "unchecked" })
            public void queryDB(String q) {
 
                         // Create new Mysql datasource
                         ds = new MysqlDataSource();
 
                         // Mysql connection parameters
                         ds.setServerName("localhost");
                         ds.setPort(3306);
                         ds.setUser("root");
                         ds.setPassword("mlodasuka");
                         ds.setDatabaseName("kassa");
 
                         try{
 
                                     // Set connetion and statement
                                     con = ds.getConnection();
                                     stmt = con.createStatement();
 
                                     // Set Recordset and metadata
                                     rs = stmt.executeQuery(q);
                                     md = rs.getMetaData();
 
                                     // Get number of columns
                                     int columnCount = md.getColumnCount();
 
                                     // Create new columns vector
                                     columns = new Vector(columnCount);
 
                                     //store column names
                                     for(int i=1; i<=columnCount; i++)
                                                  columns.add(md.getColumnName(i));
                                     data = new Vector();
 
                                     //store row data
                                     while(rs.next())
                                     {
                                                  row = new Vector(columnCount);
                                                  for(int i=1; i<=columnCount; i++)
                                                  {
                                                              row.add(rs.getString(i));
                                                  }
                                                  data.add(row);
                                     }
 
                                     // Catch execption
                         }catch(SQLException sqle){
                                     System.out.println("SQL Error: " + sqle.getMessage());
                         }
 
                         // Create new table and tablemodell and add to scrollpane
                         dtm = new DefaultTableModel(data, columns);
                         itemTable = new JTable(dtm);
                         itemTable.setBackground(Color.white);
                         scrollPane.setViewportView(itemTable);
            }
 
            public void updateTable() {
                         // Update table from user input field
                         queryDB("SELECT * FROM item WHERE name LIKE '%"+searchField.getText()+"%' OR itemid LIKE '%"+searchField.getText()+"%' ");
            }
 
            @Override
            public void actionPerformed(ActionEvent e) {
                         // Update table with user input when searchbutton is pressed
                         switch(e.getActionCommand()) {
                         case "search":
                                     updateTable();
                                     break;
                         }
 
            }
}