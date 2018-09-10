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

import java.util.Arrays;
import java.util.List;

public class AssignmentSolution implements HasScore {
	
	protected int shipIndexes[];
	protected Ship ships[];
	protected int eng;
	protected int tac;
	protected int sci;
	protected boolean ignoreEventEng;
	protected boolean ignoreEventTac;
	protected boolean ignoreEventSci;
	protected int eventCritRate;
	protected double eventCritMultiplier;
	protected double engCritMultiplier;
	protected double tacCritMultiplier;
	protected double sciCritMultiplier;
	protected int critRate;
	protected int critChance;
	protected double maintenanceReduction;
	protected double score;
	
	public AssignmentSolution(int eventCritRate, int ... shipIndexes) {
		this.eventCritRate = eventCritRate;
		this.shipIndexes = shipIndexes;
		this.ships = new Ship[shipIndexes.length];
		this.critRate = eventCritRate;
		this.critChance = 0;
		this.eventCritMultiplier = 1;
		this.engCritMultiplier = 1;
		this.tacCritMultiplier = 1;
		this.sciCritMultiplier = 1;
		this.maintenanceReduction = 0;
		this.ignoreEventEng = false;
		this.ignoreEventTac = false;
		this.ignoreEventSci = false;
	}
	
	public int getEventCritRate() {
		return eventCritRate;
	}
	
	public int[] getShipIndexes() {
		return shipIndexes;
	}
	
	public Ship[] getShips() {
		return ships;
	}
	
	/*/
	public List<Ship> getShipsAsList() {
		List<Ship> list = new ArrayList<Ship>();
		for (int i = 0; i < ships.length; i++) {
			if (ships[i] != null) {
				list.add(ships[i]);
			}
		}
		return list;
	}
	//*/
	
	public int getEng() {
		return eng;
	}
	
	public int addEng(int value) {
		this.eng += value;
		return eng;
	}
	
	public int getTac() {
		return tac;
	}
	
	public int addTac(int value) {
		this.tac += value;
		return tac;
	}
	
	public int getSci() {
		return sci;
	}
	
	public int addSci(int value) {
		this.sci += value;
		return sci;
	}
	
	public int getCritRate() {
		return critRate;
	}

	public int getCritChance() {
		return critChance;
	}
	
	/*
	public int addCritRate(int value) {
		this.critRate += value;
		return critRate;
	}
	*/
	
	public int computeCritRate(int eng, int tac, int sci) {
		critRate = (int) Math.round(eventCritRate * eventCritMultiplier + eng * engCritMultiplier + tac * tacCritMultiplier + sci * sciCritMultiplier);
		return critRate;
	}
	
	public double getEventCritMultiplier() {
		return eventCritMultiplier;
	}
	
	public double getEngCritMultiplier() {
		return engCritMultiplier;
	}
	
	public double getTacCritMultiplier() {
		return tacCritMultiplier;
	}
	
	public double getSciCritMultiplier() {
		return sciCritMultiplier;
	}
	
	public double addEventCritMultiplier(double value) {
		this.eventCritMultiplier += value;
		return eventCritMultiplier;
	}
	
	public double addEngCritMultiplier(double value) {
		this.engCritMultiplier += value;
		return engCritMultiplier;
	}
	
	public double addTacCritMultiplier(double value) {
		this.tacCritMultiplier += value;
		return tacCritMultiplier;
	}
	
	public double addSciCritMultiplier(double value) {
		this.sciCritMultiplier += value;
		return sciCritMultiplier;
	}
	
	public boolean isIgnoreEventEng() {
		return ignoreEventEng;
	}

	public void setIgnoreEventEng(boolean ignoreEventEng) {
		this.ignoreEventEng = ignoreEventEng;
	}

	public boolean isIgnoreEventTac() {
		return ignoreEventTac;
	}

	public void setIgnoreEventTac(boolean ignoreEventTac) {
		this.ignoreEventTac = ignoreEventTac;
	}

	public boolean isIgnoreEventSci() {
		return ignoreEventSci;
	}

	public void setIgnoreEventSci(boolean ignoreEventSci) {
		this.ignoreEventSci = ignoreEventSci;
	}
	
	public void addMaintenanceReudction(double reduction) {
		this.maintenanceReduction += reduction;
	}
	
	public double getMaintenanceReduction() {
		return maintenanceReduction;
	}
	
	@Override
	public void setShips(List<Ship> ships) {
		for (int i = 0; i < shipIndexes.length; i++) {
			if (shipIndexes[i] >= 0) {
				this.ships[i] = ships.get(shipIndexes[i]);
			}
			else {
				this.ships[i] = null;
			}
		}
	}

	@Override
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		/*/
		sb.append("Solution").append(score);
		for (Ship ship : ships) {
			if (ship != null) {
				sb.append(", ").append(ship);
			}
		}
		sb.append(")");
		/*/
		sb.append("Solution").append(Arrays.toString(shipIndexes)).append(" = ").append(score);
		//*/
		return sb.toString();
	}
	
}
