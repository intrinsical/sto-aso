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
package com.kor.admiralty.enums;

import java.util.Comparator;

import com.kor.admiralty.beans.LeastUsedShipComparator;
import com.kor.admiralty.beans.MostUsedShipComparator;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.beans.ShipComparator;

public enum ShipSortOrder {
	
	Default(new ShipComparator()), MostUsed(new MostUsedShipComparator()), LeastUsed(new LeastUsedShipComparator());
	
	protected Comparator<Ship> comparator;
	
	ShipSortOrder(Comparator<Ship> comparator) {
		this.comparator = comparator;
	}
	
	public Comparator<Ship> comparator() {
		return comparator;
	}

}
