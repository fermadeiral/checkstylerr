/* CellFormatHelper.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Jan 29, 2008 11:14:44 AM     2008, Created by Dennis.Chen
}}IS_NOTE

Copyright (C) 2007 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.zss.ui.impl;

import org.zkoss.poi.ss.usermodel.ZssContext;
import org.zkoss.util.Locales;
import org.zkoss.zss.model.SBook;
import org.zkoss.zss.model.SCell;
import org.zkoss.zss.model.SCellStyle;
import org.zkoss.zss.model.SColor;
import org.zkoss.zss.model.SFont;
import org.zkoss.zss.model.SHyperlink;
import org.zkoss.zss.model.SRichText;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.SCell.CellType;
import org.zkoss.zss.model.SCellStyle.Alignment;
import org.zkoss.zss.model.SCellStyle.BorderType;
import org.zkoss.zss.model.SCellStyle.FillPattern;
import org.zkoss.zss.model.SCellStyle.VerticalAlignment;
import org.zkoss.zss.model.SFont.Boldweight;
import org.zkoss.zss.model.SFont.Underline;
import org.zkoss.zss.model.SRichText.Segment;
import org.zkoss.zss.model.sys.EngineFactory;
import org.zkoss.zss.model.sys.format.FormatContext;
import org.zkoss.zss.model.sys.format.FormatEngine;
import org.zkoss.zss.model.sys.format.FormatResult;

/**
 * @author Dennis.Chen
 * 
 */
public class CellFormatHelper {

	/**
	 * cell to get the format, could be null.
	 */
	private SCell _cell;
	
	/**
	 * cell style, could be null
	 */
	private SCellStyle _cellStyle;

	private SSheet _sheet;
	
	private SBook _book;

	private int _row;

	private int _col;

	private boolean hasRightBorder_set = false;
	private boolean hasRightBorder = false;
	
	private MergeMatrixHelper _mmHelper;
	

	private FormatEngine _formatEngine;
	
	public CellFormatHelper(SSheet sheet, int row, int col, MergeMatrixHelper mmhelper) {
		_sheet = sheet;
		_book = _sheet.getBook(); 
		_row = row;
		_col = col;
		_cell = sheet.getCell(row, col);
		_cellStyle =_cell.getCellStyle();
		_mmHelper = mmhelper;
		_formatEngine = EngineFactory.getInstance().createFormatEngine();
	}

	public String getHtmlStyle() {

		StringBuffer sb = new StringBuffer();

		//String bgColor = BookHelper.indexToRGB(_book, style.getFillForegroundColor());
		//ZSS-34 cell background color does not show in excel
		//20110819, henrichen: if fill pattern is NO_FILL, shall not show the cell background color
		String fillColor = _cellStyle.getFillPattern() != SCellStyle.FillPattern.NO_FILL ? 
			_cellStyle.getFillColor().getHtmlColor() : null;
		if (fillColor != null) {
			sb.append("background-color:").append(fillColor).append(";");
		}

		processBottomBorder(sb);
		processRightBorder(sb);

		return sb.toString();
	}
	
