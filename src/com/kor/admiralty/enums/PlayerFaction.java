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
package com.kor.admiralty.enums;

import static com.kor.admiralty.ui.resources.Strings.Shared.*;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration of all player factions in STO
 * 
 *
 */
@XmlType
@XmlEnum
public enum PlayerFaction {
	Federation, Klingon, RomulanFed, RomulanKDF, JemHadarFed, JemHadarKDF;
	
	@Override
	public String toString() {
		return toString(this);
	}
	
	protected static String toString(PlayerFaction faction) {
		switch (faction) {
		case Federation: return PlayerFederation;
		case Klingon: return PlayerKlingon;
		case RomulanFed: return PlayerRomulanFed;
		case RomulanKDF: return PlayerRomulanKDF;
		case JemHadarFed: return PlayerJemHadarFed;
		case JemHadarKDF: return PlayerJemHadarKDF;
		default: return PlayerUnknown;
		}
	}
	
	public static PlayerFaction fromString(String string) {
		if (string == null) {
	        throw new IllegalArgumentException();
		}
        for (PlayerFaction faction : values()) {
            if (faction.toString().equalsIgnoreCase(string)) {
            	return faction;
            }
        }
        throw new IllegalArgumentException();
    }
	
}
