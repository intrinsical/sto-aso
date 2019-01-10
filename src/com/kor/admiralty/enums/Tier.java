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

public enum Tier {
	
	None, SmallCraft, Tier1, Tier2, Tier3, Tier4, Tier5, Tier6;
	
	public String getMaintenanceTime() {
		return getMaintenanceTime(this);
	}
	
	@Override
	public String toString() {
		return toString(this);
	}
	
	protected static String getMaintenanceTime(Tier tier) {
		switch (tier) {
		case SmallCraft: return MaintSmallCraft;
		case Tier1: return MaintTier1;
		case Tier2: return MaintTier2;
		case Tier3: return MaintTier3;
		case Tier4: return MaintTier4;
		case Tier5: return MaintTier5;
		case Tier6: return MaintTier6;
		case None:
		default: return MaintUnknown;
		}
	}
	
	protected static String toString(Tier tier) {
		switch (tier) {
		case SmallCraft: return TierSmallCraft;
		case Tier1: return TierOne;
		case Tier2: return TierTwo;
		case Tier3: return TierThree;
		case Tier4: return TierFour;
		case Tier5: return TierFive;
		case Tier6: return TierSix;
		case None:
		default: return TierUnknown;
		}
	}
	
	public static Tier fromString(String string) {
		if (string == null) {
	        throw new IllegalArgumentException();
		}
        for (Tier tier : values()) {
            if (tier.toString().equalsIgnoreCase(string)) {
            	return tier;
            }
        }
        throw new IllegalArgumentException();
    }
	
}
