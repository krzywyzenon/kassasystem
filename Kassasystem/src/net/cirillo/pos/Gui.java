package net.cirillo.pos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Gui extends JFrame implements ActionListener {

JLabel userNameLabel = new JLabel("<user>");
private static final long serialVersionUID = 1L;
private JTable dataInfo;
private JTextField searchCustomuer;
private MysqlConnection con = new MysqlConnection();
private JTextField totalToPayField;
private static ArrayList<CostItem> data = new ArrayList<CostItem>();
private static TableModel dataModel;
private JList<Object> customerList;
private CustomerHistory customerHistory;
private int customerId;
private String selectedItem;

public Gui() {
con.connect();
 
setTitle("Grupp 2 kassa");
setSize( new Dimension(673, 506));
setResizable(true);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

JMenuBar menuBar = new JMenuBar();
setJMenuBar(menuBar);

JMenu mnPos = new JMenu("Kassa");
menuBar.add(mnPos);

JMenuItem mntmLogin = new JMenuItem("Logga in som en administrat\u00F6r");
mntmLogin.setActionCommand("login");
mntmLogin.addActionListener(this);
mnPos.add(mntmLogin);

JMenuItem mntmAdLogout = new JMenuItem("Logga ut från administrat\u00F6r");
mntmAdLogout.setActionCommand("adLogout");
mntmAdLogout.addActionListener(this);
mnPos.add(mntmAdLogout);;

JSeparator separator = new JSeparator();
mnPos.add(separator);

JMenuItem mntmExit = new JMenuItem("Avsluta");
mnPos.add(mntmExit);

JMenu mnItems = new JMenu("Artiklar");
menuBar.add(mnItems);

JMenuItem mntmSearchItem = new JMenuItem("S\u00F6k artikel");
mntmSearchItem.setActionCommand("searchItem");
mntmSearchItem.addActionListener(this);
mnItems.add(mntmSearchItem);

JMenuItem mntmNewItem = new JMenuItem("L\u00E4gg till ny artikel");
mntmNewItem.setActionCommand("addItem");
mntmNewItem.addActionListener(this);
mnItems.add(mntmNewItem);

JMenuItem mntmRemoveItem = new JMenuItem("Ta bort artikel");
mnItems.add(mntmRemoveItem);

JMenu mnCustomers = new JMenu("Kunder");
menuBar.add(mnCustomers);

JMenuItem mntmSearchCustomer = new JMenuItem("S\u00F6k kund");
mnCustomers.add(mntmSearchCustomer);

JMenuItem mntmAddCustomer = new JMenuItem("L\u00E4gg till ny kund");
mnCustomers.add(mntmAddCustomer);

JMenuItem mntmRemoveCustomer = new JMenuItem("Ta bort kund");
mnCustomers.add(mntmRemoveCustomer);
 
//The model for how the table is going to look like
dataModel = new AbstractTableModel() {
 
String[] columnNames = {
"Item name",
                    "Cost",
                    "# of items",
                    "Delete"};
 
public int getColumnCount() { return columnNames.length; }
public int getRowCount() { return data.size();}
public String getColumnName(int col) { return columnNames[col]; }
public Object getValueAt(int row, int col) { 
return data.get(row).getColumn(col);
}

public Class<? extends Object> getColumnClass(int c) {
       return getValueAt(0, c).getClass(); }
 public boolean isCellEditable(int row, int col) {
       if (col == 2) {
           return true;
       } else {
           return false;
       }
   }
 public void setValueAt(Object value, int row, int col) {
 data.get(row).setValueAt(col, value);
 fireTableCellUpdated(row, col);
 }
};
 
getContentPane().setLayout(new BorderLayout(0, 0));
dataInfo = new JTable(dataModel);
dataInfo.setColumnSelectionAllowed(true);
JScrollPane tableScrollPane = new JScrollPane(dataInfo);
getContentPane().add(tableScrollPane, BorderLayout.CENTER);
 
JButton confirmButton = new JButton("Confirm transaction / Check out");
confirmButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent confirmClick) {
System.out.println(confirmClick);
for (int i = 0; i < dataInfo.getRowCount(); i++){
String nameOfItem = (String) dataInfo.getValueAt(i, 0);
int costOfItem = Integer.parseInt(String.valueOf(dataInfo.getValueAt(i, 1)));
int amountOfItems = Integer.parseInt(String.valueOf(dataInfo.getValueAt(i, 2)));
System.out.println(nameOfItem + " " + costOfItem + " " + amountOfItems);

}
 
}
});
getContentPane().add(confirmButton, BorderLayout.SOUTH);
 
dataInfo.addPropertyChangeListener(new PropertyChangeListener() {
public void propertyChange(PropertyChangeEvent arg0) {
 System.out.println("HEJsan");
}
});
dataInfo.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent event) {
 if (event.getClickCount() == 2) {
 System.out.println("HEJ");
 }
}
});
 
 
 
ArrayList<String> itemNames = new ArrayList<String>();
String query = "SELECT name FROM item ORDER BY name ASC"; 
ResultSet rs = con.select(query); 

