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

/**
 * Enumeration of all admiralty assignment rewards.
 * This may not be a complete list of all possible rewards. 
 */
public enum EventReward {
	None, EcS, EcM, EcL, DilithiumS, DilithiumM, DilithiumL, ResearchC, ResearchU, ResearchR, ResearchVR, ResearchE, Maintenance50, Maintenance100;
	
	/**
	 * Returns reward description
	 */
	@Override
	public String toString() {
		return toString(this);
	}
	
	/**
	 * Utility function that returns a description of the reward.
	 * @param reward
	 * @return
	 */
	protected static String toString(EventReward reward) {
		switch (reward) {
		case EcS: return "Small Energy Credit Package";
		case EcM: return "Medium Energy Credit Package";
		case EcL: return "Large Energy Credit Package";
		case DilithiumS: return "Small Dilithium Ore Package";
		case DilithiumM: return "Medium Dilithium Ore Package";
		case DilithiumL: return "Large Dilithium Ore Package";
		case ResearchC: return "Common R&D Materials Crate";
		case ResearchU: return "Uncommon R&D Materials Crate";
		case ResearchR: return "Rare R&D Materials Crate";
		case ResearchVR: return "Very Rare R&D Materials Crate";
		case ResearchE: return "Epic R&D Materials Crate";
		case None:
		default: return "None";
		}
	}
}
