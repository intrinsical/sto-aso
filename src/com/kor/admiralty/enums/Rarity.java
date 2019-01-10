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

import static com.kor.admiralty.ui.resources.Swing.*;
import static com.kor.admiralty.ui.resources.Strings.Shared.*;

import java.awt.Color;

public enum Rarity {
	
	None, Common, Uncommon, Rare, VeryRare, UltraRare, Epic;
	
	public Color getColor() {
		return getColor(this);
	}
	
	@Override
	public String toString() {
		return toString(this);
	}
	
	protected static String toString(Rarity rarity) {
		switch (rarity) {
		case Common: return RarityCommon;
		case Uncommon: return RarityUncommon;
		case Rare: return RarityRare;
		case VeryRare: return RarityVeryRare;
		case UltraRare: return RarityUltraRare;
		case Epic: return RarityEpic;
		case None:
		default: return RarityUnknown;
		}
	}
	
	protected static Color getColor(Rarity rarity) {
		switch (rarity) {
		case Uncommon: return ColorUncommon;
		case Rare: return ColorRare;
		case VeryRare: return ColorVeryRare;
		case UltraRare: return ColorUltraRare;
		case Epic: return ColorEpic;
		case None:
		case Common: 
		default: return ColorCommon;
		}
	}
	
	public static Rarity fromString(String string) {
		if (string == null) {
	        throw new IllegalArgumentException();
		}
        for (Rarity rarity : values()) {
            if (rarity.toString().equalsIgnoreCase(string)) {
            	return rarity;
            }
        }
        throw new IllegalArgumentException();
    }
	
}
