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
package com.kor.admiralty.io;

import java.io.IOException;
import java.io.Reader;
import java.util.SortedMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.beans.ShipImpl;
import com.kor.admiralty.beans.SpecialAbility;
import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.enums.ShipFaction;
import com.kor.admiralty.enums.Tier;

public class ShipDatabaseParser {

	public static void loadShipDatabase(Reader reader, SortedMap<String, Ship> ships) {
		try {
			for (CSVRecord record : CSVFormat.EXCEL.withHeader().parse(reader)) {
				Ship ship = loadShipRecord(record);
				if (ship != null) {
					ships.put(ship.getName().toLowerCase(), ship);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Ship loadShipRecord(CSVRecord record) {
		try {
			ShipFaction faction = ShipFaction.valueOf(record.get("Faction").trim());
			int t = Integer.parseInt(record.get("Tier").trim());
			Tier tier = Tier.None;
			if (t == 0) tier = Tier.SmallCraft;
			else if (t == 1) tier = Tier.Tier1; 
			else if (t == 2) tier = Tier.Tier2; 
			else if (t == 3) tier = Tier.Tier3; 
			else if (t == 4) tier = Tier.Tier4; 
			else if (t == 5) tier = Tier.Tier5; 
			else if (t == 6) tier = Tier.Tier6; 
			Rarity rarity = Rarity.fromString(record.get("Rarity").trim());
			Role category = Role.valueOf(record.get("Type").trim());
			// Just in case LibreOffice Calc replaced dashes ''' with '’'
			String name = record.get("Name").trim().replace('’', '\'');
			int eng = Integer.parseInt(record.get("Eng").trim());
			int tac = Integer.parseInt(record.get("Tac").trim());
			int sci = Integer.parseInt(record.get("Sci").trim());
			SpecialAbility ability = SpecialAbilityParser2.parse(record.get("Bonus").trim());
			String trait = record.get("Trait").trim();
			if (trait.length() > 0) {
				String key = trait.toLowerCase();
				String value = Datastore.getTraits().get(key);
				if (value != null && value.length() > 0) {
					trait = value;
				}
			}
			Ship ship = new ShipImpl(faction, tier, rarity, category, name, eng, tac, sci, ability, trait);
			return ship;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
