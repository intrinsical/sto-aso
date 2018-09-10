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
package com.kor.admiralty.io.ruleparser;

import java.util.regex.Matcher;

import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.rewards.RewardMaintenanceReduction;
import com.kor.admiralty.rules.PerAnyShip;
import com.kor.admiralty.rules.PerShipCategory;

public class RPMaintenance extends RuleParser {
	
	public static final String examples[] = new String[] {
		"-10% Maintenance per EngShip or SciShip", 
		"-10% Maintenance per SciShip or TacShip", 
		"-10% Maintenance per TacShip or EngShip", 
		"-10% Maintenance per TacShip or SciShip", 
		"-25% Maintenance per ANY Ship (Not Small Craft)", 
		"-25% Maintenance per EngShip", 
		"-25% Maintenance per SciShip", 
		"-25% Maintenance per TacShip", 
		"-5% Maintenance per Any Ship", 
		"-5% Maintenance per Ship", 
	}; 
	
	public RPMaintenance() {
		super("\\-(\\d+)\\% +Maintenance +per +(?:(Ship|Any +Ship|EngShip|TacShip|SciShip)(?: +(?:or|and) +(EngShip|TacShip|SciShip))?)( +\\(Not +Small +Craft\\))?", examples);
	}

	@Override
	protected SpecialAbility match(Matcher matcher) {
		String number = matcher.group(1);
		String type1 = matcher.group(2);
		String type2 = matcher.group(3);
		boolean notSmallCraft = matcher.group(4) != null;
		double value = 0;
		
		if (number != null) {
			value = Double.parseDouble(number) / 100.0d;
		}
		if (type1.equalsIgnoreCase("ship") || type1.equalsIgnoreCase("any ship")) {
			return new PerAnyShip(new RewardMaintenanceReduction(value), notSmallCraft);
		}
		else {
			Role role1 = getRole(type1);
			if (type2 == null) {
				return new PerShipCategory(new RewardMaintenanceReduction(value), role1);
			}
			else {
				Role role2 = getRole(type2);
				return new PerShipCategory(new RewardMaintenanceReduction(value), role1, role2);
			}
		}
	}

}
