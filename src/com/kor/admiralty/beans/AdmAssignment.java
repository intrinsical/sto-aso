/**
 * Copyright (C) 2015-2018 Dave Kor
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
 */
package com.kor.admiralty.beans;

import com.kor.admiralty.enums.Rarity;

public class AdmAssignment {

	public static final AdmAssignment ASSIGNMENT_NONE = new AdmAssignment("~~ Select Assignment ~~", Rarity.Common, 0,
			0, 0, 0, 0);

	protected String name;
	protected Rarity rarity;
	protected int eng;
	protected int tac;
	protected int sci;
	protected int hours;
	protected int minutes;

	public AdmAssignment(String name, Rarity rarity, int eng, int tac, int sci, int hours, int minutes) {
		this.name = name;
		this.rarity = rarity;
		this.eng = eng;
		this.tac = tac;
		this.sci = sci;
		this.hours = hours;
		this.minutes = minutes;
	}

	protected AdmAssignment(String name) {
		this(name, Rarity.Common, 0, 0, 0, 0, 0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
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

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getDuration() {
		return this.hours * 60 + this.minutes;
	}

	@Override
	public String toString() {
		return name;
	}

}
