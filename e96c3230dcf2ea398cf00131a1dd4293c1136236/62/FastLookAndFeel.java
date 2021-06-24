/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*
* see: gpl-2.0.txt
*
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
*
* see: lgpl-2.0.txt
* see: classpath-exception.txt
*
* Registered users could also use JTattoo under the terms and conditions of the
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*
* see: APACHE-LICENSE-2.0.txt
 */
package com.jtattoo.plaf.fast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIDefaults;
import javax.swing.plaf.basic.BasicPopupMenuUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

import com.jtattoo.plaf.AbstractBorderFactory;
import com.jtattoo.plaf.AbstractIconFactory;
import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.BaseCheckBoxMenuItemUI;
import com.jtattoo.plaf.BaseCheckBoxUI;
import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.BaseDesktopPaneUI;
import com.jtattoo.plaf.BaseEditorPaneUI;
import com.jtattoo.plaf.BaseFileChooserUI;
import com.jtattoo.plaf.BaseFormattedTextFieldUI;
import com.jtattoo.plaf.BaseLabelUI;
import com.jtattoo.plaf.BaseMenuItemUI;
import com.jtattoo.plaf.BaseMenuUI;
import com.jtattoo.plaf.BasePanelUI;
import com.jtattoo.plaf.BasePasswordFieldUI;
import com.jtattoo.plaf.BaseRadioButtonMenuItemUI;
import com.jtattoo.plaf.BaseRadioButtonUI;
import com.jtattoo.plaf.BaseScrollPaneUI;
import com.jtattoo.plaf.BaseSeparatorUI;
import com.jtattoo.plaf.BaseSpinnerUI;
import com.jtattoo.plaf.BaseTableHeaderUI;
import com.jtattoo.plaf.BaseTableUI;
import com.jtattoo.plaf.BaseTextAreaUI;
import com.jtattoo.plaf.BaseTextFieldUI;
import com.jtattoo.plaf.BaseToolTipUI;
import com.jtattoo.plaf.BaseTreeUI;

