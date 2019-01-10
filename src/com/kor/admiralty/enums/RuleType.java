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

import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.rewards.Reward;
import com.kor.admiralty.rewards.RewardIgnoreEventStat;
import com.kor.admiralty.rewards.RewardMultiplyCritRate;
import com.kor.admiralty.rewards.RewardStat;
import com.kor.admiralty.rewards.RewardNothing;
import com.kor.admiralty.rules.AlwaysApply;
import com.kor.admiralty.rules.PerAnyShip;
import com.kor.admiralty.rules.ProcCritRate;
import com.kor.admiralty.rules.WhenAlone;
import com.kor.admiralty.rules.NoOp;
import com.kor.admiralty.rules.PerShipCategory;

public enum RuleType {
	Unknown, All, WhenAlone, 
	PerShip, PerAnyShip, PerAnyShipNotSmallCraft, PerEng, PerTac, PerSci, PerEngTac, PerEngSci, PerTacSci, PerCruiser,  
	FromEvent, FromAllStats, FromAnyShip, 
	FromEng, FromTac, FromSci, FromEngTac, FromEngSci, FromTacSci, Ignore, 
	MultiplyCriticalRate, MultiplyBonus;
	
	public SpecialAbility rewardNothing() {
		return constructRule(this, new RewardNothing());
	}
	
	public SpecialAbility ignoreSci() {
		return constructRule(this, new RewardNothing());
	}
	
	public SpecialAbility rewardMaintenance(double quantity) {
		return constructRule(this, new RewardNothing());
	}
	
	public SpecialAbility rewardBonus(double quantity) {
		return constructRule(this, new RewardNothing());
	}
	
	public SpecialAbility rewardIgnoreEng() {
		return constructRule(this, new RewardIgnoreEventStat(true, false, false));
	}
	
	public SpecialAbility rewardIgnoreTac() {
		return constructRule(this, new RewardIgnoreEventStat(false, true, false));
	}
	
	public SpecialAbility rewardIgnoreSci() {
		return constructRule(this, new RewardIgnoreEventStat(false, false, true));
	}
	
	public SpecialAbility rewardIgnoreAllEventModifiers(boolean notSmallCraft) {
		return constructRule(this, new RewardIgnoreEventStat(true, true, true, notSmallCraft));
	}
	
	public SpecialAbility rewardEng(int quantity) {
		return constructRule(this, new RewardStat(quantity, 0, 0));
	}
	
	public SpecialAbility rewardTac(int quantity) {
		return constructRule(this, new RewardStat(0, quantity, 0));
	}
	
	public SpecialAbility rewardSci(int quantity) {
		return constructRule(this, new RewardStat(0, 0, quantity));
	}
	
	public SpecialAbility rewardTacEng(int quantity) {
		return constructRule(this, new RewardStat(quantity, quantity, 0));
	}
	
	public SpecialAbility rewardSciTac(int quantity) {
		return constructRule(this, new RewardStat(0, quantity, quantity));
	}
	
	public SpecialAbility rewardSciEng(int quantity) {
		return constructRule(this, new RewardStat(quantity, 0, quantity));
	}
	
	public SpecialAbility rewardMultiplyCritRate(double engQuantity, double tacQuantity, double sciQuantity) {
		return constructRule(this, new RewardMultiplyCritRate(engQuantity, tacQuantity, sciQuantity, 1));
	}
	
	public SpecialAbility rewardMultiplyEventCritRate(double quantity) {
		return constructRule(this, new RewardMultiplyCritRate(1, 1, 1, quantity));
	}
	
	public SpecialAbility rewardCritRating(double quantity) {
		return constructRule(this, new RewardStat(0, 0, 0, quantity));
	}
	
	public SpecialAbility rewardAll(int quantity) {
		return constructRule(this, new RewardStat(quantity, quantity, quantity));
	}
	
	protected static SpecialAbility constructRule(RuleType rule, Reward reward) {
		switch(rule) {
		case WhenAlone: return new WhenAlone(reward);
		case FromAnyShip:
		case PerAnyShip:
		case PerShip: return new PerAnyShip(reward);
		case PerAnyShipNotSmallCraft: return new PerAnyShip(reward, true);
		case PerEng: return new PerShipCategory(reward, Role.Eng);
		case PerTac: return new PerShipCategory(reward, Role.Tac);
		case PerSci: return new PerShipCategory(reward, Role.Sci);
		case PerEngTac: return new PerShipCategory(reward, Role.Tac, Role.Eng);
		case PerEngSci: return new PerShipCategory(reward, Role.Eng, Role.Sci);
		case PerTacSci: return new PerShipCategory(reward, Role.Tac, Role.Sci);
		case MultiplyCriticalRate: return new ProcCritRate(reward);
		case Ignore: 
		case All: return new AlwaysApply(reward);
		default: return new NoOp(reward);
		}
	}

	
}
