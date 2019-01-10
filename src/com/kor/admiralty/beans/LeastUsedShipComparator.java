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
package com.kor.admiralty.beans;

import java.util.Comparator;

public class LeastUsedShipComparator implements Comparator<Ship> {
	
	@Override
	public int compare(Ship s1, Ship s2) {
		int compare = s1.getUsageCount() - s2.getUsageCount();
		if (compare != 0) return compare;
		compare = s1.getTier().compareTo(s2.getTier());
		if (compare != 0) return compare;
		compare = s1.getRarity().compareTo(s2.getRarity());
		if (compare != 0) return compare;
		compare = s1.getRole().compareTo(s2.getRole());
		if (compare != 0) return compare;
		return s1.getName().compareTo(s2.getName());
	}

}
