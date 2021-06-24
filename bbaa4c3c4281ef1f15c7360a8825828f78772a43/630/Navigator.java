/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.js;

import org.loboevolution.html.js.geolocation.Geolocation;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>Navigator class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Navigator extends AbstractScriptableDelegate {
	
	private final Window window;
	
	/**
	 * <p>Constructor for Navigator.</p>
	 *
	 * @param window a {@link org.loboevolution.html.js.Window} object.
	 */
	public Navigator(Window window) {
		this.window = window;
	}

	/**
	 * <p>getGeolocation.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.geolocation.Geolocation} object.
	 */
	public Geolocation getGeolocation() {
		return new Geolocation(window);
	}

	/**
	 * <p>isOnLine.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isOnLine() {
		return true;
	}

	/**
	 * <p>getLanguage.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLanguage() {
		return "EN";
	}

	/**
	 * <p>getAppName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAppName() {
		return "Lobo Evolution";
	}

	/**
	 * <p>getAppVersion.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAppVersion() {
		return "1.0";
	}

	/**
	 * <p>getPlatform.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPlatform() {
		return System.getProperty("os.name");
	}

	/**
	 * <p>getProduct.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getProduct() {
		return this.getAppName();
	}

	/**
	 * <p>getUserAgent.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUserAgent() {
		return HttpNetwork.getUserAgentValue();
	}
}
