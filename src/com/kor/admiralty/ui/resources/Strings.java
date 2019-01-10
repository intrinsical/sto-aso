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
package com.kor.admiralty.ui.resources;

public class Strings {

	public static final String Empty = "";
	protected static final String VERSION = AdmiraltyConsole.class.getPackage().getImplementationVersion();
	
	public static String Version() {
		return VERSION;
	}
	
	public static String toFunctionString(Object object) {
		return toFunctionString(object, "");
	}
	
	public static String toFunctionString(Object object, Object params) {
		return toFunctionString(object.getClass().getSimpleName(), params);
	}

	public static String toFunctionString(String function, Object params) {
		return String.format("%s(%s)", function, params.toString());
	}
	
	public final class Shared {
		public static final String TierSmallCraft = "Small Craft";
		public static final String TierOne = "Tier 1";
		public static final String TierTwo = "Tier 2";
		public static final String TierThree = "Tier 3";
		public static final String TierFour = "Tier 4";
		public static final String TierFive = "Tier 5";
		public static final String TierSix = "Tier 6";
		public static final String TierUnknown = "Unknown Tier";
	
		public static final String MaintSmallCraft = "5m";
		public static final String MaintTier1 = "30m";
		public static final String MaintTier2 = "2h";
		public static final String MaintTier3 = "4h 30m";
		public static final String MaintTier4 = "8h";
		public static final String MaintTier5 = "12h 30m";
		public static final String MaintTier6 = "18h";
		public static final String MaintUnknown = "--";
		
		public static final String RarityUnknown = "Rarity";
		public static final String RarityCommon = "Common";
		public static final String RarityUncommon = "Uncommon";
		public static final String RarityRare = "Rare";
		public static final String RarityVeryRare = "Very Rare";
		public static final String RarityUltraRare = "Ultra Rare";
		public static final String RarityEpic = "Epic";
		
		public static final String RoleEng = "Engineering Ship";
		public static final String RoleSci = "Science Ship";
		public static final String RoleTac = "Tactical Ship";
		public static final String RoleSmc = "Small Craft";
		public static final String RoleUnknown = "None";
		
		public static final String PlayerFederation = "Federation";
		public static final String PlayerKlingon = "Klingon";
		public static final String PlayerRomulanFed = "Romulan (Fed)";
		public static final String PlayerRomulanKDF = "Romulan (KDF)";
		public static final String PlayerJemHadarFed = "Jem'Hadar (Fed)";
		public static final String PlayerJemHadarKDF = "Jem'Hadar (KDF)";
		public static final String PlayerUnknown = "Unknown";
		
		public static final String ShipFactionUnknown = "Faction";
		public static final String ShipFactionFederation = "Federation";
		public static final String ShipFactionKlingon = "Klingon";
		public static final String ShipFactionRomulan = "Romulan";
		public static final String ShipFactionJemHadar = "JemHadar";
		public static final String ShipFactionUniversal = "Universal";
		
		public static final String PriorityActive = "Active Ships";
		public static final String PriorityOneTime = "One-Time Ships";
	}

	public final class AdmiraltyConsole {
		public static final String Title = "STO Admiralty System Optimizer";
		public static final String LabelWindowPosition = "Window Position: ";
		public static final String LabelAdmiral = "Admiral";
		public static final String LabelCreateAdmiral = "Create a new admiral";
		public static final String LabelDeleteAdmiral = "Delete the current admiral";
		public static final String TitleConfirmDelete = "Delete Admiral Confirmation";
		public static final String MsgConfirmDeleteQuestion = "Are you sure you want to delete %s?";
		public static final String DescLHS = "Resize window to fill left half of the screen";
		public static final String DescCTR = "Resize window to center of screen";
		public static final String DescRHS = "Resize window to fill right half of the screen";
		public static final String DescStayOnTop = "Float this window above the STO game client";
		public static final String DescInfo = "About the Admiralty System Optimizer";
		public static final String DescShipStats = "Show ship deployment statistics";
		public static final String LabelAbout = "About";
		public static final String MsgVersionInfo = "<html><div style=\"width: 300px;\"><p><b>Star Trek Online - Admiralty System Optimizer</b><br><b>Version:</b>&nbsp;%s<br><b>Written by:</b>&nbsp;@intrinsical</p><p>&nbsp;</p><p>This is a project of love written in my spare time. If you have any suggestions, feedback or bug reports, please send them to @intrinsical via Star Trek Online's in-game mail system.</p><p>&nbsp;</p><p>A heartfelt thank you to the following good folks who have provided admiralty card information: @daBelgrave, thealexofevil (TrekBBS), pteal1979 (TrekBBS), LootCritter, @boredoftheworld, @UniversalSpartan.</p><p>&nbsp;</p><p>Star Trek Online&reg; is a registered trademark of Cryptic Studios Inc. Star Trek&reg;, Star Trek: The Next Generation&reg;, Star Trek: Deep Space Nine&reg;, Star Trek: Voyager&reg;, and Star Trek: Enterprise&reg; are all registered trademarks of CBS Studios Inc. No copyright infringement is intended. Any copyrighted material is included under fair use principles.</p></div></html>";
		public static final String LabelShipStats = "Statistics";
		public static final String TitleError = "An unexpected error has occurred.";
	}
	
