package com.sksamuel.jqm4gwt.button;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.sksamuel.jqm4gwt.DataIcon;
import com.sksamuel.jqm4gwt.HasCorners;
import com.sksamuel.jqm4gwt.HasIcon;
import com.sksamuel.jqm4gwt.HasIconShadow;
import com.sksamuel.jqm4gwt.HasInline;
import com.sksamuel.jqm4gwt.HasMini;
import com.sksamuel.jqm4gwt.HasRel;
import com.sksamuel.jqm4gwt.HasText;
import com.sksamuel.jqm4gwt.HasTransition;
import com.sksamuel.jqm4gwt.IconPos;
import com.sksamuel.jqm4gwt.JQMCommon;
import com.sksamuel.jqm4gwt.JQMContainer;
import com.sksamuel.jqm4gwt.JQMPage;
import com.sksamuel.jqm4gwt.JQMWidget;
import com.sksamuel.jqm4gwt.Transition;
import com.sksamuel.jqm4gwt.events.HasTapHandlers;
import com.sksamuel.jqm4gwt.events.JQMComponentEvents;
import com.sksamuel.jqm4gwt.events.JQMHandlerRegistration;
import com.sksamuel.jqm4gwt.events.JQMHandlerRegistration.WidgetHandlerCounter;
import com.sksamuel.jqm4gwt.events.TapEvent;
import com.sksamuel.jqm4gwt.events.TapHandler;

/**
 * @author Stephen K Samuel samspade79@gmail.com 5 May 2011 14:02:24
 * <p/>
 * An implementation of a Jquery mobile button.
 * <p/>See <a href="http://view.jquerymobile.com/1.3.2/dist/demos/widgets/buttons/">Buttons</a>
 * <p/>See also <a href="http://jquerymobile.com/demos/1.2.1/docs/buttons/buttons-types.html">Button basics</a>
 */
