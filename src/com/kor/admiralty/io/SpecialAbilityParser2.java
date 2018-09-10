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
package com.kor.admiralty.io;

import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.io.ruleparser.RPCriticalRating;
import com.kor.admiralty.io.ruleparser.RPIgnoreAllEventModifiers;
import com.kor.admiralty.io.ruleparser.RPIgnoreEvents;
import com.kor.admiralty.io.ruleparser.RPMaintenance;
import com.kor.admiralty.io.ruleparser.RPPerShip;
import com.kor.admiralty.io.ruleparser.RPWhenAlone;
import com.kor.admiralty.io.ruleparser.RuleParser;
import com.kor.admiralty.rewards.RewardNothing;
import com.kor.admiralty.rules.And;
import com.kor.admiralty.rules.NoOp;

public class SpecialAbilityParser2 {
	
	public static final RuleParser ruleParsers[] = new RuleParser[] {
		new RPIgnoreEvents(), 
		new RPIgnoreAllEventModifiers(),
		new RPCriticalRating(), /* Need support for (Not Small Craft) */
		new RPMaintenance(),
		new RPWhenAlone(),
		new RPPerShip(),
	};
	protected static final NoOp NOOP = new NoOp(new RewardNothing());
	
	public static SpecialAbility parse(String text) {
		SpecialAbility ability = parseRuleType(text);
		ability.setDescription(text);
		return ability;
	}
	
	protected static SpecialAbility parseRuleType(String text) {
		text = text.trim();
		if (text.contains("and")) {
			// Special case for parsing U.S.S. Pastak's special ability
			String parts[] = text.split("and");
			if (parts.length == 2) {
				SpecialAbility ability1 = parseRuleType(parts[0]);
				if (!(ability1 instanceof NoOp)) {
					SpecialAbility ability2 = parseRuleType(parts[1]);
					if (!(ability2 instanceof NoOp)) {
						return new And(ability1, ability2);
					}
				}
			}
		}
		for (RuleParser ruleParser : ruleParsers) {
			SpecialAbility ability = ruleParser.parse(text);
			if (ability != null) return ability;
		}
		return NOOP;
	}
	
}
