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
package com.kor.admiralty.ui.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TextFileFilter extends FileFilter {
	
	public static final FileFilter SINGLETON = new TextFileFilter();
	public static final File CURRENT_FOLDER = new File("");
	
	private TextFileFilter() {
		super();
	}

	@Override
	public boolean accept(File file) {
		return getExtension(file).equals("txt");
	}

	@Override
	public String getDescription() {
		return "Text files";
	}

	public String getExtension(File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
}
