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
