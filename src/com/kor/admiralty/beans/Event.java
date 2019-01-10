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

import com.kor.admiralty.enums.EventReward;

public class Event {

	public static final Event EVENT_NONE = new Event("~~ Select Event ~~", 0, 0, 0, 0, EventReward.None);
	
	protected String name;
	protected int eng;
	protected int tac;
	protected int sci;
	protected int critRate;
	protected EventReward reward;

	public Event(String name, int eng, int tac, int sci, int critRate, EventReward reward) {
		this.name = name;
		this.eng = eng;
		this.tac = tac;
		this.sci = sci;
		this.critRate = critRate;
		this.reward = reward;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getCritRate() {
		return critRate;
	}

	public void setCritRate(int critRate) {
		this.critRate = critRate;
	}

	public EventReward getReward() {
		return reward;
	}

	public void setReward(EventReward reward) {
		this.reward = reward;
	}

	@Override
	public String toString() {
		return name;
	}

}
