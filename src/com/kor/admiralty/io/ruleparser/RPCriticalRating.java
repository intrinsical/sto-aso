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
import com.kor.admiralty.rewards.RewardMultiplyCritRate;
import com.kor.admiralty.rules.ProcCritRate;

public class RPCriticalRating extends RuleParser {

	public static final String regex = "(\\d+(?:\\.\\d+)?)x +Critical +Rating +from +(All +Stats|Events?|ENG|TAC|SCI)(?: +(?:or|and) +(ENG|TAC|SCI))?( +\\(Not +Small +Craft\\))?"; 
	public static final String examples[] = new String[] { 
		"1.25x Critical Rating from All Stats",
		"1.5x Critical Rating from ENG and SCI", 
		"1.5x Critical Rating from ENG and TAC",
		"1.5x Critical Rating from ENG or SCI", 
		"1.5x Critical Rating from ENG or TAC",
		"1.5x Critical Rating from SCI and TAC", 
		"1.5x Critical Rating from SCI or TAC",
		"1.5x Critical Rating from TAC and ENG", 
		"1.5x Critical Rating from TAC and SCI",
		"2.5x Critical Rating from ENG", 
		"2.5x Critical Rating from SCI", 
		"2.5x Critical Rating from TAC",
		"2x Critical Rating from All Stats", 
		"2x Critical Rating from Events", 
		"3x Critical Rating from All Stats",
		"5x Critical Rating from ALL Stats", 
		"5x Critical Rating from ALL Stats (Not Small Craft)", };

	public RPCriticalRating() {
		super(regex, examples);
	}

	@Override
	protected SpecialAbility match(Matcher matcher) {
		String number = matcher.group(1);
		String type1 = matcher.group(2);
		String type2 = matcher.group(3);
		//boolean notSmallCraft = matcher.group(4) != null;
		double value = 1;
		double engValue = 1;
		double tacValue = 1;
		double sciValue = 1;
		double baseValue = 1;

		if (number != null) {
			value = Double.parseDouble(number);
		}
		if (type1 != null) {
			type1 = type1.toLowerCase();
			if (type1.equals("eng")) {
				engValue = value;
			}
			else if (type1.equals("tac")) {
				tacValue = value;
			}
			else if (type1.equals("sci")) {
				sciValue = value;
			}
			else if (type1.equals("all stats")) {
				engValue = value;
				tacValue = value;
				sciValue = value;
			} else if (type1.startsWith("event")) {
				baseValue = value;
			}
		}
		if (type2 != null) {
			type2 = type2.toLowerCase();
			if (type2.equals("eng")) {
				engValue = value;
			}
			else if (type2.equals("tac")) {
				tacValue = value;
			}
			else if (type2.equals("sci")) {
				sciValue = value;
			}
		}

		return new ProcCritRate(new RewardMultiplyCritRate(engValue, tacValue, sciValue, baseValue));
	}

}
