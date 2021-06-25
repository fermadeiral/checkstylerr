/* SortHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/8/5 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zss.ui.sys;

import org.zkoss.util.resource.Labels;
import org.zkoss.zss.api.CellOperationUtil;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.model.CellRegion;
import org.zkoss.zss.range.SRange;
import org.zkoss.zss.range.impl.DataRegionHelper;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractHandler;
import org.zkoss.zss.ui.impl.undo.SortCellAction;

/**
 * @author dennis
 *
 */
public class SortHandler extends AbstractHandler {
	private static final long serialVersionUID = 274722279166484811L;
	boolean _desc;
	public SortHandler(boolean desc) {
		this._desc = desc;
	}


	/* (non-Javadoc)
	 * @see org.zkoss.zss.ui.sys.ua.impl.AbstractHandler#processAction(org.zkoss.zss.ui.UserActionContext)
	 */
	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		//ZSS-1261: select one cell == select all valid range
		SRange srcrng = Ranges.range(sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn()).getInternalRange();
		CellRegion rng = new DataRegionHelper(srcrng).findCustomSortRegion();
		final AreaRef selection0 = new AreaRef(rng.getRow(),rng.getColumn(),rng.getLastRow(),rng.getLastColumn());
		
		final Range range = Ranges.range(sheet, selection0);
		if (range.isProtected()) {
			showProtectMessage();
			return true;
		}
		//ZSS-1261: index is based on currently selected cell/range.
		Range index1 = Ranges.range(sheet, rng.getRow(), selection.getColumn(), rng.getLastRow(), selection.getColumn());
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new SortCellAction(Labels.getLabel("zss.undo.sortAsc"),sheet, selection0.getRow(), selection0.getColumn(),
			selection0.getLastRow(), selection0.getLastColumn(), index1, _desc));
		ctx.clearClipboard();
		return true;
	}

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		return book != null && sheet != null && (!sheet.isProtected() ||
				Ranges.range(sheet).getSheetProtection().isSortAllowed());
	}

}
