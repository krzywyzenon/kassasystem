package net.cirillo.pos;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class AddItemForm extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField itemNameField;
	private JTextField ItemPriceField;

	public AddItemForm(int x, int y) {

		setTitle("Lägg till ny artikel");
		setSize(new Dimension(400, 130));
		setLocation(x-(getWidth()/2), y-(getHeight()/2));
		setAlwaysOnTop(true);

		JLabel lblArtikelnamn = new JLabel("Artikelnamn");

		itemNameField = new JTextField();
		itemNameField.setColumns(10);

		ItemPriceField = new JTextField();
		ItemPriceField.setColumns(10);

		JLabel lblPris = new JLabel("Pris");

		JButton btnSpara = new JButton("Spara");
		btnSpara.setActionCommand("save");
		btnSpara.addActionListener(this);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(itemNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblArtikelnamn))
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblPris)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(ItemPriceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnSpara)))
												.addContainerGap(13, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblArtikelnamn)
								.addComponent(lblPris))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(itemNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(ItemPriceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSpara))
										.addContainerGap(208, Short.MAX_VALUE))
				);
		getContentPane().setLayout(groupLayout);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "save":
			Items items = new Items();
			items.addItem(itemNameField.getText(), Integer.parseInt(ItemPriceField.getText()));
			this.dispose();
			break;
		}
	}
}
