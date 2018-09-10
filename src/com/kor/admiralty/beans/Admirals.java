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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.kor.admiralty.enums.PlayerFaction;

@XmlRootElement(name="admirals")
@XmlSeeAlso(Admiral.class)
public class Admirals {
	
	protected static final String PROP_ADMIRALS = "admirals";
	
	protected List<Admiral> admirals;
	protected PropertyChangeSupport change;
	
	public Admirals() {
		this.admirals = new ArrayList<Admiral>();
		this.admirals.add(new Admiral());
		this.change = new PropertyChangeSupport(this);
	}

	public List<Admiral> getAdmirals() {
		return admirals;
	}
	
	public List<Admiral> getFederationAdmirals() {
		return getPlayerFactionAdmirals(PlayerFaction.Federation, PlayerFaction.RomulanFed, PlayerFaction.JemHadarFed);
	}
	
	public List<Admiral> getKlingonAdmirals() {
		return getPlayerFactionAdmirals(PlayerFaction.Klingon, PlayerFaction.RomulanKDF, PlayerFaction.JemHadarKDF);
	}
	
	public List<Admiral> getRomulanAdmirals() {
		return getPlayerFactionAdmirals(PlayerFaction.RomulanFed, PlayerFaction.RomulanKDF);
	}
	
	public List<Admiral> getJemHadarAdmirals() {
		return getPlayerFactionAdmirals(PlayerFaction.JemHadarFed, PlayerFaction.JemHadarKDF);
	}
	
	protected List<Admiral> getPlayerFactionAdmirals(PlayerFaction ... factions) {
		List<Admiral> factionAdmirals = new ArrayList<Admiral>();
		for (Admiral admiral : admirals) {
			for (PlayerFaction faction : factions) {
				if (admiral.getFaction() == faction) {
					factionAdmirals.add(admiral);
				}
			}
		}
		return factionAdmirals;
	}
	
	@XmlElement(name="admiral")
	public void setAdmirals(List<Admiral> admirals) {
		this.admirals = admirals;
		change.firePropertyChange(PROP_ADMIRALS, admirals, admirals);
	}
	
	public void addAdmiral(Admiral admiral) {
		if (!admirals.contains(admiral)) {
			admirals.add(admiral);
			change.firePropertyChange(PROP_ADMIRALS, admirals, admirals);
		}
	}

	public void removeAdmiral(Admiral admiral) {
		if (admirals.contains(admiral)) {
			admirals.remove(admiral);
			change.firePropertyChange(PROP_ADMIRALS, admirals, admirals);
		}
	}
	
	public Admiral findByName(String name) {
		for (Admiral admiral : admirals) {
			if (admiral.getName().equalsIgnoreCase(name)) {
				return admiral;
			}
		}
		return null;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		change.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		change.removePropertyChangeListener(l);
	}

	@Override
	public String toString() {
		return admirals.toString();
	}

	public static Admiral[] toArray(Collection<Admiral> adm) {
		Admiral array[] = new Admiral[adm.size()];
		adm.toArray(array);
		return array;
	}
	
}
