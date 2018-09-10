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
package com.kor.admiralty.ui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import static com.kor.admiralty.ui.resources.Strings.Empty;
import static com.kor.admiralty.ui.resources.Strings.ExceptionDialog.*;

public class ExceptionDialog extends JDialog {

	private static final long serialVersionUID = -4020136089542992194L;
	private int dialogWidth = 500;
	private int dialogHeight = 140;

	private JLabel iconLabel = new JLabel();

	// is error panel opened up
	private boolean open = false;

	private JLabel errorLabel = new JLabel();
	private JTextArea errorTextArea = new JTextArea(Empty);

	private JTextArea exceptionTextArea = new JTextArea(Empty);
	private JScrollPane exceptionTextAreaSP = new JScrollPane();

	private JButton okButton = new JButton(LabelOkay);
	private JButton viewButton = new JButton(LabelViewError);

	private JPanel topPanel = new JPanel(new BorderLayout());

	public ExceptionDialog(Window window, String errorLabelText, String errorDescription, Throwable e) {
		super(window);
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));

		setSize(dialogWidth, dialogHeight);
		setResizable(false);
		errorTextArea.setText(errorDescription);
		errorLabel.setText(errorLabelText);
		exceptionTextArea.setText(errors.toString());
		exceptionTextAreaSP = new JScrollPane(exceptionTextArea);
		iconLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		iconLabel.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
		setupUI();
		setUpListeners();
	}

	public ExceptionDialog(Window window, String errorLabelText, Throwable e) {
		this(window, errorLabelText, null, e);
	}

	public void setupUI() {
		setTitle(TitleError);
		errorTextArea.setLineWrap(true);
		errorTextArea.setWrapStyleWord(true);
		errorTextArea.setEditable(false);
		errorTextArea.setBackground(iconLabel.getBackground());
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);
		buttonPanel.add(viewButton);

		JScrollPane textAreaSP = new JScrollPane(errorTextArea);
		textAreaSP.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
		errorLabel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
		exceptionTextArea.setPreferredSize(new Dimension(100, 100));
		topPanel.add(iconLabel, BorderLayout.WEST);

		JPanel p = new JPanel(new BorderLayout());
		p.add(errorLabel, BorderLayout.NORTH);
		p.add(textAreaSP);

		topPanel.add(p);

		add(topPanel);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void setUpListeners() {
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExceptionDialog.this.setVisible(false);
			}
		});
		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (open) {
					viewButton.setText(LabelViewError);
					topPanel.remove(exceptionTextAreaSP);
					ExceptionDialog.this.setSize(dialogWidth, dialogHeight);
					topPanel.revalidate();
					open = false;

				} else {
					viewButton.setText(LabelHideError);
					topPanel.add(exceptionTextAreaSP, BorderLayout.SOUTH);
					ExceptionDialog.this.setSize(dialogWidth, dialogHeight + 100);
					topPanel.revalidate();
					open = true;
				}
			}
		});

	}
}
