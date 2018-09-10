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
import com.kor.admiralty.rewards.RewardIgnoreEventStat;
import com.kor.admiralty.rules.AlwaysApply;

public class RPIgnoreAllEventModifiers extends RuleParser {
	
	public static final String examples[] = new String[] {
		"Ignores ALL Event Modifiers", 
		"Ignores ALL Event Modifiers (Not Small Craft)"
	};
	
	public RPIgnoreAllEventModifiers() {
		super("Ignores? +ALL +Event +Modifiers( +\\(Not +Small +Craft\\))?", examples);
	}

	@Override
	protected SpecialAbility match(Matcher matcher) {
		if (matcher.group(1) == null) {
			return new AlwaysApply(new RewardIgnoreEventStat(true, true, true, false));
		}
		else {
			return new AlwaysApply(new RewardIgnoreEventStat(true, true, true, true));
		}
	}
	
	public static void main(String args[]) {
		RPIgnoreAllEventModifiers parser = new RPIgnoreAllEventModifiers();
		parser.test();
	}

}