	private boolean processBottomBorder(StringBuffer sb) {

		boolean hitBottom = false;
		MergedRect rect = null;
		boolean hitMerge = false;

		
		// ZSS-259: should apply the bottom border from the cell of merged range's bottom
		// as processRightBorder() does.
		rect = _mmHelper.getMergeRange(_row, _col);
		int bottom = _row;
		if(rect != null) {
			hitMerge = true;
			bottom = rect.getLastRow();
		}
		SCellStyle nextStyle = _sheet.getCell(bottom,_col).getCellStyle();
		
		if (nextStyle != null){
			BorderType bb = nextStyle.getBorderBottom();
			String color = nextStyle.getBorderBottomColor().getHtmlColor();
			hitBottom = appendBorderStyle(sb, "bottom", bb, color);
		}
		

		// ZSS-259: should check and apply the top border from the bottom cell
		// of merged range's bottom as processRightBorder() does.
		if (!hitBottom) {
			bottom = hitMerge ? rect.getLastRow() + 1 : _row + 1; 
			/*if(next == null){ // don't search into merge ranges
				//check is _row+1,_col in merge range
				MergedRect rect = _mmHelper.getMergeRange(_row+1, _col);
				if(rect !=null){
					next = _sheet.getCell(rect.getTop(),rect.getLeft());
				}
			}*/
			nextStyle = _sheet.getCell(bottom,_col).getCellStyle();
			if (nextStyle != null){
				BorderType bb = nextStyle.getBorderTop();// get top border of
				String color = nextStyle.getBorderTopColor().getHtmlColor();
				// set next row top border as cell's bottom border;
				hitBottom = appendBorderStyle(sb, "bottom", bb, color);
			}
		}
		
		//border depends on next cell's background color (why? dennis, 20131118)
		if(!hitBottom && nextStyle !=null){
			//String bgColor = BookHelper.indexToRGB(_book, style.getFillForegroundColor());
			//ZSS-34 cell background color does not show in excel
			String bgColor = nextStyle.getFillPattern() != FillPattern.NO_FILL ? 
					nextStyle.getFillColor().getHtmlColor() : null;
			if (bgColor != null) {
				hitBottom = appendBorderStyle(sb, "bottom", BorderType.THICK, bgColor);
			}
		}
		
		//border depends on current cell's background color
		if(!hitBottom && _cellStyle !=null){
			//String bgColor = BookHelper.indexToRGB(_book, style.getFillForegroundColor());
			//ZSS-34 cell background color does not show in excel
			String bgColor = _cellStyle.getFillPattern() != FillPattern.NO_FILL ? 
					_cellStyle.getFillColor().getHtmlColor() : null;
			if (bgColor != null) {
				hitBottom = appendBorderStyle(sb, "bottom", BorderType.THICK, bgColor);
			}
		}
		
		return hitBottom;
	}

	private boolean processRightBorder(StringBuffer sb) {
		boolean hitRight = false;
		MergedRect rect=null;
		boolean hitMerge = false;
		//find right border of target cell 
		rect = _mmHelper.getMergeRange(_row, _col);
		int right = _col;
		if(rect!=null){
			hitMerge = true;
			right = rect.getLastColumn();
		}
		SCellStyle nextStyle = _sheet.getCell(_row,right).getCellStyle();
		if (nextStyle != null){
			BorderType bb = nextStyle.getBorderRight();
			String color = nextStyle.getBorderRightColor().getHtmlColor();
			hitRight = appendBorderStyle(sb, "right", bb, color);
		}

		
		//if no border for target cell,then check is this cell in a merge range
		//if(true) then try to get next cell after this merge range
		//else get next cell of this cell
		if(!hitRight){
			right = hitMerge?rect.getLastColumn()+1:_col+1;
			nextStyle = _sheet.getCell(_row,right).getCellStyle();
			if (nextStyle != null){
				BorderType bb = nextStyle.getBorderLeft();//get left here
				//String color = BookHelper.indexToRGB(_book, style.getLeftBorderColor());
				// ZSS-34 cell background color does not show in excel
				String color = nextStyle.getBorderLeftColor().getHtmlColor();
				hitRight = appendBorderStyle(sb, "right", bb, color);
			}
		}

		//border depends on next cell's background color (why? dennis, 20131118)
		if(!hitRight && nextStyle !=null){
			//String bgColor = BookHelper.indexToRGB(_book, style.getFillForegroundColor());
			//ZSS-34 cell background color does not show in excel
			String bgColor = nextStyle.getFillPattern() != FillPattern.NO_FILL ? 
					nextStyle.getFillColor().getHtmlColor() : null;
			if (bgColor != null) {
				hitRight = appendBorderStyle(sb, "right", BorderType.THIN, bgColor);
			}
		}
		//border depends on current cell's background color
		if(!hitRight && _cellStyle !=null){
			//String bgColor = BookHelper.indexToRGB(_book, style.getFillForegroundColor());
			//ZSS-34 cell background color does not show in excel
			String bgColor = _cellStyle.getFillPattern() != FillPattern.NO_FILL ? 
					_cellStyle.getFillColor().getHtmlColor() : null;
			if (bgColor != null) {
				hitRight = appendBorderStyle(sb, "right", BorderType.THIN, bgColor);
			}
		}
		
		return hitRight;
	}

	private boolean appendBorderStyle(StringBuffer sb, String locate, BorderType bs, String color) {
		if (bs == BorderType.NONE)
			return false;
		
		sb.append("border-").append(locate).append(":");
		switch(bs) {
		case DASHED:
		case DOTTED:
			sb.append("dashed");
			break;
		case HAIR:
			sb.append("dotted");
			break;
		default:
			sb.append("solid");
		}
		sb.append(" 1px");

		if (color != null) {
			sb.append(" ");
			sb.append(color);
		}

		sb.append(";");
		return true;
	}
	
