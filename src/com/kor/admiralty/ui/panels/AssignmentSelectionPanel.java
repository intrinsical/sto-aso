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

import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.kor.admiralty.Globals;
import com.kor.admiralty.beans.Admiral;
import com.kor.admiralty.beans.Assignment;
import com.kor.admiralty.beans.AssignmentSolution;
import com.kor.admiralty.beans.CompositeSolution;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.ui.AdmiraltyConsole;
import com.kor.admiralty.ui.AssignmentPanel;
import com.kor.admiralty.ui.resources.Images;
import com.kor.admiralty.ui.resources.Swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static com.kor.admiralty.ui.resources.Strings.Empty;
import static com.kor.admiralty.ui.resources.Strings.AdmiralPanel.*;

public class AssignmentSelectionPanel extends JPanel implements AdmiralUI {

	private static final long serialVersionUID = -3837967504802185087L;
	
	protected Admiral admiral;
	protected List<CompositeSolution> solutions = new ArrayList<CompositeSolution>();
	protected int solutionIndex = -1;
	
	protected JPanel pnlAssignmentButtons;
	protected JScrollPane sclAssignments;
	protected JPanel pnlAssignmentGrid;
	protected AssignmentPanel pnlAssignments[];
	protected JButton btnPrev;
	protected JButton btnBest;
	protected JButton btnNext;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final Action actionPlanAssignments = new PlanAssignmentAction();
	private final Action actionClearAssignments = new ClearAssignmentsAction();
	private final Action actionPrevSolution = new PrevSolutionAction();
	private final Action actionBestSolution = new BestSolutionAction();
	private final Action actionNextSolution = new NextSolutionAction();
	private final Action actionDeployShips = new DeployShipsAction();
	
	/**
	 * Create the panel.
	 */
	public AssignmentSelectionPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTop = new JPanel();
		add(pnlTop, BorderLayout.NORTH);
		GridBagLayout gbl_pnlTop = new GridBagLayout();
		gbl_pnlTop.columnWidths = new int[]{33, 113, 1, 1, 10, 31, 33, 33, 3, 25, 91, 0};
		gbl_pnlTop.rowHeights = new int[]{23, 14, 0};
		gbl_pnlTop.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlTop.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		pnlTop.setLayout(gbl_pnlTop);
		