	public final class ShipStatistics {
		public static final String Title = "STO Admiralty - Ship Usage Statistics";
		public static final String LabelAllAdmirals = "All Admirals";
		public static final String LabelFederationAdmirals = "Federation Admirals";
		public static final String LabelKlingonAdmirals = "Klingon Admirals";
		public static final String LabelRomulanAdmirals = "Romulan Admirals";
		public static final String LabelJemHadarAdmirals = "Jem'Hadar Admirals";
		public static final String LabelAdmirals = "View ship usage for...";
		public static final String LabelSortBy = "Sort by...";
		public static final String LabelDefaultSort = "Default";
		public static final String LabelMostUsed = "Most Used";
		public static final String LabelLeastUsed = "Least Used";
		public static final String LabelClearUsageData = "Clear Data";
		public static final String DescDefaultSort = "Sort ships using Cryptic's ship sorting";
		public static final String DescMostUsed = "Sort ships from most used to least used";
		public static final String DescLeastUsed = "Sort ships from least used to most used";
		public static final String DescClearUsageData = "Clear all ship usage data";
		public static final String TitleClearUsageData = "Clear Ship Usage Data?";
		public static final String MsgClearUsageData = "Clear all ship usage data for %s?\nDoing this will set all ship usage count to zero.";
	}

