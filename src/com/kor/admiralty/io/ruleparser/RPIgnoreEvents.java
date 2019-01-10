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
import com.kor.admiralty.rewards.RewardIgnoreEventStat;
import com.kor.admiralty.rules.AlwaysApply;

public class RPIgnoreEvents extends RuleParser {
	
	public static final String examples[] = new String[] {	
		"Ignore +/- ENG from Events", 
		"Ignore +/- SCI from Events", 
		"Ignore +/- TAC from Events", 
		"Ignores +/- ENG from Events", 
		"Ignores +/- SCI from Events", 
		"Ignores +/- TAC from Events", 
	};
	
	public RPIgnoreEvents() {
		super("Ignores? +\\+/\\- +(ENG|TAC|SCI) +from +Events?", examples);
	}

	@Override
	protected SpecialAbility match(Matcher matcher) {
		String param = matcher.group(1);
		if (param.equalsIgnoreCase("ENG")) return new AlwaysApply(new RewardIgnoreEventStat(true, false, false));
		else if (param.equalsIgnoreCase("TAC")) return new AlwaysApply(new RewardIgnoreEventStat(false, true, false));
		else if (param.equalsIgnoreCase("SCI")) return new AlwaysApply(new RewardIgnoreEventStat(false, false, true));
		return null;
	}
	
}
