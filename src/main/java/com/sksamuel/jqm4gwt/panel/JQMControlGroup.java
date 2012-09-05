package com.sksamuel.jqm4gwt.panel;

import com.google.gwt.dom.client.Element;
import com.sksamuel.jqm4gwt.HasOrientation;

/**
 * @author Stephen K Samuel samspade79@gmail.com 11 Jul 2011 22:09:41
 * 
 *         An implementation of {@link JQMPanel} for control groups.
 * 
 */
public class JQMControlGroup extends JQMPanel implements HasOrientation {

	protected JQMControlGroup(Element element, String styleName) {
		super(element, "controlgroup", styleName);
	}

	@Override
	public boolean isHorizontal() {
		return "true".equals(getAttribute("data-type"));
	}

	@Override
	public boolean isVertical() {
		return !isHorizontal();
	}

	@Override
	public void setHorizontal() {
		setAttribute("data-type", "horizontal");
	}

	@Override
	public void setVertical() {
		removeAttribute("data-type");
	}
}