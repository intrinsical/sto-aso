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
package com.kor.admiralty.beans;

import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.enums.ShipFaction;
import com.kor.admiralty.enums.Tier;

public interface Ship extends Comparable<Ship> {
	
	public ShipFaction getFaction();
	public void setFaction(ShipFaction faction);
	public Tier getTier();
	public void setTier(Tier tier);
	public Rarity getRarity();
	public void setRarity(Rarity rarity);
	public Role getRole();
	public void setRole(Role role);
	public String getName();
	public void setName(String name);
	public String getIconName();
	public String getDisplayName();
	public int getEng();
	public void setEng(int eng);
	public int getTac();
	public void setTac(int tac);
	public int getSci();
	public void setSci(int sci);
	public SpecialAbility getSpecialAbility();
	public void setSpecialAbility(SpecialAbility rule);
	public String getTrait();
	public void setTrait(String trait);
	public boolean hasTrait();
	public int getUsageCount();
	public boolean isOwned();
	public void setOwned(boolean owned);
	public void setUsageCount(int usageCount);
	public void incrementUsageCount(int count);
	public Ship getOneTimeShip();

}
