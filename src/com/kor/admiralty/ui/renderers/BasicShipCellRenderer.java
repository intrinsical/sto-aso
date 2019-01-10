/*******************************************************************************
 * Copyright (C) 2015, 2019 Dave Kor
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.kor.admiralty.ui.renderers;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.ui.resources.Swing;
import com.kor.admiralty.ui.resources.Images;

import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

public abstract class BasicShipCellRenderer extends JPanel implements ListCellRenderer<Ship> {

	private static final long serialVersionUID = 3185543403004009734L;
	
	private JLabel lblIcon;
	private JLabel lblName;
	
	public BasicShipCellRenderer() {
		setBorder(Swing.BorderDefault);
		setBackground(Swing.ColorBackground);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Dimension dim64 = new Dimension(64, 64);
		
		lblIcon = new JLabel();
		lblIcon.setPreferredSize(dim64);
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblIcon.gridheight = 3;
		gbc_lblIcon.insets = new Insets(5, 5, 0, 5);
		gbc_lblIcon.gridx = 0;
		gbc_lblIcon.gridy = 0;
		add(lblIcon, gbc_lblIcon);
		
		lblName = new JLabel("Ship Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblName.weightx = 10.0;
		gbc_lblName.insets = new Insets(5, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);
	}
	
	public void setShip(Ship ship) {
		getListCellRendererComponent(null, ship, 0, true, false);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Ship> list, Ship ship, int index, boolean isSelected, boolean cellHasFocus) {
		if (ship == null) {
			lblIcon.setIcon(Images.ICON_BLANK);
			lblName.setText("No Ship");
			lblName.setForeground(Rarity.Common.getColor());
		}
		else {
			lblIcon.setIcon(Images.getIcon(ship.getIconName(), ship.getFaction(), ship.getRole(), ship.getRarity(), ship.isOwned()));
			lblName.setText(ship.getDisplayName());
			lblName.setForeground(ship.getRarity().getColor());
		}
		if (isSelected) {
			setBorder(Swing.BorderHighlighted);
			setBackground(Swing.ColorBackgroundHighlighted);
		}
		else {
			setBorder(Swing.BorderDefault);
			setBackground(Swing.ColorBackground);
		}
		return this;
	}

}
