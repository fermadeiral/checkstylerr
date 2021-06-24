/**
 * Copyright 2011 The Open Source Research Group,
 *                University of Erlangen-Nürnberg
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package de.fau.cs.osr.hddiff.perfsuite.util;

import de.fau.cs.osr.hddiff.perfsuite.model.Page;
import de.fau.cs.osr.hddiff.perfsuite.model.Revision;
import de.fau.cs.osr.hddiff.tree.DiffNode;

public final class PageRevDiffNode
{
	public final Page page;
	
	public final Revision revA;
	
	public final Revision revB;
	
	public final DiffNode nodeA;
	
	public final DiffNode nodeB;
	
	// =========================================================================
	
	public PageRevDiffNode(
			Page page,
			Revision revA,
			Revision revB,
			DiffNode nodeA,
			DiffNode nodeB)
	{
		this.page = page;
		this.revA = revA;
		this.revB = revB;
		this.nodeA = nodeA;
		this.nodeB = nodeB;
	}
}
