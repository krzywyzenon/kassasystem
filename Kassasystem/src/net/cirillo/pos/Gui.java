package net.cirillo.pos;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class Gui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	public Gui() {
		setTitle("Grupp 2 kassa");
		setSize( new Dimension(673, 506));
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnPos = new JMenu("Kassa");
		menuBar.add(mnPos);

		JMenuItem mntmLogin = new JMenuItem("Logga in");
		mnPos.add(mntmLogin);

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
		
		System.out.println(MysqlConnection.getAdmin());
		System.out.println(MysqlConnection.getLoggedUserID());
	}

	public void openNewItemForm() {
		AddItemForm addItemForm = new AddItemForm(getLocation().x+(getWidth()/2),getLocation().y+(getHeight()/2));
		addItemForm.setVisible(true);
	}

	public void openSearchItemForm() {
		SearchItemForm searchItemForm = new SearchItemForm(getLocation().x+(getWidth()/2),getLocation().y+(getHeight()/2));
		searchItemForm.setVisible(true);
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
		}

	}

}
