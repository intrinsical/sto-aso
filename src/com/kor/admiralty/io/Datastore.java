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
package com.kor.admiralty.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.kor.admiralty.Globals;
import com.kor.admiralty.beans.AdmAssignment;
import com.kor.admiralty.beans.Admiral;
import com.kor.admiralty.beans.Admirals;
import com.kor.admiralty.beans.Event;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.ui.workers.SwingWorkerExecutor;

import static com.kor.admiralty.Globals.FILENAME_ADMIRALS;
import static com.kor.admiralty.Globals.FILENAME_ASSIGNMENTS;
import static com.kor.admiralty.Globals.FILENAME_EVENTS;
import static com.kor.admiralty.Globals.FILENAME_ICONCACHE;
import static com.kor.admiralty.Globals.FILENAME_NEWCACHE;
import static com.kor.admiralty.Globals.FILENAME_RENAMED;
import static com.kor.admiralty.Globals.FILENAME_SHIPCACHE;
import static com.kor.admiralty.Globals.FILENAME_TRAITS;
import static com.kor.admiralty.ui.resources.Strings.ExceptionDialog.*;

public class Datastore {

	private static final Logger logger = Logger.getGlobal();
	private static SortedMap<String, File> FILES = new TreeMap<String, File>();
	private static SortedMap<String, Ship> SHIPS = new TreeMap<String, Ship>();
	private static SortedMap<String, Event> EVENTS = new TreeMap<String, Event>();
	private static SortedMap<String, AdmAssignment> ASSIGNMENTS = new TreeMap<String, AdmAssignment>();
	private static SortedMap<String, String> RENAMED = new TreeMap<String, String>();
	private static SortedMap<String, String> TRAITS = new TreeMap<String, String>();
	private static SortedMap<String, ImageIcon> ICONS = new TreeMap<String, ImageIcon>();
	private static Admirals ADMIRALS = null;
	private static boolean ICONS_CHANGED = false;

	private static transient JAXBContext admiralsContext;
	private static transient Marshaller admiralsMarshaller;
	private static transient Unmarshaller admiralsUnmarshaller;
	private static final File FOLDER_CURRENT = file(".");

	static {
		init();
	}
	
	public static File getCurrentFolder() {
		return FOLDER_CURRENT;
	}

	public static SortedMap<String, Ship> getAllShips() {
		if (SHIPS.isEmpty()) {
			loadShipDatabase();
		}
		return SHIPS;
	}
	
	public static SortedMap<String, Event> getEvents() {
		if (EVENTS.isEmpty()) {
			loadEvents();
		}
		return EVENTS;
	}

	public static SortedMap<String, AdmAssignment> getAssignments() {
		if (ASSIGNMENTS.isEmpty()) {
			loadAssignments();
		}
		return ASSIGNMENTS;
	}
	
	public static SortedMap<String, String> getRenamedShips() {
		if (RENAMED.isEmpty()) {
			loadRenamedShips();
		}
		return RENAMED;
	}
	
	public static SortedMap<String, String> getTraits() {
		if (TRAITS.isEmpty()) {
			loadTraits();
		}
		return TRAITS;
	}
	
	public static SortedMap<String, ImageIcon> getCachedIcons() {
		if (ICONS.isEmpty()) {
			loadCachedIcons();
		}
		return ICONS;
	}
	
	public static boolean isDataFilesStale() {
		File file = file(Globals.FILENAME_HASHES);
		return file.exists() ? isStale(file) : true;
	}

	public static boolean isIconCacheStale() {
		File file = file(FILENAME_ICONCACHE);
		if (!file.exists()) return true;
		
		if (isStale(file)) {
			file.setLastModified(System.currentTimeMillis());
			return true;
		}
		return false;
	}
	
	public static void setIconCacheChanged(boolean change) {
		ICONS_CHANGED = change;
	}
	
	public static void preserveIconCache() {
		if (ICONS_CHANGED) {
			saveCachedIcons();
		}
	}
	
	private static void loadShipDatabase() {
		File file = file(FILENAME_SHIPCACHE);
		
		/*/ Unused code, currently does nothing.
		if (!file.exists()) {
			//updateShipDatabase(file);
		}

		long refreshTime = getCacheTime();
		long lastModified = file.lastModified();
		if (refreshTime < lastModified) {
			//updateShipDatabase(file);
		}
		/*/

		SHIPS.clear();
		try (Reader reader = loadFile(file)) {
			ShipDatabaseParser.loadShipDatabase(reader, SHIPS);
		} catch (IOException cause) {
			logger.log(Level.WARNING, String.format(ErrorReading, file.getName()), cause);
		}
		
		/*/ Code needs further refinement. 
		// Need to consider the implication of silently downloading the same file every week.
		// Is there a better way? Check against some sort of hash before downloading?
		if (isStale(file)) {
			// Update ships.csv for the next time
			SwingWorkerExecutor.downloadShipList(file);
		}
		/*/
	}
	
	private static void loadEvents() {
		File file = file(FILENAME_EVENTS);
		EVENTS.clear();
		try (Reader reader = loadFile(file)) {
			EventsParser.loadEvents(reader, EVENTS);
		} catch (IOException cause) {
			logger.log(Level.WARNING, String.format(ErrorReading, file.getName()), cause);
		}
	}
	
