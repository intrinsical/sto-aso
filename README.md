# Star Trek Online Admiralty System Optimizer

**[Downloads and release notes can be found here](https://intrinsical.github.io/categories/sto-aso)** 

## Help : Add or fix admiralty ship stats

Star Trek Online is constantly adding new ships to the game, and I don't have access to every ship in the game. I depend on you, the STO community, to provide me with information on the newest ships. 

Specifically, information is currently required for the following ships:

 * Vaadwaur Juggernaut [T6]
 * Mirror Engle Escort Carrier [T6]
 * Styx Terran Dreadnought Cruiser [T6]
 * T'Pau Vulcan Scout Ship [T6]
 * Shran Light Pilot Escort [T6]
 * Gagarin Miracle Worker Battlecruiser [T6]
 * Fleet Shran Light Pilot Escort [T6]
 * Fleet Gagarin Miracle Worker Battlecruiser [T6]
 * M'Chla Pilot Bird of Prey [T6]
 * Qugh Miracle Worker Battlecruiser [T6]
 * Fleet M'Chla Pilot Bird of Prey [T6]
 * Fleet Qugh Miracle Worker Battlecruiser [T6]

**<span style="font-size:larger;">[Click here to submit admiralty ship information](https://github.com/intrinsical/sto-aso/issues/new?template=submit-admiralty-ship-information.md&labels=ship+info&title=New+admiralty+ship)</span>**

## Help : Add support for tracking ship maintenance

Currently when a ship is "Deployed" on an Admiralty assignment, the ship is removed from the [Admiral's](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Admiral.java) list of "active" ships and put into the list of "maintainence" ships. The user has to manually move ships out of the maintenance list and back ito the active list.

It would be nice if ASO can track when each ship is ready to be deployed again, and automatically move the ship out of the maintenance list at the right time.

You may have noticed sections of the [Admiral](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Admiral.java) class has been commented out. Those commented sections contain my incomplete attempt at tracking maintenance times. 

TODOs: 
 * When the program starts, move ships that are ready to be deployed out of the maintenance list. 
 * While the program is running, have a background thread move ships that are ready to be deployed out of the maintenance list.
 * Update UI to display maintenance times. Some of the classes that need to be updated includes [Ship.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Ship.java), [ShipCellRenderer.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/ui/renderers/ShipCellRenderer.java) and [AdmiralPanel.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/ui/AdmiralPanel.java)

## Help : Automated update of data files

With the release of v1.0.30, ASO now has the ability to download ship icons from this github repository. It would be useful to extend this functionality to .csv files. This would potentially allow me to push new ship data without having to release a new version of ASO. 
