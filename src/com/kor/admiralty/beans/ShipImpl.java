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
import com.kor.admiralty.enums.RuleType;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.enums.ShipFaction;
import com.kor.admiralty.enums.Tier;

public class ShipImpl implements Ship, Cloneable {
	
	protected ShipFaction faction;
	protected Tier tier;
	protected Rarity rarity;
	protected Role role;
	protected String name;
	protected int eng;
	protected int tac;
	protected int sci;
	protected SpecialAbility ability;
	protected String trait;
	protected boolean maintenance;
	protected boolean oneTime;
	protected boolean showStarshipTrait;
	protected boolean showUsageCount;
	protected int usageCount;
	
	protected OneTimeShip oneTimeShip;
	
	public ShipImpl(ShipFaction faction, Tier tier, Rarity rarity, Role category, String name, int eng, int tac, int sci, SpecialAbility rule, String trait) {
		this.faction = faction;
		this.tier = tier;
		this.rarity = rarity;
		this.role = category;
		this.name = name;
		this.eng = eng;
		this.tac = tac;
		this.sci = sci;
		this.ability = rule;
		this.trait = trait;
		this.maintenance = false;
		this.oneTime = false;
		this.showStarshipTrait = false;
		this.showUsageCount = false;
		this.usageCount = -1;
	}

	@Override
	public ShipFaction getFaction() {
		return faction;
	}

	@Override
	public void setFaction(ShipFaction faction) {
		this.faction = faction;
	}

	@Override
	public Tier getTier() {
		return tier;
	}

	@Override
	public void setTier(Tier tier) {
		this.tier = tier;
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}
	
	@Override
	public Role getRole() {
		return role;
	}
	
	@Override
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getIconName() {
		return getName().replaceAll("[ \\-]", "_").replaceAll("^Fleet\\s", "").replaceAll("[\\.\\(\\)\\[\\]'�]", "") + ".png";
	}
	
	@Override
	public String getDisplayName() {
		return name;
	}

	@Override
	public int getEng() {
		return eng;
	}

	@Override
	public void setEng(int eng) {
		this.eng = eng;
	}

	@Override
	public int getTac() {
		return tac;
	}

	@Override
	public void setTac(int tac) {
		this.tac = tac;
	}

	@Override
	public int getSci() {
		return sci;
	}

	@Override
	public void setSci(int sci) {
		this.sci = sci;
	}

	@Override
	public SpecialAbility getSpecialAbility() {
		return ability;
	}

	@Override
	public void setSpecialAbility(SpecialAbility rule) {
		this.ability = rule;
	}
	
	@Override
	public String getTrait() {
		return trait;
	}
	
	@Override
	public void setTrait(String trait) {
		this.trait = trait;
	}
	
	@Override
	public boolean hasTrait() {
		return this.trait.length() > 0;
	}

	/*/
	@Override
	public boolean isActive() {
		return !maintenance;
	}
	
	@Override
	public boolean isMaintenance() {
		return maintenance;
	}
	
	@Override
	public void setMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}
	
	@Override
	public boolean isOneTime() {
		return oneTime;
	}
	
	@Override
	public boolean isShowStarshipTrait() {
		return showStarshipTrait;
	}
	
	@Override
	public void setShowStarshipTrait(boolean showStarshipTrait) {
		this.showStarshipTrait = showStarshipTrait;
	}
	
	@Override
	public boolean isShowUsageCount() {
		return showUsageCount;
	}
	
	@Override
	public void setShowUsageCount(boolean showUsageCount) {
		this.showUsageCount = showUsageCount;
	}
	//*/
	
	@Override
	public int getUsageCount() {
		return usageCount;
	}
	
	@Override
	public void setUsageCount(int usageCount) {
		this.usageCount = usageCount;
	}
	
	@Override
	public void incrementUsageCount(int count) {
		this.usageCount += count;
	}
	
	public Ship getOneTimeShip() {
		if (oneTimeShip == null) {
			oneTimeShip = new OneTimeShip(this);
		}
		return oneTimeShip;
	}
	
	@Override
	public int compareTo(Ship ship) {
		int compare = getTier().compareTo(ship.getTier());
		if (compare != 0) return compare;
		compare = getRarity().compareTo(ship.getRarity());
		if (compare != 0) return compare;
		compare = getRole().compareTo(ship.getRole());
		if (compare != 0) return compare;
		return getName().compareTo(ship.getName());
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Ship)) return false;
		Ship ship = (Ship)o;
		return getName().equals(ship.getName()) && getEng() == ship.getEng() && getTac() == ship.getTac() && getSci() == ship.getSci();
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	protected static final ShipImpl singleton = new ShipImpl(ShipFaction.Federation, Tier.Tier1, Rarity.Epic, Role.Tac, "N/A", 0, 0, 0, RuleType.All.rewardBonus(0), "");
	
	public static ShipImpl nullShip() {
		return singleton;
	}

}
