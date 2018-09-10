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
package com.kor.admiralty.ui.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.ShipSortOrder;
import com.kor.admiralty.io.Datastore;

public class ShipListModel extends AbstractListModel<Ship> {
	
	private static final long serialVersionUID = 7434906615168264076L;
	
	protected List<Ship> ships;
	protected BitSet included;
	protected int visible[];
	protected ShipSortOrder sortOrder = ShipSortOrder.Default;
	
	protected boolean showFederation = true;
	protected boolean showKlingon = true;
	protected boolean showRomulan = true;
	protected boolean showJemHadar = true;
	protected boolean showUniversal = true;
	
	protected boolean showEngineering = true;
	protected boolean showTactical = true;
	protected boolean showScience = true;
	
	protected boolean showSmallCraft = true;
	protected boolean showTier1 = true;
	protected boolean showTier2 = true;
	protected boolean showTier3 = true;
	protected boolean showTier4 = true;
	protected boolean showTier5 = true;
	protected boolean showTier6 = true;
	
	protected boolean showCommon = true;
	protected boolean showUncommon = true;
	protected boolean showRare = true;
	protected boolean showVeryRare = true;
	protected boolean showUltraRare = true;
	protected boolean showEpic = true;
	
	
	public ShipListModel() {
		this.ships = new ArrayList<Ship>();
		this.included = new BitSet();
		this.visible = new int[0];
	}
	
	public ShipListModel(Collection<Ship> ships) {
		this.ships = new ArrayList<Ship>(ships);
		
		int size = this.ships.size();
		this.included = new BitSet(size);
		this.included.set(0, size);
		this.visible = new int[size];
		for (int i = 0; i < size; i++) {
			this.visible[i] = i;
		}
	}
	
	public void removeAllShips() {
		ships.clear();
		updateIncluded();
	}
	
	public void setShips(Collection<Ship> collection) {
		ships.clear();
		addShips(collection);
	}

	public void addShips(Collection<Ship> collection) {
		ships.addAll(collection);
		this.visible = new int[ships.size()];
		updateIncluded();
	}

	@Override
	public int getSize() {
		return included.cardinality();
	}
	
	@Override
	public Ship getElementAt(int index) {
		return ships.get(visible[index]);
	}
	
	public ShipSortOrder getShipSortOrder() {
		return sortOrder;
	}
	
	public void setShipSortOrder(ShipSortOrder sortOrder) {
		this.sortOrder = sortOrder;
		updateIncluded();
	}

	public boolean isShowFederation() {
		return showFederation;
	}

	public void setShowFederation(boolean showFederation) {
		this.showFederation = showFederation;
		updateIncluded();
	}

	public boolean isShowKlingon() {
		return showKlingon;
	}

	public void setShowKlingon(boolean showKlingon) {
		this.showKlingon = showKlingon;
		updateIncluded();
	}

	public boolean isShowRomulan() {
		return showRomulan;
	}

	public void setShowRomulan(boolean showRomulan) {
		this.showRomulan = showRomulan;
		updateIncluded();
	}

	public boolean isShowJemHadar() {
		return showRomulan;
	}

	public void setShowJemHadar(boolean showJemHadar) {
		this.showJemHadar = showJemHadar;
		updateIncluded();
	}

	public boolean isShowUniversal() {
		return showUniversal;
	}

	public void setShowUniversal(boolean showUniversal) {
		this.showUniversal = showUniversal;
		updateIncluded();
	}

	public boolean isShowEngineering() {
		return showEngineering;
	}

	public void setShowEngineering(boolean showEngineering) {
		this.showEngineering = showEngineering;
		updateIncluded();
	}

	public boolean isShowTactical() {
		return showTactical;
	}

	public void setShowTactical(boolean showTactical) {
		this.showTactical = showTactical;
		updateIncluded();
	}

	public boolean isShowScience() {
		return showScience;
	}

	public void setShowScience(boolean showScience) {
		this.showScience = showScience;
		updateIncluded();
	}

	public boolean isShowSmallCraft() {
		return showSmallCraft;
	}

	public void setShowSmallCraft(boolean showSmallCraft) {
		this.showSmallCraft = showSmallCraft;
		updateIncluded();
	}

	public boolean isShowTier1() {
		return showTier1;
	}

	public void setShowTier1(boolean showTier1) {
		this.showTier1 = showTier1;
		updateIncluded();
	}

	public boolean isShowTier2() {
		return showTier2;
	}