try {
rs.beforeFirst();
while (rs.next()) { 
   String itemName = rs.getString("name"); 
   // add group names to the array list
   itemNames.add(itemName);
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
 
TableColumn itemColumnDeleteButton = dataInfo.getColumnModel().getColumn(3);
 
JLabel itemCost = new JLabel("Cost");
JPanel test = new JPanel();
test.setPreferredSize(new Dimension(250,600));
getContentPane().add(test, BorderLayout.EAST);
 
JLabel loggedInAsLabel = new JLabel("Logged in as:");
 
 
JSeparator separator_1 = new JSeparator();
 
//Creating the JList for customers and getting the data for it
customerList = new JList<Object>();
customerList.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent eventList) {
	if(!MysqlConnection.getUser().equals("kassa_user")){
		
		if (eventList.getClickCount() == 2) {
			selectedItem = (String) customerList.getSelectedValue();
			System.out.println(selectedItem + ": dubbleclick");
			con.connect();
			ResultSet qq = con.select("SELECT customerid FROM customer WHERE name='" + selectedItem + "';");
			try {
				qq.beforeFirst();
				System.out.println(con.getUser());
				while (qq.next()) { 
					customerId = qq.getInt("customerid"); 
					System.out.println(customerId);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			con.close();
			
			customerHistory = new CustomerHistory(getLocation().x+(getWidth()/2),getLocation().y+(getHeight()/2),customerId, selectedItem);
			customerHistory.setVisible(true);
		}
	}
}
});
try {
con.populateJList(customerList, "SELECT * FROM customer ORDER BY name ASC;", "name");
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

customerList.setBorder(new LineBorder(new Color(0, 0, 0)));
 
searchCustomuer = new JTextField();
searchCustomuer.setText("Search for customer");
searchCustomuer.setColumns(10);
 
JLabel totalToPay = new JLabel("Total to pay:");
 
totalToPayField = new JTextField();
totalToPayField.setEditable(false);
totalToPayField.setColumns(10);
 

 
GroupLayout gl_test = new GroupLayout(test);
gl_test.setHorizontalGroup(
gl_test.createParallelGroup(Alignment.LEADING)
.addGroup(gl_test.createSequentialGroup()
.addContainerGap()
.addGroup(gl_test.createParallelGroup(Alignment.LEADING)
.addGroup(gl_test.createSequentialGroup()
.addComponent(loggedInAsLabel)
.addPreferredGap(ComponentPlacement.RELATED)
.addComponent(createNameLabel(), GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
.addContainerGap())
.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
.addGroup(Alignment.TRAILING, gl_test.createSequentialGroup()
.addGroup(gl_test.createParallelGroup(Alignment.TRAILING)
.addComponent(searchCustomuer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
.addComponent(customerList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
.addGroup(gl_test.createSequentialGroup()
.addComponent(totalToPay)
.addPreferredGap(ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
.addComponent(totalToPayField, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))
.addGap(18))))
);
gl_test.setVerticalGroup(
gl_test.createParallelGroup(Alignment.LEADING)
.addGroup(gl_test.createSequentialGroup()
.addGroup(gl_test.createParallelGroup(Alignment.BASELINE)
.addComponent(loggedInAsLabel)
.addComponent(createNameLabel()))
.addPreferredGap(ComponentPlacement.RELATED)
.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
.addPreferredGap(ComponentPlacement.RELATED)
.addComponent(searchCustomuer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
.addPreferredGap(ComponentPlacement.RELATED)
.addComponent(customerList, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
.addPreferredGap(ComponentPlacement.RELATED)
.addGroup(gl_test.createParallelGroup(Alignment.BASELINE)
.addComponent(totalToPay)
.addComponent(totalToPayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
.addContainerGap())
);
test.setLayout(gl_test);
 
JLabel itemAmount = new JLabel("Amount");
 
addItem("apa", 23, 4);
addItem("Dennis", 45000, 3);
addItem("Skräp", 1, 2);
 
//Summarizing the total cost of everything that should be payed for
int costAdded = 0;
for (int i = 0; i < 3; i++) {
costAdded += Integer.parseInt(String.valueOf(dataInfo.getValueAt(i, 1)));
}
String valueOfAll = String.valueOf(costAdded); 
totalToPayField.setText(valueOfAll);
}
//Add a row to the JTable in the mainwindow
public static void addItem(String itemName, int itemCost, int itemAmount) {
data.add(new CostItem(itemName, itemCost * itemAmount, itemAmount));
}
public void deleteItem() {
 
}
public void openNewItemForm() {
AddItemForm addItemForm = new AddItemForm(getLocation().x+(getWidth()/2),getLocation().y+(getHeight()/2));
addItemForm.setVisible(true);
}

public void openSearchItemForm() {
SearchItemForm searchItemForm = new SearchItemForm(getLocation().x+(getWidth()/2),getLocation().y+(getHeight()/2));
searchItemForm.setVisible(true);
}

public void adminLoginForm(){
	AdminStuff adminHandler = new AdminStuff(con, userNameLabel);
	adminHandler.adminAuthCheck();
}
public void adminLogout(){
	AdminStuff adminHandler = new AdminStuff(con, userNameLabel);
	adminHandler.adminLogout();
}

public JLabel createNameLabel(){
	con = new MysqlConnection();
	con.connect();
	ResultSet result = con.selectStaff("select * from staff where staffid = '" + MysqlConnection.getLoggedUserID() + "'");
	try {
		result.next();
		if(MysqlConnection.getAdmin()){
			userNameLabel.setText(result.getString("name"));
		}else{
			userNameLabel.setText(result.getString("name"));
		}
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	con.close();
	return userNameLabel;
	
}
 
@Override
public void actionPerformed(ActionEvent e) {
switch(e.getActionCommand()) {
case "addItem":
openNewItemForm();
break;
case "searchItem":
openSearchItemForm();
break;
case "login":
	adminLoginForm();
	break;
case "adLogout":
	adminLogout();
	break;
}

}
}
