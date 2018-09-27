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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.kor.admiralty.Globals;
import com.kor.admiralty.enums.PlayerFaction;
import com.kor.admiralty.enums.ShipViewMode;
import com.kor.admiralty.io.Datastore;

@XmlType(propOrder = { "name", "faction", "active", "maintenance", "oneTime", /*"schedule",*/ "usage" })
@XmlRootElement(name = "admiral")
public class Admiral {
	
	public static final String PROP_NAME = "name";
	public static final String PROP_FACTION = "faction";
	public static final String PROP_ACTIVE = "active";
	public static final String PROP_MAINTENANCE = "maintenance";
	public static final String PROP_ONETIME = "oneTime";
	//public static final String PROP_SCHEDULE = "maintenance2";
	public static final String PROP_USAGE = "usage";
	public static final String PROP_ASSIGNMENTCOUNT = "numAssignments";
	public static final String PROP_PRIORITIZEACTIVE = "prioritizeActive";
	public static final String PROP_ASSIGNMENTS = "assignments";

	protected String name;
	protected PlayerFaction faction;
	protected List<String> active;
	protected List<String> maintenance;
	//protected Map<String, Long> maintenanceV2;
	protected List<String> oneTime;
	protected Map<String, Integer> usage;
	protected int numAssignments;
	protected boolean prioritizeActive;
	protected List<Assignment> assignments;
	protected PropertyChangeSupport change;

	public Admiral() {
		this.name = "New Admiral";
		this.faction = PlayerFaction.Federation;
		this.active = new ArrayList<String>();
		this.maintenance = new ArrayList<String>();
		//this.maintenanceV2 = new HashMap<String, Long>();
		this.oneTime = new ArrayList<String>();
		this.usage = new HashMap<String, Integer>();
		this.numAssignments = 1;
		this.prioritizeActive = true;
		this.assignments = new ArrayList<Assignment>();
		this.change = new PropertyChangeSupport(this);
		for (int i = 0; i < Globals.MAX_ASSIGNMENTS; i++) {
			this.assignments.add(new Assignment());
		}
	}

	public String getName() {
		return name;
	}

