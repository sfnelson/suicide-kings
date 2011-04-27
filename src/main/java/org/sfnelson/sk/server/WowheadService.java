package org.sfnelson.sk.server;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.sfnelson.sk.server.domain.Loot;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WowheadService {

	private static final WowheadService instance = new WowheadService();

	public static WowheadService getInstance() {
		return instance;
	}

	public Loot getLoot(long reference) {

		Loot loot;

		try {
			URL url = new URL(String.format("http://www.wowhead.com/item=%d&xml", reference));

			WowheadHandler handler = new WowheadHandler(reference);

			SAXParser p = SAXParserFactory.newInstance().newSAXParser();
			p.parse(url.openStream(), handler);

			loot = handler.getLoot();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			loot = null;
		}

		return loot;
	}

	private static class WowheadHandler extends DefaultHandler {

		private final Loot loot;

		private boolean isName = false;

		@Override
		public void characters(char[] data, int start, int length)
		throws SAXException {
			if (isName) {
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < length; i++) {
					buffer.append(data[start + i]);
				}
				loot.setName(buffer.toString().trim());
				isName = false;
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
		throws SAXException {
			if (qName.equals("name")) {
				isName = true;
			}
		}

		public WowheadHandler(long reference) {
			loot = new Loot();
			loot.setReference(reference);
		}

		public Loot getLoot() {
			return loot;
		}
	}

	public static void main(String args[]) {
		System.out.println(getInstance().getLoot(58278));
	}
}
