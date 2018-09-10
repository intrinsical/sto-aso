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

import javax.swing.RowFilter;

import com.kor.admiralty.beans.Ship;

public class ShipRowFilter extends RowFilter<ShipTableModel, Integer> {
	
	protected boolean showFederation = true;
	protected boolean showKlingon = true;
	protected boolean showRomulan = true;
	protected boolean showUniversal = true;
	
	protected boolean showEngineering = true;
	protected boolean showTactical = true;
	protected boolean showScience = true;
	
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
	
	public ShipRowFilter() {
		super();
	}
	
	@Override
	public boolean include(javax.swing.RowFilter.Entry<? extends ShipTableModel, ? extends Integer> entry) {
		int row = entry.getIdentifier();
		Ship ship = (Ship)entry.getModel().getValueAt(row, ShipTableModel.COL_SHIP);
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

	public boolean isShowFederation() {
		return showFederation;
	}

	public void setShowFederation(boolean showFederation) {
		this.showFederation = showFederation;
	}

	public boolean isShowKlingon() {
		return showKlingon;
	}

	public void setShowKlingon(boolean showKlingon) {
		this.showKlingon = showKlingon;
	}

	public boolean isShowRomulan() {
		return showRomulan;
	}

	public void setShowRomulan(boolean showRomulan) {
		this.showRomulan = showRomulan;
	}

	public boolean isShowUniversal() {
		return showUniversal;
	}

	public void setShowUniversal(boolean showUniversal) {
		this.showUniversal = showUniversal;
	}

	public boolean isShowEngineering() {
		return showEngineering;
	}

	public void setShowEngineering(boolean showEngineering) {
		this.showEngineering = showEngineering;
	}

	public boolean isShowTactical() {
		return showTactical;
	}

	public void setShowTactical(boolean showTactical) {
		this.showTactical = showTactical;
	}

	public boolean isShowScience() {
		return showScience;
	}

	public void setShowScience(boolean showScience) {
		this.showScience = showScience;
	}

	public boolean isShowTier1() {
		return showTier1;
	}

	public void setShowTier1(boolean showTier1) {
		this.showTier1 = showTier1;
	}

	public boolean isShowTier2() {
		return showTier2;
	}

	public void setShowTier2(boolean showTier2) {
		this.showTier2 = showTier2;
	}

	public boolean isShowTier3() {
		return showTier3;
	}

	public void setShowTier3(boolean showTier3) {
		this.showTier3 = showTier3;
	}

	public boolean isShowTier4() {
		return showTier4;
	}

	public void setShowTier4(boolean showTier4) {
		this.showTier4 = showTier4;
	}

	public boolean isShowTier5() {
		return showTier5;
	}

	public void setShowTier5(boolean showTier5) {
		this.showTier5 = showTier5;
	}

	public boolean isShowTier6() {
		return showTier6;
	}

	public void setShowTier6(boolean showTier6) {
		this.showTier6 = showTier6;
	}

	public boolean isShowCommon() {
		return showCommon;
	}

	public void setShowCommon(boolean showCommon) {
		this.showCommon = showCommon;
	}

	public boolean isShowUncommon() {
		return showUncommon;
	}

	public void setShowUncommon(boolean showUncommon) {
		this.showUncommon = showUncommon;
	}

	public boolean isShowRare() {
		return showRare;
	}

	public void setShowRare(boolean showRare) {
		this.showRare = showRare;
	}

	public boolean isShowVeryRare() {
		return showVeryRare;
	}

	public void setShowVeryRare(boolean showVeryRare) {
		this.showVeryRare = showVeryRare;
	}

	public boolean isShowUltraRare() {
		return showUltraRare;
	}

	public void setShowUltraRare(boolean showUltraRare) {
		this.showUltraRare = showUltraRare;
	}

	public boolean isShowEpic() {
		return showEpic;
	}

	public void setShowEpic(boolean showEpic) {
		this.showEpic = showEpic;
	}

}
