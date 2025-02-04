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
package org.spldev.util.io;

import java.util.*;

import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * This is an extension of a default xml reader, which saves the line numbers
 * via user data.
 *
 * @author Jens Meinicke
 * @author Sebastian Krieter
 */
public class PositionalXMLHandler extends DefaultHandler {

	public final static String LINE_NUMBER_KEY_NAME = "lineNumber";

	private final ArrayDeque<Element> elementStack = new ArrayDeque<>();
	private final StringBuilder textBuffer = new StringBuilder();

	private final Document doc;

	private Locator locator;

	public PositionalXMLHandler(Document doc) {
		super();
		this.doc = doc;
	}

	@Override
	public void setDocumentLocator(final Locator locator) {
		this.locator = locator;
	}

	@Override
	public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
		throws SAXException {
		addTextIfNeeded();
		final Element el = doc.createElement(qName);
		for (int i = 0; i < attributes.getLength(); i++) {
			el.setAttribute(attributes.getQName(i), attributes.getValue(i));
		}
		el.setUserData(LINE_NUMBER_KEY_NAME, locator.getLineNumber(), null);
		elementStack.push(el);
	}

	@Override
	public void endElement(final String uri, final String localName, final String qName) {
		addTextIfNeeded();
		final Element closedEl = elementStack.pop();
		if (elementStack.isEmpty()) {
			doc.appendChild(closedEl);
		} else {
			elementStack.peek().appendChild(closedEl);
		}
	}

	@Override
	public void characters(final char ch[], final int start, final int length) throws SAXException {
		textBuffer.append(ch, start, length);
	}

	private void addTextIfNeeded() {
		if (textBuffer.length() > 0) {
			elementStack.peek().appendChild(doc.createTextNode(textBuffer.toString()));
			textBuffer.delete(0, textBuffer.length());
		}
	}

}
