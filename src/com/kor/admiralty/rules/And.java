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

import com.kor.admiralty.beans.Assignment;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.beans.AssignmentSolution;
import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.ui.resources.Strings;

public class And extends SpecialAbility {
	
	protected SpecialAbility ability1;
	protected SpecialAbility ability2;
	
	public And(SpecialAbility ability1, SpecialAbility ability2) {
		super(null);
		this.ability1 = ability1;
		this.ability2 = ability2;
	}

	@Override
	public void procShip(AssignmentSolution solution, Ship source, Ship ship) {
		ability1.procShip(solution, source, ship);
		ability2.procShip(solution, source, ship);
	}

	@Override
	public void procAssignment(AssignmentSolution solution, Assignment assignment) {
		ability1.procAssignment(solution, assignment);
		ability2.procAssignment(solution, assignment);
	}

	@Override
	public void procCriticals(AssignmentSolution solution, Assignment assignment) {
		ability1.procCriticals(solution, assignment);
		ability2.procCriticals(solution, assignment);
	}

	@Override
	public void procMaintenanceReduction(AssignmentSolution solution, Assignment assignment) {
		ability1.procMaintenanceReduction(solution, assignment);
		ability2.procMaintenanceReduction(solution, assignment);
	}

	@Override
	public String toParamString() {
		return Strings.toFunctionString(this, String.format("%s, %s, %s", ability1, ability2, reward));
	}

}