/**
 * <p>FastLookAndFeel class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class FastLookAndFeel extends AbstractLookAndFeel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final String darkBlue = "0 32 96";
	private static final String lightBlue = "160 160 200";
	private static final String extraLightBlue = "210 210 240";
	private static final String superLightBlue = "230 230 255";
	private static final String darkGreen = "0 76 57";
	private static final String lightGreen = "172 191 186";
	private static final String extraLightGreen = "218 226 223";
	private static final String superLightGreen = "228 234 232";

	private static FastDefaultTheme myTheme = null;

	private static final List<String> themesList = new ArrayList<String>();
	private static final Map<String, Properties> themesMap = new HashMap<String, Properties>();
	private static final Properties defaultProps = new Properties();
	private static final Properties smallFontProps = new Properties();
	private static final Properties largeFontProps = new Properties();
	private static final Properties giantFontProps = new Properties();
	private static final Properties blueProps = new Properties();
	private static final Properties blueSmallFontProps = new Properties();
	private static final Properties blueLargeFontProps = new Properties();
	private static final Properties blueGiantFontProps = new Properties();
	private static final Properties greenProps = new Properties();
	private static final Properties greenSmallFontProps = new Properties();
	private static final Properties greenLargeFontProps = new Properties();
	private static final Properties greenGiantFontProps = new Properties();

	static {
		smallFontProps.setProperty("controlTextFont", "Dialog 10");
		smallFontProps.setProperty("systemTextFont", "Dialog 10");
		smallFontProps.setProperty("userTextFont", "Dialog 10");
		smallFontProps.setProperty("menuTextFont", "Dialog 10");
		smallFontProps.setProperty("windowTitleFont", "Dialog bold 10");
		smallFontProps.setProperty("subTextFont", "Dialog 8");

		largeFontProps.setProperty("controlTextFont", "Dialog 14");
		largeFontProps.setProperty("systemTextFont", "Dialog 14");
		largeFontProps.setProperty("userTextFont", "Dialog 14");
		largeFontProps.setProperty("menuTextFont", "Dialog 14");
		largeFontProps.setProperty("windowTitleFont", "Dialog bold 14");
		largeFontProps.setProperty("subTextFont", "Dialog 12");

		giantFontProps.setProperty("controlTextFont", "Dialog 18");
		giantFontProps.setProperty("systemTextFont", "Dialog 18");
		giantFontProps.setProperty("userTextFont", "Dialog 18");
		giantFontProps.setProperty("menuTextFont", "Dialog 18");
		giantFontProps.setProperty("windowTitleFont", "Dialog 18");
		giantFontProps.setProperty("subTextFont", "Dialog 16");

		blueProps.setProperty("selectionBackgroundColor", extraLightBlue);
		blueProps.setProperty("focusCellColor", darkBlue);
		blueProps.setProperty("buttonBackgroundColor", superLightBlue);
		blueProps.setProperty("controlBackgroundColor", superLightBlue);
		blueProps.setProperty("windowTitleBackgroundColor", lightBlue);
		blueProps.setProperty("windowTitleColorLight", superLightBlue);
		blueProps.setProperty("windowTitleColorDark", extraLightBlue);
		blueProps.setProperty("windowBorderColor", lightBlue);
		blueProps.setProperty("windowInactiveTitleBackgroundColor", extraLightBlue);
		blueProps.setProperty("windowInactiveBorderColor", extraLightBlue);
		blueProps.setProperty("menuBackgroundColor", superLightBlue);
		blueProps.setProperty("menuSelectionBackgroundColor", lightBlue);
		blueProps.setProperty("toolbarBackgroundColor", "244 244 244");

		greenProps.setProperty("selectionBackgroundColor", lightGreen);
		greenProps.setProperty("focusCellColor", darkGreen);
		greenProps.setProperty("buttonBackgroundColor", lightGreen);
		greenProps.setProperty("controlBackgroundColor", extraLightGreen);
		greenProps.setProperty("windowTitleBackgroundColor", lightGreen);
		greenProps.setProperty("windowTitleColorLight", extraLightGreen);
		greenProps.setProperty("windowTitleColorDark", lightGreen);
		greenProps.setProperty("windowBorderColor", lightGreen);
		greenProps.setProperty("windowInactiveTitleBackgroundColor", extraLightGreen);
		greenProps.setProperty("windowInactiveBorderColor", extraLightGreen);
		greenProps.setProperty("menuBackgroundColor", superLightGreen);
		greenProps.setProperty("menuSelectionBackgroundColor", lightGreen);
		greenProps.setProperty("toolbarBackgroundColor", "244 244 244");

		String key;
		String value;
		Iterator<Object> iter = smallFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = smallFontProps.getProperty(key);
			blueSmallFontProps.setProperty(key, value);
			greenSmallFontProps.setProperty(key, value);
		}
		iter = largeFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = largeFontProps.getProperty(key);
			blueLargeFontProps.setProperty(key, value);
			greenLargeFontProps.setProperty(key, value);
		}
		iter = giantFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = giantFontProps.getProperty(key);
			blueGiantFontProps.setProperty(key, value);
			greenGiantFontProps.setProperty(key, value);
		}

		iter = blueProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = blueProps.getProperty(key);
			blueSmallFontProps.setProperty(key, value);
			blueLargeFontProps.setProperty(key, value);
			blueGiantFontProps.setProperty(key, value);
		}
		iter = greenProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = greenProps.getProperty(key);
			greenSmallFontProps.setProperty(key, value);
			greenLargeFontProps.setProperty(key, value);
			greenGiantFontProps.setProperty(key, value);
		}

		themesList.add("Default");
		themesList.add("Small-Font");
		themesList.add("Large-Font");
		themesList.add("Giant-Font");
		themesList.add("Blue");
		themesList.add("Blue-Small-Font");
		themesList.add("Blue-Large-Font");
		themesList.add("Blue-Giant-Font");
		themesList.add("Green");
		themesList.add("Green-Small-Font");
		themesList.add("Green-Large-Font");
		themesList.add("Green-Giant-Font");

		themesMap.put("Default", defaultProps);
		themesMap.put("Small-Font", smallFontProps);
		themesMap.put("Large-Font", largeFontProps);
		themesMap.put("Giant-Font", giantFontProps);
		themesMap.put("Blue", blueProps);
		themesMap.put("Blue-Small-Font", blueSmallFontProps);
		themesMap.put("Blue-Large-Font", blueLargeFontProps);
		themesMap.put("Blue-Giant-Font", blueGiantFontProps);
		themesMap.put("Green", greenProps);
		themesMap.put("Green-Small-Font", greenSmallFontProps);
		themesMap.put("Green-Large-Font", greenLargeFontProps);
		themesMap.put("Green-Giant-Font", greenGiantFontProps);
	}

	/**
	 * <p>getThemeProperties.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.util.Properties} object.
	 */
	public static Properties getThemeProperties(String name) {
		return themesMap.get(name);
	}

	/**
	 * <p>getThemes.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> getThemes() {
		return themesList;
	}

	/**
	 * <p>setCurrentTheme.</p>
	 *
	 * @param themesProps a {@link java.util.Properties} object.
	 */
	public static void setCurrentTheme(Properties themesProps) {
		setTheme(themesProps);
	}

	/**
	 * <p>setTheme.</p>
	 *
	 * @param themesProps a {@link java.util.Properties} object.
	 */
	public static void setTheme(Properties themesProps) {
		currentThemeName = "fastTheme";
		if (myTheme == null) {
			myTheme = new FastDefaultTheme();
		}
		if (myTheme != null && themesProps != null) {
			myTheme.setUpColor();
			myTheme.setProperties(themesProps);
			myTheme.setUpColorArrs();
			AbstractLookAndFeel.setTheme(myTheme);
		}
	}

	/**
	 * <p>setTheme.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public static void setTheme(String name) {
		setTheme(themesMap.get(name));
		if (myTheme != null) {
			AbstractTheme.setInternalName(name);
		}
	}

	/**
	 * <p>setTheme.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param licenseKey a {@link java.lang.String} object.
	 * @param logoString a {@link java.lang.String} object.
	 */
	public static void setTheme(String name, String licenseKey, String logoString) {
		Properties props = themesMap.get(name);
		if (props != null) {
			props.put("licenseKey", licenseKey);
			props.put("logoString", logoString);
			setTheme(props);
			if (myTheme != null) {
				AbstractTheme.setInternalName(name);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void createDefaultTheme() {
		if (myTheme == null) {
			myTheme = new FastDefaultTheme();
		}
		setTheme(myTheme);
	}

	/** {@inheritDoc} */
	@Override
	public AbstractBorderFactory getBorderFactory() {
		return FastBorderFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return "The Fast Look and Feel";
	}

	/** {@inheritDoc} */
	@Override
	public AbstractIconFactory getIconFactory() {
		return FastIconFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getID() {
		return "Fast";
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return "Fast";
	}

	/** {@inheritDoc} */
	@Override
	protected void initClassDefaults(UIDefaults table) {
		if (!"fastTheme".equals(currentThemeName)) {
			setTheme("Default");
		}
		super.initClassDefaults(table);
		Object[] uiDefaults = { "PopupMenuUI", BasicPopupMenuUI.class.getName(),
				// BaseLookAndFeel classes
				"LabelUI", BaseLabelUI.class.getName(), "SeparatorUI", BaseSeparatorUI.class.getName(), "TextFieldUI",
				BaseTextFieldUI.class.getName(), "TextAreaUI", BaseTextAreaUI.class.getName(), "EditorPaneUI",
				BaseEditorPaneUI.class.getName(), "PasswordFieldUI", BasePasswordFieldUI.class.getName(), "ComboBoxUI",
				BaseComboBoxUI.class.getName(), "CheckBoxUI", BaseCheckBoxUI.class.getName(), "RadioButtonUI",
				BaseRadioButtonUI.class.getName(), "ToolTipUI", BaseToolTipUI.class.getName(), "TreeUI",
				BaseTreeUI.class.getName(), "TableUI", BaseTableUI.class.getName(), "TableHeaderUI",
				BaseTableHeaderUI.class.getName(), "PanelUI", BasePanelUI.class.getName(), "ScrollPaneUI",
				BaseScrollPaneUI.class.getName(), "ProgressBarUI", BasicProgressBarUI.class.getName(), "FileChooserUI",
				BaseFileChooserUI.class.getName(), "MenuUI", BaseMenuUI.class.getName(), "MenuItemUI",
				BaseMenuItemUI.class.getName(), "CheckBoxMenuItemUI", BaseCheckBoxMenuItemUI.class.getName(),
				"RadioButtonMenuItemUI", BaseRadioButtonMenuItemUI.class.getName(), "PopupMenuSeparatorUI",
				BaseSeparatorUI.class.getName(), "DesktopPaneUI", BaseDesktopPaneUI.class.getName(),
				// FastLookAndFeel classes
				"ButtonUI", FastButtonUI.class.getName(), "ToggleButtonUI", FastToggleButtonUI.class.getName(),
				"ScrollBarUI", FastScrollBarUI.class.getName(), "SliderUI", FastSliderUI.class.getName(),
				"TabbedPaneUI", FastTabbedPaneUI.class.getName(), "SplitPaneUI", FastSplitPaneUI.class.getName(),
				"ToolBarUI", FastToolBarUI.class.getName(), "InternalFrameUI", FastInternalFrameUI.class.getName(),
				"RootPaneUI", FastRootPaneUI.class.getName(), };
		table.putDefaults(uiDefaults);
		table.put("FormattedTextFieldUI", BaseFormattedTextFieldUI.class.getName());
		table.put("SpinnerUI", BaseSpinnerUI.class.getName());
	}

	/** {@inheritDoc} */
	@Override
	protected void initComponentDefaults(UIDefaults table) {
		super.initComponentDefaults(table);
		table.put("SplitPane.centerOneTouchButtons", Boolean.FALSE);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}

} // end of class FastLookAndFeel
