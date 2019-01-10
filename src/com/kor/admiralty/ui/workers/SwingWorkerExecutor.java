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

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingWorker;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.io.Datastore;
import com.kor.admiralty.ui.resources.ActualShipIconFactory;

public class SwingWorkerExecutor {
	
	private static final int MAX_WORKER_THREAD = 3;
	private static final SwingWorkerExecutor EXECUTOR = new SwingWorkerExecutor();

	private ExecutorService workerThreadPool = Executors.newFixedThreadPool(MAX_WORKER_THREAD);

	private SwingWorkerExecutor() {
	}

	public static SwingWorkerExecutor getInstance() {
		return EXECUTOR;
	}

	/**
	 * 
	 * Adds the SwingWorker to the thread pool for execution.
	 * 
	 * @param worker
	 *            - The SwingWorker thread to execute.
	 * 
	 */
	public void execute(SwingWorker<?, ?> worker) {
		workerThreadPool.submit(worker);
	}
	
	public void downloadShipList(File file) {
		execute(new ShipDataDownloader(file));
	}
	
	public void downloadIcon(Ship ship) {
		// Don't download if we already have a ship icon either in the .jar or icons.zip file
		String iconName = ship.getIconName();
		if (ActualShipIconFactory.hasBundledIcon(iconName)) return;
		if (Datastore.getCachedIcons().containsKey(iconName)) return;
		execute(new ShipIconLoader(ship.getName().toLowerCase(), iconName));
	}
	
}
