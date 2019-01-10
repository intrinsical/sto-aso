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

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import com.kor.admiralty.Globals;
import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.io.Datastore;
import com.kor.admiralty.ui.resources.ActualShipIconFactory;

public class ShipIconLoader extends SwingWorker<ImageIcon, ImageIcon> {

	protected static final String URL_WEBICONS = String.format(Globals.URL_UPDATE, "icons/%s");
	protected static final Logger LOGGER = Logger.getGlobal();
	protected static final Component COMPONENT = new Component() { private static final long serialVersionUID = -8700758873181010251L; };
	protected static final MediaTracker TRACKER = new MediaTracker(COMPONENT);
	protected static int TRACKER_ID;

	protected String shipName;
	protected String fileName;
	protected int trackerId;

	public ShipIconLoader(String shipName, String fileName) {
		this.shipName = shipName;
		this.fileName = fileName;
		this.trackerId = TRACKER_ID++;
	}

	@Override
	protected ImageIcon doInBackground() throws Exception {
		String address = String.format(URL_WEBICONS, fileName);
		URL url = new URL(address);
		//System.out.println("Downloading " + address);
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		Ship ship = Datastore.getAllShips().get(shipName);
		
		if (waitOnImage(image)) {
			// Successfully downloaded ship icon
			ImageIcon shipIcon = ActualShipIconFactory.buildIcon(image, ship.getFaction(), ship.getRole(), ship.getRarity());
			Datastore.getCachedIcons().put(fileName, shipIcon);
			System.out.println("OKAY " + address);
			return shipIcon;
		}
		System.err.println("FAIL " + address);
		return null;
	}
	
	protected boolean waitOnImage(Image image) {
		int status = MediaTracker.LOADING;
		try {
			TRACKER.addImage(image, trackerId);
			TRACKER.waitForID(trackerId);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			status = TRACKER.statusID(trackerId, false);
			TRACKER.removeImage(image, trackerId);
		}
		return (status == MediaTracker.COMPLETE);
	}

}