	public final class AdmiralPanel {
		public static final String TabPrimary = "Primary Ships";
		public static final String TabAssignments = "Assignments";
		public static final String LabelName = "Name";
		public static final String LabelFaction = "Faction: ";
		public static final String LabelShipPriority = "Prioritize: ";
		public static final String LabelShip = "Ship";
		public static final String LabelExportShips = "Export";
		public static final String LabelImportShips = "Import";
		public static final String LabelActiveShips = "Active Ships";
		public static final String LabelMaintenanceShips = "Maintenance Ships";
		public static final String LabelOneTimeShips = "One-Time Ships";
		public static final String LabelNumAssignments = "Number of Assignments";
		public static final String LabelPlanAssignments = "Plan Assignments";
		public static final String LabelClearAssignments = "Clear Assignments";
		public static final String LabelDeploymentPlans = "Select Deployment Plans";
		public static final String LabelPrev = "Prev";
		public static final String LabelBest = "Best";
		public static final String LabelNext = "Next";
		public static final String HtmlActiveShips = "<html><b>Active Ships</b> (%d ships)</html>";
		public static final String HtmlMaintenanceShips = "<html><b>Maintenance Ships</b> (%d ships)</html>";
		public static final String HtmlOneTimeShips = "<html><b>One-Time Ships</b> (%d ships)</html>";
		public static final String HtmlPlanAssignments = "<html><center><u>P</u>lan<br>Assignments</center></html>";
		public static final String HtmlClearAssignments = "<html><center><u>C</u>lear<br>Assignments</center></html>";
		public static final String HtmlDeployShips = "<html><center><u>D</u>eploy<br>Ships</center></html>";
		public static final String DescNumAssignments = "Set the number of assignments to plan for";
		public static final String DescAddActiveShips = "Add active ship(s) to this admiral's roster";
		public static final String DescRemoveActiveShips = "Remove active ship(s) from this admiral's roster";
		public static final String DescExportShips = "Export this admiral's ships to a file";
		public static final String DescImportShips = "Add ships from an exported ship list to this admiral's ship roster";
		public static final String DescAddOneTimeShips = "Add one-time ship(s) to this admiral's roster";
		public static final String DescRemoveOneTimeShips = "Remove one-time ship(s) from this admiral's roster";
		public static final String DescAllMaintenanceToActive = "Move all maintenance ships to the active list";
		public static final String DescAllActiveToMaintenance = "Move all active ships to the maintenance list";
		public static final String DescPlanAssignments = "Find the best ships to send on these assignments";
		public static final String DescClearAssignments = "Reset all assignments back to default values";
		public static final String DescMaintenanceToActive = "Move selected ship(s) to the active list";
		public static final String DescActiveToMaintenance = "Move selected ship(s) to the maintenance list";
		public static final String DescPrev = "Show the previous best solution";
		public static final String DescBest = "Show the best solution";
		public static final String DescNext = "Show the next best solution";
		public static final String DescDeployShips = "Deploy the ships";
		public static final String MsgNoSolution = "<html><p>Sorry, unable to find a solution for this set of assignments as they may be too similar.</p><p>Please try again with either a smaller set of assignments or use different assignments.</p></html>";
		public static final String MsgNoShipsToDeploy = "No ships to assign.";
		public static final String MsgExportSuccessful = "Ships have been exported to %s";
		public static final String MsgExportFailed = "Unable to export ships to %s";
		public static final String MsgImportSuccessful = "%d ships have been imported from %s";
		public static final String MsgNoImport = "No ships were imported from %s";
		public static final String MsgImportFailed = "Unable to import ships from %s";
		public static final String TitleAddActiveShips = "Add Active Ships";
		public static final String TitleRemoveActiveShips = "Remove Active Ships";
		public static final String TitleExportShips = "Export ships to text file";
		public static final String TitleImportShips = "Import ships from text file";
		public static final String TitleAddOneTimeShips = "Add One-Time Ships";
		public static final String TitleRemoveOneTimeShips = "Remove Active Ships";
		public static final String TitleExportShip = "Export Ships";
		public static final String TitleImportShip = "Import Ships";
	}
	
	public final class AssignmentPanel {
		public static final String HtmlSlot = "<html>%s: <font color=\"%s\"><b>%d</b></font> / <b>%d</b></html>";
		public static final String ColorPositive = "green";
		public static final String ColorNegative = "black";
		public static final String LabelENG = "ENG";
		public static final String LabelTAC = "TAC";
		public static final String LabelSCI = "SCI";
		public static final String labelCRIT = "CRIT";
		public static final String LabelCriticalChance = "Crit Chance:";
		public static final String LabelRequired = "Required";
		public static final String TabAssignedShips = "Assigned Ships";
		public static final String TabAssignmentStats = "Assignment Stats";
		public static final String Percent = "%d%%";
	}
	
	public final class ShipDetailsPanel {
		public static final String DefaultShipName = "Cruiser";
		public static final String DefaultSpecialAbility = "Ability";
		public static final String LabelMaintenance = "Maintenance";
		public static final String LabelTier = "Tier";
		public static final String LabelRarity = "Rarity";
		public static final String LabelShipStats = "Stats";
		public static final String LabelSpecialAbility = "Special Ability";
	}
	
	public final class ShipSelectionPanel {
		public static final String LabelOkay = "Okay";
		public static final String LabelCancel = "Cancel";
		public static final String LabelFilter = "Fitler";
		public static final String LabelFaction = "Faction";
		public static final String LabelRole = "Role";
		public static final String LabelTier = "Tier";
		public static final String LabelRarity = "Rarity";
		public static final String LabelSortBy = "Sort By...";
		public static final String LabelEngineering = "Engineering";
		public static final String LabelTactical = "Tactical";
		public static final String LabelScience = "Science";
		public static final String LabelSmallCraft = "Small Craft";
		public static final String TitleFilter = "Filter Ships";
	}
	
	public final class ExceptionDialog {
		public static final String TitleError = "Error";
		public static final String LabelOkay = "OK";
		public static final String LabelViewError = "View Error";
		public static final String LabelHideError = "Hide Error";
	}
	
}