	public void setShowTier2(boolean showTier2) {
		this.showTier2 = showTier2;
		updateIncluded();
	}

	public boolean isShowTier3() {
		return showTier3;
	}

	public void setShowTier3(boolean showTier3) {
		this.showTier3 = showTier3;
		updateIncluded();
	}

	public boolean isShowTier4() {
		return showTier4;
	}

	public void setShowTier4(boolean showTier4) {
		this.showTier4 = showTier4;
		updateIncluded();
	}

	public boolean isShowTier5() {
		return showTier5;
	}

	public void setShowTier5(boolean showTier5) {
		this.showTier5 = showTier5;
		updateIncluded();
	}

	public boolean isShowTier6() {
		return showTier6;
	}

	public void setShowTier6(boolean showTier6) {
		this.showTier6 = showTier6;
		updateIncluded();
	}

	public boolean isShowCommon() {
		return showCommon;
	}

	public void setShowCommon(boolean showCommon) {
		this.showCommon = showCommon;
		updateIncluded();
	}

	public boolean isShowUncommon() {
		return showUncommon;
	}

	public void setShowUncommon(boolean showUncommon) {
		this.showUncommon = showUncommon;
		updateIncluded();
	}

	public boolean isShowRare() {
		return showRare;
	}

	public void setShowRare(boolean showRare) {
		this.showRare = showRare;
		updateIncluded();
	}

	public boolean isShowVeryRare() {
		return showVeryRare;
	}

	public void setShowVeryRare(boolean showVeryRare) {
		this.showVeryRare = showVeryRare;
		updateIncluded();
	}

	public boolean isShowUltraRare() {
		return showUltraRare;
	}

	public void setShowUltraRare(boolean showUltraRare) {
		this.showUltraRare = showUltraRare;
		updateIncluded();
	}

	public boolean isShowEpic() {
		return showEpic;
	}

	public void setShowEpic(boolean showEpic) {
		this.showEpic = showEpic;
		updateIncluded();
	}
	
	protected boolean include(Ship ship) {
		switch (ship.getFaction()) {
		case Federation: 
			if (!showFederation) return false;
			break;
		case Klingon: 
			if (!showKlingon) return false;
			break;
		case Romulan: 
			if (!showRomulan) return false;
			break;
		case JemHadar:
			if (!showJemHadar) return false;
			break;
		case Universal: 
			if (!showUniversal) return false;
			break;
		default:
		}
		
		switch (ship.getRole()) {
		case Eng:
			if (!showEngineering) return false;
			break;
		case Tac:
			if (!showTactical) return false;
			break;
		case Sci:
			if (!showScience) return false;
			break;
		default:
		}
		
		switch (ship.getTier()) {
		case SmallCraft:
			if (!showSmallCraft) return false;
			break;
		case Tier1: 
			if (!showTier1) return false;
			break;
		case Tier2: 
			if (!showTier2) return false;
			break;
		case Tier3: 
			if (!showTier3) return false;
			break;
		case Tier4: 
			if (!showTier4) return false;
			break;
		case Tier5: 
			if (!showTier5) return false;
			break;
		case Tier6: 
			if (!showTier6) return false;
			break;
		default:
		}
		
		switch (ship.getRarity()) {
		case Common: 
			if (!showCommon) return false;
			break;
		case Uncommon: 
			if (!showUncommon) return false;
			break;
		case Rare: 
			if (!showRare) return false;
			break;
		case VeryRare: 
			if (!showVeryRare) return false;
			break;
		case UltraRare: 
			if (!showUltraRare) return false;
			break;
		case Epic: 
			if (!showEpic) return false;
			break;
		default:
		}
		
		return true;
	}

	protected void updateIncluded() {
		Collections.sort(ships, sortOrder.comparator());
		Arrays.fill(visible, -1);
		included.clear();
		int counter = 0;
		for (int i = 0; i < ships.size(); i++) {
			Ship ship = ships.get(i);
			boolean include = include(ship);
			included.set(i, include);
			if (include) {
				visible[counter] = i;
				counter++;
			}
		}
		fireContentsChanged(this, 0, ships.size());
	}
	
	public static void main(String args[]) {
		Collection<Ship> ships = Datastore.getAllShips().values();
		ShipListModel model = new ShipListModel(ships);
		//model.setShowFederation(false);
		for (int i = 1; i < model.getSize(); i++) {
			Ship ship = model.getElementAt(i);
			System.out.println(i + ": " + ship);
		}
		
		System.out.println(model.getSize() + "/" + ships.size() + " ships.");
	}

}
