/*******************************************************************************
 * Copyright (C) 2015, 2018 Dave Kor
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

import com.kor.admiralty.beans.Ship;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class UsageCountCellRenderer extends ShipCellRenderer {

	private static final long serialVersionUID = 8576217946824150570L;
	protected JLabel lblUsageCount;
	
	/**
	 * Create the panel.
	 */
	public UsageCountCellRenderer() {
		super();
		Dimension dim64 = new Dimension(64, 64);
		lblUsageCount = new JLabel("0");
		lblUsageCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsageCount.setForeground(Color.WHITE);
		lblUsageCount.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsageCount.setPreferredSize(dim64);
		lblUsageCount.setText(String.format("%,d", 0));
		GridBagConstraints gbc_lblUsageCount = new GridBagConstraints();
		gbc_lblUsageCount.anchor = GridBagConstraints.EAST;
		gbc_lblUsageCount.gridheight = 3;
		gbc_lblUsageCount.insets = new Insets(5, 0, 5, 5);
		gbc_lblUsageCount.gridx = 1;
		gbc_lblUsageCount.gridy = 0;
		add(lblUsageCount, gbc_lblUsageCount);
	}
	
	public void setShip(Ship ship) {
		getListCellRendererComponent(null, ship, 0, true, false);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Ship> list, Ship ship, int index, boolean isSelected, boolean cellHasFocus) {
		if (lblUsageCount == null) return this;
		super.getListCellRendererComponent(list, ship, index, isSelected, cellHasFocus);
		if (ship == null) {
			lblUsageCount.setText(String.format("%,d", 0));
		}
		else {
			lblUsageCount.setText(String.format("%,d", ship.getUsageCount()));
		}
		return this;
	}

}