	public static String getFontCSSStyle(SCell cell, SFont font) {
		final StringBuffer sb = new StringBuffer();
		
		String fontName = font.getName();
		if (fontName != null) {
			sb.append("font-family:").append(fontName).append(";");
		}
		
		String textColor = font.getColor().getHtmlColor();

		if (textColor != null) {
			sb.append("color:").append(textColor).append(";");
		}

		final Underline fontUnderline = font.getUnderline(); 
		final boolean strikeThrough = font.isStrikeout();
		boolean isUnderline = fontUnderline == Underline.SINGLE || fontUnderline == Underline.SINGLE_ACCOUNTING;
		if (strikeThrough || isUnderline) {
			sb.append("text-decoration:");
			if (strikeThrough)
				sb.append(" line-through");
			if (isUnderline)	
				sb.append(" underline");
			sb.append(";");
		}

		final Boldweight weight = font.getBoldweight();
		final boolean italic = font.isItalic();
		sb.append("font-weight:").append(weight).append(";");
		if (italic)
			sb.append("font-style:").append("italic;");

		//ZSS-748
		int fontSize = font.getHeightPoints();
		if (font.getTypeOffset() != SFont.TypeOffset.NONE) {
			fontSize = (int) (0.7 * fontSize + 0.5) ;
		}
		sb.append("font-size:").append(fontSize).append("pt;");
		
		//ZSS-748
		if (font.getTypeOffset() == SFont.TypeOffset.SUPER)
			sb.append("vertical-align:").append("super;");
		else if (font.getTypeOffset() == SFont.TypeOffset.SUB)
			sb.append("vertical-align:").append("sub;");
		return sb.toString();
	}

	public String getInnerHtmlStyle() {
		if (!_cell.isNull()) {
			
			final StringBuffer sb = new StringBuffer();
			sb.append(getTextCSSStyle( _cell));
			
			//vertical alignment
			VerticalAlignment verticalAlignment = _cellStyle.getVerticalAlignment();
			sb.append("display: table-cell;");
			switch (verticalAlignment) {
			case TOP:
				sb.append("vertical-align: top;");
				break;
			case CENTER:
				sb.append("vertical-align: middle;");
				break;
			case BOTTOM:
				sb.append("vertical-align: bottom;");
				break;
			}
			
			//final SFont font = _cellStyle.getFont();
			
			//sb.append(BookHelper.getFontCSSStyle(_book, font));
			//sb.append(getFontCSSStyle(_cell, font));

			//condition color
			//final FormatResult ft = _formatEngine.format(_cell, new FormatContext(ZssContext.getCurrent().getLocale()));
			//final boolean isRichText = ft.isRichText();
			//if (!isRichText) {
			//	final SColor color = ft.getColor();
			//	if(color!=null){
			//		final String htmlColor = color.getHtmlColor();
			//		sb.append("color:").append(htmlColor).append(";");
			//	}
			//}

			return sb.toString();
		}
		return "";
	}
	
	// ZSS-725: separate inner and font style to avoid the conflict between
	// vertical alignment, subscript and superscript.
	public String getFontHtmlStyle() {
		if (!_cell.isNull()) {
			
			final StringBuffer sb = new StringBuffer();
			final SFont font = _cellStyle.getFont();
			
			//sb.append(BookHelper.getFontCSSStyle(_book, font));
			sb.append(getFontCSSStyle(_cell, font));

			//condition color
			final FormatResult ft = _formatEngine.format(_cell, new FormatContext(ZssContext.getCurrent().getLocale()));
			final boolean isRichText = ft.isRichText();
			if (!isRichText) {
				final SColor color = ft.getColor();
				if(color!=null){
					final String htmlColor = color.getHtmlColor();
					sb.append("color:").append(htmlColor).append(";");
				}
			}

			return sb.toString();
		}
		return "";
	}
	
	/* given alignment and cell type, return real alignment */
	//Halignment determined by style alignment, text format and value type  
	public static Alignment getRealAlignment(SCell cell) {
		SCellStyle style = cell.getCellStyle();
		CellType type = cell.getType();
		Alignment align = style.getAlignment();
		if (align == Alignment.GENERAL) {
			final String format = style.getDataFormat();
			if (format != null && format.startsWith("@")) //a text format
				type = CellType.STRING;
			else if (type == CellType.FORMULA)
				type = cell.getFormulaResultType();
			switch(type) {
			case BLANK:
				return align;
			case BOOLEAN:
				return Alignment.CENTER;
			case ERROR:
				return Alignment.CENTER;
			case NUMBER:
				return Alignment.RIGHT;
			case STRING:
				return Alignment.LEFT;
			}
		}
		return align;
	}
	
