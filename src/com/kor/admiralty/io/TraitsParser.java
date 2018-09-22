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

import com.github.rjeschke.txtmark.Processor;

public class TraitsParser {
	
	private static final String FORMAT_ATTRIBUTE = " (%s)";
	private static final String FORMAT_MARKDOWN = "#%s\r\n%s";

	public static void loadTraits(Reader reader, SortedMap<String, String> traits) {
		try {
			for (CSVRecord record : CSVFormat.EXCEL.withHeader().parse(reader)) {
				String trait = record.get("Trait").trim();
				String attributes = record.get("Attributes").trim();
				String description = record.get("Description").trim();
				
				String title = trait;
				if (attributes.length() > 0) {
					title += String.format(FORMAT_ATTRIBUTE, attributes); 
				}
				if (description.length() > 0) {
					String markdown = String.format(FORMAT_MARKDOWN, title, description.replace("$", "\r\n")); 
					description = "<html>" + Processor.process(markdown) + "</html>";
				}
				traits.put(trait.toLowerCase(), description);
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
