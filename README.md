# Star Trek Online Admiralty System Optimizer

Downloads and release notes can be found at https://intrinsical.github.io/categories/sto-aso

If you would like to help, here are some areas to focus on:

 * Add support for tracking ship maintenance
   * Currently when a ship is "Deployed" on an Admiralty assignment, the ship is removed from the [Admiral's](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Admiral.java) list of "active" ships and put into the list of "maintainence" ships. The user has to manually move ships out of the maintenance list and back ito the active list.
   * It would be nice if ASO can track when each ship is ready to be deployed again, and automatically move the ship out of the maintenance list at the right time.
   * You may have noticed sections of the [Admiral](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Admiral.java) class has been commented out. Those commented sections contain my incomplete attempt at tracking maintenance times. 
   * TODOs: 
     * When the program starts, move ships that are ready to be deployed out of the maintenance list. 
     * While the program is running, have a background thread move ships that are ready to be deployed out of the maintenance list.
     * Update UI to display maintenance times. Classes that needs to be updated includes [Ship.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Ship.java), [ShipCellRenderer.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/ui/renderers/ShipCellRenderer.java) and [AdmiralPanel.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/ui/AdmiralPanel.java)
