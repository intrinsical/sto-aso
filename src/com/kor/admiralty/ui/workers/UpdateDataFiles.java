package com.kor.admiralty.ui.workers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;
import javax.xml.bind.DatatypeConverter;

import com.kor.admiralty.Globals;
import com.kor.admiralty.io.Datastore;

import static com.kor.admiralty.Globals.FILENAME_ASSIGNMENTS;
import static com.kor.admiralty.Globals.FILENAME_EVENTS;
import static com.kor.admiralty.Globals.FILENAME_RENAMED;
import static com.kor.admiralty.Globals.FILENAME_SHIPCACHE;
import static com.kor.admiralty.Globals.FILENAME_TRAITS;
import static com.kor.admiralty.ui.resources.Strings.ExceptionDialog.*;

public class UpdateDataFiles extends SwingWorker<Properties, Boolean> {

	protected static final Logger logger = Logger.getLogger(UpdateDataFiles.class.getName());
	protected static final String URL_HASHES = url(Globals.FILENAME_HASHES);
	protected static final String FILENAMES[] = { FILENAME_SHIPCACHE, FILENAME_RENAMED, FILENAME_EVENTS, FILENAME_ASSIGNMENTS,
			FILENAME_TRAITS };

	protected File fileHashes;
	protected Properties hashesLocal;
	protected Properties hashesRemote;

	public UpdateDataFiles() {
		this.fileHashes = new File(Globals.FILENAME_HASHES);
	}
	
	@Override
	protected Properties doInBackground() throws Exception {
		Properties localHashes = loadLocalHashes();
		Properties remoteHashes = loadRemoteHashes();
		for (Object key : localHashes.keySet()) {
			String localHash = localHashes.containsKey(key) ? localHashes.get(key).toString() : "";
			String remoteHash = remoteHashes.containsKey(key) ? remoteHashes.get(key).toString() : "";
			
			if (!localHash.equals(remoteHash)) {
				String filename = key.toString();
				File file = Datastore.file(filename);
				String url = url(filename); 
				SwingWorkerExecutor.downloadFile(file, url);
			}
		}
		return null;
	}

	@Override
	public void done() {

	}

	protected Properties loadLocalHashes() {
		Properties properties = new Properties();
		if (!fileHashes.exists()) {
			for (String filename : FILENAMES) {
				properties.setProperty(filename, hash(filename));
			}
			storeLocalProperties(properties, fileHashes);
		} else {
			loadLocalProperties(properties, fileHashes);
		}
		return properties;
	}

	protected Properties loadRemoteHashes() {
		Properties properties = new Properties();
		try {
			URL url = new URL(URL_HASHES);
			try (Reader reader = new InputStreamReader(url.openStream())) {
				properties.load(reader);
			} catch (IOException cause) {
				logger.log(Level.WARNING, String.format(ErrorReading, URL_HASHES), cause);
			}
		} catch (MalformedURLException cause) {
			logger.log(Level.WARNING, String.format(ErrorMalformedUrl, URL_HASHES), cause);
		}
		return properties;
	}

	protected void loadLocalProperties(Properties properties, File file) {
		try (Reader reader = new FileReader(file)) {
			properties.load(reader);
		} catch (FileNotFoundException cause) {
			logger.log(Level.WARNING, "", cause);
		} catch (IOException cause) {
			logger.log(Level.WARNING, String.format(ErrorReading, file.getName()), cause);
		}
	}

	protected void storeLocalProperties(Properties properties, File file) {
		try (Writer writer = new FileWriter(file)) {
			properties.store(writer, "");
		} catch (IOException cause) {
			logger.log(Level.WARNING, String.format(ErrorWriting, file.getName()), cause);
		}
	}

	protected String hash(String filename) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(Files.readAllBytes(Paths.get(filename)));
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toLowerCase();
		} catch (NoSuchAlgorithmException cause) {
			logger.log(Level.WARNING, ErrorNoMD5, cause);
		} catch (IOException cause) {
			logger.log(Level.WARNING, String.format(ErrorReading, filename), cause);
		}
		return "";
	}
	
	protected static String url(String filename) {
		return String.format(Globals.URL_UPDATE, "data/" + filename);
	}

	public static void main(String args[]) {
		logger.info(URL_HASHES);
		logger.info(Datastore.file(".").toString());
		SwingWorkerExecutor.updateDataFiles();
	}

}
