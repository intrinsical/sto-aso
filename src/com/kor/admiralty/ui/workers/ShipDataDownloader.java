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

import com.kor.admiralty.Globals;
import com.kor.admiralty.io.Datastore;

public class ShipDataDownloader extends SwingWorker<Boolean, Boolean> {

	protected static final String URL_SHIPDATA = String.format(Globals.URL_UPDATE, "data/ships.csv");
	protected static final Logger LOGGER = Logger.getGlobal();
	
	protected File file;

	public ShipDataDownloader(File file) {
		this.file = file;
	}

	@Override
	protected Boolean doInBackground() {
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			URL url = new URL(URL_SHIPDATA);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			writer = new FileWriter(file);
			Datastore.copy(reader, writer);
		} catch (MalformedURLException cause) {
			LOGGER.log(Level.WARNING, "Malformed URL: " + URL_SHIPDATA, cause);
			return false;
		} catch (IOException cause) {
			LOGGER.log(Level.WARNING, "Error while downloading " + URL_SHIPDATA, cause);
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException cause) {
					LOGGER.log(Level.WARNING, "Error while downloading " + URL_SHIPDATA, cause);
				}
			}
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException cause) {
					LOGGER.log(Level.WARNING, "Error while downloading " + URL_SHIPDATA, cause);
				}
			}
		}
		return true;
	}
	
}