public class JQMButton extends JQMWidget implements HasText<JQMButton>, HasRel<JQMButton>,
        HasTransition<JQMButton>, HasClickHandlers, HasInline<JQMButton>,
        HasIcon<JQMButton>, HasCorners<JQMButton>, HasIconShadow<JQMButton>, HasMini<JQMButton>,
        HasTapHandlers {

    private boolean alwaysActive;

    /**
     * Create a {@link JQMButton} with the given text that does not link to
     * anything. This button would only react to events if a link is added or
     * a click handler is attached.
     *
     * @param text the text to display on the button
     */
    public @UiConstructor JQMButton(String text) {
        this(new Anchor(text));
    }

    /**
     * Convenience constructor that creates a button that shows the given
     * {@link JQMPage} when clicked. The link will use a Transition.POP type.
     * <p/>
     * Note that the page param is an already instantiated page and thus will
     * be immediately inserted into the DOM. Do not use this constructor when
     * you want to lazily add the page.
     *
     * @param text the text to display on the button
     * @param c    the {@link JQMContainer} to create a link to
     */
    public JQMButton(String text, final JQMContainer c) {
        this(text, c, null);
    }

    /**
     * Convenience constructor that creates a button that shows the given
     * {@link JQMPage} when clicked.
     * <p/>
     * Note that the page param is an already instantiated page and thus will
     * be immediately inserted into the DOM. Do not use this constructor when
     * you want to lazily add the page.
     *
     * @param text the text to display on the button
     * @param c    the {@link JQMContainer} to create a link to
     * @param t    the transition type to use
     */
    public JQMButton(String text, final JQMContainer c, final Transition t) {
        this(text, "#" + c.getId(), t);
        withRel(c.getRelType());
    }

    /**
     * Convenience constructor that creates a button that shows the given url
     * when clicked. The link will use a Transition.POP type.
     * <p/>
     * Note that the page param is an already instantiated page and thus will
     * be immediately inserted into the DOM. Do not use this constructor when
     * you want to lazily add the page.
     *
     * @param text the text to display on the button
     * @param url  the HTTP url to create a link to
     */
    public JQMButton(String text, String url) {
        this(text, url, null);
    }

    /**
     * Convenience constructor that creates a button that shows the given url
     * when clicked.
     * <p/>
     * Note that the page param is an already instantiated page and thus will
     * be immediately inserted into the DOM. Do not use this constructor when
     * you want to lazily add the page.
     *
     * @param text the text to display on the button
     * @param url  the HTTP url to create a link to
     * @param t    the transition type to use
     */
    public JQMButton(String text, String url, final Transition t) {
        this(text);
        if (url != null)
            setHref(url);
        if (t != null)
            withTransition(t);
    }

    protected JQMButton(Widget widget) {
        initWidget(widget);
        setStyleName("jqm4gwt-button");
        setDataRole("button");
        setId();
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

	@Override
	public HandlerRegistration addTapHandler(TapHandler handler) {
        // this is not a native browser event so we will have to manage it via JS
        return JQMHandlerRegistration.registerJQueryHandler(new WidgetHandlerCounter() {
			@Override
			public int getHandlerCountForWidget(Type<?> type) {
				return getHandlerCount(type);
			}
        }, this, handler, JQMComponentEvents.TAP_EVENT, TapEvent.getType());
	}

	@Override
    public IconPos getIconPos() {
        return JQMCommon.getIconPos(this);
    }

    @Override
    public String getRel() {
        return getElement().getAttribute("rel");
    }

    @Override
    public String getText() {
        Element e = getElement();
        while (e.getFirstChildElement() != null) {
            e = e.getFirstChildElement();
        }
        return e.getInnerText();
    }

    @Override
    public Transition getTransition() {
        String attr = getElement().getAttribute("data-transition");
        if (attr == null)
            return null;
        return Transition.valueOf(attr);
    }

    @Override
    public boolean isCorners() {
        return JQMCommon.isCorners(this);
    }

    /**
     * Returns true if this button is set to load the linked page as a dialog page
     *
     * @return true if this link will show as a dialog
     */
    public boolean isDialog() {
        return "dialog".equals(getAttribute("data-rel"));
    }

    /**
     * Returns true if this button is set to load a popup
     */
    public boolean isPopup() {
        return "popup".equals(getAttribute("data-rel"));
    }

    public void setPopup(boolean popup) {
        setRel(popup ? "popup" : null);
    }

    public JQMButton withPopup(boolean popup) {
        setPopup(popup);
        return this;
    }

    public String getPopupPos() {
        return JQMCommon.getPopupPos(this);
    }

    public void setPopupPos(String pos) {
        JQMCommon.setPopupPos(this, pos);
    }

    @Override
    public boolean isIconShadow() {
        return JQMCommon.isIconShadow(this);
    }

    /**
     * @return true if this button is set to inline
     */
    @Override
    public boolean isInline() {
        return JQMCommon.isInline(this);
    }

    @Override
    public boolean isMini() {
        return JQMCommon.isMini(this);
    }

    public String getHref() {
        return getAttribute("href");
    }

    public void setHref(String url) {
         setAttribute("href", url);
    }

    public JQMButton withHref(String url) {
        setHref(url);
        return this;
    }

    @Override
    public JQMButton removeIcon() {
        JQMCommon.setIcon(this, null);
        return this;
    }

    /**
     * Sets this buttom to be a back button. This will override any URL set on
     * the button.
     */
    public void setBack(boolean back) {
        setRel(back ? "back" : null);
    }

    public JQMButton withBack(boolean back) {
        setBack(back);
        return this;
    }

    @Override
    public void setCorners(boolean corners) {
        JQMCommon.setCorners(this, corners);
    }

    @Override
    public JQMButton withCorners(boolean corners) {
        setCorners(corners);
        return this;
    }

    /**
     * Sets this buttom to be a dialog button. This changes the look and feel
     * of the page that is loaded as a consequence of clicking on this button.
     */
    public JQMButton withDialog(boolean dialog) {
        setDialog(dialog);
        return this;
    }

    public void setDialog(boolean dialog) {
        setRel(dialog ? "dialog" : null);
    }

    /**
     * Short cut for withRel("external");
     */
    public void setExternal(boolean external) {
        setRel(external ? "external" : null);
    }

    public JQMButton withExternal(boolean external) {
        setExternal(external);
        return this;
    }

    /**
     * Sets the icon used by this button. See {@link DataIcon}.
     */
    @Override
    public void setBuiltInIcon(DataIcon icon) {
        JQMCommon.setIcon(this, icon);
    }

    @Override
    public void setIconURL(String src) {
        if (src == null)
            removeIcon();
        else
            getElement().setAttribute("data-icon", src);
    }

    /**
     * Sets the icon used by this button. See {@link DataIcon}.
     */
    @Override
    public JQMButton withBuiltInIcon(DataIcon icon) {
        setBuiltInIcon(icon);
        return this;
    }

    @Override
    public JQMButton withIconURL(String src) {
        setIconURL(src);
        return this;
    }

    /**
     * Sets the position of the icon. If you desire an icon only button then
     * set the position to IconPos.NOTEXT
     */
    @Override
    public void setIconPos(IconPos pos) {
        JQMCommon.setIconPos(this, pos);
    }

    /**
     * Sets the position of the icon. If you desire an icon only button then
     * set the position to IconPos.NOTEXT
     */
    @Override
    public JQMButton withIconPos(IconPos pos) {
        setIconPos(pos);
        return this;
    }

    /**
     * Applies the drop shadow style to the select button if set to true.
     */
    @Override
    public void setIconShadow(boolean shadow) {
        JQMCommon.setIconShadow(this, shadow);
    }

    /**
     * Applies the drop shadow style to the select button if set to true.
     */
    @Override
    public JQMButton withIconShadow(boolean shadow) {
        setIconShadow(shadow);
        return this;
    }

    /**
     * Sets this button to be inline.
     * <p/>
     * NOTE: If this button is inside a {@link JQMButtonGroup} then you must
     * call withInline(boolean) on the button group itself and not each button
     * individually.
     *
     * @param inline true to change to line or false to switch to full width
     */
    @Override
    public void setInline(boolean inline) {
        JQMCommon.setInline(this, inline);
    }

    /**
     * Sets this button to be inline.
     * <p/>
     * NOTE: If this button is inside a {@link JQMButtonGroup} then you must
     * call withInline(boolean) on the button group itself and not each button
     * individually.
     *
     * @param inline true to change to line or false to switch to full width
     */
    @Override
    public JQMButton withInline(boolean inline) {
        setInline(inline);
        return this;
    }

    /**
     * If set to true then renders a smaller version of the standard-sized element.
     */
    @Override
    public void setMini(boolean mini) {
        JQMCommon.setMini(this, mini);
    }

    /**
     * If set to true then renders a smaller version of the standard-sized element.
     */
    @Override
    public JQMButton withMini(boolean mini) {
        setMini(mini);
        return this;
    }

    @Override
    public void setRel(String rel) {
        if (rel == null) getElement().removeAttribute("data-rel");
        else getElement().setAttribute("data-rel", rel);
    }

    @Override
    public JQMButton withRel(String rel) {
        setRel(rel);
        return this;
    }

    @Override
    public void setText(String text) {
        // if the button has already been rendered then we need to go down
        // deep until we get the
        // final span
        Element e = getElement();
        while (e.getFirstChildElement() != null) {
            e = e.getFirstChildElement();
        }
        e.setInnerText(text);
    }

    @Override
    public JQMButton withText(String text) {
        setText(text);
        return this;
    }

    /**
     * Sets the transition to be used by this button when loading the URL.
     */
    @Override
    public void setTransition(Transition transition) {
        if (transition != null)
            setAttribute("data-transition", transition.getJQMValue());
        else
            removeAttribute("data-transition");
    }

    /**
     * Sets the transition to be used by this button when loading the URL.
     */
    @Override
    public JQMButton withTransition(Transition transition) {
        setTransition(transition);
        return this;
    }

    public void setTransitionReverse(boolean reverse) {
        if (reverse) setAttribute("data-direction", "reverse");
        else removeAttribute("data-direction");
    }

    public JQMButton withTransitionReverse(boolean reverse) {
        setTransitionReverse(reverse);
        return this;
    }

    public boolean isIconNoDisc() {
        return JQMCommon.isIconNoDisc(this);
    }

    public void setIconNoDisc(boolean value) {
        JQMCommon.setIconNoDisc(this, value);
    }

    public boolean isIconAlt() {
        return JQMCommon.isIconAlt(this);
    }

    /**
     * @param value - if true "white vs. black" icon style will be used
     */
    public void setIconAlt(boolean value) {
        JQMCommon.setIconAlt(this, value);
    }

    public boolean isAlwaysActive() {
        return alwaysActive;
    }

    /**
     * @param value - if true button always be highlighted as active.
     */
    public void setAlwaysActive(boolean value) {
        if (alwaysActive == value) return;
        alwaysActive = value;
        JQMCommon.setBtnActive(this, alwaysActive);
        if (alwaysActive) Scheduler.get().scheduleFinally(createAlwaysActiveCmd());
    }

    private Scheduler.RepeatingCommand createAlwaysActiveCmd() {
        return new Scheduler.RepeatingCommand() {
            @Override
            public boolean execute() {
                if (alwaysActive) JQMCommon.setBtnActive(JQMButton.this, true);
                return alwaysActive; // stops when alwaysActive == false
            }};
    }

}
