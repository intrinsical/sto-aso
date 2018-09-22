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

import com.kor.admiralty.beans.AdmAssignment;
import com.kor.admiralty.enums.Rarity;

public class AssignmentsParser {

	public static void loadAssignments(Reader reader, SortedMap<String, AdmAssignment> assignments) {
		try {
			for (CSVRecord record : CSVFormat.EXCEL.withHeader().parse(reader)) {
				AdmAssignment assignment = loadAssignment(record);
				if (assignment != null) {
					assignments.put(assignment.getName().toLowerCase(), assignment);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static AdmAssignment loadAssignment(CSVRecord record) {
		try {
			// Just in case LibreOffice Calc replaced dashes '-' with '–'
			String name = record.get("Assignment").trim().replace('–', '-');
			Rarity rarity = Rarity.valueOf(record.get("Rarity").trim());
			int eng = Integer.parseInt(record.get("Eng").trim());
			int tac = Integer.parseInt(record.get("Tac").trim());
			int sci = Integer.parseInt(record.get("Sci").trim());
			int hours = Integer.parseInt(record.get("Hours").trim());
			int minutes = Integer.parseInt(record.get("Minutes").trim());
			AdmAssignment assignment = new AdmAssignment(name, rarity, eng, tac, sci, hours, minutes);
			return assignment;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
