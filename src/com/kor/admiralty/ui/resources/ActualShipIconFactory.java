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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.SortedMap;

import javax.swing.ImageIcon;

import com.kor.admiralty.enums.Rarity;
import com.kor.admiralty.enums.Role;
import com.kor.admiralty.enums.ShipFaction;
import com.kor.admiralty.io.Datastore;

public class ActualShipIconFactory extends GenericShipIconFactory {
	
	protected static final Image IMG_ENG = getBicubicScaledImage("frame_eng.png");
	protected static final Image IMG_TAC = getBicubicScaledImage("frame_tac.png");
	protected static final Image IMG_SCI = getBicubicScaledImage("frame_sci.png");
	protected static final Image IMG_SMC = getBicubicScaledImage("frame_smc.png");
	protected static final Image IMG_UNCOMMON = getBicubicScaledImage("frame_uncommon.png");
	protected static final Image IMG_RARE = getBicubicScaledImage("frame_rare.png");
	protected static final Image IMG_VERYRARE = getBicubicScaledImage("frame_veryrare.png");
	protected static final Image IMG_ULTRARARE = getBicubicScaledImage("frame_ultrarare.png");
	protected static final Image IMG_EPIC = getBicubicScaledImage("frame_epic.png");
	
	public ActualShipIconFactory() {
		super();
	}
	
	@Override
	public ImageIcon getIcon(String iconName, ShipFaction faction, Role role, Rarity rarity, boolean owned) {
		SortedMap<String, ImageIcon> cache = Datastore.getCachedIcons();
		if (cache.containsKey(iconName)) {
			return cache.get(iconName);
		}
		
		if (!hasBundledIcon(iconName)) {
			// Ship icon is not found in the .jar file, fallback to using generic ship icon 
			return super.getIcon(iconName, faction, role, rarity, owned);
		}
		
		Image shipIcon = getSmoothScaledImage(iconName);
		ImageIcon imageIcon = buildIcon(shipIcon, faction, role, rarity); 
		cache.put(iconName, imageIcon);
		Datastore.setIconCacheChanged(true);
		return imageIcon;
	}
	
	public static boolean hasBundledIcon(String iconName) {
		return ActualShipIconFactory.class.getResource(iconName) != null;
	}

	public static ImageIcon buildIcon(Image shipIcon, ShipFaction faction, Role role, Rarity rarity) {
		BufferedImage image = new BufferedImage(SPAN_IMAGE, SPAN_IMAGE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		// Draw faction background
		switch (faction) {
		case Universal:
		case None:
			g.drawImage(IMG_FED_BKG, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case Federation: 
			g.drawImage(IMG_FED_BKG, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case Klingon:
			g.drawImage(IMG_KDF_BKG, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case Romulan:
			g.drawImage(IMG_ROM_BKG, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case JemHadar:
			g.drawImage(IMG_JH_BKG, 0, 0, SPAN_IMAGE, SPAN_IMAGE,  null);
			break;
		}
		
		// Draw ship icon
		g.drawImage(shipIcon, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
		
		// Draw role frame
		switch (role) {
		case Eng:
			g.drawImage(IMG_ENG, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case Sci:
			g.drawImage(IMG_SCI, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case Tac:
			g.drawImage(IMG_TAC, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case Smc:
			g.drawImage(IMG_SMC, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case None:
		}
		
		// Draw rarity frame
		switch (rarity) {
		case Uncommon: 
			g.drawImage(IMG_UNCOMMON, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case Rare: 
			g.drawImage(IMG_RARE, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case VeryRare: 
			g.drawImage(IMG_VERYRARE, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case UltraRare: 
			g.drawImage(IMG_ULTRARARE, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case Epic: 
			g.drawImage(IMG_EPIC, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
			break;
		case None:
		case Common: 
		}
		
		g.dispose();
		return new ImageIcon(image);
	}
	
	protected static Image getBicubicScaledImage(String name) {
		BufferedImage image = getImage(name);
		BufferedImage scaledImage = new BufferedImage(SPAN_IMAGE, SPAN_IMAGE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.drawImage(image, 0, 0, SPAN_IMAGE, SPAN_IMAGE, null);
		graphics2D.dispose();
		return scaledImage;
	}
	
	protected static Image getSmoothScaledImage(String name) {
		BufferedImage image = getImage(name);
		return image.getScaledInstance(SPAN_IMAGE, SPAN_IMAGE, Image.SCALE_SMOOTH);
	}
}