	public static String getTextCSSStyle(SCell cell) {
		final SCellStyle style = cell.getCellStyle();

		final StringBuffer sb = new StringBuffer();
		Alignment textHAlign = getRealAlignment(cell);
		
		switch(textHAlign) {
		case RIGHT:
			sb.append("text-align:").append("right").append(";");
			break;
		case CENTER:
		case CENTER_SELECTION:
			sb.append("text-align:").append("center").append(";");
			break;
		}

		boolean textWrap = style.isWrapText();
		if (textWrap) {
			sb.append("white-space:").append("normal").append(";");
		}/*else{ sb.append("white-space:").append("nowrap").append(";"); }*/

		return sb.toString();
	}

	public boolean hasRightBorder() {
		if(hasRightBorder_set){
			return hasRightBorder;
		}else{
			hasRightBorder = processRightBorder(new StringBuffer());
			hasRightBorder_set = true;
		}
		return hasRightBorder;
	}
	
	public String getCellFormattedText(){
		final FormatResult ft = _formatEngine.format(_cell, new FormatContext(ZssContext.getCurrent().getLocale()));
		return ft.getText();
	}
	
	public String getCellEditText(){
		return _formatEngine.getEditText(_cell, new FormatContext(ZssContext.getCurrent().getLocale()));
	}
	
	/**
	 * Gets Cell text by given row and column, it handling
	 */
	static public String getRichCellHtmlText(SSheet sheet, int row,int column){
		final SCell cell = sheet.getCell(row, column);
		String text = "";
		if (!cell.isNull()) {
			boolean wrap = cell.getCellStyle().isWrapText();
			
			final FormatResult ft = EngineFactory.getInstance().createFormatEngine().format(cell, new FormatContext(ZssContext.getCurrent().getLocale()));
			if (ft.isRichText()) {
				final SRichText rstr = ft.getRichText();
				final SHyperlink hlink = cell.getHyperlink();
				if (hlink == null) {
					text = getRichTextHtml(rstr, wrap, true);
				} else {
					text = getHyperlinkHtml(getRichTextHtml(rstr, wrap, true), hlink);						
				}
			} else {
				text = escapeText(ft.getText(), wrap, true);
				final SHyperlink hlink = cell.getHyperlink();
				if (hlink != null) {
					text = getHyperlinkHtml(text, hlink);
				}				
			}

			
		}
		return text;
	}
	
	// ZSS-725
	static public String getRichTextEditCellHtml(SSheet sheet, int row,int column){
		final SCell cell = sheet.getCell(row, column);
		String text = "";
		if (!cell.isNull()) {
			boolean wrap = cell.getCellStyle().isWrapText();
			
			final FormatResult ft = EngineFactory.getInstance().createFormatEngine().format(cell, new FormatContext(ZssContext.getCurrent().getLocale()));
			if (ft.isRichText()) {
				final SRichText rstr = ft.getRichText();
				final SHyperlink hlink = cell.getHyperlink();
				StringBuilder sb = new StringBuilder();
				for(Segment seg: rstr.getSegments()) {
					sb.append(getFontTextHtml(escapeText(seg.getText(), wrap, true), seg.getFont()));
				}
				
				if (hlink == null) {
					text = sb.toString();
				} else {
					text = getHyperlinkHtml(sb.toString(), hlink);						
				}
			} else {
				text = escapeText(ft.getText(), wrap, true);
				final SHyperlink hlink = cell.getHyperlink();
				if (hlink != null) {
					text = getHyperlinkHtml(text, hlink);
				}
				text = getFontTextHtml(text, cell.getCellStyle().getFont());
			}
		}
		return text;
	}
	
	// ZSS-725
	static private String getFontTextHtml(String text, SFont font) {
		StringBuilder sb = new StringBuilder();
		sb.append("<span style=\"")
			.append(getFontCSSStyle(font, false))
			.append("\">");
		sb.append(text);
		sb.append("</span>");
		return sb.toString();
	}
	