	private static void loadAssignments() {
		File file = file(FILENAME_ASSIGNMENTS);
		ASSIGNMENTS.clear();
		try (Reader reader = loadFile(file)) {
			AssignmentsParser.loadAssignments(reader, ASSIGNMENTS);
		} catch (IOException cause) {
			logger.log(Level.WARNING, String.format(ErrorReading, file.getName()), cause);
		}
	}
	
	private static void loadRenamedShips() {
		File file = file(FILENAME_RENAMED);
		RENAMED.clear();
		try (Reader reader = loadFile(file)) {
			RenamedShipParser.loadRenamedShips(reader, RENAMED);
		} catch (IOException cause) {
			logger.log(Level.WARNING, String.format(ErrorReading, file.getName()), cause);
		}
	}
	
	private static void loadTraits() {
		File file = file(FILENAME_TRAITS);
		TRAITS.clear();
		try (Reader reader = loadFile(file)) {
			TraitsParser.loadTraits(reader, TRAITS);
		} catch (IOException cause) {
			logger.log(Level.WARNING, String.format(ErrorReading, file.getName()), cause);
		}
	}
	
	private static void loadCachedIcons() {
		File file = file(FILENAME_ICONCACHE);
		ICONS.clear();
		IconLoader.loadCachedIcons(file, ICONS);
	}
	
	private static void saveCachedIcons() {
		File oldFile = file(FILENAME_ICONCACHE);
		if (oldFile.exists()) {
			oldFile.delete();
		}
		
		File newFile = file(FILENAME_NEWCACHE);
		IconLoader.saveCachedIcons(newFile, ICONS);
		
		newFile.renameTo(oldFile);
	}
	
	/*/
	private static long getCacheTime() {
		return System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000);
	}
	//*/

	public static Admirals getAdmirals() {
		if (ADMIRALS == null) {
			try {
				ADMIRALS = loadAdmirals(file(FILENAME_ADMIRALS));
			} catch (JAXBException cause) {
				logger.log(Level.WARNING, String.format(ErrorReading, FILENAME_ADMIRALS), cause);
			}
			for (Admiral admiral : ADMIRALS.getAdmirals()) {
				admiral.validateShips();
				admiral.activateShips();
			}
			if (isDataFilesStale()) {
				SwingWorkerExecutor.updateDataFiles();
			}
			if (isIconCacheStale()) {
				// Download icons for ships owned by the user
				// As there can potentially be hundreds of icons to download,
				// the update is done in the background. 
				// As a result, the UI may not always be up to date for the current run.
				for (Ship ship : getAllShips().values()) {
					if (ship.isOwned()) SwingWorkerExecutor.downloadIcon(ship); 
				}
			}
		}
		return ADMIRALS;
	}
	
	public static void setAdmirals(Admirals admirals) {
		try {
			saveAdmirals(file(FILENAME_ADMIRALS), admirals);
		} catch (JAXBException cause) {
			logger.log(Level.WARNING, String.format(ErrorWriting, FILENAME_ADMIRALS), cause);
		}
	}

	public static Admirals loadAdmirals(File file) throws JAXBException {
		if (!file.exists()) {
			ADMIRALS = new Admirals();
			saveAdmirals(file, ADMIRALS);
		}
		return (Admirals)admiralsUnmarshaller.unmarshal(file);
	}

	public static void saveAdmirals(File file, Admirals admirals) throws JAXBException {
		admiralsMarshaller.marshal(admirals, file);
	}
	
	public static boolean exportShips(File file, Collection<Ship> ships) {
		try {
			PrintStream out = new PrintStream(file);
			for(Ship ship : ships) {
				out.println(ship.getDisplayName());
			}
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static int importShips(File file, Admiral admiral) {
		int counter = 0;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			SortedMap<String, Ship> ships = getAllShips();
			String line = reader.readLine();
			while (line != null) {
				line = line.trim();
				Ship ship = ships.get(line.toLowerCase());
			    if (ship != null) {
			    	admiral.addActive(line);
			    	counter++;
			    }
			    line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return counter;
	}

	private static void init() {
		// Setup JAXB parser for admirals.xml file
		try {
			admiralsContext = JAXBContext.newInstance(Admirals.class);
			admiralsMarshaller = admiralsContext.createMarshaller();
			admiralsMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			admiralsUnmarshaller = admiralsContext.createUnmarshaller();
		} catch (Throwable cause) {
			logger.log(Level.WARNING, ErrorInitJAXB, cause);
		}
	}
	
	public static File file(String filename) {
		if (!FILES.containsKey(filename)) {
			FILES.put(filename, new File(filename));
		}
		return FILES.get(filename);
	}

	private static Reader loadFile(File file) {
		if (file.exists()) {
			try {
				return new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean isFresh(File file) {
		return Globals.isTimestampFresh(file.lastModified());
	}
	
	public static boolean isStale(File file) {
		return Globals.isTimestampStale(file.lastModified());
	}
	
	public static void copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[1024];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
	}
	
}
