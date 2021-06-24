package org.gwtbootstrap3.extras.notify.client.ui;

/*
 * #%L
 * GwtBootstrap3
 * %%
 * Copyright (C) 2013 - 2015 GwtBootstrap3
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.gwtbootstrap3.extras.animate.client.ui.constants.Animation;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyIconType;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyPlacement;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyUrlTarget;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This class represent basic Notify's settings, that you can use to customize display of each Notify.
 * <p/>
 * You can also set current state as default for all new Notifies.
 *
 * @author jeffisenhart
 * @author Sven Jacobs
 * @author Joshua Godi
 * @author Pavel Zlámal
 * @author Xiaodong SUN
 * @see #makeDefault()
 */
public class NotifySettings extends JavaScriptObject {

    /**
     * Set element name or class or ID to append Notify to. Default is 'body'.
     *
     * @param element Name, class or ID
     */
    public final native void setElement(String element) /*-{
        this.element = element;
    }-*/;

    //FIXME setPosition

    /**
     * Set type of Notify (CSS style class name). Default is INFO.
     *
     * @param type one of INFO, WARNING, DANGER, SUCCESS
     * @see org.gwtbootstrap3.extras.notify.client.constants.NotifyType
     */
    public final void setType(final NotifyType type) {
        if (type != null) {
            setType(type.getCssName());
        }
    }

    /**
     * Set custom style name to Notify. Resulting class name is "alert-[customType]".
     *
     * @param customType Style name to set
     */
    public final native void setType(String customType) /*-{
        this.type = customType;
    }-*/;

    /**
     * Set placement of Notify on screen. Defaults placement is {@link NotifyPlacement#TOP_RIGHT}.
     *
     * @param placement Notify's placement on screen
     * @see org.gwtbootstrap3.extras.notify.client.constants.NotifyPlacement
     */
    public final void setPlacement(final NotifyPlacement placement) {
        if (placement != null) {
            setNotifyPlacement(placement);
        } else {
            setNotifyPlacement(NotifyPlacement.TOP_RIGHT);
        }
    }

    /**
     * Set native property of Notify's placement.
     *
     * @param placement Notify's placement on screen
     */
    private final native void setNotifyPlacement(final NotifyPlacement placement) /*-{
        var from = placement.@org.gwtbootstrap3.extras.notify.client.constants.NotifyPlacement::getFrom()();
        var align = placement.@org.gwtbootstrap3.extras.notify.client.constants.NotifyPlacement::getAlign()();
        this.placement = { from: from, align: horizontal };
    }-*/;

    /**
     * Set offset (space between Notify and screen/browser edges) for each axis. Default is 20 PX for both.
     *
     * @param offX Offset for X axis in PX
     * @param offY Offset for Y axis in PX
     */
    public final native void setOffset(int offX, int offY) /*-{
        this.offset = { x: offX, y: offY };
    }-*/;

    /**
     * Set custom spacing between two Notifies. Default is 10 PX.
     *
     * @param space Spacing in PX
     */
    public final native void setSpacing(int space) /*-{
        this.spacing = space;
    }-*/;

    /**
     * Set custom Z-index. Default is 1031.
     *
     * @param zIndex Z-index
     */
    public final native void setZIndex(int zIndex) /*-{
        this.z_index = zIndex;
    }-*/;

    /**
     * Set delay, how long Notify stays on screen. Default is 5000 ms.
     * Set to zero for unlimited time.
     *
     * @param mDelay Delay in milliseconds or zero for unlimited
     */
    public final native void setDelay(int mDelay) /*-{
        this.delay = mDelay;
    }-*/;

    /**
     * Set timer. It's value is removed from remaining 'delay' on each 'timer' period.
     * This way you can speed up hiding of Notify. If timer > remaining delay, Notify is
     * hidden after delay runs out (ignoring timer).
     *
     * @param timer Time in milliseconds
     * @see #setDelay(int)
     */
    public final native void setTimer(int timer) /*-{
        this.timer = timer;
    }-*/;

    /**
     * Set custom URL target.<br>
     * <br>
     * Defaults to {@link NotifyUrlTarget#BLANK}.
     *
     * @param urlTarget URL target
     */
    public final native void setUrlTarget(NotifyUrlTarget urlTarget) /*-{
    	if (urlTarget !== null)
            this.url_target = urlTarget.@org.gwtbootstrap3.extras.notify.client.constants.NotifyUrlTarget::getTarget()();
    }-*/;

