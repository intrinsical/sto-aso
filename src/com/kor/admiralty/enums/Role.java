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
package com.kor.admiralty.enums;

import static com.kor.admiralty.ui.resources.Strings.Shared.*;

public enum Role {
	None, Eng, Sci, Tac, Smc;
	
	@Override
	public String toString() {
		return toString(this);
	}
	
	protected static String toString(Role role) {
		switch (role) {
		case Eng: return RoleEng;
		case Sci: return RoleSci;
		case Tac: return RoleTac;
		case Smc: return RoleSmc;
		case None: 
		default: return RoleUnknown;
		}
	}
	
	public static Role fromString(String string) {
		if (string == null) {
	        throw new IllegalArgumentException();
		}
        for (Role role : values()) {
            if (role.toString().equalsIgnoreCase(string)) {
            	return role;
            }
        }
        throw new IllegalArgumentException();
    }
	
}
