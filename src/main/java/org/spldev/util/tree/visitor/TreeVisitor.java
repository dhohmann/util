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
package org.spldev.util.tree.visitor;

import java.util.*;

import org.spldev.util.tree.*;
import org.spldev.util.tree.structure.*;

/**
 * Interface for a visitor used in the traversal of a tree.
 * 
 * @see Trees
 * 
 * @author Sebastian Krieter
 */
public interface TreeVisitor<R, T extends Tree<?>> {

	enum VisitorResult {
		Continue, SkipChildren, SkipAll, Fail
	}

	static <T> T getCurrentNode(List<T> path) {
		return path.get(path.size() - 1);
	}

	static <T> T getParentNode(List<T> path) {
		return (path.size() > 1) ? path.get(path.size() - 2) : null;
	}

	default VisitorResult firstVisit(List<T> path) {
		return VisitorResult.Continue;
	}

	default VisitorResult lastVisit(List<T> path) {
		return VisitorResult.Continue;
	}

	default void reset() {
	}

	default R getResult() {
		return null;
	}

}
