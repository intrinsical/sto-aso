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
package com.kor.admiralty.io.ruleparser;

import java.util.regex.Matcher;

import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.rewards.RewardStat;
import com.kor.admiralty.rules.PerAnyShip;
import com.kor.admiralty.rules.PerShipCategory;

public class RPPerShip extends RuleParser {
	
	public static final String regex = "\\+(\\d+) +(All Stats|ENG|TAC|SCI)(?: +and +(ENG|TAC|SCI))? +(?:per|from) +(Any Ship|EngShip|TacShip|SciShip)(?: +(?:or|and) +(EngShip|TacShip|SciShip))?( +\\(Not Small Craft\\))?";
	public static final String examples[] = new String[] {
		"+10 ALL Stats per ANY Ship (Not Small Craft)", 
		"+10 ENG per EngShip", 
		"+10 ENG per SciShip", 
		"+10 ENG per TacShip", 
		"+10 SCI per EngShip", 
		"+10 SCI per Sciship", 
		"+10 Sci per TacShip", 
		"+10 TAC per EngShip", 
		"+10 TAC per SciShip", 
		"+10 TAC per TacShip", 
		"+20 SCI per Any Ship", 
		"+3 ENG and TAC per SciShip", 
		"+5 ENG and SCI from Any Ship", 
		"+5 ENG and SCI per SciShip or EngShip", 
		"+5 ENG and TAC per EngShip or TacShip", 
		"+5 SCI and ENG per EngShip and TacShip", 
		"+5 SCI and ENG per EngShip or TacShip", 
		"+5 SCI and ENG per SciShip or TacShip", 
		"+5 SCI and TAC per SciShip or TacShip", 
		"+5 SCI per EngShip", 
		"+5 SCI per TacShip", 
		"+5 TAC and ENG per EngShip or TacShip", 
		"+5 TAC and ENG per TacShip or EngShip", 
		"+5 TAC and SCI per SciShip or EngShip", 
		"+8 ENG and SCI per EngShip", 
		"+8 ENG and SCI per TacShip", 
		"+8 Eng and Tac per EngShip", 
		"+8 ENG and TAC per SciShip", 
		"+8 ENG and TAC per TacShip", 
		"+8 ENG per Any Ship", 
		"+8 ENG per EngShip or TacShip", 
		"+8 ENG per SciShip or TacShip", 
		"+8 ENG per TacShip or SciShip", 
		"+8 SCI and TAC per EngShip", 
		"+8 SCI and TAC per TacShip", 
		"+8 SCI from Any Ship", 
		"+8 SCI per Any Ship", 
		"+8 SCI per EngShip or TacShip", 
		"+8 SCI per SciShip or EngShip", 
		"+8 TAC and ENG per SciShip", 
		"+8 TAC and SCI per EngShip", 
		"+8 TAC per Any Ship", 
		"+8 TAC per EngShip or SciShip", 
		"+8 TAC per EngShip or TacShip", 
		"+8 TAC per SciShip or TacShip",
		"+15 ENG and TAC per Any Ship",
		"+20 TAC per Any Ship",
	};
	
	public RPPerShip() {
		super(regex, examples);
	}

	@Override
	protected SpecialAbility match(Matcher matcher) {
		String number = matcher.group(1);
		String stat1 = matcher.group(2);
		String stat2 = matcher.group(3);
		String type1 = matcher.group(4);
		String type2 = matcher.group(5);
		boolean notSmallCraft = matcher.group(6) != null;
		int value = 0;
		int engValue = 0;
		int tacValue = 0;
		int sciValue = 0;
		
		if (number != null) {
			value = Integer.parseInt(number);
		}
		if (stat1.equalsIgnoreCase("all stats")) {
			engValue = value;
			tacValue = value;
			sciValue = value;
		}
		else if (stat1.equalsIgnoreCase("eng")) {
			engValue = value;
		}
		else if (stat1.equalsIgnoreCase("tac")) {
			tacValue = value;
		}
		else if (stat1.equalsIgnoreCase("sci")) {
			sciValue = value;
		}
		if (stat2 != null) {
			if (stat2.equalsIgnoreCase("eng")) {
				engValue = value;
			}
			else if (stat2.equalsIgnoreCase("tac")) {
				tacValue = value;
			}
			else if (stat2.equalsIgnoreCase("sci")) {
				sciValue = value;
			}
		}
		RewardStat reward = new RewardStat(engValue, tacValue, sciValue);
		if (type1.equalsIgnoreCase("any ship")) {
			return new PerAnyShip(reward, notSmallCraft);
		}
		else {
			Role role1 = getRole(type1);
			if (type2 == null) {
				return new PerShipCategory(reward, role1);
			}
			else {
				Role role2 = getRole(type2);
				return new PerShipCategory(reward, role1, role2);
			}
		}
	}

}
