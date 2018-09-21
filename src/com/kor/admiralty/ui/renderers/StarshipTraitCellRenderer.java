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
import javax.swing.JTextPane;

import com.kor.admiralty.Globals;
import com.kor.admiralty.beans.Ship;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class StarshipTraitCellRenderer extends BasicShipCellRenderer {

	private static final long serialVersionUID = -8211496775487348321L;
	private static final CustomHTMLEditorKit HTML_KIT = new CustomHTMLEditorKit(Globals.STYLESHEET_TRAIT);
	
	protected JTextPane lblStarshipTrait;
	
	/**
	 * Create the panel.
	 */
	public StarshipTraitCellRenderer() {
		super();
		
		lblStarshipTrait = new JTextPane();
		lblStarshipTrait.setContentType("text/html");
		lblStarshipTrait.setEditorKit(HTML_KIT);
		lblStarshipTrait.setEditable(false);
		lblStarshipTrait.setOpaque(false);
		lblStarshipTrait.setBackground(new Color(0, 0, 0, 0));
		lblStarshipTrait.setDocument(HTML_KIT.createDefaultDocument());
		GridBagConstraints gbc_lblStarshipTrait = new GridBagConstraints();
		gbc_lblStarshipTrait.fill = GridBagConstraints.BOTH;
		gbc_lblStarshipTrait.anchor = GridBagConstraints.WEST;
		gbc_lblStarshipTrait.weightx = 10.0;
		gbc_lblStarshipTrait.gridheight = 2;
		gbc_lblStarshipTrait.insets = new Insets(0, 0, 0, 5);
		gbc_lblStarshipTrait.gridx = 1;
		gbc_lblStarshipTrait.gridy = 1;
		add(lblStarshipTrait, gbc_lblStarshipTrait);
		
		setShip(null);
	}
	
	public void setShip(Ship ship) {
		getListCellRendererComponent(null, ship, 0, true, false);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Ship> list, Ship ship, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, ship, index, isSelected, cellHasFocus);
		if (ship == null) {
			lblStarshipTrait.setText("");;
		}
		else {
			lblStarshipTrait.setText(ship.getTrait());
		}
		return this;
	}

}
