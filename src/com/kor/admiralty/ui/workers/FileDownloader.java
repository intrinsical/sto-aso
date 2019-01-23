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
package com.kor.admiralty.ui.workers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import com.kor.admiralty.io.Datastore;

public class FileDownloader extends SwingWorker<Boolean, Boolean> {

	protected static final Logger LOGGER = Logger.getGlobal();
	
	protected File file;
	protected String remoteName;

	public FileDownloader(File file, String remoteName) {
		this.file = file;
		this.remoteName = remoteName;
	}

	@Override
	protected Boolean doInBackground() {
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			URL url = new URL(remoteName);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			writer = new FileWriter(file);
			Datastore.copy(reader, writer);
		} catch (MalformedURLException cause) {
			LOGGER.log(Level.WARNING, "Malformed URL: " + remoteName, cause);
			return false;
		} catch (IOException cause) {
			LOGGER.log(Level.WARNING, "Error while downloading " + remoteName, cause);
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException cause) {
					LOGGER.log(Level.WARNING, "Error while downloading " + remoteName, cause);
				}
			}
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException cause) {
					LOGGER.log(Level.WARNING, "Error while downloading " + remoteName, cause);
				}
			}
		}
		return true;
	}
	
}
