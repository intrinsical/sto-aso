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

public class Ship implements Comparable<Ship>, Cloneable {
	
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
	
	protected Ship oneTimeShip;
	protected Ship starshipTraitShip;
	protected Ship usageCountShip;
	
	public Ship(ShipFaction faction, Tier tier, Rarity rarity, Role category, String name, int eng, int tac, int sci, SpecialAbility rule, String trait) {
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

	public ShipFaction getFaction() {
		return faction;
	}

	public void setFaction(ShipFaction faction) {
		this.faction = faction;
	}

	public Tier getTier() {
		return tier;
	}

	public void setTier(Tier tier) {
		this.tier = tier;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIconName() {
		return name.replaceAll("[ \\-]", "_").replaceAll("^Fleet\\s", "").replaceAll("[\\.\\(\\)\\[\\]'�]", "") + ".png";
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getTac() {
		return tac;
	}

	public void setTac(int tac) {
		this.tac = tac;
	}

	public int getSci() {
		return sci;
	}

	public void setSci(int sci) {
		this.sci = sci;
	}

	public SpecialAbility getSpecialAbility() {
		return ability;
	}

	public void setSpecialAbility(SpecialAbility rule) {
		this.ability = rule;
	}
	
	public String getTrait() {
		return trait;
	}
	
	public void setTrait(String trait) {
		this.trait = trait;
	}
	
	public boolean hasTrait() {
		return this.trait.length() > 0;
	}

	public boolean isActive() {
		return !maintenance;
	}
	
	public boolean isMaintenance() {
		return maintenance;
	}
	
	public void setMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}
	
	public boolean isOneTime() {
		return oneTime;
	}
	
	public boolean isShowStarshipTrait() {
		return showStarshipTrait;
	}
	
	public void setShowStarshipTrait(boolean showStarshipTrait) {
		this.showStarshipTrait = showStarshipTrait;
	}
	
	public boolean isShowUsageCount() {
		return showUsageCount;
	}
	
	public void setShowUsageCount(boolean showUsageCount) {
		this.showUsageCount = showUsageCount;
	}
	
	public int getUsageCount() {
		return usageCount;
	}
	
	public void setUsageCount(int usageCount) {
		this.usageCount = usageCount;
	}
	
	public void incrementUsageCount(int count) {
		this.usageCount += count;
	}
	
	public Ship getOneTimeShip() {
		if (oneTimeShip == null) {
			try {
				oneTimeShip = (Ship)clone();
				oneTimeShip.oneTime = true;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return oneTimeShip;
	}
	
	public Ship getStarshipTraitShip() {
		if (starshipTraitShip == null) {
			try {
				starshipTraitShip = (Ship)clone();
				starshipTraitShip.showStarshipTrait = true;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return starshipTraitShip;
	}
	
	public Ship getUsageCountShip() {
		if (usageCountShip == null) {
			try {
				usageCountShip = (Ship)clone();
				usageCountShip.showUsageCount = true;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return usageCountShip;
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
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Ship)) return false;
		Ship ship = (Ship)o;
		return name.equals(ship.name) && eng == ship.eng && tac == ship.tac && sci == ship.sci;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	protected static final Ship singleton = new Ship(ShipFaction.Federation, Tier.Tier1, Rarity.Epic, Role.Tac, "N/A", 0, 0, 0, RuleType.All.rewardBonus(0), "");
	
	public static Ship nullShip() {
		return singleton;
	}

}
