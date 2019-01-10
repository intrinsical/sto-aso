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
package com.kor.admiralty.io;

import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.enums.RuleType;
import com.kor.admiralty.rules.And;
import com.kor.admiralty.rules.NoOp;

public class SpecialAbilityParser {
	
	public static SpecialAbility parse(String text) {
		String temp = text.trim().toLowerCase();
		SpecialAbility ability = parseRuleType(temp);
		ability.setDescription(text);
		return ability;
	}
	
	protected static SpecialAbility parseRuleType(String text) {
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
		if (text.contains("when")) {
			if (text.endsWith("when alone")) {
				return parseReward(text, RuleType.WhenAlone);
			}
			else {
				return RuleType.Unknown.rewardNothing();
			}
		}
		else if (text.contains("x critical rating")) {
			return parseMultiplier(text, RuleType.MultiplyCriticalRate);
		}
		else if (text.contains("x bonus")) {
			return parseMultiplier(text, RuleType.MultiplyBonus);
		}
		else if (text.contains("per")) {
			if (text.endsWith("per ship")) {
				return parseReward(text, RuleType.PerShip);
			}
			else if (text.endsWith("per any ship (not small craft)")) {
				return parseReward(text, RuleType.PerAnyShipNotSmallCraft);
			}
			else if (text.endsWith("per any ship")) {
				return parseReward(text, RuleType.PerAnyShip);
			}
			else if (text.endsWith("per engship")) {
				return parseReward(text, RuleType.PerEng);
			}
			else if (text.endsWith("per tacship")) {
				return parseReward(text, RuleType.PerTac);
			}
			else if (text.endsWith("per sciship")) {
				return parseReward(text, RuleType.PerSci);
			}
			else if (text.endsWith("per engship or tacship") || text.endsWith("per tacship or engship")) {
				return parseReward(text, RuleType.PerEngTac);
			}
			else if (text.endsWith("per engship or sciship") || text.endsWith("per sciship or engship")) {
				return parseReward(text, RuleType.PerEngSci);
			}
			else if (text.endsWith("per tacship or sciship") || text.endsWith("per sciship or tacship")) {
				return parseReward(text, RuleType.PerTacSci);
			}
			else if (text.endsWith("per cruiser")) {
				return parseReward(text, RuleType.PerCruiser);
			}
			else {
				return RuleType.Unknown.rewardNothing();
			}
		}
		else if (text.contains("ignore")) {
			if (text.contains("+/- eng")) {
				return RuleType.Ignore.rewardIgnoreEng();
			}
			else if (text.contains("+/- tac")) {
				return RuleType.Ignore.rewardIgnoreTac();
			}
			else if (text.contains("+/- sci")) {
				return RuleType.Ignore.rewardIgnoreSci();
			}
			else if (text.contains("all event modifiers (not small craft)")) {
				return RuleType.Ignore.rewardIgnoreAllEventModifiers(true);
			}
			else if (text.contains("all event modifiers")) {
				return RuleType.Ignore.rewardIgnoreAllEventModifiers(false);
			}
			else {
				return RuleType.Unknown.rewardNothing();
			}
		}
		else if (text.contains("from")) {
			if (text.endsWith("from events")) {
				return parseReward(text, RuleType.FromEvent);
			}
			else if (text.endsWith("from any ship")) {
				return parseReward(text, RuleType.FromAnyShip);
			}
			else if (text.endsWith("from eng")) {
				return parseReward(text, RuleType.FromEng);
			}
			else if (text.endsWith("from tac")) {
				return parseReward(text, RuleType.FromTac);
			}
			else if (text.endsWith("from sci")) {
				return parseReward(text, RuleType.FromSci);
			}
			else if (text.endsWith("from eng and tac") || text.endsWith("from tac and eng")) {
				return parseReward(text, RuleType.FromEngTac);
			}
			else if (text.endsWith("from eng and sci") || text.endsWith("from sci and eng")) {
				return parseReward(text, RuleType.FromEngSci);
			}
			else if (text.endsWith("from tac and sci") || text.endsWith("from sci and tac")) {
				return parseReward(text, RuleType.FromTacSci);
			}
			else if (text.endsWith("from all stats")) {
				return parseReward(text, RuleType.FromAllStats);
			}
			else {
				return RuleType.Unknown.rewardNothing();
			}
		}
		else {
			return RuleType.Unknown.rewardNothing();
		}
	}
	
