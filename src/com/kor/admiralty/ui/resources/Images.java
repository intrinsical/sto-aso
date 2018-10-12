/*******************************************************************************
 * Copyright (C) 2015, 2018 Dave Kor
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

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.enums.ShipFaction;

public class Images {
	
	protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
	protected static final int SPAN_IMAGE = 64;
	protected static final int SPAN_ICON = 24;
	protected static final BufferedImage IMG_BLANK = new BufferedImage(SPAN_IMAGE, SPAN_IMAGE, BufferedImage.TYPE_INT_ARGB);
	protected static final TreeMap<String, Image> CACHE = new TreeMap<String, Image>();
	protected static final ShipIconFactory SHIP_ICON_FACTORY = new ActualShipIconFactory();
	protected static final ShipIconFactory SHIP_GENERIC_ICON_FACTORY = new GenericShipIconFactory();
	
	public static final Image IMG_ASO = TOOLKIT.createImage(Images.class.getResource("aso.png"));
	
	public static final ImageIcon ICON_BLANK = new ImageIcon(IMG_BLANK);
	public static final ImageIcon ICON_TAC = new ImageIcon(Images.class.getResource("tac.png"));
	public static final ImageIcon ICON_ENG = new ImageIcon(Images.class.getResource("eng.png"));
	public static final ImageIcon ICON_SCI = new ImageIcon(Images.class.getResource("sci.png"));
	public static final ImageIcon ICON_FEDENG = new ImageIcon(Images.class.getResource("ico_fedeng.png"));
	public static final ImageIcon ICON_FEDTAC = new ImageIcon(Images.class.getResource("ico_fedtac.png"));
	public static final ImageIcon ICON_FEDSCI = new ImageIcon(Images.class.getResource("ico_fedsci.png"));
	public static final ImageIcon ICON_KDFENG = new ImageIcon(Images.class.getResource("ico_kdfeng.png"));
	public static final ImageIcon ICON_KDFTAC = new ImageIcon(Images.class.getResource("ico_kdftac.png"));
	public static final ImageIcon ICON_KDFSCI = new ImageIcon(Images.class.getResource("ico_kdfsci.png"));
	public static final ImageIcon ICON_ROMENG = new ImageIcon(Images.class.getResource("ico_romeng.png"));
	public static final ImageIcon ICON_ROMTAC = new ImageIcon(Images.class.getResource("ico_romtac.png"));
	public static final ImageIcon ICON_ROMSCI = new ImageIcon(Images.class.getResource("ico_romsci.png"));
	public static final ImageIcon ICON_LEFT_ALL = new ImageIcon(Images.class.getResource("all_left.png"));
	public static final ImageIcon ICON_LEFT_ONE = new ImageIcon(Images.class.getResource("one_left.png"));
	public static final ImageIcon ICON_RIGHT_ALL = new ImageIcon(Images.class.getResource("all_right.png"));
	public static final ImageIcon ICON_RIGHT_ONE = new ImageIcon(Images.class.getResource("one_right.png"));
	public static final ImageIcon ICON_ADD = new ImageIcon(Images.class.getResource("add.png"));
	public static final ImageIcon ICON_REMOVE = new ImageIcon(Images.class.getResource("remove.png"));
	public static final ImageIcon ICON_PLUS = new ImageIcon(Images.class.getResource("plus.png"));
	public static final ImageIcon ICON_MINUS = new ImageIcon(Images.class.getResource("minus.png"));
	public static final ImageIcon ICON_PREV = ICON_LEFT_ONE;
	public static final ImageIcon ICON_NEXT = ICON_RIGHT_ONE;
	public static final ImageIcon ICON_BEST = new ImageIcon(Images.class.getResource("best.png"));
	public static final ImageIcon ICON_CHARACTER = new ImageIcon(Images.class.getResource("person.png"));
	public static final ImageIcon ICON_LHS = new ImageIcon(Images.class.getResource("lhs.png"));
	public static final ImageIcon ICON_CTR = new ImageIcon(Images.class.getResource("ctr.png"));
	public static final ImageIcon ICON_RHS = new ImageIcon(Images.class.getResource("rhs.png"));
	public static final ImageIcon ICON_PIN = new ImageIcon(Images.class.getResource("pin.png"));
	public static final ImageIcon ICON_INFO = new ImageIcon(Images.class.getResource("info.png"));
	public static final ImageIcon ICON_CHART = new ImageIcon(Images.class.getResource("chart.png"));
	public static final ImageIcon ICON_EXPORT = new ImageIcon(Images.class.getResource("export.png"));
	public static final ImageIcon ICON_IMPORT = new ImageIcon(Images.class.getResource("import.png"));
	
	//public static ImageIcon getIcon(String iconName, ShipFaction faction, Role role, Rarity rarity) {
	//	return getIcon(iconName, faction, role, rarity, false);
	//}
	
	public static ImageIcon getIcon(String iconName, ShipFaction faction, Role role, Rarity rarity, boolean owned) {
		return owned ? SHIP_ICON_FACTORY.getIcon(iconName, faction, role, rarity, owned) : SHIP_GENERIC_ICON_FACTORY.getIcon(iconName, faction, role, rarity, owned);
	}
	
}
