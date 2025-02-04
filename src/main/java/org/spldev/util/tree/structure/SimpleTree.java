/* -----------------------------------------------------------------------------
 * Util Lib - Miscellaneous utility functions.
 * Copyright (C) 2021  Sebastian Krieter
 * 
 * This file is part of Util Lib.
 * 
 * Util Lib is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * Util Lib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Util Lib.  If not, see <https://www.gnu.org/licenses/>.
 * 
 * See <https://github.com/skrieter/utils> for further information.
 * -----------------------------------------------------------------------------
 */
package org.spldev.util.tree.structure;

import java.util.*;

public class SimpleTree<D> extends AbstractNonTerminal<SimpleTree<D>> {

	protected D data;

	public SimpleTree() {
		super();
	}

	public SimpleTree(D data) {
		super();
		this.data = data;
	}

	public D getData() {
		return data;
	}

	public void setData(D data) {
		this.data = data;
	}

	@Override
	public SimpleTree<D> cloneNode() {
		final SimpleTree<D> clone = new SimpleTree<>();
		clone.data = data;
		return clone;
	}

	@Override
	public boolean equalsNode(Object other) {
		return (getClass() == other.getClass())
			&& Objects.equals(getData(), ((SimpleTree<?>) other).getData());
	}

	@Override
	public String toString() {
		return "SimpleTree [" + data + "]";
	}

}
