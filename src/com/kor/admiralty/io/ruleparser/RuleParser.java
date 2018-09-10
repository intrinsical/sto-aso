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
import java.util.regex.Pattern;

import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.enums.Role;

public abstract class RuleParser {
	
	protected Pattern pattern;
	protected String examples[];
	
	protected RuleParser(String regex, String examples[]) {
		this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		this.examples = examples;
	}
	
	public SpecialAbility parse(String text) {
		Matcher matcher = pattern.matcher(text);
		if (matcher.matches()) {
			return match(matcher);
		}
		return null;
	}
	
	public String[] examples() {
		return examples;
	}
	
	protected abstract SpecialAbility match(Matcher matcher);
	
	protected void debug(String example) {
		Matcher matcher = pattern.matcher(example);
		if (!matcher.matches()) {
			System.out.println("    No match found");
		}
		else {
			int count = matcher.groupCount();
			for (int i = 1; i <= count; i++) {
				String group = matcher.group(i);
				System.out.print("     " + i + ": " + group);
			}
			System.out.println();
		}
	}

	public boolean test() {
		boolean passed = true;
		for (String example : examples) {
			SpecialAbility ability = parse(example);
			if (ability != null) {
				System.out.println("Pass: " + example + " -> " + ability.toParamString());
			}
			else {
				System.out.println("FAIL: " + example);
				debug(example);
				passed = false;
			}
		}
		return passed;
	}
	
	protected Role getRole(String text) {
		if (text == null) return Role.None;
		else if (text.equalsIgnoreCase("engship")) return Role.Eng;
		else if (text.equalsIgnoreCase("tacship")) return Role.Tac;
		else if (text.equalsIgnoreCase("sciship")) return Role.Sci;
		else return Role.None;
	}
	
}
