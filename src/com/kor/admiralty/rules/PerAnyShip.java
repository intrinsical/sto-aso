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
package com.kor.admiralty.rules;

import com.kor.admiralty.beans.Assignment;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.beans.AssignmentSolution;
import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.rewards.Reward;
import com.kor.admiralty.ui.resources.Strings;

public class PerAnyShip extends SpecialAbility {
	
	protected boolean notSmallCraft;
	
	public PerAnyShip(Reward reward) {
		this(reward, false);
	}

	public PerAnyShip(Reward reward, boolean notSmallCraft) {
		super(reward);
		this.notSmallCraft = notSmallCraft;
	}

	@Override
	public void procShip(AssignmentSolution solution, Ship source, Ship ship) {
		if (ship == null) return;
		
		if (source.getRole() == Role.Smc) {
			// Source ship is a small craft
			if (notSmallCraft && ship.getRole() == Role.Smc) return;
			reward.apply(null, solution);
		}
		else {
			// Source ship is a standard ship
			if (ship.getRole() == Role.Smc) return;
			reward.apply(null, solution);
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
			PerAnyShip pas = (PerAnyShip)obj;
			return notSmallCraft == pas.notSmallCraft; 
		}
		return false;
	}

	@Override
	public String toParamString() {
		return Strings.toFunctionString(this, String.format("notSmallCraft: %b,  %s", notSmallCraft, reward));
	}

}