	private static String getHyperlinkHtml(String label, SHyperlink link) {
		String addr = escapeText(link.getAddress()==null?"":link.getAddress(), false, false); //TODO escape something?
		if (label == null) {
			label = escapeText(link.getLabel(), false, false);
		}
		if( label == null) {
			label = escapeText(addr, false, false);
		}
		final StringBuffer sb  = new StringBuffer();
		//ZSS-233, don't use href directly to avoid direct click on spreadsheet at the beginning.
		sb.append("<a zs.t=\"SHyperlink\" z.t=\"").append(link.getType().getValue()).append("\" href=\"javascript:\" z.href=\"")
			.append(addr).append("\">")
			.append(label==null?"":label)
			.append("</a>");
		return sb.toString();		
	}
	
	private static String getRichTextHtml(SRichText text, boolean wrap, boolean multiline) {
		StringBuilder sb = new StringBuilder();
		for(Segment seg:text.getSegments()){
			sb.append("<span style=\"")
				.append(getFontCSSStyle(seg.getFont()))
				.append("\">");
			sb.append(escapeText(seg.getText(),wrap,multiline));
			sb.append("</span>");
		}
		return sb.toString();
	}
	
	private static String getFontCSSStyle(SFont font) {
		return getFontCSSStyle(font, true);
	}
	
	//ZSS-725
	private static String getFontCSSStyle(SFont font, boolean resizeScript) {
		final StringBuffer sb = new StringBuffer();
		
		String fontName = font.getName();
		if (fontName != null) {
			sb.append("font-family:").append(fontName).append(";");
		}
		
		String textColor = font.getColor().getHtmlColor();
		if (textColor != null) {
			sb.append("color:").append(textColor).append(";");
		}

		final SFont.Underline fontUnderline = font.getUnderline(); 
		final boolean strikeThrough = font.isStrikeout();
		boolean isUnderline = fontUnderline == SFont.Underline.SINGLE || fontUnderline == SFont.Underline.SINGLE_ACCOUNTING;
		if (strikeThrough || isUnderline) {
			sb.append("text-decoration:");
			if (strikeThrough)
				sb.append(" line-through");
			if (isUnderline)	
				sb.append(" underline");
			sb.append(";");
		}

		final SFont.Boldweight weight = font.getBoldweight();
		
		sb.append("font-weight:").append(weight==SFont.Boldweight.BOLD?"bold":"normal").append(";");
		
		final boolean italic = font.isItalic();
		if (italic)
			sb.append("font-style:").append("italic;");
		
		//ZSS-748
		//ZSS-725
		int fontSize = font.getHeightPoints();
		if (resizeScript && font.getTypeOffset() != SFont.TypeOffset.NONE) {
			fontSize = (int) (0.7 * fontSize + 0.5);
		}
		sb.append("font-size:").append(fontSize).append("pt;");
		//ZSS-748
		if (font.getTypeOffset() == SFont.TypeOffset.SUPER)
			sb.append("vertical-align:").append("super;");
		else if (font.getTypeOffset() == SFont.TypeOffset.SUB)
			sb.append("vertical-align:").append("sub;");
		return sb.toString();
	}
	
	/**
	 * Gets Cell text by given row and column
	 */
	static public String getCellHtmlText(SSheet sheet, int row,int column){
		final SCell cell = sheet.getCell(row, column);
		String text = "";
		if (cell != null) {
			boolean wrap = cell.getCellStyle().isWrapText();
			
			final FormatResult ft = EngineFactory.getInstance().createFormatEngine().format(cell, new FormatContext(ZssContext.getCurrent().getLocale()));
			if (ft.isRichText()) {
				final SRichText rstr = ft.getRichText();
				text = rstr.getText();
			} else {
				text = ft.getText();
			}
			text = escapeText(text, wrap, true);
		}
		return text;
	}
	
	private static String escapeText(String text, boolean wrap, boolean multiline) {
		final StringBuffer out = new StringBuffer();
		for (int j = 0, tl = text.length(); j < tl; ++j) {
			char cc = text.charAt(j);
			switch (cc) {
			case '&': out.append("&amp;"); break;
			case '<': out.append("&lt;"); break;
			case '>': out.append("&gt;"); break;
			case ' ': out.append(wrap?" ":"&nbsp;"); break;
			case '\n':
				if (wrap && multiline) {
					out.append("<br/>");
					break;
				}
			default:
				out.append(cc);
			}
		}
		return out.toString();
	}

}
