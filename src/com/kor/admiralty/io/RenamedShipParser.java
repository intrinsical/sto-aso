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

public class RenamedShipParser {

	public static void loadRenamedShips(Reader reader, SortedMap<String, String> ships) {
		try {
			for (CSVRecord record : CSVFormat.EXCEL.withHeader().parse(reader)) {
				String oldName = record.get("Old").trim();
				String newName = record.get("New").trim();
				ships.put(oldName, newName);
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
	
}