		JLabel label = new JLabel(LabelNumAssignments);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.SOUTH;
		gbc_label.weighty = 1.0;
		gbc_label.weightx = 3.0;
		gbc_label.insets = new Insets(5, 5, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		pnlTop.add(label, gbc_label);

		JLabel lblSpacer1 = new JLabel(Empty);
		GridBagConstraints gbc_lblSpacer1 = new GridBagConstraints();
		gbc_lblSpacer1.weighty = 2.0;
		gbc_lblSpacer1.weightx = 100.0;
		gbc_lblSpacer1.gridheight = 2;
		gbc_lblSpacer1.insets = new Insets(5, 5, 0, 5);
		gbc_lblSpacer1.gridx = 1;
		gbc_lblSpacer1.gridy = 1;
		pnlTop.add(lblSpacer1, gbc_lblSpacer1);

		JLabel lblSpacer2 = new JLabel(Empty);
		GridBagConstraints gbc_lblSpacer2 = new GridBagConstraints();
		gbc_lblSpacer2.weighty = 2.0;
		gbc_lblSpacer2.weightx = 100.0;
		gbc_lblSpacer2.gridheight = 2;
		gbc_lblSpacer2.insets = new Insets(5, 5, 0, 5);
		gbc_lblSpacer2.gridx = 4;
		gbc_lblSpacer2.gridy = 1;
		pnlTop.add(lblSpacer2, gbc_lblSpacer2);

		pnlAssignmentButtons = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.weighty = 1.0;
		gbc_panel_1.weightx = 3.0;
		gbc_panel_1.insets = new Insets(0, 5, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		pnlTop.add(pnlAssignmentButtons, gbc_panel_1);

		JButton btnPlanAssignments = new JButton(LabelPlanAssignments);
		GridBagConstraints gbc_btnPlanAssignments = new GridBagConstraints();
		gbc_btnPlanAssignments.weightx = 1.0;
		gbc_btnPlanAssignments.gridheight = 2;
		gbc_btnPlanAssignments.weighty = 2.0;
		gbc_btnPlanAssignments.fill = GridBagConstraints.BOTH;
		gbc_btnPlanAssignments.insets = new Insets(5, 5, 5, 5);
		gbc_btnPlanAssignments.gridx = 2;
		gbc_btnPlanAssignments.gridy = 0;
		pnlTop.add(btnPlanAssignments, gbc_btnPlanAssignments);
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
		pnlTop.add(btnClearAssignments, gbc_btnClearAssignments);

		JLabel lblSelectPlans = new JLabel(LabelDeploymentPlans);
		GridBagConstraints gbc_lblSelectPlans = new GridBagConstraints();
		gbc_lblSelectPlans.anchor = GridBagConstraints.SOUTH;
		gbc_lblSelectPlans.weighty = 1.0;
		gbc_lblSelectPlans.weightx = 3.0;
		gbc_lblSelectPlans.gridwidth = 3;
		gbc_lblSelectPlans.insets = new Insets(5, 5, 5, 5);
		gbc_lblSelectPlans.gridx = 5;
		gbc_lblSelectPlans.gridy = 0;
		pnlTop.add(lblSelectPlans, gbc_lblSelectPlans);

		btnPrev = new JButton(actionPrevSolution);
		btnPrev.setEnabled(false);
		GridBagConstraints gbc_btnPrev = new GridBagConstraints();
		gbc_btnPrev.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPrev.weighty = 1.0;
		gbc_btnPrev.weightx = 1.0;
		gbc_btnPrev.insets = new Insets(0, 5, 5, 5);
		gbc_btnPrev.gridx = 5;
		gbc_btnPrev.gridy = 1;
		pnlTop.add(btnPrev, gbc_btnPrev);

		btnBest = new JButton(actionBestSolution);
		btnBest.setEnabled(false);
		GridBagConstraints gbc_btnBest = new GridBagConstraints();
		gbc_btnBest.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBest.weighty = 1.0;
		gbc_btnBest.weightx = 1.0;
		gbc_btnBest.insets = new Insets(0, 0, 5, 0);
		gbc_btnBest.gridx = 6;
		gbc_btnBest.gridy = 1;
		pnlTop.add(btnBest, gbc_btnBest);

		btnNext = new JButton(actionNextSolution);
		btnNext.setEnabled(false);
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNext.weighty = 1.0;
		gbc_btnNext.weightx = 1.0;
		gbc_btnNext.insets = new Insets(0, 5, 5, 5);
		gbc_btnNext.gridx = 7;
		gbc_btnNext.gridy = 1;
		pnlTop.add(btnNext, gbc_btnNext);

		JButton btnDeployShips = new JButton(actionDeployShips);
		GridBagConstraints gbc_btnDeployShips = new GridBagConstraints();
		gbc_btnDeployShips.weighty = 2.0;
		gbc_btnDeployShips.weightx = 1.0;
		gbc_btnDeployShips.gridheight = 2;
		gbc_btnDeployShips.insets = new Insets(5, 5, 5, 5);
		gbc_btnDeployShips.fill = GridBagConstraints.VERTICAL;
		gbc_btnDeployShips.gridx = 8;
		gbc_btnDeployShips.gridy = 0;
		pnlTop.add(btnDeployShips, gbc_btnDeployShips);

		sclAssignments = new JScrollPane();
		pnlAssignmentGrid = new JPanel();
		sclAssignments.setViewportView(pnlAssignmentGrid);
		pnlAssignmentGrid.setLayout(new GridLayout(0, 1, 5, 5));
		add(sclAssignments, BorderLayout.CENTER);
		
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
			for (int i = 0; i < Globals.MAX_ASSIGNMENTS; i++) {
				pnlAssignments[i].setAssignment(admiral.getAssignment(i));
			}
			this.admiral.addPropertyChangeListener(this);
		}
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
		if (property == Admiral.PROP_ASSIGNMENTCOUNT) {
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
