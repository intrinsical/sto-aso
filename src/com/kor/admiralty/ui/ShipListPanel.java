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
package com.kor.admiralty.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.PlayerFaction;
import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.enums.ShipFaction;
import com.kor.admiralty.enums.ShipSortOrder;
import com.kor.admiralty.enums.Tier;
import com.kor.admiralty.io.Datastore;
import com.kor.admiralty.ui.models.ShipListModel;
import com.kor.admiralty.ui.renderers.ShipCellRenderer;
import com.kor.admiralty.ui.resources.Swing;
import static com.kor.admiralty.ui.resources.Strings.ShipSelectionPanel.*;

import javax.swing.JLabel;

import org.jdesktop.swingx.JXTaskPane;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.Action;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;

public class ShipListPanel extends JPanel {

	private static final long serialVersionUID = -8979655049435634369L;

	protected static final String OKAY_CANCEL[] = new String[] { LabelOkay, LabelCancel };

	protected ShipListModel shipListModel = new ShipListModel();
	protected ListCellRenderer<Ship> shipCellRenderer = ShipCellRenderer.cellRenderer();
	protected JList<Ship> lstShips;
	protected JCheckBox chckbxFederation;
	protected JCheckBox chckbxKlingon;
	protected JCheckBox chckbxRomulan;
	protected JCheckBox chckbxJemHadar;
	protected JCheckBox chckbxUniversal;
	protected JCheckBox chckbxSmallCraft;
	protected JCheckBox chckbxTier1;
	protected JCheckBox chckbxTier2;
	protected JCheckBox chckbxTier3;
	protected JCheckBox chckbxTier4;
	protected JCheckBox chckbxTier5;
	protected JCheckBox chckbxTier6;
	private final Action actionFederation = new FederationAction(shipListModel);
	private final Action actionKlingon = new KlingonAction(shipListModel);
	private final Action actionRomulan = new RomulanAction(shipListModel);
	private final Action actionJemHadar = new JemHadarAction(shipListModel);
	private final Action actionUniversal = new UniversalAction(shipListModel);
	private final Action actionEngineering = new EngineeringAction(shipListModel);
	private final Action actionTactical = new TacticalAction(shipListModel);
	private final Action actionScience = new ScienceAction(shipListModel);
	private final Action actionTier1 = new Tier1Action(shipListModel);
	private final Action actionTier2 = new Tier2Action(shipListModel);
	private final Action actionTier3 = new Tier3Action(shipListModel);
	private final Action actionTier4 = new Tier4Action(shipListModel);
	private final Action actionTier5 = new Tier5Action(shipListModel);
	private final Action actionTier6 = new Tier6Action(shipListModel);
	private final Action actionCommon = new CommonAction(shipListModel);
	private final Action actionUncommon = new UncommonAction(shipListModel);
	private final Action actionRare = new RareAction(shipListModel);
	private final Action actionVeryRare = new VeryRareAction(shipListModel);
	private final Action actionUltraRare = new UltraRareAction(shipListModel);
	private final Action actionEpic = new EpicAction(shipListModel);
	private final Action actionSmallCraft = new SmallCraftAction(shipListModel);

