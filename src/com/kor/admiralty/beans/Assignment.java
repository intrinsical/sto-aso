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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Assignment {
	
	public static final String PROP_ENG = "eng";
	public static final String PROP_TAC = "tac";
	public static final String PROP_SCI = "sci";
	public static final String PROP_EVT_ENG = "eventEng";
	public static final String PROP_EVT_TAC = "eventTac";
	public static final String PROP_EVT_SCI = "eventSci";
	public static final String PROP_CRITRATE = "critRate";
	public static final String PROP_BOOSTCRIT = "boostCrit";
	public static final String PROP_DURATION = "duration";
	
	protected int requiredEng;
	protected int requiredTac;
	protected int requiredSci;
	protected int eventEng;
	protected int eventTac;
	protected int eventSci;
	protected int eventCritRate;
	protected int targetCritChance;
	protected int duration;
	protected PropertyChangeSupport change;
	
	public Assignment() {
		this(0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public Assignment(int requiredEng, int requiredTac, int requiredSci, int eventEng, int eventTac, int eventSci, int eventCritRate) {
		this(requiredEng, requiredTac, requiredSci, eventEng, eventTac, eventSci, eventCritRate, 0, 0);
	}
	
	public Assignment(int requiredEng, int requiredTac, int requiredSci, int eventEng, int eventTac, int eventSci, int eventCritRate, int targetCritChance, int duration) {
		this.requiredEng = requiredEng;
		this.requiredTac = requiredTac;
		this.requiredSci = requiredSci;
		this.eventEng = eventEng;
		this.eventTac = eventTac;
		this.eventSci = eventSci;
		this.eventCritRate = eventCritRate;
		this.targetCritChance = targetCritChance;
		this.duration = duration;
		this.change = new PropertyChangeSupport(this);
	}
	
	public int eng() {
		return requiredEng + eventEng;
	}

	public int tac() {
		return requiredTac + eventTac;
	}

	public int sci() {
		return requiredSci + eventSci;
	}
	
	public int critRate() {
		return getTargetCritRate();
	}
	
	public int getRequiredEng() {
		return requiredEng;
	}

	public void setRequiredEng(int eng) {
		int oldEng = requiredEng;
		requiredEng = eng;
		change.firePropertyChange(PROP_ENG, oldEng, eng);
	}

	public int getRequiredTac() {
		return requiredTac;
	}

	public void setRequiredTac(int tac) {
		int oldTac = requiredTac;
		requiredTac = tac;
		change.firePropertyChange(PROP_TAC, oldTac, tac);
	}

	public int getRequiredSci() {
		return requiredSci;
	}

	public void setRequiredSci(int sci) {
		int oldSci = requiredSci;
		requiredSci = sci;
		change.firePropertyChange(PROP_SCI, oldSci, sci);
	}
	
	
	public int getEventEng() {
		return eventEng;
	}

	public void setEventEng(int eventEng) {
		int oldEng = this.eventEng;
		this.eventEng = eventEng;
		change.firePropertyChange(PROP_EVT_ENG, oldEng, eventEng);
	}

	public int getEventTac() {
		return eventTac;
	}

	public void setEventTac(int eventTac) {
		int oldTac = this.eventTac;
		this.eventTac = eventTac;
		change.firePropertyChange(PROP_EVT_TAC, oldTac, eventTac);
	}

	public int getEventSci() {
		return eventSci;
	}

	public void setEventSci(int eventSci) {
		int oldSci = this.eventSci;
		this.eventSci = eventSci;
		change.firePropertyChange(PROP_EVT_SCI, oldSci, eventSci);
	}

	public int getEventCritRate() {
		return eventCritRate;
	}

	public void setEventCritRate(int eventCritRate) {
		int oldCritRate = this.eventCritRate;
		this.eventCritRate = eventCritRate;
		change.firePropertyChange(PROP_CRITRATE, oldCritRate, eventCritRate);
	}
	
	public int getTargetCritRate() {
		int total = requiredEng + eventEng + requiredTac + eventTac + requiredSci + eventSci;
		double chance = (targetCritChance / 100d);
		double critRateMultiplier = (2 * chance) / (1 - chance);
		return (int) Math.floor(total * critRateMultiplier);
	}
	
	public int getTargetCritChance() {
		return targetCritChance;
	}
	
	public void setTargetCritChance(int targetCritChance) {
		int oldTargetCritChance = this.targetCritChance;
		this.targetCritChance = targetCritChance;
		change.firePropertyChange(PROP_CRITRATE, oldTargetCritChance, targetCritChance);
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		int oldDuration = this.duration;
		this.duration = duration;
		change.firePropertyChange(PROP_DURATION, oldDuration, duration);
	}
	
	public void clear() {
		setRequiredEng(0);
		setRequiredTac(0);
		setRequiredSci(0);
		setEventEng(0);
		setEventTac(0);
		setEventSci(0);
		setEventCritRate(0);
		setTargetCritChance(0);
		setDuration(0);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		change.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		change.removePropertyChangeListener(l);
	}

}
