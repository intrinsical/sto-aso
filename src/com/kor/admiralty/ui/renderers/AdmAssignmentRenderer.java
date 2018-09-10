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

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import com.kor.admiralty.beans.AdmAssignment;

public class AdmAssignmentRenderer extends JLabel implements ListCellRenderer<AdmAssignment> {

	private static final long serialVersionUID = -7305800513394805960L;

	protected int maxLength;
	
	public AdmAssignmentRenderer() {
		this(-1);
	}
	
	public AdmAssignmentRenderer(int maxLength) {
		super();
		setBorder(new EmptyBorder(0, 5, 0, 5));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		this.maxLength = maxLength;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends AdmAssignment> list, AdmAssignment assignment, int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		String text = assignment.getName();
		if (maxLength > 0 && text.length() > maxLength) {
			int idx = text.lastIndexOf(' ', maxLength);
			text = text.substring(0, idx) + "...";
		}
		setText("<html><p style='word-wrap: break-word; width:" + list.getWidth() + "'>" + text + "</p></html>");
		//setForeground(assignment.getRarity().getColor());
		//setText(String.format(html,  assignment.getName()));
		return this;
	}

}
