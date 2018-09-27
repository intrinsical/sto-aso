package com.kor.admiralty.ui.workers;

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
	
	public void downloadIcon(Ship ship) {
		// Don't download if we already have a ship icon either in the .jar or icons.zip file
		String iconName = ship.getIconName();
		if (ActualShipIconFactory.hasBundledIcon(iconName)) return;
		if (Datastore.getCachedIcons().containsKey(iconName)) return;
		execute(new ShipIconLoader(ship.getName().toLowerCase(), iconName));
	}
	
}
