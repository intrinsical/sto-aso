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

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.Beans;
import java.util.Collection;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import com.kor.admiralty.beans.Admiral;
import com.kor.admiralty.beans.Admirals;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.ShipSortOrder;
import com.kor.admiralty.io.Datastore;
import com.kor.admiralty.ui.renderers.UsageCountCellRenderer;

import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;

import static com.kor.admiralty.ui.resources.Strings.ShipStatistics.*;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;

public class ShipUsageFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = -7842523811419803589L;

	protected Admirals admirals;
	protected ShipListPanel pnlShips;

	// protected ShipListModel shipModel;
	protected UsageCountCellRenderer shipCellRenderer;
	protected JComboBox<String> cbxAdmirals;
	private final ShipViewAction actionShipView = new ShipViewAction();
	private final Action actionDefaultSort = new DefaultSortAction();
	private final Action actionMostUsed = new MostUsedAction();
	private final Action actionLeastUsed = new LeastUsedAction();
	private final Action actionClearUsageData = new ClearUsageDataAction();

	/**
	 * Create the frame.
	 */
	public ShipUsageFrame() {
		setTitle(Title);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addComponentListener(actionShipView);

		LineBorder border = new LineBorder(getBackground().darker(), 1, true);

		JPanel pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new BorderLayout(5, 5));

		JPanel pnlControls = new JPanel();
		pnlMain.add(pnlControls, BorderLayout.NORTH);
		GridBagLayout gbl_pnlControls = new GridBagLayout();
		gbl_pnlControls.columnWidths = new int[] { 0, 0 };
		gbl_pnlControls.rowHeights = new int[] { 0 };
		gbl_pnlControls.columnWeights = new double[] { 0.0, 0.0 };
		gbl_pnlControls.rowWeights = new double[] { 0.0 };
		pnlControls.setLayout(gbl_pnlControls);

		JPanel pnlAdmirals = new JPanel();
		pnlAdmirals.setBorder(new TitledBorder(border, LabelAdmirals, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlAdmirals = new GridBagConstraints();
		gbc_pnlAdmirals.weighty = 1.0;
		gbc_pnlAdmirals.weightx = 9.0;
		gbc_pnlAdmirals.fill = GridBagConstraints.BOTH;
		gbc_pnlAdmirals.insets = new Insets(0, 0, 0, 5);
		gbc_pnlAdmirals.gridx = 0;
		gbc_pnlAdmirals.gridy = 0;
		pnlControls.add(pnlAdmirals, gbc_pnlAdmirals);
		GridBagLayout gbl_pnlAdmirals = new GridBagLayout();
		gbl_pnlAdmirals.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlAdmirals.rowHeights = new int[] { 0 };
		gbl_pnlAdmirals.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlAdmirals.rowWeights = new double[] { 0.0 };
		pnlAdmirals.setLayout(gbl_pnlAdmirals);

		cbxAdmirals = new JComboBox<String>();
		GridBagConstraints gbc_cbxAdmirals = new GridBagConstraints();
		gbc_cbxAdmirals.insets = new Insets(0, 5, 5, 0);
		gbc_cbxAdmirals.weightx = 1.0;
		gbc_cbxAdmirals.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbxAdmirals.gridx = 0;
		gbc_cbxAdmirals.gridy = 0;
		pnlAdmirals.add(cbxAdmirals, gbc_cbxAdmirals);

		JButton btnClearUsageData = new JButton();
		btnClearUsageData.setAction(actionClearUsageData);
		GridBagConstraints gbc_btnClearUsageData = new GridBagConstraints();
		gbc_btnClearUsageData.insets = new Insets(0, 1, 5, 5);
		gbc_btnClearUsageData.gridx = 1;
		gbc_btnClearUsageData.gridy = 0;
		pnlAdmirals.add(btnClearUsageData, gbc_btnClearUsageData);
		cbxAdmirals.addActionListener(actionShipView);

		JPanel pnlSortBy = new JPanel();
		pnlSortBy.setBorder(new TitledBorder(border, "Sort by...", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlSortBy = new GridBagConstraints();
		gbc_pnlSortBy.weighty = 1.0;
		gbc_pnlSortBy.weightx = 1.0;
		gbc_pnlSortBy.fill = GridBagConstraints.BOTH;
		gbc_pnlSortBy.gridx = 1;
		gbc_pnlSortBy.gridy = 0;
		pnlControls.add(pnlSortBy, gbc_pnlSortBy);

		ButtonGroup grpSortBy = new ButtonGroup();
		pnlSortBy.setLayout(new GridLayout(0, 3, 0, 0));
		JToggleButton btnDefault = new JToggleButton(actionDefaultSort);
		pnlSortBy.add(btnDefault);
		grpSortBy.add(btnDefault);

		JToggleButton btnMostUsed = new JToggleButton(actionMostUsed);
		btnMostUsed.setSelected(true);
		pnlSortBy.add(btnMostUsed);
		grpSortBy.add(btnMostUsed);

		JToggleButton btnLeastUsed = new JToggleButton(actionLeastUsed);
		pnlSortBy.add(btnLeastUsed);
		grpSortBy.add(btnLeastUsed);

		final JComboBox<ShipSortOrder> cbxSortOrder;
		if (Beans.isDesignTime()) {
		} else {
			cbxSortOrder = new JComboBox<ShipSortOrder>(ShipSortOrder.values());
			cbxSortOrder.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ShipSortOrder sortOrder = (ShipSortOrder) cbxSortOrder.getSelectedItem();
					setSortOrder(sortOrder);
				}
			});
		}

		pnlShips = new ShipListPanel();
		pnlShips.setShipSortOrder(ShipSortOrder.MostUsed);
		pnlShips.setCellRenderer(new UsageCountCellRenderer());
		pnlMain.add(pnlShips);

		if (Beans.isDesignTime()) {
			initDesignTime();
		} else {
			initRunTime();
		}
	}

	protected void initDesignTime() {
		setBounds(0, 0, 640, 480);
	}

	protected void initRunTime() {
		this.admirals = Datastore.getAdmirals();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int) (screen.getWidth() * 0.4);
		int h = (int) (screen.getHeight() - 34);
		int x = (int) ((screen.getWidth() - w) / 2);
		int y = 0;
		setSize(w, h);
		setLocation(x, y);

		cbxAdmirals.addItem(LabelAllAdmirals);
		cbxAdmirals.addItem(LabelFederationAdmirals);
		cbxAdmirals.addItem(LabelKlingonAdmirals);
		cbxAdmirals.addItem(LabelRomulanAdmirals);
		cbxAdmirals.addItem(LabelJemHadarAdmirals);
		for (Admiral admiral : admirals.getAdmirals()) {
			cbxAdmirals.addItem(admiral.getName());
		}
	}

	protected void setShipView(String name) {
		if (name == null || name.equals(LabelAllAdmirals)) {
			setShipViewAll();
		} else if (name.equals(LabelFederationAdmirals)) {
			setShipViewFederation();
		} else if (name.equals(LabelKlingonAdmirals)) {
			setShipViewKlingon();
		} else if (name.equals(LabelRomulanAdmirals)) {
			setShipViewRomulan();
		} else if (name.equals(LabelJemHadarAdmirals)) {
			setShipViewJemHadar();
		} else {
			Admiral admiral = admirals.findByName(name);
			setShipView(admiral);
		}
	}

	protected void setShipViewAll() {
		setShipView(admirals.getAdmirals());
	}

	protected void setShipViewFederation() {
		setShipView(admirals.getFederationAdmirals());
	}

	protected void setShipViewKlingon() {
		setShipView(admirals.getKlingonAdmirals());
	}

	protected void setShipViewRomulan() {
		setShipView(admirals.getRomulanAdmirals());
	}

	protected void setShipViewJemHadar() {
		setShipView(admirals.getJemHadarAdmirals());
	}

	protected void setShipView(Collection<Admiral> collection) {
		setShipView(Admirals.toArray(collection));
	}

	protected void setShipView(Admiral... array) {
		Set<Ship> ships = Admiral.getShipUsageData(array);
		pnlShips.setShips(ships);
	}
	
	protected void clearAllUsageData() {
		clearUsageData(admirals.getAdmirals());
	}
	
	protected void clearFederationUsageData() {
		clearUsageData(admirals.getFederationAdmirals());
	}
	
	protected void clearKlingonUsageData() {
		clearUsageData(admirals.getKlingonAdmirals());
	}
	
	protected void clearRomulanUsageData() {
		clearUsageData(admirals.getRomulanAdmirals());
	}
	
	protected void clearJemHadarUsageData() {
		clearUsageData(admirals.getJemHadarAdmirals());
	}
	
	protected void clearUsageData(Collection<Admiral> collection) {
		clearUsageData(Admirals.toArray(collection));
	}
	
	protected void clearUsageData(Admiral ... array) {
		for (Admiral admiral : array) {
			admiral.clearUsage();
		}
		setShipView(array);
	}
	
	protected void setSortOrder(ShipSortOrder sortOrder) {
		pnlShips.setShipSortOrder(sortOrder);
	}

	private class DefaultSortAction extends AbstractAction {

		private static final long serialVersionUID = 2591067670029290567L;

		public DefaultSortAction() {
			super(LabelDefaultSort);
			putValue(SHORT_DESCRIPTION, DescDefaultSort);
		}

		public void actionPerformed(ActionEvent e) {
			pnlShips.setShipSortOrder(ShipSortOrder.Default);
		}
	}

	private class MostUsedAction extends AbstractAction {

		private static final long serialVersionUID = -8939959467353282880L;

		public MostUsedAction() {
			super(LabelMostUsed);
			putValue(SHORT_DESCRIPTION, DescMostUsed);
		}

		public void actionPerformed(ActionEvent e) {
			pnlShips.setShipSortOrder(ShipSortOrder.MostUsed);
		}
	}

	private class LeastUsedAction extends AbstractAction {

		private static final long serialVersionUID = -4791903586696391645L;

		public LeastUsedAction() {
			super(LabelLeastUsed);
			putValue(SHORT_DESCRIPTION, DescLeastUsed);
		}

		public void actionPerformed(ActionEvent e) {
			pnlShips.setShipSortOrder(ShipSortOrder.LeastUsed);
		}
	}

	private class ClearUsageDataAction extends AbstractAction {
		
		private static final long serialVersionUID = -2506691204971648770L;

		public ClearUsageDataAction() {
			super(LabelClearUsageData);
			putValue(SHORT_DESCRIPTION, DescClearUsageData);
		}

		public void actionPerformed(ActionEvent e) {
			String title = TitleClearUsageData;
			String name = cbxAdmirals.getSelectedItem().toString();
			String message = String.format(MsgClearUsageData, name);
			int result = JOptionPane.showConfirmDialog(AdmiraltyConsole.STATS_FRAME, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				if (name == null || name.equals(LabelAllAdmirals)) {
					clearAllUsageData();
				} else if (name.equals(LabelFederationAdmirals)) {
					clearFederationUsageData();
				} else if (name.equals(LabelKlingonAdmirals)) {
					clearKlingonUsageData();
				} else if (name.equals(LabelRomulanAdmirals)) {
					clearRomulanUsageData();
				} else if (name.equals(LabelJemHadarAdmirals)) {
					clearJemHadarUsageData();
				} else {
					Admiral admiral = admirals.findByName(name);
					clearUsageData(admiral);
				}
			}
		}
	}

	private class ShipViewAction extends ComponentAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateView();
		}

		@Override
		public void componentShown(ComponentEvent e) {
			updateView();
		}

		protected void updateView() {
			String name = cbxAdmirals.getSelectedItem().toString();
			setShipView(name);
		}

	}

	public void run() {
		if (isVisible()) {
			setVisible(false);
		} else {
			setVisible(true);
			toFront();
			repaint();
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(AdmiraltyConsole.STATS_FRAME);
	}

}