	protected static SpecialAbility parseMultiplier(String text, RuleType rule) {
		double multiplier = Double.parseDouble(text.substring(0, text.indexOf('x')));;
		if (text.endsWith("from events")) {
			return rule.rewardMultiplyEventCritRate(multiplier);
		}
		else if (text.endsWith("from eng")) {
			return rule.rewardMultiplyCritRate(multiplier, 0, 0);
		}
		else if (text.endsWith("from tac")) {
			return rule.rewardMultiplyCritRate(0, multiplier, 0);
		}
		else if (text.endsWith("from sci")) {
			return rule.rewardMultiplyCritRate(0, 0, multiplier);
		}
		else if (text.endsWith("from eng and tac") || text.endsWith("from tac and eng") || text.endsWith("from eng or tac") || text.endsWith("from tac or eng")) {
			return rule.rewardMultiplyCritRate(multiplier, multiplier, 0);
		}
		else if (text.endsWith("from eng and sci") || text.endsWith("from sci and eng") || text.endsWith("from eng or sci") || text.endsWith("from sci or eng")) {
			return rule.rewardMultiplyCritRate(multiplier, 0, multiplier);
		}
		else if (text.endsWith("from tac and sci") || text.endsWith("from sci and tac") || text.endsWith("from tac or sci") || text.endsWith("from sci or tac")) {
			return rule.rewardMultiplyCritRate(0, multiplier, multiplier);
		}
		else if (text.endsWith("from all stats")) {
			return rule.rewardMultiplyCritRate(multiplier, multiplier, multiplier);
		}
		return RuleType.Unknown.rewardNothing();
	}
	
	protected static SpecialAbility parseReward(String text, RuleType rule) {
		if (text.contains("ignore")) {
			if (text.contains("+/- eng")) {
				return rule.rewardIgnoreEng();
			}
			else if (text.contains("+/- tac")) {
				return rule.rewardIgnoreTac();
			}
			else if (text.contains("+/- sci")) {
				return rule.rewardIgnoreSci();
			}
			else {
				return RuleType.Unknown.rewardNothing();
			}
		}
		else if (text.contains("maintenance")) {
			int index = text.indexOf('%');
			double value = Double.parseDouble(text.substring(0, index));
			return rule.rewardMaintenance(value);
		}
		else if (text.startsWith("+")) {
			int index = text.indexOf(' ');
			int value = Integer.parseInt(text.substring(0, index));
			String string = text.substring(index).trim();
			if (string.startsWith("eng and tac") || string.startsWith("tac and eng")) {
				return rule.rewardTacEng(value);
			}
			else if (string.startsWith("eng and sci") || string.startsWith("sci and eng")) {
				return rule.rewardSciEng(value);
			}
			else if (string.startsWith("tac and sci") || string.startsWith("sci and tac")) {
				return rule.rewardSciTac(value);
			}
			else if (string.startsWith("eng")) {
				return rule.rewardEng(value);
			}
			else if (string.startsWith("tac")) {
				return rule.rewardTac(value);
			}
			else if (string.startsWith("sci")) {
				return rule.rewardSci(value);
			}
			else if (string.startsWith("all")) {
				return rule.rewardAll(value);
			}
			else {
				return rule.rewardNothing();
			}
		}
		else {
			return RuleType.Unknown.rewardAll(0);
		}
	}
	
}
