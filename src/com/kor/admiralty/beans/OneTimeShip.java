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
package com.kor.admiralty.beans;

import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.enums.ShipFaction;
import com.kor.admiralty.enums.Tier;

public class OneTimeShip implements Ship {

	protected ShipImpl parent;
	
	public OneTimeShip(ShipImpl parent) {
		this.parent = parent;
	}

	@Override
	public ShipFaction getFaction() {
		return parent.getFaction();
	}

	@Override
	public void setFaction(ShipFaction faction) {
		parent.setFaction(faction);
	}

	@Override
	public Tier getTier() {
		return parent.getTier();
	}

	@Override
	public void setTier(Tier tier) {
		parent.setTier(tier);
	}

	@Override
	public Rarity getRarity() {
		return parent.getRarity();
	}

	@Override
	public void setRarity(Rarity rarity) {
		parent.setRarity(rarity);
	}

	@Override
	public Role getRole() {
		return parent.getRole();
	}

	@Override
	public void setRole(Role role) {
		parent.setRole(role);
	}

	@Override
	public String getName() {
		return parent.getName();
	}

	@Override
	public void setName(String name) {
		parent.setName(name);
	}

	@Override
	public String getIconName() {
		return parent.getIconName();
	}
	
	@Override
	public String getDisplayName() {
		return "(1x) " + getName();
	}

	@Override
	public int getEng() {
		return parent.getEng();
	}

	@Override
	public void setEng(int eng) {
		parent.setEng(eng);
	}

	@Override
	public int getTac() {
		return parent.getTac();
	}

	@Override
	public void setTac(int tac) {
		parent.setTac(tac);
	}

	@Override
	public int getSci() {
		return parent.getSci();
	}

	@Override
	public void setSci(int sci) {
		parent.setSci(sci);
	}

	@Override
	public SpecialAbility getSpecialAbility() {
		return parent.getSpecialAbility();
	}

	@Override
	public void setSpecialAbility(SpecialAbility rule) {
		parent.setSpecialAbility(rule);
	}

	@Override
	public String getTrait() {
		return parent.getTrait();
	}

	@Override
	public void setTrait(String trait) {
		parent.setTrait(trait);
	}

	@Override
	public boolean hasTrait() {
		return parent.hasTrait();
	}
	
	@Override 
	public boolean isOwned() {
		return false;
	}
	
	@Override
	public void setOwned(boolean owned) {
	}

	@Override
	public int getUsageCount() {
		return parent.getUsageCount();
	}

	@Override
	public void setUsageCount(int usageCount) {
		parent.setUsageCount(usageCount);
	}

	@Override
	public void incrementUsageCount(int count) {
		parent.incrementUsageCount(count);
	}

	@Override
	public Ship getOneTimeShip() {
		return parent.getOneTimeShip();
	}

	@Override
	public int compareTo(Ship ship) {
		return parent.compareTo(ship);
	}

}
