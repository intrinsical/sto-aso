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

import com.kor.admiralty.rewards.Reward;

public abstract class SpecialAbility implements Comparable<SpecialAbility> {
	
	public static final String STR_UNKNOWN = "Unknown".intern();
	
	protected Reward reward;
	protected String desc;
	
	public SpecialAbility(Reward reward) {
		this.reward = reward;
		this.desc = STR_UNKNOWN;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public void setDescription(String desc) {
		this.desc = desc;
	}
	
	public abstract void procShip(AssignmentSolution solution, Ship source, Ship target);
	public abstract void procAssignment(AssignmentSolution solution, Assignment assignment);
	public abstract void procCriticals(AssignmentSolution solution, Assignment assignment);
	public abstract void procMaintenanceReduction(AssignmentSolution solution, Assignment assignment);
	public abstract String toParamString();
	
	@Override
	public String toString() {
		if (desc == STR_UNKNOWN) {
			return toParamString();
		}
		else {
			return desc;
		}
	}

	@Override
	public int compareTo(SpecialAbility ability) {
		return getDescription().compareTo(ability.getDescription());
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!getClass().equals(obj.getClass())) return false;
		if (obj instanceof SpecialAbility) {
			SpecialAbility ability = (SpecialAbility)obj;
			if (reward == ability.reward) return true;
			if (reward.equals(ability.reward)) return true;
		}
		return false;
	}

}