	/**
	 * Create the panel.
	 */
	public ShipListPanel() {
		setLayout(new BorderLayout(0, 0));

		JXTaskPane taskPane = new JXTaskPane(LabelFilter);
		taskPane.setCollapsed(true);
		add(taskPane, BorderLayout.NORTH);
		taskPane.setTitle(TitleFilter);
		JPanel pnlFilter = new JPanel();
		taskPane.getContentPane().add(pnlFilter);
		GridBagLayout gbl_pnlFilter = new GridBagLayout();
		gbl_pnlFilter.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_pnlFilter.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlFilter.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlFilter.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlFilter.setLayout(gbl_pnlFilter);

		JLabel lblFaction = new JLabel(LabelFaction);
		Swing.setFont(lblFaction, Font.BOLD, 12);
		GridBagConstraints gbc_lblFaction = new GridBagConstraints();
		gbc_lblFaction.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFaction.weightx = 1.0;
		gbc_lblFaction.insets = new Insets(5, 5, 5, 5);
		gbc_lblFaction.gridx = 0;
		gbc_lblFaction.gridy = 0;
		pnlFilter.add(lblFaction, gbc_lblFaction);

		JLabel lblRole = new JLabel(LabelRole);
		Swing.setFont(lblRole, Font.BOLD, 12);
		GridBagConstraints gbc_lblRole = new GridBagConstraints();
		gbc_lblRole.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRole.weightx = 1.0;
		gbc_lblRole.insets = new Insets(5, 5, 5, 5);
		gbc_lblRole.gridx = 1;
		gbc_lblRole.gridy = 0;
		pnlFilter.add(lblRole, gbc_lblRole);

		JLabel lblTier = new JLabel(LabelTier);
		Swing.setFont(lblTier, Font.BOLD, 12);
		GridBagConstraints gbc_lblTier = new GridBagConstraints();
		gbc_lblTier.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTier.weightx = 1.0;
		gbc_lblTier.insets = new Insets(5, 5, 5, 5);
		gbc_lblTier.gridx = 2;
		gbc_lblTier.gridy = 0;
		pnlFilter.add(lblTier, gbc_lblTier);

		JLabel lblRarity = new JLabel(LabelRarity);
		Swing.setFont(lblRarity, Font.BOLD, 12);
		GridBagConstraints gbc_lblRarity = new GridBagConstraints();
		gbc_lblRarity.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRarity.weightx = 1.0;
		gbc_lblRarity.insets = new Insets(5, 5, 5, 0);
		gbc_lblRarity.gridx = 3;
		gbc_lblRarity.gridy = 0;
		pnlFilter.add(lblRarity, gbc_lblRarity);

		chckbxFederation = new JCheckBox(actionFederation);
		chckbxFederation.setSelected(true);
		GridBagConstraints gbc_chckbxFederation = new GridBagConstraints();
		gbc_chckbxFederation.weightx = 1.0;
		gbc_chckbxFederation.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxFederation.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxFederation.gridx = 0;
		gbc_chckbxFederation.gridy = 1;
		pnlFilter.add(chckbxFederation, gbc_chckbxFederation);

		chckbxKlingon = new JCheckBox(actionKlingon);
		chckbxKlingon.setSelected(true);
		GridBagConstraints gbc_chckbxKlingon = new GridBagConstraints();
		gbc_chckbxKlingon.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxKlingon.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxKlingon.gridx = 0;
		gbc_chckbxKlingon.gridy = 2;
		pnlFilter.add(chckbxKlingon, gbc_chckbxKlingon);

		chckbxRomulan = new JCheckBox(actionRomulan);
		chckbxRomulan.setSelected(true);
		GridBagConstraints gbc_chckbxRomulan = new GridBagConstraints();
		gbc_chckbxRomulan.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxRomulan.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxRomulan.gridx = 0;
		gbc_chckbxRomulan.gridy = 3;
		pnlFilter.add(chckbxRomulan, gbc_chckbxRomulan);

		chckbxJemHadar = new JCheckBox(actionJemHadar);
		chckbxJemHadar.setSelected(true);
		GridBagConstraints gbc_chckbxJemHadar = new GridBagConstraints();
		gbc_chckbxJemHadar.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxJemHadar.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxJemHadar.gridx = 0;
		gbc_chckbxJemHadar.gridy = 4;
		pnlFilter.add(chckbxJemHadar, gbc_chckbxJemHadar);

		chckbxUniversal = new JCheckBox(actionUniversal);
		chckbxUniversal.setSelected(true);
		GridBagConstraints gbc_chckbxUniversal = new GridBagConstraints();
		gbc_chckbxUniversal.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxUniversal.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxUniversal.gridx = 0;
		gbc_chckbxUniversal.gridy = 5;
		pnlFilter.add(chckbxUniversal, gbc_chckbxUniversal);

		JCheckBox chckbxEngineering = new JCheckBox(actionEngineering);
		chckbxEngineering.setSelected(true);
		GridBagConstraints gbc_chckbxEngineering = new GridBagConstraints();
		gbc_chckbxEngineering.weightx = 1.0;
		gbc_chckbxEngineering.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxEngineering.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxEngineering.gridx = 1;
		gbc_chckbxEngineering.gridy = 1;
		pnlFilter.add(chckbxEngineering, gbc_chckbxEngineering);

		JCheckBox chckbxTactical = new JCheckBox(actionTactical);
		chckbxTactical.setSelected(true);
		GridBagConstraints gbc_chckbxTactical = new GridBagConstraints();
		gbc_chckbxTactical.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxTactical.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTactical.gridx = 1;
		gbc_chckbxTactical.gridy = 2;
		pnlFilter.add(chckbxTactical, gbc_chckbxTactical);

		JCheckBox chckbxScience = new JCheckBox(actionScience);
		chckbxScience.setSelected(true);
		GridBagConstraints gbc_chckbxScience = new GridBagConstraints();
		gbc_chckbxScience.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxScience.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxScience.gridx = 1;
		gbc_chckbxScience.gridy = 3;
		pnlFilter.add(chckbxScience, gbc_chckbxScience);

		chckbxSmallCraft = new JCheckBox(actionSmallCraft);
		chckbxSmallCraft.setSelected(true);
		GridBagConstraints gbc_chckbxSmallCraft = new GridBagConstraints();
		gbc_chckbxSmallCraft.weightx = 1.0;
		gbc_chckbxSmallCraft.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxSmallCraft.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSmallCraft.gridx = 2;
		gbc_chckbxSmallCraft.gridy = 1;
		pnlFilter.add(chckbxSmallCraft, gbc_chckbxSmallCraft);

		chckbxTier1 = new JCheckBox(actionTier1);
		chckbxTier1.setSelected(true);
		GridBagConstraints gbc_chckbxTier = new GridBagConstraints();
		gbc_chckbxTier.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxTier.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTier.gridx = 2;
		gbc_chckbxTier.gridy = 2;
		pnlFilter.add(chckbxTier1, gbc_chckbxTier);

		chckbxTier2 = new JCheckBox(actionTier2);
		chckbxTier2.setSelected(true);
		GridBagConstraints gbc_chckbxTier_1 = new GridBagConstraints();
		gbc_chckbxTier_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxTier_1.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTier_1.gridx = 2;
		gbc_chckbxTier_1.gridy = 3;
		pnlFilter.add(chckbxTier2, gbc_chckbxTier_1);

		chckbxTier3 = new JCheckBox(actionTier3);
		chckbxTier3.setSelected(true);
		GridBagConstraints gbc_chckbxTier_2 = new GridBagConstraints();
		gbc_chckbxTier_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxTier_2.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTier_2.gridx = 2;
		gbc_chckbxTier_2.gridy = 4;
		pnlFilter.add(chckbxTier3, gbc_chckbxTier_2);

		chckbxTier4 = new JCheckBox(actionTier4);
		chckbxTier4.setSelected(true);
		GridBagConstraints gbc_chckbxTier_3 = new GridBagConstraints();
		gbc_chckbxTier_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxTier_3.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTier_3.gridx = 2;
		gbc_chckbxTier_3.gridy = 5;
		pnlFilter.add(chckbxTier4, gbc_chckbxTier_3);

		chckbxTier5 = new JCheckBox(actionTier5);
		chckbxTier5.setSelected(true);
		GridBagConstraints gbc_chckbxTier_4 = new GridBagConstraints();
		gbc_chckbxTier_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxTier_4.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTier_4.gridx = 2;
		gbc_chckbxTier_4.gridy = 6;
		pnlFilter.add(chckbxTier5, gbc_chckbxTier_4);

		chckbxTier6 = new JCheckBox(actionTier6);
		chckbxTier6.setSelected(true);
		GridBagConstraints gbc_chckbxTier_5 = new GridBagConstraints();
		gbc_chckbxTier_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxTier_5.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxTier_5.gridx = 2;
		gbc_chckbxTier_5.gridy = 7;
		pnlFilter.add(chckbxTier6, gbc_chckbxTier_5);

		JCheckBox chckbxCommon = new JCheckBox(actionCommon);
		chckbxCommon.setSelected(true);
		GridBagConstraints gbc_chckbxCommon = new GridBagConstraints();
		gbc_chckbxCommon.weightx = 1.0;
		gbc_chckbxCommon.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxCommon.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxCommon.gridx = 3;
		gbc_chckbxCommon.gridy = 1;
		pnlFilter.add(chckbxCommon, gbc_chckbxCommon);

		JCheckBox chckbxUncommon = new JCheckBox(actionUncommon);
		chckbxUncommon.setSelected(true);
		GridBagConstraints gbc_chckbxUncommon = new GridBagConstraints();
		gbc_chckbxUncommon.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxUncommon.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxUncommon.gridx = 3;
		gbc_chckbxUncommon.gridy = 2;
		pnlFilter.add(chckbxUncommon, gbc_chckbxUncommon);

		JCheckBox chckbxRare = new JCheckBox(actionRare);
		chckbxRare.setSelected(true);
		GridBagConstraints gbc_chckbxRare = new GridBagConstraints();
		gbc_chckbxRare.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxRare.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxRare.gridx = 3;
		gbc_chckbxRare.gridy = 3;
		pnlFilter.add(chckbxRare, gbc_chckbxRare);

		JCheckBox chckbxVeryRare = new JCheckBox(actionVeryRare);
		chckbxVeryRare.setSelected(true);
		GridBagConstraints gbc_chckbxVeryRare = new GridBagConstraints();
		gbc_chckbxVeryRare.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxVeryRare.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxVeryRare.gridx = 3;
		gbc_chckbxVeryRare.gridy = 4;
		pnlFilter.add(chckbxVeryRare, gbc_chckbxVeryRare);

		JCheckBox chckbxUltraRare = new JCheckBox(actionUltraRare);
		chckbxUltraRare.setSelected(true);
		GridBagConstraints gbc_chckbxUltraRare = new GridBagConstraints();
		gbc_chckbxUltraRare.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxUltraRare.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxUltraRare.gridx = 3;
		gbc_chckbxUltraRare.gridy = 5;
		pnlFilter.add(chckbxUltraRare, gbc_chckbxUltraRare);

		JCheckBox chckbxEpic = new JCheckBox(actionEpic);
		chckbxEpic.setSelected(true);
		GridBagConstraints gbc_chckbxEpic = new GridBagConstraints();
		gbc_chckbxEpic.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxEpic.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxEpic.gridx = 3;
		gbc_chckbxEpic.gridy = 6;
		pnlFilter.add(chckbxEpic, gbc_chckbxEpic);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (e.getAdjustmentType() == AdjustmentEvent.TRACK) {
					e.getAdjustable().setBlockIncrement(1);
				}
			}
		});
		add(scrollPane, BorderLayout.CENTER);
		lstShips = new JList<Ship>(shipListModel);
		lstShips.setCellRenderer(shipCellRenderer);
		scrollPane.setViewportView(lstShips);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension preferredSize = getPreferredSize();
		preferredSize.height = (int) (screenSize.height * 0.7f);
		setPreferredSize(preferredSize);
	}
	
	public void setCellRenderer(ListCellRenderer<Ship> renderer) {
		this.shipCellRenderer = renderer;
		lstShips.setCellRenderer(shipCellRenderer);
	}
	
	public void setShips(Collection<Ship> ships) {
		shipListModel.setShips(ships);
	}

	public List<Ship> getSelectedShips() {
		return lstShips.getSelectedValuesList();
	}
	
	public ShipSortOrder getShipSortOrder() {
		return shipListModel.getShipSortOrder();
	}
	
	public void setShipSortOrder(ShipSortOrder sortOrder) {
		shipListModel.setShipSortOrder(sortOrder);
	}
	
	public void addListSelectionListener(ListSelectionListener listener) {
		lstShips.addListSelectionListener(listener);
	}
	
	public void setTier6Only() {
		shipListModel.setShowSmallCraft(false);
		shipListModel.setShowTier1(false);
		shipListModel.setShowTier2(false);
		shipListModel.setShowTier3(false);
		shipListModel.setShowTier4(false);
		shipListModel.setShowTier5(false);
		shipListModel.setShowTier6(true);
		chckbxSmallCraft.setSelected(false);
		chckbxTier1.setSelected(false);
		chckbxTier2.setSelected(false);
		chckbxTier3.setSelected(false);
		chckbxTier4.setSelected(false);
		chckbxTier5.setSelected(false);
		chckbxTier6.setSelected(true);
	}

	public void setFederationPlayer() {
		shipListModel.setShowFederation(true);
		shipListModel.setShowKlingon(false);
		shipListModel.setShowRomulan(false);
		shipListModel.setShowJemHadar(true);
		shipListModel.setShowUniversal(true);
		chckbxFederation.setSelected(true);
		chckbxKlingon.setSelected(false);
		chckbxRomulan.setSelected(false);
		chckbxJemHadar.setSelected(true);
		chckbxUniversal.setSelected(true);
	}

	public void setKlingonPlayer() {
		shipListModel.setShowFederation(false);
		shipListModel.setShowKlingon(true);
		shipListModel.setShowRomulan(false);
		shipListModel.setShowJemHadar(true);
		shipListModel.setShowUniversal(true);
		chckbxFederation.setSelected(false);
		chckbxKlingon.setSelected(true);
		chckbxRomulan.setSelected(false);
		chckbxJemHadar.setSelected(true);
		chckbxUniversal.setSelected(true);
	}

	public void setRomulanFederationPlayer() {
		shipListModel.setShowFederation(true);
		shipListModel.setShowKlingon(false);
		shipListModel.setShowRomulan(true);
		shipListModel.setShowJemHadar(true);
		shipListModel.setShowUniversal(true);
		chckbxFederation.setSelected(true);
		chckbxKlingon.setSelected(false);
		chckbxRomulan.setSelected(true);
		chckbxJemHadar.setSelected(true);
		chckbxUniversal.setSelected(true);
	}

	public void setRomulanKlingonPlayer() {
		shipListModel.setShowFederation(false);
		shipListModel.setShowKlingon(true);
		shipListModel.setShowRomulan(true);
		shipListModel.setShowJemHadar(true);
		shipListModel.setShowUniversal(true);
		chckbxFederation.setSelected(false);
		chckbxKlingon.setSelected(true);
		chckbxRomulan.setSelected(true);
		chckbxJemHadar.setSelected(true);
		chckbxUniversal.setSelected(true);
	}
	
	public void setJemHadarFederationPlayer() {
		shipListModel.setShowFederation(true);
		shipListModel.setShowKlingon(false);
		shipListModel.setShowRomulan(false);
		shipListModel.setShowJemHadar(true);
		shipListModel.setShowUniversal(true);
		chckbxFederation.setSelected(true);
		chckbxKlingon.setSelected(false);
		chckbxRomulan.setSelected(false);
		chckbxJemHadar.setSelected(true);
		chckbxUniversal.setSelected(true);
	}

	public void setJemHadarKlingonPlayer() {
		shipListModel.setShowFederation(false);
		shipListModel.setShowKlingon(true);
		shipListModel.setShowRomulan(false);
		shipListModel.setShowJemHadar(true);
		shipListModel.setShowUniversal(true);
		chckbxFederation.setSelected(false);
		chckbxKlingon.setSelected(true);
		chckbxRomulan.setSelected(false);
		chckbxJemHadar.setSelected(true);
		chckbxUniversal.setSelected(true);
	}

	private abstract class FilterAction extends AbstractAction {
		private static final long serialVersionUID = 4411461033232137866L;
		protected ShipListModel model;

		public FilterAction(ShipListModel model, String label) {
			this.model = model;
			putValue(NAME, label);
			putValue(SHORT_DESCRIPTION, label);
		}

		protected boolean isSelected(ActionEvent e) {
			return ((JCheckBox) e.getSource()).isSelected();
		}
	}

	private class FederationAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		FederationAction(ShipListModel model) {
			super(model, ShipFaction.Federation.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowFederation(isSelected(e));
		}
	}

	private class KlingonAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		KlingonAction(ShipListModel model) {
			super(model, ShipFaction.Klingon.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowKlingon(isSelected(e));
		}
	}

	private class RomulanAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		RomulanAction(ShipListModel model) {
			super(model, ShipFaction.Romulan.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowRomulan(isSelected(e));
		}
	}

	private class JemHadarAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		JemHadarAction(ShipListModel model) {
			super(model, ShipFaction.JemHadar.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowJemHadar(isSelected(e));
		}
	}

	private class UniversalAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		UniversalAction(ShipListModel model) {
			super(model, ShipFaction.Universal.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowUniversal(isSelected(e));
		}
	}

	private class EngineeringAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		EngineeringAction(ShipListModel model) {
			super(model, LabelEngineering);
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowEngineering(isSelected(e));
		}
	}

	private class TacticalAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		TacticalAction(ShipListModel model) {
			super(model, LabelTactical);
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowTactical(isSelected(e));
		}
	}

	private class ScienceAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		ScienceAction(ShipListModel model) {
			super(model, LabelScience);
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowScience(isSelected(e));
		}
	}

	private class SmallCraftAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		SmallCraftAction(ShipListModel model) {
			super(model, LabelSmallCraft);
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowSmallCraft(isSelected(e));
		}
	}

	private class Tier1Action extends FilterAction {
		private static final long serialVersionUID = 1L;

		Tier1Action(ShipListModel model) {
			super(model, Tier.Tier1.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowTier1(isSelected(e));
		}
	}

	private class Tier2Action extends FilterAction {
		private static final long serialVersionUID = 1L;

		Tier2Action(ShipListModel model) {
			super(model, Tier.Tier2.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowTier2(isSelected(e));
		}
	}

	private class Tier3Action extends FilterAction {
		private static final long serialVersionUID = 1L;

		Tier3Action(ShipListModel model) {
			super(model, Tier.Tier3.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowTier3(isSelected(e));
		}
	}

	private class Tier4Action extends FilterAction {
		private static final long serialVersionUID = 1L;

		Tier4Action(ShipListModel model) {
			super(model, Tier.Tier4.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowTier4(isSelected(e));
		}
	}

	private class Tier5Action extends FilterAction {
		private static final long serialVersionUID = 1L;

		Tier5Action(ShipListModel model) {
			super(model, Tier.Tier5.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowTier5(isSelected(e));
		}
	}

	private class Tier6Action extends FilterAction {
		private static final long serialVersionUID = 1L;

		Tier6Action(ShipListModel model) {
			super(model, Tier.Tier6.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowTier6(isSelected(e));
		}
	}

	private class CommonAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		CommonAction(ShipListModel model) {
			super(model, Rarity.Common.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowCommon(isSelected(e));
		}
	}

	private class UncommonAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		UncommonAction(ShipListModel model) {
			super(model, Rarity.Uncommon.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowUncommon(isSelected(e));
		}
	}

	private class RareAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		RareAction(ShipListModel model) {
			super(model, Rarity.Rare.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowRare(isSelected(e));
		}
	}

	private class VeryRareAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		VeryRareAction(ShipListModel model) {
			super(model, Rarity.VeryRare.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowVeryRare(isSelected(e));
		}
	}

	private class UltraRareAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		UltraRareAction(ShipListModel model) {
			super(model, Rarity.UltraRare.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowUltraRare(isSelected(e));
		}
	}

	private class EpicAction extends FilterAction {
		private static final long serialVersionUID = 1L;

		EpicAction(ShipListModel model) {
			super(model, Rarity.Epic.toString());
		}

		public void actionPerformed(ActionEvent e) {
			model.setShowEpic(isSelected(e));
		}
	}
	
	public static List<Ship> dialogAddOneTimeShips(Container container, PlayerFaction faction, String title) {
		ShipListPanel panel = new ShipListPanel();
		panel.setShips(new TreeSet<Ship>(Datastore.getAllShips().values()));
		panel.setTier6Only();
		switch (faction) {
		case Federation:
			panel.setFederationPlayer();
			break;
		case Klingon:
			panel.setKlingonPlayer();
			break;
		case RomulanFed:
			panel.setRomulanFederationPlayer();
			break;
		case RomulanKDF:
			panel.setRomulanKlingonPlayer();
			break;
		case JemHadarFed:
			panel.setJemHadarFederationPlayer();
			break;
		case JemHadarKDF:
			panel.setJemHadarKlingonPlayer();
			break;
		default:
		}
		return dialog(container, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
	}

	public static List<Ship> dialogActiveShips(Container container, PlayerFaction faction, Collection<Ship> ships, String title) {
		ShipListPanel panel = new ShipListPanel();
		panel.setShips(ships);
		switch (faction) {
		case Federation:
			panel.setFederationPlayer();
			break;
		case Klingon:
			panel.setKlingonPlayer();
			break;
		case RomulanFed:
			panel.setRomulanFederationPlayer();
			break;
		case RomulanKDF:
			panel.setRomulanKlingonPlayer();
			break;
		case JemHadarFed:
			panel.setJemHadarFederationPlayer();
			break;
		case JemHadarKDF:
			panel.setJemHadarKlingonPlayer();
			break;
		default:
		}
		return dialog(container, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
	}

	public static List<Ship> dialog(Container container, Collection<Ship> ships, String title) {
		ShipListPanel panel = new ShipListPanel();
		panel.setShips(ships);
		return dialog(container, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
	}

	protected static List<Ship> dialog(Container container, ShipListPanel panel, String title, int optionType,
			int messageType, Icon icon) {
		int result = JOptionPane.showOptionDialog(container, panel, title, optionType, messageType, icon, OKAY_CANCEL, LabelOkay);
		if (result != 0) {
			return Collections.emptyList();
		}
		return panel.getSelectedShips();
	}

}