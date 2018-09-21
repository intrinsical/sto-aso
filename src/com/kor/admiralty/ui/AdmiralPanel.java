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
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;

import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.kor.admiralty.Globals;
import com.kor.admiralty.beans.Admiral;
import com.kor.admiralty.beans.Assignment;
import com.kor.admiralty.beans.AssignmentSolution;
import com.kor.admiralty.beans.CompositeSolution;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.PlayerFaction;
import com.kor.admiralty.enums.ShipPriority;
import com.kor.admiralty.io.Datastore;
import com.kor.admiralty.ui.components.JColumnList;
import com.kor.admiralty.ui.components.JListComponentAdapter;
import com.kor.admiralty.ui.models.ShipListModel;
import com.kor.admiralty.ui.renderers.ShipCellRenderer;
import com.kor.admiralty.ui.renderers.StarshipTraitCellRenderer;
import com.kor.admiralty.ui.resources.Images;
import com.kor.admiralty.ui.resources.Swing;

import static com.kor.admiralty.ui.resources.Strings.Empty;
import static com.kor.admiralty.ui.resources.Strings.AdmiralPanel.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JComboBox;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

public class AdmiralPanel extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 6385797348923426101L;

	protected Admiral admiral;
	protected JTextField txtName;
	protected JComboBox<PlayerFaction> cbxFaction;
	protected JComboBox<ShipPriority> cbxShipPriority;
	protected ListCellRenderer<Ship> shipCellRenderer;
	protected JList<Ship> lstActive;
	protected JList<Ship> lstMaintenance;
	protected JList<Ship> lstOneTimeShips;
	protected JList<Ship> lstTraits;
	protected ShipListModel shipActiveModel;
	protected ShipListModel shipMaintenanceModel;
	protected ShipListModel shipOneTimeModel;
	protected ShipListModel shipTraitsModel;
	protected JLabel lblActive;
	protected JLabel lblMaintenance;
	protected JLabel lblOnetimeShips;
	protected JButton btnAllActive;
	protected JButton btnActive;
	protected JButton btnMaintenance;
	protected JButton btnAllMaintenance;
	protected JButton btnAddOneTime;
	protected JButton btnRemoveOneTime;
	protected JButton btnPrev;
	protected JButton btnBest;
	protected JButton btnNext;
	protected JPanel pnlAssignmentButtons;
	protected JPanel pnlAssignmentGrid;
	protected JScrollPane sclAssignments;
	protected AssignmentPanel pnlAssignments[];
	protected AdjustmentListener adjustmentListener = new AdjustmentListener() {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			if (e.getAdjustmentType() == AdjustmentEvent.TRACK) {
				e.getAdjustable().setBlockIncrement(76);
			}
		}

	};
	private final Action actionAddShip = new AddActiveShipAction();
	private final Action actionRemoveShip = new RemoveActiveShipAction();
	private final Action actionAddOneTimeShip = new AddOneTimeShipAction();
	private final Action actionRemoveOneTimeShip = new RemoveOneTimeShipAction();
	private final Action actionAllMaintenanceToActive = new AllMaintenanceToActiveAction();
	private final Action actionAllActiveToMaintenance = new AllActiveToMaintenanceAction();
	private final Action actionMaintenanceToActive = new MaintenanceToActiveAction();
	private final Action actionActiveToMaintenance = new ActiveToMaintenanceAction();
	private final Action actionPlanAssignments = new PlanAssignmentAction();
	private final Action actionClearAssignments = new ClearAssignmentsAction();
	private final Action actionPrevSolution = new PrevSolutionAction();
	private final Action actionBestSolution = new BestSolutionAction();
	private final Action actionNextSolution = new NextSolutionAction();
	private final Action actionDeployShips = new DeployShipsAction();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	protected List<CompositeSolution> solutions = new ArrayList<CompositeSolution>();
	protected int solutionIndex = -1;

	/**
	 * Create the panel.
	 */
	public AdmiralPanel() {
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

		JPanel pnlPrimaryShips = new JPanel();
		tabAdmiral.addTab(TabPrimary, null, pnlPrimaryShips, null);
		GridBagLayout gbl_pnlPrimaryShips = new GridBagLayout();
		gbl_pnlPrimaryShips.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_pnlPrimaryShips.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_pnlPrimaryShips.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0 };
		gbl_pnlPrimaryShips.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0 };
		pnlPrimaryShips.setLayout(gbl_pnlPrimaryShips);

		lblActive = new JLabel(LabelActiveShips);
		GridBagConstraints gbc_lblActive = new GridBagConstraints();
		gbc_lblActive.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblActive.anchor = GridBagConstraints.WEST;
		gbc_lblActive.insets = new Insets(5, 5, 0, 5);
		gbc_lblActive.gridx = 0;
		gbc_lblActive.gridy = 0;
		pnlPrimaryShips.add(lblActive, gbc_lblActive);

		JPanel pnlButtons = new JPanel();
		pnlButtons.setBorder(null);
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.insets = new Insets(5, 0, 0, 5);
		gbc_pnlButtons.anchor = GridBagConstraints.NORTH;
		gbc_pnlButtons.weighty = 100.0;
		gbc_pnlButtons.weightx = 1.0;
		gbc_pnlButtons.gridheight = 6;
		gbc_pnlButtons.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlButtons.gridx = 3;
		gbc_pnlButtons.gridy = 1;
		pnlPrimaryShips.add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[] { 0 };
		gbl_pnlButtons.rowHeights = new int[] { 0, 0 };
		gbl_pnlButtons.columnWeights = new double[] { 0.0 };
		gbl_pnlButtons.rowWeights = new double[] { 0.0, 0.0 };
		pnlButtons.setLayout(gbl_pnlButtons);

		JButton btnAddShip = new JButton(actionAddShip);
		GridBagConstraints gbc_btnAddShip = new GridBagConstraints();
		gbc_btnAddShip.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddShip.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddShip.gridx = 0;
		gbc_btnAddShip.gridy = 0;
		pnlButtons.add(btnAddShip, gbc_btnAddShip);

		JButton btnRemoveShip = new JButton(actionRemoveShip);
		GridBagConstraints gbc_btnRemoveShip = new GridBagConstraints();
		gbc_btnRemoveShip.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemoveShip.gridx = 0;
		gbc_btnRemoveShip.gridy = 1;
		pnlButtons.add(btnRemoveShip, gbc_btnRemoveShip);

		lblMaintenance = new JLabel(LabelMaintenanceShips);
		GridBagConstraints gbc_lblMaintenance = new GridBagConstraints();
		gbc_lblMaintenance.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMaintenance.anchor = GridBagConstraints.WEST;
		gbc_lblMaintenance.insets = new Insets(5, 5, 0, 5);
		gbc_lblMaintenance.gridx = 2;
		gbc_lblMaintenance.gridy = 0;
		pnlPrimaryShips.add(lblMaintenance, gbc_lblMaintenance);

		JScrollPane sclActive = new JScrollPane();
		sclActive.getVerticalScrollBar().setUnitIncrement(76);
		sclActive.getVerticalScrollBar().getBlockIncrement(76);
		sclActive.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
		GridBagConstraints gbc_sclActive = new GridBagConstraints();
		gbc_sclActive.weighty = 10.0;
		gbc_sclActive.weightx = 100.0;
		gbc_sclActive.gridheight = 6;
		gbc_sclActive.insets = new Insets(5, 5, 5, 5);
		gbc_sclActive.fill = GridBagConstraints.BOTH;
		gbc_sclActive.gridx = 0;
		gbc_sclActive.gridy = 1;
		pnlPrimaryShips.add(sclActive, gbc_sclActive);

		shipCellRenderer = ShipCellRenderer.cellRenderer();
		shipActiveModel = new ShipListModel();
		lstActive = new JList<Ship>(shipActiveModel);
		lstActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = lstActive.locationToIndex(e.getPoint());
					Ship ship = shipActiveModel.getElementAt(index);
					lstActive.clearSelection();
					admiral.removeActive(ship.getName());
					admiral.addMaintenance(ship.getName());
				}
			}
		});
		lstActive.setCellRenderer(shipCellRenderer);
		sclActive.setViewportView(lstActive);

		JLabel lblTop = new JLabel(Empty);
		GridBagConstraints gbc_lblTop = new GridBagConstraints();
		gbc_lblTop.weightx = 1.0;
		gbc_lblTop.weighty = 100.0;
		gbc_lblTop.gridx = 1;
		gbc_lblTop.gridy = 1;
		pnlPrimaryShips.add(lblTop, gbc_lblTop);

		JScrollPane sclMaintenance = new JScrollPane();
		sclMaintenance.getVerticalScrollBar().setUnitIncrement(76);
		sclMaintenance.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
		GridBagConstraints gbc_sclMaintenance = new GridBagConstraints();
		gbc_sclMaintenance.weighty = 10.0;
		gbc_sclMaintenance.weightx = 100.0;
		gbc_sclMaintenance.gridheight = 6;
		gbc_sclMaintenance.insets = new Insets(5, 5, 5, 5);
		gbc_sclMaintenance.fill = GridBagConstraints.BOTH;
		gbc_sclMaintenance.gridx = 2;
		gbc_sclMaintenance.gridy = 1;
		pnlPrimaryShips.add(sclMaintenance, gbc_sclMaintenance);

		shipMaintenanceModel = new ShipListModel();
		lstMaintenance = new JList<Ship>(shipMaintenanceModel);
		lstMaintenance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = lstMaintenance.locationToIndex(e.getPoint());
					Ship ship = shipMaintenanceModel.getElementAt(index);
					lstMaintenance.clearSelection();
					admiral.removeMaintenance(ship.getName());
					admiral.addActive(ship.getName());
				}
			}
		});
		lstMaintenance.setCellRenderer(shipCellRenderer);
		sclMaintenance.setViewportView(lstMaintenance);

		btnAllActive = new JButton(actionAllMaintenanceToActive);
		GridBagConstraints gbc_btnAllActive = new GridBagConstraints();
		gbc_btnAllActive.insets = new Insets(0, 0, 5, 0);
		gbc_btnAllActive.weightx = 1.0;
		gbc_btnAllActive.weighty = 1.0;
		gbc_btnAllActive.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAllActive.gridx = 1;
		gbc_btnAllActive.gridy = 2;
		pnlPrimaryShips.add(btnAllActive, gbc_btnAllActive);

		btnActive = new JButton(actionMaintenanceToActive);
		GridBagConstraints gbc_btnActive = new GridBagConstraints();
		gbc_btnActive.insets = new Insets(0, 0, 5, 0);
		gbc_btnActive.weightx = 1.0;
		gbc_btnActive.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnActive.weighty = 1.0;
		gbc_btnActive.gridx = 1;
		gbc_btnActive.gridy = 3;
		pnlPrimaryShips.add(btnActive, gbc_btnActive);

		btnMaintenance = new JButton(actionActiveToMaintenance);
		GridBagConstraints gbc_btnMaintenance = new GridBagConstraints();
		gbc_btnMaintenance.insets = new Insets(0, 0, 5, 0);
		gbc_btnMaintenance.weightx = 1.0;
		gbc_btnMaintenance.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMaintenance.weighty = 1.0;
		gbc_btnMaintenance.gridx = 1;
		gbc_btnMaintenance.gridy = 4;
		pnlPrimaryShips.add(btnMaintenance, gbc_btnMaintenance);

		btnAllMaintenance = new JButton(actionAllActiveToMaintenance);
		GridBagConstraints gbc_btnAllMaintenance = new GridBagConstraints();
		gbc_btnAllMaintenance.weightx = 1.0;
		gbc_btnAllMaintenance.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAllMaintenance.weighty = 1.0;
		gbc_btnAllMaintenance.gridx = 1;
		gbc_btnAllMaintenance.gridy = 5;
		pnlPrimaryShips.add(btnAllMaintenance, gbc_btnAllMaintenance);

		JLabel lblBottom = new JLabel(Empty);
		GridBagConstraints gbc_lblBottom = new GridBagConstraints();
		gbc_lblBottom.weightx = 1.0;
		gbc_lblBottom.weighty = 100.0;
		gbc_lblBottom.gridx = 1;
		gbc_lblBottom.gridy = 6;
		pnlPrimaryShips.add(lblBottom, gbc_lblBottom);

		JPanel pnlOneTime = new JPanel();
		tabAdmiral.addTab(LabelOneTimeShips, null, pnlOneTime, null);
		GridBagLayout gbl_pnlOneTime = new GridBagLayout();
		gbl_pnlOneTime.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlOneTime.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_pnlOneTime.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlOneTime.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		pnlOneTime.setLayout(gbl_pnlOneTime);

		lblOnetimeShips = new JLabel(LabelOneTimeShips);
		GridBagConstraints gbc_lblOnetimeShips = new GridBagConstraints();
		gbc_lblOnetimeShips.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOnetimeShips.insets = new Insets(5, 5, 5, 0);
		gbc_lblOnetimeShips.gridwidth = 2;
		gbc_lblOnetimeShips.gridx = 0;
		gbc_lblOnetimeShips.gridy = 0;
		pnlOneTime.add(lblOnetimeShips, gbc_lblOnetimeShips);

		JScrollPane sclOneTimeShips = new JScrollPane();
		sclOneTimeShips.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
		GridBagConstraints gbc_sclOneTimeShips = new GridBagConstraints();
		gbc_sclOneTimeShips.weightx = 5.0;
		gbc_sclOneTimeShips.weighty = 10.0;
		gbc_sclOneTimeShips.gridheight = 3;
		gbc_sclOneTimeShips.insets = new Insets(5, 5, 5, 5);
		gbc_sclOneTimeShips.fill = GridBagConstraints.BOTH;
		gbc_sclOneTimeShips.gridx = 0;
		gbc_sclOneTimeShips.gridy = 1;
		pnlOneTime.add(sclOneTimeShips, gbc_sclOneTimeShips);

		shipOneTimeModel = new ShipListModel();
		lstOneTimeShips = new JList<Ship>(shipOneTimeModel);
		lstOneTimeShips.setCellRenderer(shipCellRenderer);
		sclOneTimeShips.setViewportView(lstOneTimeShips);

		btnAddOneTime = new JButton(actionAddOneTimeShip);
		GridBagConstraints gbc_btnAddOneTime = new GridBagConstraints();
		gbc_btnAddOneTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddOneTime.weighty = 1.0;
		gbc_btnAddOneTime.weightx = 1.0;
		gbc_btnAddOneTime.insets = new Insets(5, 5, 5, 5);
		gbc_btnAddOneTime.gridx = 1;
		gbc_btnAddOneTime.gridy = 1;
		pnlOneTime.add(btnAddOneTime, gbc_btnAddOneTime);

		btnRemoveOneTime = new JButton(actionRemoveOneTimeShip);
		GridBagConstraints gbc_btnRemoveOneTime = new GridBagConstraints();
		gbc_btnRemoveOneTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemoveOneTime.weighty = 1.0;
		gbc_btnRemoveOneTime.weightx = 1.0;
		gbc_btnRemoveOneTime.insets = new Insets(0, 5, 5, 5);
		gbc_btnRemoveOneTime.gridx = 1;
		gbc_btnRemoveOneTime.gridy = 2;
		pnlOneTime.add(btnRemoveOneTime, gbc_btnRemoveOneTime);

		JLabel lblEmpty = new JLabel(Empty);
		GridBagConstraints gbc_panel2 = new GridBagConstraints();
		gbc_panel2.weighty = 1000.0;
		gbc_panel2.gridy = 3;
		gbc_panel2.gridx = 1;
		gbc_pnlButtons.insets = new Insets(0, 0, 5, 5);
		gbc_pnlButtons.weighty = 100.0;
		gbc_pnlButtons.weightx = 1.0;
		gbc_pnlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlButtons.gridx = 1;
		gbc_pnlButtons.gridy = 3;
		pnlOneTime.add(lblEmpty, gbc_panel2);

		JPanel pnlAssignmentsTab = new JPanel();
		tabAdmiral.addTab(TabAssignments, null, pnlAssignmentsTab, null);
		pnlAssignmentsTab.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		pnlAssignmentsTab.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 1.0 };
		panel.setLayout(gbl_panel);

		JLabel label = new JLabel(LabelNumAssignments);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.SOUTH;
		gbc_label.weighty = 1.0;
		gbc_label.weightx = 3.0;
		gbc_label.insets = new Insets(5, 5, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);

		JLabel lblSpacer1 = new JLabel(Empty);
		GridBagConstraints gbc_lblSpacer1 = new GridBagConstraints();
		gbc_lblSpacer1.weighty = 2.0;
		gbc_lblSpacer1.weightx = 100.0;
		gbc_lblSpacer1.gridheight = 2;
		gbc_lblSpacer1.insets = new Insets(5, 5, 0, 5);
		gbc_lblSpacer1.gridx = 1;
		gbc_lblSpacer1.gridy = 1;
		panel.add(lblSpacer1, gbc_lblSpacer1);

		JLabel lblSpacer2 = new JLabel(Empty);
		GridBagConstraints gbc_lblSpacer2 = new GridBagConstraints();
		gbc_lblSpacer2.weighty = 2.0;
		gbc_lblSpacer2.weightx = 100.0;
		gbc_lblSpacer2.gridheight = 2;
		gbc_lblSpacer2.insets = new Insets(5, 5, 0, 5);
		gbc_lblSpacer2.gridx = 4;
		gbc_lblSpacer2.gridy = 1;
		panel.add(lblSpacer2, gbc_lblSpacer2);

		pnlAssignmentButtons = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.weighty = 1.0;
		gbc_panel_1.weightx = 3.0;
		gbc_panel_1.insets = new Insets(0, 5, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel.add(pnlAssignmentButtons, gbc_panel_1);

		JButton btnPlanAssignments = new JButton(LabelPlanAssignments);
		GridBagConstraints gbc_btnPlanAssignments = new GridBagConstraints();
		gbc_btnPlanAssignments.weightx = 1.0;
		gbc_btnPlanAssignments.gridheight = 2;
		gbc_btnPlanAssignments.weighty = 2.0;
		gbc_btnPlanAssignments.fill = GridBagConstraints.BOTH;
		gbc_btnPlanAssignments.insets = new Insets(5, 5, 5, 5);
		gbc_btnPlanAssignments.gridx = 2;
		gbc_btnPlanAssignments.gridy = 0;
		panel.add(btnPlanAssignments, gbc_btnPlanAssignments);
		Swing.setFont(btnPlanAssignments, Font.PLAIN, 12);
		btnPlanAssignments.setAction(actionPlanAssignments);

		JButton btnClearAssignments = new JButton(LabelClearAssignments);
		btnClearAssignments.setAction(actionClearAssignments);
		Swing.setFont(btnClearAssignments, Font.PLAIN, 12);
		GridBagConstraints gbc_btnClearAssignments = new GridBagConstraints();
		gbc_btnClearAssignments.weightx = 1.0;
		gbc_btnClearAssignments.gridheight = 2;
		gbc_btnClearAssignments.weighty = 2.0;
		gbc_btnClearAssignments.insets = new Insets(5, 5, 5, 5);
		gbc_btnClearAssignments.fill = GridBagConstraints.BOTH;
		gbc_btnClearAssignments.gridx = 3;
		gbc_btnClearAssignments.gridy = 0;
		panel.add(btnClearAssignments, gbc_btnClearAssignments);

		JLabel lblSelectPlans = new JLabel(LabelDeploymentPlans);
		GridBagConstraints gbc_lblSelectPlans = new GridBagConstraints();
		gbc_lblSelectPlans.anchor = GridBagConstraints.SOUTH;
		gbc_lblSelectPlans.weighty = 1.0;
		gbc_lblSelectPlans.weightx = 3.0;
		gbc_lblSelectPlans.gridwidth = 3;
		gbc_lblSelectPlans.insets = new Insets(5, 5, 5, 5);
		gbc_lblSelectPlans.gridx = 5;
		gbc_lblSelectPlans.gridy = 0;
		panel.add(lblSelectPlans, gbc_lblSelectPlans);

		btnPrev = new JButton(actionPrevSolution);
		btnPrev.setEnabled(false);
		GridBagConstraints gbc_btnPrev = new GridBagConstraints();
		gbc_btnPrev.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPrev.weighty = 1.0;
		gbc_btnPrev.weightx = 1.0;
		gbc_btnPrev.insets = new Insets(0, 5, 5, 5);
		gbc_btnPrev.gridx = 5;
		gbc_btnPrev.gridy = 1;
		panel.add(btnPrev, gbc_btnPrev);

		btnBest = new JButton(actionBestSolution);
		btnBest.setEnabled(false);
		GridBagConstraints gbc_btnBest = new GridBagConstraints();
		gbc_btnBest.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBest.weighty = 1.0;
		gbc_btnBest.weightx = 1.0;
		gbc_btnBest.insets = new Insets(0, 0, 5, 0);
		gbc_btnBest.gridx = 6;
		gbc_btnBest.gridy = 1;
		panel.add(btnBest, gbc_btnBest);

		btnNext = new JButton(actionNextSolution);
		btnNext.setEnabled(false);
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNext.weighty = 1.0;
		gbc_btnNext.weightx = 1.0;
		gbc_btnNext.insets = new Insets(0, 5, 5, 5);
		gbc_btnNext.gridx = 7;
		gbc_btnNext.gridy = 1;
		panel.add(btnNext, gbc_btnNext);

		JButton btnDeployShips = new JButton(actionDeployShips);
		GridBagConstraints gbc_btnDeployShips = new GridBagConstraints();
		gbc_btnDeployShips.weighty = 2.0;
		gbc_btnDeployShips.weightx = 1.0;
		gbc_btnDeployShips.gridheight = 2;
		gbc_btnDeployShips.insets = new Insets(5, 5, 5, 5);
		gbc_btnDeployShips.fill = GridBagConstraints.VERTICAL;
		gbc_btnDeployShips.gridx = 8;
		gbc_btnDeployShips.gridy = 0;
		panel.add(btnDeployShips, gbc_btnDeployShips);

		sclAssignments = new JScrollPane();
		pnlAssignmentsTab.add(sclAssignments);

		pnlAssignmentGrid = new JPanel();
		sclAssignments.setViewportView(pnlAssignmentGrid);
		pnlAssignmentGrid.setLayout(new GridLayout(0, 1, 5, 5));
		
		JPanel pnlStarshipTraits = new JPanel();
		tabAdmiral.addTab("Starship Traits", null, pnlStarshipTraits, null);
		GridBagLayout gbl_pnlStarshipTraits = new GridBagLayout();
		gbl_pnlStarshipTraits.columnWidths = new int[] {0, 0};
		gbl_pnlStarshipTraits.rowHeights = new int[] {0, 0, 0};
		gbl_pnlStarshipTraits.columnWeights = new double[]{0.0, 0.0};
		gbl_pnlStarshipTraits.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pnlStarshipTraits.setLayout(gbl_pnlStarshipTraits);
		
		JLabel lblStarshipTraits = new JLabel("Unlockable Starship Traits:");
		lblStarshipTraits.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblStarshipTraits = new GridBagConstraints();
		gbc_lblStarshipTraits.weightx = 1.0;
		gbc_lblStarshipTraits.fill = GridBagConstraints.BOTH;
		gbc_lblStarshipTraits.insets = new Insets(5, 5, 0, 5);
		gbc_lblStarshipTraits.gridx = 0;
		gbc_lblStarshipTraits.gridy = 0;
		pnlStarshipTraits.add(lblStarshipTraits, gbc_lblStarshipTraits);
		
		JScrollPane sclTraits = new JScrollPane();
		GridBagConstraints gbc_sclTraits = new GridBagConstraints();
		gbc_sclTraits.insets = new Insets(0, 5, 5, 5);
		gbc_sclTraits.weightx = 1.0;
		gbc_sclTraits.weighty = 1.0;
		gbc_sclTraits.fill = GridBagConstraints.BOTH;
		gbc_sclTraits.gridx = 0;
		gbc_sclTraits.gridy = 1;
		pnlStarshipTraits.add(sclTraits, gbc_sclTraits);
		
		shipTraitsModel = new ShipListModel();
		lstTraits = new JColumnList<Ship>(shipTraitsModel);
		lstTraits.setLayoutOrientation(JList.VERTICAL);
		lstTraits.setCellRenderer(new StarshipTraitCellRenderer());
		sclTraits.setViewportView(lstTraits);
		sclTraits.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnlStarshipTraits.addComponentListener(new JListComponentAdapter<Ship>(lstTraits));
		
		if (Beans.isDesignTime()) {
			initDesignTime();
		} else {
			initRunTime();
		}
	}

	protected void initDesignTime() {
		AssignmentPanel assignmentPanel = new AssignmentPanel();
		pnlAssignmentGrid.add(assignmentPanel);
	}

	protected void initRunTime() {
		pnlAssignments = new AssignmentPanel[Globals.MAX_ASSIGNMENTS];
		for (int i = 0; i < Globals.MAX_ASSIGNMENTS; i++) {
			// Create toggle button
			JToggleButton toggle = new JToggleButton(new AssignmentNumberAction(i + 1));
			toggle.setSelected(i == 0);
			pnlAssignmentButtons.add(toggle);
			buttonGroup.add(toggle);

			// Instantiate AssignmentPanel
			pnlAssignments[i] = new AssignmentPanel();
			pnlAssignments[i].setVisible(i < 1);
			pnlAssignmentGrid.add(pnlAssignments[i]);
		}
		int height = (int)(pnlAssignments[0].getPreferredSize().getHeight() / 20);
		sclAssignments.getVerticalScrollBar().setUnitIncrement(height);
	}

	public AdmiralPanel(Admiral admiral) {
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
			for (int i = 0; i < Globals.MAX_ASSIGNMENTS; i++) {
				pnlAssignments[i].setAssignment(admiral.getAssignment(i));
			}
			shipActiveModel.addShips(admiral.getActiveShips());
			shipMaintenanceModel.addShips(admiral.getMaintenanceShips());
			shipOneTimeModel.addShips(admiral.getOneTimeShips());
			shipTraitsModel.addShips(admiral.getStarshipTraits());
			lblActive.setText(String.format(HtmlActiveShips, shipActiveModel.getSize()));
			lblMaintenance.setText(String.format(HtmlMaintenanceShips, shipMaintenanceModel.getSize()));
			lblOnetimeShips.setText(String.format(HtmlOneTimeShips, shipOneTimeModel.getSize()));
			admiral.addPropertyChangeListener(this);
		}
	}

	public int getAssignmentCount() {
		return admiral.getAssignmentCount();
	}

	public void setSolutions(List<CompositeSolution> solutions) {
		this.solutions.clear();
		this.solutions.addAll(solutions);
		setSolutionIndex(0);
	}

	public void clearSolutions() {
		this.solutions.clear();
		this.solutions.addAll(solutions);
		setSolutionIndex(-1);
	}

	protected void setSolutionIndex(int index) {
		solutionIndex = index;
		btnPrev.setEnabled(solutionIndex > 0);
		btnBest.setEnabled(solutionIndex != 0);
		btnNext.setEnabled(solutionIndex < this.solutions.size() - 1);
		if (solutionIndex >= 0 && solutionIndex < solutions.size()) {
			CompositeSolution solution = solutions.get(solutionIndex);
			for (int i = 0; i < solution.size(); i++) {
				AssignmentSolution aSolution = solution.getSolution(i);
				pnlAssignments[i].setAssignmentSolution(aSolution);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String property = e.getPropertyName();
		if (property == Admiral.PROP_NAME) {
			// txtName.setText(admiral.getName());
		} else if (property == Admiral.PROP_FACTION) {
			cbxFaction.setSelectedItem(admiral.getFaction());
		} else if (property == Admiral.PROP_ACTIVE) {
			shipActiveModel.setShips(admiral.getActiveShips());
			lblActive.setText(String.format(HtmlActiveShips, shipActiveModel.getSize()));
			shipTraitsModel.setShips(admiral.getStarshipTraits());
		} else if (property == Admiral.PROP_MAINTENANCE) {
			shipMaintenanceModel.setShips(admiral.getMaintenanceShips());
			lblMaintenance.setText(String.format(HtmlMaintenanceShips, shipMaintenanceModel.getSize()));
		} else if (property == Admiral.PROP_ONETIME) {
			shipOneTimeModel.setShips(admiral.getOneTimeShips());
			lblOnetimeShips.setText(String.format(HtmlOneTimeShips, shipOneTimeModel.getSize()));
		} else if (property == Admiral.PROP_ASSIGNMENTCOUNT) {
			int count = admiral.getAssignmentCount();
			for (int i = 0; i < Globals.MAX_ASSIGNMENTS; i++) {
				pnlAssignments[i].setVisible(i < count);
				pnlAssignments[i].setEnabled(i < count);
			}
		}

	}

	private class AssignmentNumberAction extends AbstractAction {
		private static final long serialVersionUID = 1132930085427895573L;
		int number;

		public AssignmentNumberAction(int number) {
			this.number = number;
			putValue(NAME, Empty + number);
			putValue(SHORT_DESCRIPTION, DescNumAssignments);
			int mnemonic = '\0';
			if (number == 1) mnemonic = KeyEvent.VK_1;
			else if (number == 2) mnemonic = KeyEvent.VK_2;
			else if (number == 3) mnemonic = KeyEvent.VK_3;
			putValue(MNEMONIC_KEY, mnemonic);
		}

		public void actionPerformed(ActionEvent e) {
			admiral.setAssignmentCount(number);
		}
	}

	private class AddActiveShipAction extends AbstractAction {
		private static final long serialVersionUID = 2156513045423271256L;

		public AddActiveShipAction() {
			super(LabelShip, Images.ICON_ADD);
			putValue(SHORT_DESCRIPTION, DescAddActiveShips);
		}

		public void actionPerformed(ActionEvent e) {
			Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
			TreeSet<Ship> inputShips = new TreeSet<Ship>(Datastore.getAllShips().values());
			inputShips.removeAll(admiral.getActiveShips());
			inputShips.removeAll(admiral.getMaintenanceShips());
			List<Ship> ships = ShipSelectionPanel.dialogActiveShips(window, admiral.getFaction(), inputShips, TitleAddActiveShips);
			if (!ships.isEmpty()) {
				admiral.addActiveShips(ships);
			}
		}
	}

	private class RemoveActiveShipAction extends AbstractAction {
		private static final long serialVersionUID = -4040927572982355707L;

		public RemoveActiveShipAction() {
			super(LabelShip, Images.ICON_REMOVE);
			putValue(SHORT_DESCRIPTION, DescRemoveActiveShips);
		}

		public void actionPerformed(ActionEvent e) {
			Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
			Set<Ship> inputShips = new TreeSet<Ship>();
			inputShips.addAll(admiral.getActiveShips());
			inputShips.addAll(admiral.getMaintenanceShips());
			List<Ship> ships = ShipSelectionPanel.dialog(window, inputShips, TitleRemoveActiveShips);
			if (!ships.isEmpty()) {
				admiral.removeActiveOrMaintenanceShips(ships);
			}
		}
	}

	private class AddOneTimeShipAction extends AbstractAction {
		private static final long serialVersionUID = -9000567166027604196L;

		public AddOneTimeShipAction() {
			super(LabelShip, Images.ICON_ADD);
			putValue(SHORT_DESCRIPTION, DescAddOneTimeShips);
		}

		public void actionPerformed(ActionEvent e) {
			Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
			List<Ship> ships = ShipSelectionPanel.dialogAddOneTimeShips(window, admiral.getFaction(), TitleAddOneTimeShips);
			if (!ships.isEmpty()) {
				admiral.addOneTimeShips(ships);
			}
		}
	}

	private class RemoveOneTimeShipAction extends AbstractAction {
		private static final long serialVersionUID = -5773265252031585211L;

		public RemoveOneTimeShipAction() {
			super(LabelShip, Images.ICON_REMOVE);
			putValue(SHORT_DESCRIPTION, DescRemoveOneTimeShips);
		}

		public void actionPerformed(ActionEvent e) {
			Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
			Set<Ship> inputShips = new TreeSet<Ship>(admiral.getOneTimeShips());
			List<Ship> ships = ShipSelectionPanel.dialog(window, inputShips, TitleRemoveOneTimeShips);
			if (!ships.isEmpty()) {
				admiral.removeOneTimeShips(ships);
			}
		}
	}

	private class AllMaintenanceToActiveAction extends AbstractAction {
		private static final long serialVersionUID = -509981822658289573L;

		public AllMaintenanceToActiveAction() {
			super(Empty, Images.ICON_LEFT_ALL);
			putValue(SHORT_DESCRIPTION, DescAllMaintenanceToActive);
		}

		public void actionPerformed(ActionEvent e) {
			Set<Ship> ships = admiral.getMaintenanceShips();
			admiral.addActiveShips(ships);
			admiral.removeMaintenanceShips(ships);
		}
	}

	private class AllActiveToMaintenanceAction extends AbstractAction {
		private static final long serialVersionUID = -7137558996349465891L;

		public AllActiveToMaintenanceAction() {
			super(Empty, Images.ICON_RIGHT_ALL);
			putValue(SHORT_DESCRIPTION, DescAllActiveToMaintenance);
		}

		public void actionPerformed(ActionEvent e) {
			Set<Ship> ships = admiral.getActiveShips();
			admiral.addMaintenanceShips(ships);
			admiral.removeActiveShips(ships);
		}
	}

	private class MaintenanceToActiveAction extends AbstractAction {
		private static final long serialVersionUID = 9205828368701721872L;

		public MaintenanceToActiveAction() {
			super(Empty, Images.ICON_LEFT_ONE);
			putValue(SHORT_DESCRIPTION, DescMaintenanceToActive);
		}

		public void actionPerformed(ActionEvent e) {
			List<Ship> ships = lstMaintenance.getSelectedValuesList();
			if (!ships.isEmpty()) {
				admiral.removeMaintenanceShips(ships);
				admiral.addActiveShips(ships);
			}
			lstActive.setSelectedIndices(new int[0]);
			lstMaintenance.setSelectedIndices(new int[0]);
		}
	}

	private class ActiveToMaintenanceAction extends AbstractAction {
		private static final long serialVersionUID = -3757003998462960644L;

		public ActiveToMaintenanceAction() {
			super(Empty, Images.ICON_RIGHT_ONE);
			putValue(SHORT_DESCRIPTION, DescActiveToMaintenance);
		}

		public void actionPerformed(ActionEvent e) {
			List<Ship> ships = lstActive.getSelectedValuesList();
			if (!ships.isEmpty()) {
				admiral.removeActiveShips(ships);
				admiral.addMaintenanceShips(ships);
			}
			lstActive.setSelectedIndices(new int[0]);
			lstMaintenance.setSelectedIndices(new int[0]);
		}
	}

	private class PlanAssignmentAction extends AbstractAction {
		private static final long serialVersionUID = 6495337889146948055L;

		public PlanAssignmentAction() {
			putValue(NAME, HtmlPlanAssignments);
			putValue(SHORT_DESCRIPTION, DescPlanAssignments);
			putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		}

		public void actionPerformed(ActionEvent e) {
			List<Ship> ships = admiral.getDeployableShips();
			List<CompositeSolution> answers = admiral.solveAssignments(ships);
			if (answers.isEmpty()) {
				Window window = SwingUtilities.windowForComponent((Component)e.getSource());
				JOptionPane.showMessageDialog(window, MsgNoSolution);
			}
			else {
				setSolutions(answers);
			}
		}
	}

	private class ClearAssignmentsAction extends AbstractAction {
		private static final long serialVersionUID = -2911438009333065675L;

		public ClearAssignmentsAction() {
			putValue(NAME, HtmlClearAssignments);
			putValue(SHORT_DESCRIPTION, DescClearAssignments);
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		}

		public void actionPerformed(ActionEvent e) {
			int count = admiral.getAssignmentCount();
			for (int i = 0; i < count; i++) {
				Assignment assignment = admiral.getAssignment(i);
				assignment.clear();
				pnlAssignments[i].clearAssignment();
				pnlAssignments[i].setAssignmentSolution(null);
			}
			clearSolutions();
		}
	}

	private class PrevSolutionAction extends AbstractAction {
		private static final long serialVersionUID = 6703568394727570745L;

		public PrevSolutionAction() {
			super(LabelPrev, Images.ICON_PREV);
			putValue(SHORT_DESCRIPTION, DescPrev);
			putValue(MNEMONIC_KEY, KeyEvent.VK_COMMA);
		}

		public void actionPerformed(ActionEvent e) {
			setSolutionIndex(solutionIndex - 1);
		}
	}

	private class BestSolutionAction extends AbstractAction {
		private static final long serialVersionUID = -2375413099526657416L;

		public BestSolutionAction() {
			super(LabelBest, Images.ICON_BEST);
			putValue(SHORT_DESCRIPTION, DescBest);
			putValue(MNEMONIC_KEY, KeyEvent.VK_B);
		}

		public void actionPerformed(ActionEvent e) {
			setSolutionIndex(0);
		}
	}

	private class NextSolutionAction extends AbstractAction {
		private static final long serialVersionUID = -8503210636249158149L;

		public NextSolutionAction() {
			super(LabelNext, Images.ICON_NEXT);
			putValue(SHORT_DESCRIPTION, DescNext);
			putValue(MNEMONIC_KEY, KeyEvent.VK_PERIOD);
		}

		public void actionPerformed(ActionEvent e) {
			setSolutionIndex(solutionIndex + 1);
		}
	}

	private class DeployShipsAction extends AbstractAction {
		private static final long serialVersionUID = -4737708180950586981L;

		public DeployShipsAction() {
			putValue(NAME, HtmlDeployShips);
			putValue(SHORT_DESCRIPTION, DescDeployShips);
			putValue(MNEMONIC_KEY, KeyEvent.VK_D);
		}

		public void actionPerformed(ActionEvent e) {
			if (solutionIndex < 0) {
				JOptionPane.showMessageDialog(AdmiraltyConsole.CONSOLE, MsgNoShipsToDeploy);
				return;
			}
			
			int shipCount = 0;
			List<Ship> ships = new ArrayList<Ship>();
			CompositeSolution cs = solutions.get(solutionIndex);
			for (AssignmentSolution solution : cs.getSolutions()) {
				for (Ship ship : solution.getShips()) {
					if (ship != null) {
						ships.add(ship);
						shipCount++;
					}
				}
			}
			
			if (shipCount == 0) {
				JOptionPane.showMessageDialog(AdmiraltyConsole.CONSOLE, MsgNoShipsToDeploy);
				return;
			}

			String message = admiral.assignShips(ships);
			JOptionPane.showMessageDialog(AdmiraltyConsole.CONSOLE, message);
		}
	}
}
