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
package com.kor.admiralty.ui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.kor.admiralty.beans.Admiral;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.ui.components.JColumnList;
import com.kor.admiralty.ui.components.JListComponentAdapter;
import com.kor.admiralty.ui.models.ShipListModel;
import com.kor.admiralty.ui.renderers.StarshipTraitCellRenderer;

import java.awt.GridBagLayout;
import javax.swing.ScrollPaneConstants;

public class StarshipTraitsPanel extends JPanel implements AdmiralUI {

	private static final long serialVersionUID = -8042884852436619063L;
	
	protected Admiral admiral;
	protected ShipListModel uiModel;
	protected JList<Ship> uiList;
	
	public StarshipTraitsPanel(Admiral admiral) {
		this();
		setAdmiral(admiral);
	}

	/**
	 * Create the panel.
	 */
	protected StarshipTraitsPanel() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_panel);
		
		JLabel label = new JLabel("Unlockable Starship Traits:");
		label.setForeground(Color.BLACK);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.weightx = 1.0;
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(5, 5, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 5, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		uiModel = new ShipListModel();
		uiList = new JColumnList<Ship>(uiModel);
		uiList.setLayoutOrientation(JList.VERTICAL);
		uiList.setCellRenderer(new StarshipTraitCellRenderer());
		scrollPane.setViewportView(uiList);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		addComponentListener(new JListComponentAdapter<Ship>(uiList));
	}
	
	@Override
	public Admiral getAdmiral() {
		return admiral;
	}
	
	@Override
	public void setAdmiral(Admiral admiral) {
		if (this.admiral != null) {
			this.admiral.removePropertyChangeListener(this);
		}
		this.admiral = admiral;
		if (this.admiral != null) {
			uiModel.addShips(admiral.getStarshipTraits());
			admiral.addPropertyChangeListener(this);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String property = e.getPropertyName();
		if (property == Admiral.PROP_ACTIVE) {
			uiModel.setShips(admiral.getStarshipTraits());
		}
	}

}
