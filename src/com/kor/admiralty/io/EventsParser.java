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

import com.kor.admiralty.beans.Event;
import com.kor.admiralty.enums.EventReward;

public class EventsParser {

	public static void loadEvents(Reader reader, SortedMap<String, Event> events) {
		try {
			for (CSVRecord record : CSVFormat.EXCEL.withHeader().parse(reader)) {
				Event event = loadEvent(record);
				if (event != null) {
					events.put(event.getName().toLowerCase(), event);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Event loadEvent(CSVRecord record) {
		try {
			String name = record.get("Event").trim();
			int eng = Integer.parseInt(record.get("Eng").trim());
			int tac = Integer.parseInt(record.get("Tac").trim());
			int sci = Integer.parseInt(record.get("Sci").trim());
			int critRate = Integer.parseInt(record.get("Crit").trim());
			EventReward reward = EventReward.valueOf(record.get("Reward").trim());
			Event event = new Event(name, eng, tac, sci, critRate, reward);
			return event;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
