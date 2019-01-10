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

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;
import java.util.List;

public class CustomFocusTraversalPolicy extends FocusTraversalPolicy {

	private List<Component> order;

	public CustomFocusTraversalPolicy(ArrayList<Component> order) {
		this.order = new ArrayList<Component>(order);
	}

	@Override
	public Component getComponentAfter(Container container, Component component) {
		int idx = (order.indexOf(component) + 1) % order.size();
        return order.get(idx);
	}

	@Override
	public Component getComponentBefore(Container container, Component component) {
		int idx = order.indexOf(component) - 1;
        if (idx < 0) {
            idx = order.size() - 1;
        }
        return order.get(idx);	}

	@Override
	public Component getFirstComponent(Container container) {
		return order.get(0);
	}

	@Override
	public Component getLastComponent(Container container) {
		return order.get(order.size() - 1);
	}

	@Override
	public Component getDefaultComponent(Container container) {
		return getFirstComponent(container);
	}
}
