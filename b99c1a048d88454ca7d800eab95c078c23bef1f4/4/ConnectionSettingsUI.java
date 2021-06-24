package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Dimension;
import java.net.InetSocketAddress;
import java.net.Proxy;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.border.EmptyBorder;

import org.loboevolution.gui.CheckBox;
import org.loboevolution.gui.FieldType;
import org.loboevolution.gui.FormField;
import org.loboevolution.gui.FormPanel;
import org.loboevolution.gui.Panel;
import org.loboevolution.gui.RadioButton;
import org.loboevolution.gui.SwingTasks;
import org.loboevolution.store.ConnectionStore;

/**
 * <p>ConnectionSettingsUI class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ConnectionSettingsUI extends AbstractSettingsUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/** The authentication panel. */
	private final FormPanel authenticationPanel = new FormPanel();
	
	/** The authenticated check box. */
	private final CheckBox authenticatedCheckBox = new CheckBox("Authenticate with proxy server.");

	/** The bypass local check box. */
	private final CheckBox bypassLocalCheckBox = new CheckBox("Bypass proxy for local addresses.");

	/** The host field. */
	private final transient FormField hostField = new FormField(FieldType.TEXT);

	/** The host port panel. */
	private final FormPanel hostPortPanel = new FormPanel();

	/** The http proxy radio button. */
	private final RadioButton httpProxyRadioButton = new RadioButton("HTTP proxy");

	/** The no proxy radio button. */
	private final RadioButton noProxyRadioButton = new RadioButton("Direct connection (no proxy)");
	
	/** The socks proxy radio button. */
	private final RadioButton socksProxyRadioButton = new RadioButton("SOCKS proxy");

	/** The password field. */
	private final transient FormField passwordField = new FormField(FieldType.PASSWORD);

	/** The port field. */
	private final transient FormField portField = new FormField(FieldType.TEXT);

	/** The proxy host area. */
	private final Box proxyHostArea = new Box(BoxLayout.Y_AXIS);

	/** The user name field. */
	private final transient FormField userNameField = new FormField(FieldType.TEXT);

	/**
	 * Instantiates a new connection settings ui.
	 */
	public ConnectionSettingsUI() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		this.noProxyRadioButton.addChangeListener(e -> updateEnabling());
		this.authenticatedCheckBox.addChangeListener(e -> updateEnabling());
		this.userNameField.setCaption("User name:");
		this.passwordField.setCaption("Password:");
		this.authenticationPanel.addField(this.userNameField);
		this.authenticationPanel.addField(this.passwordField);
		this.hostField.setCaption("Host:");
		this.portField.setCaption("Port:");
		this.hostPortPanel.addField(this.hostField);
		this.hostPortPanel.addField(this.portField);

		final ButtonGroup group = new ButtonGroup();
		group.add(this.noProxyRadioButton);
		group.add(this.httpProxyRadioButton);
		group.add(this.socksProxyRadioButton);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(8, 8)));
		this.add(getProxyBox());
		this.add(SwingTasks.createVerticalFill());
		loadSettings();
		updateEnabling();
	}

	/**
	 * Gets the proxy box.
	 *
	 * @return the proxy box
	 */
	private Component getProxyBox() {
		final Box radioBox = new Box(BoxLayout.Y_AXIS);
		radioBox.setPreferredSize(new Dimension(600, 200));
		radioBox.add(this.noProxyRadioButton);
		radioBox.add(this.httpProxyRadioButton);
		radioBox.add(this.socksProxyRadioButton);

		final Box radioBoxExpander = new Box(BoxLayout.X_AXIS);
		radioBoxExpander.add(radioBox);
		radioBoxExpander.add(Box.createGlue());

		final Panel box = new Panel("Proxy");
		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
		box.add(radioBoxExpander);
		box.add(getProxyHostArea());
		return box;
	}

	/**
	 * Gets the proxy host area.
	 *
	 * @return the proxy host area
	 */
	private Component getProxyHostArea() {
		final Box checkBoxBox = new Box(BoxLayout.Y_AXIS);
		checkBoxBox.setPreferredSize(new Dimension(600, 200));
		checkBoxBox.add(this.bypassLocalCheckBox);
		checkBoxBox.add(this.authenticatedCheckBox);

		final Box checkBoxBoxExpander = new Box(BoxLayout.X_AXIS);
		checkBoxBoxExpander.add(checkBoxBox);
		checkBoxBoxExpander.add(Box.createHorizontalGlue());

		final Box box = this.proxyHostArea;
		box.setBorder(new EmptyBorder(8, 16, 8, 8));
		box.add(this.hostPortPanel);
		box.add(checkBoxBoxExpander);
		box.add(this.authenticationPanel);
		return box;
	}

	/**
	 * Load settings.
	 */
	private void loadSettings() {
		final ConnectionStore settings = ConnectionStore.getConnection();
		switch (settings.getProxyType()) {
		case DIRECT:
			this.noProxyRadioButton.setSelected(true);
			break;
		case SOCKS:
			this.socksProxyRadioButton.setSelected(true);
			break;
		default:
			this.httpProxyRadioButton.setSelected(true);
			break;
		}
		this.authenticatedCheckBox.setSelected(settings.isAuthenticated());
		this.userNameField.setValue(settings.getUserName());
		this.passwordField.setValue(settings.getPassword());
		this.bypassLocalCheckBox.setSelected(settings.isDisableProxyForLocalAddresses());
		final InetSocketAddress socketAddress = settings.getInetSocketAddress();
		if (socketAddress == null) {
			this.hostField.setValue("");
			this.portField.setValue("");
		} else {
			this.hostField.setValue(socketAddress.getHostName());
			this.portField.setValue(String.valueOf(socketAddress.getPort()));
		}
		this.authenticationPanel.revalidate();
		this.hostPortPanel.revalidate();
	}

	/** {@inheritDoc} */
	@Override
	public void restoreDefaults() {
		final ConnectionStore settings = new ConnectionStore();
		settings.restoreDefaults();
		loadSettings();
	}

	/** {@inheritDoc} */
	@Override
	public void save() throws RuntimeException {
		final ConnectionStore settings = new ConnectionStore();
		Proxy.Type proxyType;
		if (this.noProxyRadioButton.isSelected()) {
			proxyType = Proxy.Type.DIRECT;
		} else if (this.httpProxyRadioButton.isSelected()) {
			proxyType = Proxy.Type.HTTP;
		} else if (this.socksProxyRadioButton.isSelected()) {
			proxyType = Proxy.Type.SOCKS;
		} else {
			throw new IllegalStateException("not expected");
		}
		settings.setProxyType(proxyType);
		settings.setAuthenticated(this.authenticatedCheckBox.isSelected());
		settings.setUserName(this.userNameField.getValue());
		settings.setPassword(this.passwordField.getValue());
		settings.setDisableProxyForLocalAddresses(this.bypassLocalCheckBox.isSelected());
		final String host = this.hostField.getValue();
		if ("".equals(host) && proxyType != Proxy.Type.DIRECT) {
			throw new RuntimeException("To set up a proxy, a host name must be provided.");
		}
		int port;
		try {
			port = Integer.parseInt(this.portField.getValue());
		} catch (final NumberFormatException nfe) {
			if (proxyType != Proxy.Type.DIRECT) {
				throw new RuntimeException("The port must be a number.");
			} else {
				port = 0;
			}
		}
		final InetSocketAddress socketAddress = new InetSocketAddress(host, port);
		settings.setInetSocketAddress(socketAddress);
		settings.deleteConnection();
		settings.insertConnection();
	}

	/**
	 * Update enabling.
	 */
	private void updateEnabling() {
		SwingTasks.setNestedEnabled(this.proxyHostArea, !this.noProxyRadioButton.isSelected());
		SwingTasks.setNestedEnabled(this.authenticationPanel, this.authenticatedCheckBox.isSelected());
	}
}
