package com.kor.admiralty.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.kor.admiralty.beans.Ship;
import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.enums.Tier;
import com.kor.admiralty.io.Datastore;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class DataValidator extends JFrame {

	private static final long serialVersionUID = -8614776853165864718L;
	private static final String FOLDERNAME = "D:\\github\\sto-aso\\icons";
	
	private List<LoggingAction> actions;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataValidator frame = new DataValidator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DataValidator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		init();
	}
	
	public void init() {
		actions = new ArrayList<LoggingAction>();
		actions.add(new VerifyShipIconsAction());
		actions.add(new VerifyTraitsAction());
		
		for (LoggingAction action : actions) {
			action.actionPerformed(null);
			String name = (String) action.getValue(Action.NAME);
			JScrollPane scrollPane = new JScrollPane();
			JLabel label = new JLabel(action.toString());
			label.setVerticalAlignment(SwingConstants.TOP);
			scrollPane.setViewportView(label);
			tabbedPane.addTab(name, null, scrollPane, null);
		}
	}
	
	private abstract class LoggingAction extends AbstractAction implements Runnable {
		private static final long serialVersionUID = 6930278396074971674L;
		protected StringBuilder sb;
		public LoggingAction(String name) {
			super(name);
		}
		public final void actionPerformed(ActionEvent e) {
			sb = new StringBuilder();
			sb.append("<html>");
			try {
				run();
			} catch (Throwable cause) {
				log(cause);
			}
			sb.append("</html>");
		}
		protected void log(String message) {
			sb.append(String.format("<p>%s</p>", message));
		}
		protected void log(Throwable t) {
			log("Error: " + t.getMessage());
		}
		public String toString() {
			return sb.toString();
		}
	}
	
	private class VerifyShipIconsAction extends LoggingAction {
		private static final long serialVersionUID = 8333590579621353835L;
		public VerifyShipIconsAction() {
			super("Missing Ship Icons");
		}
		public void run() {
			File folder = new File(FOLDERNAME);
			SortedSet<String> filenames = new TreeSet<String>(); 
			int counter = 0;
			for (Ship ship : Datastore.getAllShips().values()) {
				String name = ship.getName();
				String iconName = ship.getIconName();
				filenames.add(iconName);
				File iconFile = new File(folder, iconName);
				boolean hasIcon = iconFile.exists();
				
				if (!hasIcon) {
					counter++;
					log(String.format("%3d: %s\t%s", counter, name, iconName));
				}
			}
			if (counter == 0) {
				log("All ships have icons.");
			}
			
			counter = 0;
			for (File file : folder.listFiles()) {
				String filename = file.getName();
				if (!filename.endsWith(".png")) continue;
				
				if (!filenames.contains(filename)) {
					counter++;
					log(file.getName());
				}
			}
			if (counter == 0) {
				log("All images are accounted for.");
			}
		}
	}
	
	private class VerifyTraitsAction extends LoggingAction {
		private static final long serialVersionUID = 4847994585223344441L;
		public VerifyTraitsAction() {
			super("Missing Traits");
		}
		public void run() {
			int counter = 0;
			for (Ship ship : Datastore.getAllShips().values()) {
				boolean isTier6 = ship.getTier().equals(Tier.Tier6);
				boolean isEpic = ship.getRarity().equals(Rarity.Epic);
				boolean isFleet = ship.getName().toLowerCase().startsWith("fleet");
				boolean hasTrait = ship.hasTrait();
				if (!isTier6) continue;
				if (isEpic) continue;
				if (isFleet) continue;
				if (!hasTrait) {
					counter++;
					log(String.format("Ship with no trait: %s", ship.getDisplayName()));
				}
			}
			if (counter == 0) {
				log("All T6 ships have traits.");
			}
		}
	}
	
}
