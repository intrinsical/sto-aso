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
package com.kor.admiralty.ui.renderers;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class CustomHTMLEditorKit extends HTMLEditorKit {
	
	private static final long serialVersionUID = 638075815406218349L;
	
	private StyleSheet customStyle;
	
	public CustomHTMLEditorKit() {
		super();
	}
	
	public CustomHTMLEditorKit(StyleSheet styleSheet) {
		super();
		setStyleSheet(styleSheet);
	}

    @Override
    public StyleSheet getStyleSheet() {
        return customStyle == null ? super.getStyleSheet() : customStyle;
    }

    @Override
    public void setStyleSheet(StyleSheet s) {
        this.customStyle = s;
    }

    public StyleSheet getDefaultStyleSheet() {
        return super.getStyleSheet();
    }

    public void setDefaultStyleSheet(StyleSheet s) {
        super.setStyleSheet(s);
    }

}
