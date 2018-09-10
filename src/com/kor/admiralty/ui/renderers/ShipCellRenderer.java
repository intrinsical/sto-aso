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
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;

import com.kor.admiralty.Globals;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.ui.resources.Swing;
import com.kor.admiralty.ui.resources.Images;

import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class ShipCellRenderer extends JPanel implements ListCellRenderer<Ship> {

	private static final long serialVersionUID = 5072040648881085415L;
	private static ShipCellRenderer SINGLETON; 
	private static final CustomHTMLEditorKit HTML_KIT = new CustomHTMLEditorKit(Globals.STYLESHEET_TRAIT);
	
	public static ListCellRenderer<Ship> cellRenderer() {
		if (SINGLETON == null) {
			SINGLETON = new ShipCellRenderer();
		}
		return SINGLETON;
	}
	
	protected JPanel pnlStats;
	protected JLabel lblIcon;
	protected JLabel lblName;
	protected JLabel lblEng;
	protected JLabel lblTac;
	protected JLabel lblSci;
	protected JLabel lblAbility;
	protected JTextPane lblStarshipTrait;
	protected JLabel lblUsageCount;
	
	/**
	 * Create the panel.
	 */
	public ShipCellRenderer() {
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
		
		pnlStats = new JPanel();
		pnlStats.setBorder(null);
		pnlStats.setOpaque(false);
		pnlStats.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pnlStats.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblStats = new GridBagConstraints();
		gbc_lblStats.insets = new Insets(0, 0, 5, 5);
		gbc_lblStats.weightx = 10.0;
		gbc_lblStats.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStats.gridx = 1;
		gbc_lblStats.gridy = 1;
		add(pnlStats, gbc_lblStats);
		GridBagLayout gbl_pnlStats = new GridBagLayout();
		gbl_pnlStats.columnWidths = new int[]{28, 28, 28, 0};
		gbl_pnlStats.rowHeights = new int[]{21, 0};
		gbl_pnlStats.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlStats.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlStats.setLayout(gbl_pnlStats);
		
		lblEng = new JLabel("0", Images.ICON_ENG, JLabel.LEFT);
		lblEng.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblEng = new GridBagConstraints();
		gbc_lblEng.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEng.insets = new Insets(0, 0, 0, 15);
		gbc_lblEng.gridx = 0;
		gbc_lblEng.gridy = 0;
		pnlStats.add(lblEng, gbc_lblEng);
		
		lblTac = new JLabel("0", Images.ICON_TAC, JLabel.LEFT);
		lblTac.setForeground(Color.WHITE);
		lblTac.setIconTextGap(2);
		GridBagConstraints gbc_lblTac = new GridBagConstraints();
		gbc_lblTac.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTac.insets = new Insets(0, 0, 0, 20);
		gbc_lblTac.gridx = 1;
		gbc_lblTac.gridy = 0;
		pnlStats.add(lblTac, gbc_lblTac);
		
		lblSci = new JLabel("0", Images.ICON_SCI, JLabel.LEFT);
		lblSci.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblSci = new GridBagConstraints();
		gbc_lblSci.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSci.gridx = 2;
		gbc_lblSci.gridy = 0;
		pnlStats.add(lblSci, gbc_lblSci);
		
		lblAbility = new JLabel("Special Ability");
		lblAbility.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAbility.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblAbility = new GridBagConstraints();
		gbc_lblAbility.weightx = 10.0;
		gbc_lblAbility.insets = new Insets(0, 0, 0, 5);
		gbc_lblAbility.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAbility.gridx = 1;
		gbc_lblAbility.gridy = 2;
		add(lblAbility, gbc_lblAbility);
		
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
		
		lblUsageCount = new JLabel("0");
		lblUsageCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsageCount.setForeground(Color.WHITE);
		lblUsageCount.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsageCount.setPreferredSize(dim64);
		GridBagConstraints gbc_lblUsageCount = new GridBagConstraints();
		gbc_lblUsageCount.anchor = GridBagConstraints.EAST;
		gbc_lblUsageCount.gridheight = 3;
		gbc_lblUsageCount.insets = new Insets(5, 0, 5, 5);
		gbc_lblUsageCount.gridx = 1;
		gbc_lblUsageCount.gridy = 0;
		add(lblUsageCount, gbc_lblUsageCount);
		
		setShip(null);
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
			lblEng.setText("0");
			lblTac.setText("0");
			lblSci.setText("0");
			lblAbility.setText("");
			lblStarshipTrait.setText("");;
			lblStarshipTrait.setVisible(false);
			lblUsageCount.setText(String.format("%,d", 0));
			lblUsageCount.setVisible(false);
		}
		else {
			lblIcon.setIcon(Images.getIcon(ship.getIconName(), ship.getFaction(), ship.getRole(), ship.getRarity()));
			if (ship.isOneTime()) {
				lblName.setText("(1x) " + ship.getName());
			}
			else {
				lblName.setText(ship.getName());
			}
			boolean showTrait = ship.isShowStarshipTrait();
			pnlStats.setVisible(!showTrait);
			lblName.setForeground(ship.getRarity().getColor());
			lblEng.setText("" + ship.getEng());
			lblTac.setText("" + ship.getTac());
			lblSci.setText("" + ship.getSci());
			lblAbility.setText(ship.getSpecialAbility().toString());
			lblAbility.setVisible(!showTrait);
			lblStarshipTrait.setText(ship.getTrait());
			lblStarshipTrait.setVisible(showTrait);
			lblUsageCount.setText(String.format("%,d", ship.getUsageCount()));
			lblUsageCount.setVisible(ship.isShowUsageCount());
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
