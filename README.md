# Star Trek Online Admiralty System Optimizer

Downloads and release notes can be found at https://intrinsical.github.io/categories/sto-aso

If you would like to help, here are some areas to focus on:

## Adding new admiralty ships and stats / Fixing ships with wrong stats

Star Trek Online is constantly adding new ships to the game, and I don't have access to every ship in the game. I depend on you, the STO community, to provide me with information on the newest ships. 

Until I find a better way for managing submissions, please use this form to [submit ship information](https://github.com/intrinsical/sto-aso/issues/new?template=submit-admiralty-ship-information.md&labels=ship+info&title=New+admiralty+ship). 

## Add support for tracking ship maintenance

Currently when a ship is "Deployed" on an Admiralty assignment, the ship is removed from the [Admiral's](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Admiral.java) list of "active" ships and put into the list of "maintainence" ships. The user has to manually move ships out of the maintenance list and back ito the active list.

It would be nice if ASO can track when each ship is ready to be deployed again, and automatically move the ship out of the maintenance list at the right time.

You may have noticed sections of the [Admiral](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Admiral.java) class has been commented out. Those commented sections contain my incomplete attempt at tracking maintenance times. 

TODOs: 
 * When the program starts, move ships that are ready to be deployed out of the maintenance list. 
 * While the program is running, have a background thread move ships that are ready to be deployed out of the maintenance list.
 * Update UI to display maintenance times. Some of the classes that need to be updated includes [Ship.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/beans/Ship.java), [ShipCellRenderer.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/ui/renderers/ShipCellRenderer.java) and [AdmiralPanel.class](https://github.com/intrinsical/sto-aso/blob/master/src/com/kor/admiralty/ui/AdmiralPanel.java)

## Icons for every ship

Currently ASO only include icons for commonly used ships (small crafts, ships from Tiers 1 to 4, Epic ships, summer event ships, winter event ships, special event ships, free promotional ships). This is to prevent users from having to download a bloated program filled with ship icons they do not need. 

Perhaps we can host ship icons somewhere (maybe github?). ASO can download and cache icons only for ships that the user owns. 
