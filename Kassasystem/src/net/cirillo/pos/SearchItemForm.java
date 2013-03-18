package net.cirillo.pos;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class SearchItemForm extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField searchField;

	public SearchItemForm(int x, int y) {

		setTitle("Sök artikel");
		setSize(new Dimension(400, 500));
		setLocation(x-(getWidth()/2), y-(getHeight()/2));
		setAlwaysOnTop(true);

		JButton btnSearch = new JButton("S\u00F6k");
		btnSearch.setActionCommand("search");
		btnSearch.addActionListener(this);

		JLabel lblArtikelnrnamn = new JLabel("ArtikelNr,Namn");

		searchField = new JTextField();
		searchField.setColumns(10);

		JList list = new JList();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(list, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblArtikelnrnamn)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSearch)))
										.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblArtikelnrnamn)
								.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSearch))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(list, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
								.addContainerGap())
				);
		getContentPane().setLayout(groupLayout);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "search":
			Items items = new Items();
			items.SearchItem(searchField.getText());
			break;
		}
	}
}