    /**
     * Set custom URL target. Default is "_blank".
     * <p/>
     * See http://www.w3schools.com/tags/att_a_target.asp for possible values.
     *
     * @param urlTarget URL target
     */
    public final native void setUrlTarget(String urlTarget) /*-{
        this.url_target = urlTarget;
    }-*/;

    /**
     * Pause countdown of display timeout when mouse is hovering above the Notify.
     * Countdown continues (not restarted) if mouse leaves the Notify.
     *
     * @param pauseOnMouseOver TRUE = pause / FALSE = not pause
     */
    public final native void setPauseOnMouseOver(boolean pauseOnMouseOver) /*-{
        this.mouse_over = pauseOnMouseOver ? 'pause' : null;
    }-*/;

    /**
     * Set Animation to Notify when it enters and exit the screen.
     *
     * Default is enter = Animation.FADE_IN_DOWN, exit = Animation.FADE_OUT_UP
     *
     * @see org.gwtbootstrap3.extras.animate.client.ui.constants.Animation
     *
     * @param enter animation style when Notify enters the screen
     * @param exit  animation style when Notify exists the screen
     */
    public final void setAnimation(Animation enter, Animation exit) {
        setAnimation((enter != null) ? enter.getCssName() : Animation.NO_ANIMATION.getCssName(),
                (exit != null) ? exit.getCssName() : Animation.NO_ANIMATION.getCssName());
    }

    /**
     * Set custom CSS style for animations of Notify when it enters and exits the screen.
     * You must write your own CSS animation definition.
     *
     * @param enter animation style when Notify enters the screen
     * @param exit  animation style when Notify exists the screen
     */
    public final native void setAnimation(String enter, String exit) /*-{
        this.animate = { enter: enter, exit: exit };
    }-*/;

    /**
     * Set icon type you will use for Notify. Default is 'class', which
     * allows to use iconic fonts like FontAwesome.
     * If you want to use images instead of class, set value to "image".<br>
     * <br>
     * Defaults to {@link NotifyIconType#CLASS}.
     *
     * @param iconType the icon type
     * @see NotifyIconType
     */
    public final native void setIconType(NotifyIconType iconType) /*-{
    	if (iconType !== null)
            this.icon_type = iconType.@org.gwtbootstrap3.extras.notify.client.constants.NotifyIconType::getType()();
    }-*/;

    /**
     * Set custom HTML Template of Notify. Default value is:
     * <p/>
     *
     * &lt;div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert"&gt;<br/>
     * &nbsp;&nbsp;&lt;button type="button" aria-hidden="true" class="close" data-notify="dismiss"&gt;x&lt;/button&gt;<br/>
     * &nbsp;&nbsp;&lt;span data-notify="icon"&gt;&lt;/span&gt;<br/>
     * &nbsp;&nbsp;&lt;span data-notify="title"&gt;{1}&lt;/span&gt;<br/>
     * &nbsp;&nbsp;&lt;span data-notify="message"&gt;{2}&lt;/span&gt;<br/>
     * &nbsp;&nbsp;&lt;div class="progress" data-notify="progressbar"&gt;<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&lt;div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"&gt;&lt;/div&gt;<br/>
     * &nbsp;&nbsp;&lt;/div&gt;<br/>
     * &nbsp;&nbsp;&lt;a href="{3}" target="{4}" data-notify="url"&gt;&lt;/a&gt;<br/>
     * &lt;/div&gt;
     *
     * <p/>
     * Where:
     * <ul>
     * <li>{0} = type</li>
     * <li>{1} = title</li>
     * <li>{2} = message</li>
     * <li>{3} = url</li>
     * <li>{4} = target</li>
     * </ul>
     *
     * @param html Custom HTML template
     * @see documentation at: http://bootstrap-notify.remabledesigns.com/
     */
    public final native void setTemplate(String html) /*-{
        this.template = html;
    }-*/;

    /**
     * Make this NotifySettings as default for all new Notifies.
     * <p/>
     * Values set to this NotifySettings overrides original default values.
     * If value for some property is not set, original default value is kept.
     */
    public final native void makeDefault() /*-{
        $wnd.jQuery.notifyDefaults();
    }-*/;

}