	@XmlElement(name = "name", required = true)
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		change.firePropertyChange(PROP_NAME, oldName, this.name);
	}

	public PlayerFaction getFaction() {
		return faction;
	}

	@XmlElement(name = "faction", required = true)
	public void setFaction(PlayerFaction faction) {
		PlayerFaction oldFaction = this.faction;
		this.faction = faction;
		change.firePropertyChange(PROP_FACTION, oldFaction, this.faction);
	}

	public List<String> getActive() {
		return active;
	}

	@XmlElement(name = "active")
	public void setActive(List<String> active) {
		ArrayList<String> oldList = new ArrayList<String>(this.active);
		this.active = active;
		change.firePropertyChange(PROP_ACTIVE, oldList, this.active);
	}

	public List<String> getMaintenance() {
		return maintenance;
	}

	@XmlElement(name = "maintenance")
	public void setMaintenance(List<String> maintenance) {
		ArrayList<String> oldList = new ArrayList<String>(this.maintenance);
		this.maintenance = maintenance;
		change.firePropertyChange(PROP_MAINTENANCE, oldList, this.maintenance);
	}

	public List<String> getOneTime() {
		return oneTime;
	}

	@XmlElement(name = "onetime")
	public void setOneTime(List<String> oneTime) {
		ArrayList<String> oldList = new ArrayList<String>(this.oneTime);
		this.oneTime = oneTime;
		change.firePropertyChange(PROP_ONETIME, oldList, this.oneTime);
	}
	
	public Map<String, Integer> getUsage() {
		return usage;
	}
	
	/*
	@XmlElement(name = "maintenance2")
	public void setMaintenanceV2(Map<String, Long> maintenanceV2) {
		Map<String, Long> oldMaintenanceV2 = new HashMap<String, Long>(this.maintenanceV2);
		this.maintenanceV2 = maintenanceV2;
		change.firePropertyChange(PROP_SCHEDULE, oldMaintenanceV2, maintenanceV2);
	}
	
	public Map<String, Long> getMaintenanceV2() {
		return maintenanceV2;
	}
	*/

	@XmlElement(name = "usage")
	public void setUsage(Map<String, Integer> usage) {
		HashMap<String, Integer> oldMap = new HashMap<String, Integer>(this.usage);
		this.usage = usage;
		change.firePropertyChange(PROP_USAGE, oldMap, this.usage);
	}
	
	public void clearUsage() {
		this.usage.clear();
	}
	
	public int getAssignmentCount() {
		return numAssignments;
	}
	
	@XmlTransient
	public void setAssignmentCount(int numAssignments) {
		int oldNum = this.numAssignments;
		this.numAssignments = numAssignments;
		change.firePropertyChange(PROP_ASSIGNMENTCOUNT, oldNum, this.numAssignments);
	}
	
	public boolean getPrioritizeActive() {
		return prioritizeActive;
	}
	
	@XmlAttribute(name = "prioritizeActive")
	public void setPrioritizeActive(boolean prioritizeActive) {
		boolean oldVal = this.prioritizeActive;
		this.prioritizeActive = prioritizeActive;
		change.firePropertyChange(PROP_PRIORITIZEACTIVE, oldVal, this.prioritizeActive);
	}
	
	public List<Assignment> getAssignments() {
		return assignments;
	}
	
	@XmlTransient
	public void setAssignments(List<Assignment> assignments) {
		ArrayList<Assignment> oldList = new ArrayList<Assignment>(this.assignments);
		this.assignments = assignments;
		change.firePropertyChange(PROP_ASSIGNMENTS, oldList, this.assignments);
	}

	public void addActive(String shipName) {
		if (!active.contains(shipName)) {
			List<String> oldActive = new ArrayList<String>(active);
			active.add(shipName);
			change.firePropertyChange(PROP_ACTIVE, oldActive, active);
		}
	}

	public void removeActive(String shipName) {
		if (active.contains(shipName)) {
			List<String> oldActive = new ArrayList<String>(active);
			active.remove(shipName);
			change.firePropertyChange(PROP_ACTIVE, oldActive, active);
		}
	}

	public void addMaintenance(String shipName) {
		if (!maintenance.contains(shipName)) {
			List<String> oldMaintenance = new ArrayList<String>(maintenance);
			maintenance.add(shipName);
			change.firePropertyChange(PROP_MAINTENANCE, oldMaintenance, maintenance);
		}
	}

	public void removeMaintenance(String shipName) {
		if (maintenance.contains(shipName)) {
			List<String> oldMaintenance = new ArrayList<String>(maintenance);
			maintenance.remove(shipName);
			change.firePropertyChange(PROP_MAINTENANCE, oldMaintenance, maintenance);
		}
	}

	public void addOneTime(String shipName) {
		if (!oneTime.contains(shipName)) {
			List<String> oldOneTime = new ArrayList<String>(oneTime);
			oneTime.add(shipName);
			change.firePropertyChange(PROP_ONETIME, oldOneTime, oneTime);
		}
	}

	public void removeOneTime(String shipName) {
		if (oneTime.contains(shipName)) {
			List<String> oldOneTime = new ArrayList<String>(oneTime);
			oneTime.remove(shipName);
			change.firePropertyChange(PROP_ONETIME, oldOneTime, oneTime);
		}
	}
	
	public Assignment getAssignment(int index) {
		if (index < 0) return null;
		if (index >= assignments.size()) return null;
		return assignments.get(index);
	}

	public Set<Ship> getActiveShips() {
		Set<Ship> ships = new TreeSet<Ship>();
		_getShips(active, ships);
		return ships;
	}
	
	@XmlTransient
	public void setActiveShips(Set<Ship> ships) {
		List<String> oldActive = new ArrayList<String>(active);
		setShips(active, ships);
		change.firePropertyChange(PROP_ACTIVE, oldActive, active);
	}
	
	public void addActiveShips(Collection<Ship> ships) {
		List<String> oldActive = new ArrayList<String>(active);
		addShips(active, ships);
		change.firePropertyChange(PROP_ACTIVE, oldActive, active);
	}
	
	public void removeActiveShips(Collection<Ship> ships) {
		List<String> oldActive = new ArrayList<String>(active);
		removeShips(active, ships);
		change.firePropertyChange(PROP_ACTIVE, oldActive, active);
	}

	public Set<Ship> getMaintenanceShips() {
		Set<Ship> ships = new TreeSet<Ship>();
		_getShips(maintenance, ships);
		//_getShips(schedule.keySet(), ships);
		return ships;
	}

	@XmlTransient
	public void setMaintenanceShips(Set<Ship> ships) {
		List<String> oldMaintenance = new ArrayList<String>(maintenance);
		setShips(maintenance, ships);
		change.firePropertyChange(PROP_MAINTENANCE, oldMaintenance, maintenance);
	}

	public void addMaintenanceShips(Collection<Ship> ships) {
		List<String> oldMaintenance = new ArrayList<String>(maintenance);
		addShips(maintenance, ships);
		change.firePropertyChange(PROP_MAINTENANCE, oldMaintenance, maintenance);
	}
	
	public void removeMaintenanceShips(Collection<Ship> ships) {
		List<String> oldMaintenance = new ArrayList<String>(maintenance);
		removeShips(maintenance, ships);
		change.firePropertyChange(PROP_MAINTENANCE, oldMaintenance, maintenance);
	}

	public void removeActiveOrMaintenanceShips(Collection<Ship> ships) {
		List<String> oldActive = new ArrayList<String>(active);
		List<String> oldMaintenance = new ArrayList<String>(maintenance);
		for (Ship ship : ships) {
			String name = ship.getName();
			if (active.contains(name)) {
				active.remove(name);
			}
			else if (maintenance.contains(name)) {
				maintenance.remove(name);
			}
		}
		change.firePropertyChange(PROP_ACTIVE, oldActive, active);
		change.firePropertyChange(PROP_MAINTENANCE, oldMaintenance, maintenance);
	}

	public List<Ship> getOneTimeShips() {
		List<Ship> ships = new ArrayList<Ship>();
		_getShips(oneTime, ships, ShipViewMode.OneTime);
		Collections.sort(ships);
		return ships;
	}
	
	@XmlTransient
	public void setOneTimeShips(Set<Ship> ships) {
		List<String> oldOneTime = new ArrayList<String>(oneTime);
		setShips(oneTime, ships);
		change.firePropertyChange(PROP_ONETIME, oldOneTime, oneTime);
	}
	
	public void addOneTimeShips(Collection<Ship> ships) {
		List<String> oldOneTime = new ArrayList<String>(oneTime);
		addShips(oneTime, ships);
		change.firePropertyChange(PROP_ONETIME, oldOneTime, oneTime);
	}
	
	public void removeOneTimeShips(Collection<Ship> ships) {
		List<String> oldOneTime = new ArrayList<String>(oneTime);
		removeShips(oneTime, ships);
		change.firePropertyChange(PROP_ONETIME, oldOneTime, oneTime);
	}
	
	public List<Ship> getStarshipTraits() {
		List<Ship> ships = new ArrayList<Ship>();
		_getShips(active, ships, ShipViewMode.StarshipTrait);
		_getShips(maintenance, ships, ShipViewMode.StarshipTrait);
		Collections.sort(ships);
		return ships;
	}

	public String assignShips(List<Ship> ships) {
		StringBuilder sbMaintenance = new StringBuilder();
		StringBuilder sbOneTime = new StringBuilder();
		List<String> oldActive = new ArrayList<String>(active);
		List<String> oldMaintenance = new ArrayList<String>(maintenance);
		List<String> oldOneTime = new ArrayList<String>(oneTime);
		Map<String, Integer> oldUsage = new HashMap<String, Integer>(usage);
		for (Ship ship : ships) {
			if (ship == null) continue;
			String shipName = ship.getName();
			if (active.remove(shipName)) {
				// Move active ship to maintenance roster
				maintenance.add(shipName);
				sbMaintenance.append("<li>").append(shipName).append("</li>");
				useShip(shipName);
			}
			else if (oneTime.remove(shipName)) {
				// Removed one-time ship
				sbOneTime.append("<li>").append(shipName).append("</li>");
				useShip(shipName);
			}
		}
		change.firePropertyChange(PROP_ACTIVE, oldActive, active);
		change.firePropertyChange(PROP_MAINTENANCE, oldMaintenance, maintenance);
		change.firePropertyChange(PROP_ONETIME, oldOneTime, oneTime);
		change.firePropertyChange(PROP_USAGE, oldUsage, usage);
		
		String strMaintenance = sbMaintenance.toString();
		String strOneTime = sbOneTime.toString();
		if (strMaintenance.length() + strOneTime.length() == 0) {
			return "These ships have already been assigned.";
		}
		else {
			StringBuilder sb = new StringBuilder().append("<html>");
			if (strMaintenance.length() > 0) {
				sb.append("Active ship(s) assigned:</br><ul class=\"info\">").append(strMaintenance).append("</ul>");
			}
			if (strOneTime.length() > 0) {
				sb.append("One-time ship(s) assigned:</br><ul class=\"info\">").append(strOneTime).append("</ul>");
			}
			return sb.append("</html>").toString();
		}
	}
	
	/*
	public String assignShipsV2(Map<Ship, Long> ships) {
		StringBuilder sbMaintenance = new StringBuilder();
		StringBuilder sbOneTime = new StringBuilder();
		List<String> oldActive = new ArrayList<String>(active);
		Map<String, Long> oldMaintenanceV2 = new HashMap<String, Long>(maintenanceV2);
		List<String> oldOneTime = new ArrayList<String>(oneTime);
		Map<String, Integer> oldUsage = new HashMap<String, Integer>(usage);
		for (Map.Entry<Ship, Long> entry: ships.entrySet()) {
			Ship ship = entry.getKey();
			long time = entry.getValue();
			if (ship == null) continue;
			String shipName = ship.getName();
			
			if (active.remove(shipName)) {
				// Move active ship to maintenance schedule
				maintenanceV2.put(shipName, time);
				sbMaintenance.append("<li>").append(shipName).append("</li>");
				useShip(shipName);
			}
			else if (oneTime.remove(shipName)) {
				// Removed one-time ship
				sbOneTime.append("<li>").append(shipName).append("</li>");
				useShip(shipName);
			}
		}
		change.firePropertyChange(PROP_ACTIVE, oldActive, active);
		change.firePropertyChange(PROP_SCHEDULE, oldMaintenanceV2, maintenanceV2);
		change.firePropertyChange(PROP_ONETIME, oldOneTime, oneTime);
		change.firePropertyChange(PROP_USAGE, oldUsage, usage);
		
		String strMaintenance = sbMaintenance.toString();
		String strOneTime = sbOneTime.toString();
		if (strMaintenance.length() + strOneTime.length() == 0) {
			return "These ships have already been assigned.";
		}
		else {
			StringBuilder sb = new StringBuilder().append("<html>");
			if (strMaintenance.length() > 0) {
				sb.append("Active ship(s) assigned:</br><ul>").append(strMaintenance).append("</ul>");
			}
			if (strOneTime.length() > 0) {
				sb.append("One-time ship(s) assigned:</br><ul>").append(strOneTime).append("</ul>");
			}
			return sb.append("</html>").toString();
		}
	}
	*/
	
	public List<Ship> getDeployableShips() {
		List<Ship> ships = new ArrayList<Ship>();
		if (prioritizeActive) {
			ships.addAll(getActiveShips());
			ships.addAll(getOneTimeShips());
		}
		else {
			ships.addAll(getOneTimeShips());
			ships.addAll(getActiveShips());
		}
		return ships;
	}
	
	public static Set<Ship> getShipUsageData(Admiral ... admirals) {
		Map<String, Ship> ships = Datastore.getAllShips();
		Set<Ship> usageData = new TreeSet<Ship>();
		for (Admiral admiral : admirals) {
			for (Map.Entry<String, Integer> entry : admiral.getUsage().entrySet()) {
				String shipName = entry.getKey();
				int usageCount = entry.getValue();
				Ship ship = ships.get(shipName.toLowerCase());
				if (!usageData.contains(ship)) {
					ship.setUsageCount(usageCount);
					usageData.add(ship);
				}
				else {
					ship.incrementUsageCount(usageCount);
				}
			}
			for (String shipName : admiral.getActive()) {
				Ship ship = ships.get(shipName.toLowerCase());
				if (!usageData.contains(ship)) {
					ship.setUsageCount(0);
					usageData.add(ship);
				}
			}
			for (String shipName : admiral.getMaintenance()) {
				Ship ship = ships.get(shipName.toLowerCase());
				if (!usageData.contains(ship)) {
					ship.setUsageCount(0);
					usageData.add(ship);
				}
			}
		}
		return usageData;
	}
	
	public List<CompositeSolution> solveAssignments(List<Ship> ships) {
		Assignment assignment1 = numAssignments >= 1 ? assignments.get(0) : null;
		Assignment assignment2 = numAssignments >= 2 ? assignments.get(1) : null;
		Assignment assignment3 = numAssignments >= 3 ? assignments.get(2) : null;
		List<CompositeSolution> solutions = Solver.solve(assignment1, assignment2, assignment3, ships, Globals.SOLVER_DEPTH);
		return solutions;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		change.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		change.removePropertyChangeListener(l);
	}
	
	protected void _getShips(Collection<String> names, Collection<Ship> ships) {
		_getShips(names, ships, ShipViewMode.Default);
	}
	
	protected void _getShips(Collection<String> names, Collection<Ship> ships, ShipViewMode viewMode) {
		SortedMap<String, Ship> database = Datastore.getAllShips();
		for (String name : names) {
			Ship ship = database.get(name.toLowerCase());
			if (ship != null) {
				switch (viewMode) {
				case OneTime:
					ships.add(ship.getOneTimeShip());
					break;
				case StarshipTrait:
					if (ship.hasTrait()) {
						ships.add(ship);
					}
					break;
				case Default:
				case UsageCount:
				default:
					ships.add(ship);
					break;
				}
			}
		}
	}
	
	/**
	 * Validation check on all ships.
	 * 1) Check every ship against the "renamed ship list".
	 * 2) Remove ships that do not exist in the ship database.
	 * 3) Identify ships owned by the player
	 * 4) If required, download ship icon from the web  
	 */
	public void validateShips() {
		List<String> activeErr = new ArrayList<String>();
		List<String> maintenanceErr = new ArrayList<String>();
		List<String> maintenanceV2Err = new ArrayList<String>();
		List<String> oneTimeErr = new ArrayList<String>();
		List<String> usageErr = new ArrayList<String>();
		SortedMap<String, Ship> database = Datastore.getAllShips();
		SortedMap<String, String> renamed = Datastore.getRenamedShips();
		for (String name : renamed.keySet()) {
			String newName = renamed.get(name);
			if (active.contains(name)) {
				active.remove(name);
				active.add(newName);
			}
			if (maintenance.contains(name)) {
				maintenance.remove(name);
				maintenance.add(newName);
			}
			/*
			if (maintenanceV2.containsKey(name)) {
				long time = maintenanceV2.get(name);
				maintenanceV2.remove(name);
				maintenanceV2.put(newName, time);
			}
			*/
			if (oneTime.contains(name)) {
				oneTime.remove(name);
				oneTime.add(newName);
			}
			if (usage.containsKey(name)) {
				usage.put(newName, usage.get(name));
				usage.remove(name);
			}
		}
		for (String name : active) {
			String shipId = name.toLowerCase(); 
			if (!database.containsKey(shipId)) {
				// Ship does not exist in the ship database
				activeErr.add(name);
			}
			else {
				// Mark ship as owned by player
				// Download ship icon if required
				database.get(shipId).setOwned(true);
			}
		}
		
		for (String name : maintenance) {
			String shipId = name.toLowerCase();
			if (!database.containsKey(shipId)) {
				// Ship does not exist in the ship database
				maintenanceErr.add(name);
			}
			else {
				// Mark ship as owned by player
				database.get(shipId).setOwned(true);
			}
		}
		/*
		for (String name : maintenanceV2.keySet()) {
			if (!database.containsKey(name.toLowerCase())) {
				// Ship does not exist in the ship database
				maintenanceV2Err.add(name);
			}
			else {
				// Mark ship as owned by player
				database.get(name).setOwned(true);
			}
		}
		*/
		for (String name : oneTime) {
			if (!database.containsKey(name.toLowerCase())) {
				oneTimeErr.add(name);
			}
		}
		for (String name : usage.keySet()) {
			if (!database.containsKey(name.toLowerCase())) {
				usageErr.add(name);
			}
		}
		for (Iterator<Map.Entry<String, Integer>> iterator = usage.entrySet().iterator(); iterator.hasNext(); ) {
			String name = iterator.next().getKey();
			if (!database.containsKey(name.toLowerCase())) {
				iterator.remove();
			}
		}
		if (!activeErr.isEmpty()) {
			active.removeAll(activeErr);
		}
		if (!maintenanceErr.isEmpty()) {
			maintenance.removeAll(maintenanceErr);
		}
		/*
		if (!maintenanceV2Err.isEmpty()) {
			for (String name : maintenanceV2Err) {
				maintenanceV2.remove(name);
			}
		}
		*/
		if (!oneTimeErr.isEmpty()) {
			oneTime.removeAll(oneTimeErr);
		}
		if (!usageErr.isEmpty()) {
			for (String name : usageErr) {
				usage.remove(name);
			}
		}
		activeErr.clear();
		maintenanceErr.clear();
		oneTimeErr.clear();
		maintenanceV2Err.clear();
		usageErr.clear();
	}
	
	/**
	 * For each ship that has completed it's maintenance, 
	 * 1) Remove the ship from the maintenance list.
	 * 2) Add the the ship to the active ship list.
	 */
	public void activateShips() {
		/*
		long currentTime = System.currentTimeMillis();
		for (Map.Entry<String, Long> entry : getSchedule().entrySet()) {
			String shipname = entry.getKey();
			long time = entry.getValue();
			if (time < currentTime && maintenance.contains(shipname)) {
				maintenance.remove(shipname);
				active.add(shipname);
			}
		}*/
	}
	
	protected void setShips(List<String> names, Set<Ship> ships) {
		names.clear();
		addShips(names, ships);
	}
	
	protected void addShips(List<String> names, Collection<Ship> ships) {
		for (Ship ship : ships) {
			names.add(ship.getName());
		}
	}

	protected void removeShips(List<String> names, Collection<Ship> ships) {
		for (Ship ship : ships) {
			names.remove(ship.getName());
		}
	}
	
	protected void useShip(String shipName) {
		int count = 0;
		if (usage.containsKey(shipName)) {
			count = usage.get(shipName);
		}
		usage.put(shipName, count + 1);
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(name).append(" {\n\tFaction: ").append(faction).append("\n\tActive Ships: ")
				.append(active.size()).append("\n\tMaintenance Ships: ").append(maintenance.size())
				.append("\n\tOne Time Ships: ").append(oneTime.size())
				.append("\n\tShip Usage: ").append(usage.size()).append("\n}").toString();
	}

}
