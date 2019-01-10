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
package com.kor.admiralty.ui.panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import com.kor.admiralty.beans.Admiral;
import com.kor.admiralty.enums.PlayerFaction;
import com.kor.admiralty.enums.ShipPriority;

import static com.kor.admiralty.ui.resources.Strings.AdmiralPanel.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.SwingConstants;

public class AdmiralPanel2 extends JPanel implements AdmiralUI {

	private static final long serialVersionUID = 6385797348923426101L;

	protected Admiral admiral;
	protected List<AdmiralUI> admiralUIs;
	protected JTextField txtName;
	protected JComboBox<PlayerFaction> cbxFaction;
	protected JComboBox<ShipPriority> cbxShipPriority;
	protected AdjustmentListener adjustmentListener = new AdjustmentListener() {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			if (e.getAdjustmentType() == AdjustmentEvent.TRACK) {
				e.getAdjustable().setBlockIncrement(76);
			}
		}

	};

	/**
	 * Create the panel.
	 */
	public AdmiralPanel2() {
		admiralUIs = new ArrayList<AdmiralUI>();
		setLayout(new BorderLayout(5, 5));

		JPanel pnlAdmiral = new JPanel();
		pnlAdmiral.setBorder(null);
		add(pnlAdmiral, BorderLayout.NORTH);
		GridBagLayout gbl_pnlAdmiral = new GridBagLayout();
		gbl_pnlAdmiral.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_pnlAdmiral.rowHeights = new int[] { 0 };
		gbl_pnlAdmiral.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0 };
		gbl_pnlAdmiral.rowWeights = new double[] { 0.0 };
		pnlAdmiral.setLayout(gbl_pnlAdmiral);

		JLabel lblName = new JLabel(LabelName);
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.weightx = 1.0;
		gbc_lblName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblName.insets = new Insets(5, 5, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		pnlAdmiral.add(lblName, gbc_lblName);

		txtName = new JTextField();
		txtName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				admiral.setName(txtName.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				admiral.setName(txtName.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				admiral.setName(txtName.getText());
			}
		});
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.weightx = 100.0;
		gbc_txtName.anchor = GridBagConstraints.WEST;
		gbc_txtName.insets = new Insets(5, 0, 5, 5);
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 0;
		pnlAdmiral.add(txtName, gbc_txtName);
		txtName.setColumns(20);

		JLabel lblFaction = new JLabel(LabelFaction);
		lblFaction.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblFaction = new GridBagConstraints();
		gbc_lblFaction.weightx = 1.0;
		gbc_lblFaction.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFaction.insets = new Insets(5, 5, 5, 5);
		gbc_lblFaction.gridx = 2;
		gbc_lblFaction.gridy = 0;
		pnlAdmiral.add(lblFaction, gbc_lblFaction);

		if (Beans.isDesignTime()) {
			cbxFaction = new JComboBox<PlayerFaction>();
		} else {
			cbxFaction = new JComboBox<PlayerFaction>(PlayerFaction.values());
			cbxFaction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PlayerFaction faction = (PlayerFaction) cbxFaction.getSelectedItem();
					admiral.setFaction(faction);
				}
			});
		}
		GridBagConstraints gbc_cbxFaction = new GridBagConstraints();
		gbc_cbxFaction.weightx = 10.0;
		gbc_cbxFaction.anchor = GridBagConstraints.WEST;
		gbc_cbxFaction.insets = new Insets(5, 5, 5, 5);
		gbc_cbxFaction.gridx = 3;
		gbc_cbxFaction.gridy = 0;
		pnlAdmiral.add(cbxFaction, gbc_cbxFaction);
		
		JLabel lblShipPriority = new JLabel(LabelShipPriority);
		lblShipPriority.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblShipPriority = new GridBagConstraints();
		gbc_lblShipPriority.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblShipPriority.weightx = 1.0;
		gbc_lblFaction.weightx = 1.0;
		gbc_lblFaction.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFaction.insets = new Insets(5, 5, 5, 5);
		gbc_lblFaction.gridx = 4;
		gbc_lblFaction.gridy = 0;
		pnlAdmiral.add(lblShipPriority, gbc_lblShipPriority);

		if (Beans.isDesignTime()) {
			cbxShipPriority = new JComboBox<ShipPriority>();
		} else {
			cbxShipPriority = new JComboBox<ShipPriority>(ShipPriority.values());
			cbxShipPriority.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ShipPriority priority = (ShipPriority) cbxShipPriority.getSelectedItem();
					admiral.setPrioritizeActive(priority == ShipPriority.Active);
				}
			});
		}
		GridBagConstraints gbc_btnShipPriority = new GridBagConstraints();
		gbc_btnShipPriority.anchor = GridBagConstraints.WEST;
		gbc_btnShipPriority.weightx = 10.0;
		gbc_cbxFaction.weightx = 10.0;
		gbc_cbxFaction.anchor = GridBagConstraints.WEST;
		gbc_cbxFaction.insets = new Insets(5, 5, 5, 5);
		gbc_cbxFaction.gridx = 5;
		gbc_cbxFaction.gridy = 0;
		pnlAdmiral.add(cbxShipPriority, gbc_btnShipPriority);

		JTabbedPane tabAdmiral = new JTabbedPane(JTabbedPane.TOP);
		add(tabAdmiral, BorderLayout.CENTER);

		ShipRosterPanel pnlPrimaryShips = new ShipRosterPanel(null);
		tabAdmiral.addTab(TabPrimary, null, pnlPrimaryShips, null);
		admiralUIs.add(pnlPrimaryShips);

		OneTimeShipPanel pnlOneTime = new OneTimeShipPanel(null);
		tabAdmiral.addTab(LabelOneTimeShips, null, pnlOneTime, null);
		admiralUIs.add(pnlOneTime);
		
		AssignmentSelectionPanel pnlAssignments = new AssignmentSelectionPanel();
		tabAdmiral.addTab(TabAssignments, null, pnlAssignments, null);
		admiralUIs.add(pnlAssignments);

		StarshipTraitsPanel pnlStarshipTraits = new StarshipTraitsPanel(null);
		tabAdmiral.addTab("Starship Traits", null, pnlStarshipTraits, null);
		admiralUIs.add(pnlStarshipTraits);
	}

	public AdmiralPanel2(Admiral admiral) {
		this();
		setAdmiral(admiral);
	}

	public Admiral getAdmiral() {
		return admiral;
	}

	public void setAdmiral(Admiral admiral) {
		if (this.admiral != null) {
			this.admiral.removePropertyChangeListener(this);
		}
		this.admiral = admiral;
		if (this.admiral != null) {
			txtName.setText(admiral.getName());
			cbxFaction.setSelectedItem(admiral.getFaction());
			cbxShipPriority.setSelectedItem(admiral.getPrioritizeActive() ? ShipPriority.Active : ShipPriority.OneTime);
			admiral.addPropertyChangeListener(this);
		}
	}

	public int getAssignmentCount() {
		return admiral.getAssignmentCount();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String property = e.getPropertyName();
		if (property == Admiral.PROP_NAME) {
			// txtName.setText(admiral.getName());
		} else if (property == Admiral.PROP_FACTION) {
			cbxFaction.setSelectedItem(admiral.getFaction());
		}

	}

}
