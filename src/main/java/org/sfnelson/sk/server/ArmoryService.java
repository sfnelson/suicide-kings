package org.sfnelson.sk.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.sfnelson.sk.server.domain.ArmoryReference;
import org.sfnelson.sk.server.domain.Character;
import org.sfnelson.sk.server.domain.Realm;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ArmoryService {

	private static final ArmoryService instance = new ArmoryService();

	public static ArmoryService getInstance() {
		return instance;
	}

	public Character getCharacter(Realm realm, String name) {

		String region = realm.getRegion().toLowerCase();
		String server = realm.getServer().toLowerCase();

		ArmoryReference armory = new ArmoryReference();

		try {
			URL url = new URL("http://" + region + ".battle.net/wow/en/character/" + server + "/" + name + "/simple");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			Pattern p = Pattern.compile("http://" + region + ".battle.net/static-render/"
					+ region + "/" + server + "/[0-9]*/[0-9]*-profilemain.jpg?");

			while ((line = reader.readLine()) != null) {
				Matcher m = p.matcher(line);
				if (m.find()) break;
			}

			if (line == null) return null;

			Pattern id_p = Pattern.compile("/([0-9]*)/([0-9]*)-");
			Matcher id_m = id_p.matcher(line);
			if (id_m.find()) {
				armory.setArmoryHash(Long.parseLong(id_m.group(1)));
				armory.setArmoryReference(Long.parseLong(id_m.group(2)));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		Character c = new Character();
		c.setName(name);
		c.setRealm(realm.getId());
		c.setArmory(armory);
		return c;
	}

	public List<Realm> getRealms(String region) {

		List<Realm> realms;

		try {
			URL url = new URL(String.format("http://%s.battle.net/wow/en/status", region.toLowerCase()));

			RealmHandler handler = new RealmHandler(region);

			SAXParser p = SAXParserFactory.newInstance().newSAXParser();
			p.parse(url.openStream(), handler);

			realms = handler.servers;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			realms = Collections.emptyList();
		}

		Collections.sort(realms,  new Comparator<Realm>() {
			@Override
			public int compare(Realm a, Realm b) {
				return a.getServer().compareTo(b.getServer());
			}
		});
		return realms;
	}

	public static void main(String args[]) {
		List<Realm> realms = getInstance().getRealms("us");
		for (Realm r: realms) {
			System.out.printf("%s-%s (%s) %s %s\n", r.getRegion(), r.getServer(), r.getType(), r.getPopulation(), r.getLocale());
		}

		System.out.println(realms.size() + " servers");
	}

	private static class RealmHandler extends DefaultHandler {

		final String region;
		final List<Realm> servers = new ArrayList<Realm>();

		private Realm current = null;
		private boolean finished = false;
		private boolean foundRealms = false;
		private boolean foundTable = false;
		private boolean isServer = false;
		private boolean isLocale = false;

		public RealmHandler(String region) {
			this.region = region;
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (finished);
			else if (current != null && qName.equals("td")) {
				String cls = attributes.getValue("class");
				if (cls == null);
				else if (cls.equals("name")) {
					isServer = true;
				}
				else if (cls.equals("type")) {
					current.setType(attributes.getValue("data-raw"));
				}
				else if (cls.equals("population")) {
					current.setPopulation(attributes.getValue("data-raw"));
				}
				else if (cls.equals("locale")) {
					isLocale = true;
				}
			}
			else if (foundTable && qName.equals("tr")) {
				current = new Realm();
				current.setRegion(region);
			}
			else if (foundRealms && qName.equals("table")) {
				foundTable = true;
			}
			else if (attributes.getIndex("id") >= 0 && attributes.getValue("id").equals("all-realms")) {
				foundRealms = true;
			}
		}

		@Override
		public void characters(final char[] ch, final int start, final int length) throws SAXException {
			if (isServer) {
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < length; i++) {
					buffer.append(ch[start + i]);
				}
				current.setServer(buffer.toString().trim());
				isServer = false;
			}
			if (isLocale) {
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < length; i++) {
					buffer.append(ch[start + i]);
				}
				current.setLocale(buffer.toString().trim());
				isLocale = false;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (current != null && qName.equals("tr")) {
				if (current.getServer() != null) servers.add(current);
				current = null;
			}
			else if (foundTable && qName.equals("table")) {
				finished = true;
			}
		}
	}
}
