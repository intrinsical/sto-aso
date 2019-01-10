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
package com.kor.admiralty.ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.SwingConstants;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.enums.Tier;
import com.kor.admiralty.ui.resources.Images;
import com.kor.admiralty.ui.resources.Swing;
import static com.kor.admiralty.ui.resources.Strings.Empty;
import static com.kor.admiralty.ui.resources.Strings.ShipDetailsPanel.*;

import java.awt.FlowLayout;

public class ShipDetailsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6921895817840896986L;

	protected JLabel lblIcon;
	protected JLabel lblShipName;
	protected JLabel lblTier;
	protected JLabel lblRarity;
	protected JLabel lblEng;
	protected JLabel lblTac;
	protected JLabel lblSci;
	protected JLabel lblSpecialAbility;
	protected JLabel lblMaintenance;
	private JPanel pnlStats;

	/**
	 * Create the panel.
	 */
	public ShipDetailsPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		setLayout(gridBagLayout);

		lblIcon = new JLabel(Images.ICON_BLANK);
		Dimension iconSize = new Dimension(64, 64);
		lblIcon.setPreferredSize(iconSize);
		lblIcon.setMaximumSize(iconSize);
		lblIcon.setMinimumSize(iconSize);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridheight = 5;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		add(lblIcon, gbc_btnNewButton);

		lblShipName = new JLabel(DefaultShipName);
		Swing.setFont(lblShipName, Font.BOLD, 12);
		GridBagConstraints gbc_lblShipName = new GridBagConstraints();
		gbc_lblShipName.weighty = 1.0;
		gbc_lblShipName.weightx = 3.0;
		gbc_lblShipName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblShipName.insets = new Insets(0, 5, 5, 0);
		gbc_lblShipName.gridx = 1;
		gbc_lblShipName.gridy = 0;
		add(lblShipName, gbc_lblShipName);

		lblMaintenance = new JLabel(Tier.Tier1.getMaintenanceTime());
		GridBagConstraints gbc_lblMaintenance = new GridBagConstraints();
		gbc_lblMaintenance.weighty = 1.0;
		gbc_lblMaintenance.weightx = 1.0;
		gbc_lblMaintenance.gridwidth = 2;
		gbc_lblMaintenance.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMaintenance.insets = new Insets(0, 5, 5, 0);
		gbc_lblMaintenance.gridx = 0;
		gbc_lblMaintenance.gridy = 10;
		add(lblMaintenance, gbc_lblMaintenance);

		JLabel strMaintenance = new JLabel(LabelMaintenance);
		Swing.setFont(strMaintenance, Font.BOLD, 12);
		GridBagConstraints gbc_strMaintenance = new GridBagConstraints();
		gbc_strMaintenance.weighty = 1.0;
		gbc_strMaintenance.weightx = 1.0;
		gbc_strMaintenance.insets = new Insets(0, 5, 5, 0);
		gbc_strMaintenance.fill = GridBagConstraints.HORIZONTAL;
		gbc_strMaintenance.gridwidth = 2;
		gbc_strMaintenance.gridx = 0;
		gbc_strMaintenance.gridy = 9;
		add(strMaintenance, gbc_strMaintenance);

		lblSpecialAbility = new JLabel(DefaultSpecialAbility);
		GridBagConstraints gbc_lblSpecialAbility = new GridBagConstraints();
		gbc_lblSpecialAbility.weighty = 1.0;
		gbc_lblSpecialAbility.weightx = 1.0;
		gbc_lblSpecialAbility.insets = new Insets(0, 5, 5, 0);
		gbc_lblSpecialAbility.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSpecialAbility.gridwidth = 2;
		gbc_lblSpecialAbility.gridx = 0;
		gbc_lblSpecialAbility.gridy = 8;
		add(lblSpecialAbility, gbc_lblSpecialAbility);

		JLabel strTier = new JLabel(LabelTier);
		Swing.setFont(strTier, Font.BOLD, 12);
		strTier.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_strTier = new GridBagConstraints();
		gbc_strTier.weighty = 1.0;
		gbc_strTier.weightx = 3.0;
		gbc_strTier.fill = GridBagConstraints.HORIZONTAL;
		gbc_strTier.insets = new Insets(0, 5, 5, 0);
		gbc_strTier.gridx = 1;
		gbc_strTier.gridy = 1;
		add(strTier, gbc_strTier);

		lblTier = new JLabel(Tier.Tier1.toString());
		GridBagConstraints gbc_lblTier = new GridBagConstraints();
		gbc_lblTier.weighty = 1.0;
		gbc_lblTier.weightx = 3.0;
		gbc_lblTier.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTier.insets = new Insets(0, 5, 5, 0);
		gbc_lblTier.gridx = 1;
		gbc_lblTier.gridy = 2;
		add(lblTier, gbc_lblTier);

		JLabel strRarity = new JLabel(LabelRarity);
		Swing.setFont(strRarity, Font.BOLD, 12);
		GridBagConstraints gbc_strRarity = new GridBagConstraints();
		gbc_strRarity.weighty = 1.0;
		gbc_strRarity.weightx = 3.0;
		gbc_strRarity.insets = new Insets(0, 5, 5, 0);
		gbc_strRarity.fill = GridBagConstraints.HORIZONTAL;
		gbc_strRarity.gridx = 1;
		gbc_strRarity.gridy = 3;
		add(strRarity, gbc_strRarity);

		lblRarity = new JLabel(Rarity.Common.toString());
		GridBagConstraints gbc_lblRarity = new GridBagConstraints();
		gbc_lblRarity.weighty = 1.0;
		gbc_lblRarity.weightx = 3.0;
		gbc_lblRarity.insets = new Insets(0, 5, 5, 0);
		gbc_lblRarity.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRarity.gridx = 1;
		gbc_lblRarity.gridy = 4;
		add(lblRarity, gbc_lblRarity);

		JLabel strStats = new JLabel(LabelShipStats);
		Swing.setFont(strStats, Font.BOLD, 12);
		GridBagConstraints gbc_strStats = new GridBagConstraints();
		gbc_strStats.weighty = 1.0;
		gbc_strStats.weightx = 1.0;
		gbc_strStats.insets = new Insets(0, 5, 5, 0);
		gbc_strStats.gridwidth = 2;
		gbc_strStats.fill = GridBagConstraints.HORIZONTAL;
		gbc_strStats.gridx = 0;
		gbc_strStats.gridy = 5;
		add(strStats, gbc_strStats);

		JLabel strSpecialAbility = new JLabel(LabelSpecialAbility);
		Swing.setFont(strSpecialAbility, Font.BOLD, 12);
		GridBagConstraints gbc_strSpecialAbility = new GridBagConstraints();
		gbc_strSpecialAbility.weighty = 1.0;
		gbc_strSpecialAbility.weightx = 1.0;
		gbc_strSpecialAbility.insets = new Insets(0, 5, 5, 0);
		gbc_strSpecialAbility.gridwidth = 2;
		gbc_strSpecialAbility.fill = GridBagConstraints.HORIZONTAL;
		gbc_strSpecialAbility.gridx = 0;
		gbc_strSpecialAbility.gridy = 7;
		add(strSpecialAbility, gbc_strSpecialAbility);

		JPanel pnlEmpty = new JPanel();
		GridBagConstraints gbc_pnlEmpty = new GridBagConstraints();
		gbc_pnlEmpty.insets = new Insets(0, 0, 5, 0);
		gbc_pnlEmpty.weighty = 1000.0;
		gbc_pnlEmpty.weightx = 1.0;
		gbc_pnlEmpty.gridwidth = 2;
		gbc_pnlEmpty.fill = GridBagConstraints.BOTH;
		gbc_pnlEmpty.gridx = 0;
		gbc_pnlEmpty.gridy = 11;
		add(pnlEmpty, gbc_pnlEmpty);
		
		pnlStats = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlStats.getLayout();
		flowLayout.setHgap(20);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnlStats.setBorder(null);
		GridBagConstraints gbc_pnlStats = new GridBagConstraints();
		gbc_pnlStats.insets = new Insets(0, 5, 5, 0);
		gbc_pnlStats.gridwidth = 2;
		gbc_pnlStats.fill = GridBagConstraints.BOTH;
		gbc_pnlStats.gridx = 0;
		gbc_pnlStats.gridy = 6;
		add(pnlStats, gbc_pnlStats);
		
		lblEng = new JLabel(Empty + 0, Images.ICON_ENG, JLabel.LEFT);
		pnlStats.add(lblEng);
		
		lblTac = new JLabel(Empty + 0, Images.ICON_TAC, JLabel.LEFT);
		pnlStats.add(lblTac);
		
		lblSci = new JLabel(Empty + 0, Images.ICON_SCI, JLabel.LEFT);
		pnlStats.add(lblSci);

	}

	public void setShip(Ship ship) {
		if (ship == null) {
			lblIcon.setIcon(Images.ICON_BLANK);
			lblShipName.setText(Empty);
			lblTier.setText(Empty);
			lblRarity.setText(Empty);
			lblEng.setText(Empty + 0);
			lblTac.setText(Empty + 0);
			lblSci.setText(Empty + 0);
			lblSpecialAbility.setText(Empty);
			lblMaintenance.setText(Empty);
		} else {
			lblIcon.setIcon(Images.getIcon(ship.getIconName(), ship.getFaction(), ship.getRole(), ship.getRarity(), ship.isOwned()));
			lblShipName.setText(ship.getName());
			lblTier.setText(ship.getTier().toString());
			lblRarity.setText(ship.getRarity().toString());
			lblEng.setText(Empty + ship.getEng());
			lblTac.setText(Empty + ship.getTac());
			lblSci.setText(Empty + ship.getSci());
			lblSpecialAbility.setText(ship.getSpecialAbility().toString());
			lblMaintenance.setText(ship.getTier().getMaintenanceTime());
		}
	}

}
