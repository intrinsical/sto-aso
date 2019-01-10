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
package com.kor.admiralty.rules;

import java.util.Arrays;

import com.kor.admiralty.beans.Assignment;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.beans.AssignmentSolution;
import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.rewards.Reward;
import com.kor.admiralty.ui.resources.Strings;

public class PerShipCategory extends SpecialAbility {
	
	protected Role categories[];
	
	public PerShipCategory(Reward reward, Role ... categories) {
		super(reward);
		this.categories = categories;
	}

	@Override
	public void procShip(AssignmentSolution solution, Ship source, Ship ship) {
		if (ship == null) return;
		for (Role category : categories) {
			if (ship.getRole() == category) {
				reward.apply(null, solution);
			}
		}
	}
	
	@Override 
	public void procAssignment(AssignmentSolution solution, Assignment assignment) {
	}
	
	@Override
	public void procCriticals(AssignmentSolution solution, Assignment assignment) {
	}

	@Override
	public void procMaintenanceReduction(AssignmentSolution solution, Assignment assignment) {
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			PerShipCategory psc = (PerShipCategory)obj;
			if (categories.length != psc.categories.length) return false;
			for (int i = 0; i < categories.length; i++) {
				Role role1 = categories[i];
				Role role2 = psc.categories[i];
				if (!role1.equals(role2)) return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public String toParamString() {
		return Strings.toFunctionString(this, String.format("%s, %s", Arrays.toString(categories), reward));
	}

}
