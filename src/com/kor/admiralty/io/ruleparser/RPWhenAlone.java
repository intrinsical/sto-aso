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
import com.kor.admiralty.rewards.RewardStat;
import com.kor.admiralty.rules.WhenAlone;

public class RPWhenAlone extends RuleParser {

	public static final String regex = "\\+(\\d+) +(All(?: +Stats)?|ENG|TAC|SCI)(?: +and +(ENG|TAC|SCI))? +when +Alone";
	public static final String examples[] = new String[] { 
		"+10 ENG and TAC when Alone", 
		"+15 All Stats when Alone", 
		"+15 ENG and SCI when Alone", 
		"+20 All Stats when Alone", 
		"+20 ENG and SCI when Alone", 
		"+20 SCI and TAC when Alone", 
		"+20 TAC and ENG when Alone", 
		"+35 ENG when Alone", 
		"+35 SCI when Alone", 
		"+35 TAC and ENG when Alone", 
		"+35 TAC when Alone",
		"+50 ALL when Alone",
	};

	public RPWhenAlone() {
		super(regex, examples);
	}

	@Override
	protected SpecialAbility match(Matcher matcher) {
		String number = matcher.group(1);
		String type1 = matcher.group(2);
		String type2 = matcher.group(3);
		int value = 0;
		int engValue = 0;
		int tacValue = 0;
		int sciValue = 0;
		
		if (number != null) {
			value = Integer.parseInt(number);
		}
		if (type1.toLowerCase().startsWith("all")) {
			engValue = value;
			tacValue = value;
			sciValue = value;
		}
		else if (type1.equalsIgnoreCase("eng")) {
			engValue = value;
		}
		else if (type1.equalsIgnoreCase("tac")) {
			tacValue = value;
		}
		else if (type1.equalsIgnoreCase("sci")) {
			sciValue = value;
		}
		if (type2 != null) {
			if (type2.equalsIgnoreCase("eng")) {
				engValue = value;
			}
			else if (type2.equalsIgnoreCase("tac")) {
				tacValue = value;
			}
			else if (type2.equalsIgnoreCase("sci")) {
				sciValue = value;
			}
		}
		
		return new WhenAlone(new RewardStat(engValue, tacValue, sciValue));
	}

}
