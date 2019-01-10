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

import static com.kor.admiralty.ui.resources.Strings.AdmiraltyConsole.Title;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.io.Datastore;
import com.kor.admiralty.ui.components.JColumnList;
import com.kor.admiralty.ui.models.ShipListModel;
import com.kor.admiralty.ui.renderers.StarshipTraitCellRenderer;
import com.kor.admiralty.ui.resources.Images;
import com.kor.admiralty.ui.resources.Swing;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JList;

public class TraitViewer extends JFrame implements Runnable {
	
	private static final long serialVersionUID = -1956005915682128915L;
	
	private static final TraitViewer VIEWER = new TraitViewer();

	public static void main(String[] args) {
		EventQueue.invokeLater(VIEWER);
		Swing.overrideComboBoxMouseWheel();
	}
	
	protected JList<Ship> traitsList;
	protected ShipListModel traitsModel;
	protected StarshipTraitCellRenderer cellRenderer;
	
	public TraitViewer() {
		Swing.setLookAndFeel();
		setTitle(Title);
		setIconImage(Images.IMG_ASO);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane traitsScroll = new JScrollPane();
		getContentPane().add(traitsScroll);
		
		cellRenderer = new StarshipTraitCellRenderer();
		traitsModel = new ShipListModel();
		traitsList = new JColumnList<Ship>(traitsModel);
		traitsList.setLayoutOrientation(JList.VERTICAL);
		traitsList.setCellRenderer(cellRenderer);
		traitsScroll.setViewportView(traitsList);
		traitsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		List<Ship> ships = new ArrayList<Ship>();
		for (Ship ship : Datastore.getAllShips().values()) {
			if (ship.hasTrait()) {
				ships.add(ship);
			}
		}
		traitsModel.addShips(ships);
	}

	@Override
	public void run() {
		VIEWER.setVisible(true);
		VIEWER.toFront();
		VIEWER.repaint();
	}

}
