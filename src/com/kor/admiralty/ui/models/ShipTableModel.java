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
package com.kor.admiralty.ui.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.Role;

public class ShipTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1744691456642188110L;
	
	public static final int COL_SHIP = -1;
	public static final int COL_NAME = 0;
	public static final int COL_ROLE = 1;
	public static final int COL_ENG = 2;
	public static final int COL_TAC = 3;
	public static final int COL_SCI = 4;
	public static final int COL_BONUS = 5;
	
	public static final String[] columnNames = new String[] {"Name", "Role", "Eng", "Tac", "Sci", "Bonus"};
	@SuppressWarnings("rawtypes")
	public static final Class[] columnClasses = new Class[] {String.class, Role.class, int.class, int.class, int.class, String.class};
	
	public static final int preferredWidths[] = new int[] {400, 100, 50, 50, 50, 300};
	
	
	protected List<Ship> ships;
	
	public ShipTableModel() {
		this.ships = new ArrayList<Ship>();
	}
	
	public ShipTableModel(Collection<Ship> ships) {
		this();
		this.ships.addAll(ships);
	}
	
	public void addShips(Collection<Ship> ships) {
		this.ships.addAll(ships);
		fireTableDataChanged();
	}
	
	public void clearShips() {
		this.ships.clear();
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return ships.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(int col) {
        return columnClasses[col];
    }

	@Override
	public Object getValueAt(int row, int column) {
		if (row >= ships.size()) return null;
		Ship ship = ships.get(row);
		if (column == COL_SHIP) {
			return ship;
		}
		else if (column == COL_NAME) {
			return ship.getName();
		}
		else if (column == COL_ROLE) {
			return ship.getRole();
		}
		else if (column == COL_ENG) {
			return ship.getEng();
		}
		else if (column == COL_TAC) {
			return ship.getTac();
		}
		else if (column == COL_SCI) {
			return ship.getSci();
		}
		else if (column == COL_BONUS) {
			return ship.getSpecialAbility().toString();
		}
		else return null;
	}

}
