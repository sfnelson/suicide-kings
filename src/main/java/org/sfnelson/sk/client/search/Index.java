package org.sfnelson.sk.client.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Index<T> {

	public interface Serializer<T> {
		String[] visit(T input);
	}

	private final Serializer<T> serializer;
	private final Comparator<T> comparator;
	private final List<T> all = new ArrayList<T>();
	private final List<T> emptyList = new ArrayList<T>();
	private final Set<String> emptySet = new HashSet<String>(0);
	private final Map<String, Set<String>> tupleIndex = new HashMap<String, Set<String>>();
	private final Map<String, List<T>> tokenIndex = new HashMap<String, List<T>>();

	public Index(Serializer<T> serializer, Comparator<T> comparator) {
		this.serializer = serializer;
		this.comparator = comparator;
	}

	public Index(List<T> data, Serializer<T> serializer, Comparator<T> comparator) {
		this(serializer, comparator);
		all.addAll(data);
		for (T target: data) {
			for (String token: serializer.visit(target)) {
				addString(token, target);
			}
		}
	}

	public void add(T target) {
		all.add(target);
		Collections.sort(all, comparator);
		for (String token: serializer.visit(target)) {
			addString(token, target);
		}
	}

	private void addString(String token, T target) {
		token = token.toLowerCase();
		if (!tokenIndex.containsKey(token)) {
			for (int i = 0; i < token.length(); i++) {
				put(token.substring(i, i + 1), token);
			}
			for (int i = 0; i < token.length() - 1; i++) {
				put(token.substring(i, i + 2), token);
			}
			for (int i = 0; i < token.length() - 2; i++) {
				put(token.substring(i, i + 3), token);
			}
		}

		put(token, target);
	}

	public List<T> filter(String filter) {
		if (filter.length() == 0) return all;

		Set<String> tokens = tokenize(filter);

		List<T> current = null;
		for (String token: tokens) {
			if (current == null) current = getMatches(token);
			else current = intersection(current, getMatches(token));
		}

		if (current == null) return emptyList;
		else return current;
	}

	private Set<String> tokenize(String filter) {
		Set<String> tokens = new HashSet<String>();

		filter = filter.toLowerCase();
		while (filter.indexOf(' ') != -1) {
			tokens.add(filter.substring(0, filter.indexOf(' ')));
			filter = filter.substring(filter.indexOf(' ') + 1);
		}
		tokens.add(filter);
		tokens.remove("");

		return tokens;
	}

	private List<T> intersection(List<T> left, List<T> right) {
		List<T> intersection = new ArrayList<T>();

		int a = 0;
		int b = 0;
		while (a < left.size() && b < right.size()) {
			T l = left.get(a);
			T r = right.get(b);
			int comp = comparator.compare(l, r);
			if (comp < 0) {
				a++;
			}
			else if (comp > 0) {
				b++;
			}
			else {
				intersection.add(l);
				a++;
				b++;
			}
		}

		return intersection;
	}

	private List<T> union(List<T> left, List<T> right) {
		List<T> union = new ArrayList<T>();

		int a = 0;
		int b = 0;
		while (a < left.size() && b < right.size()) {
			T l = left.get(a);
			T r = right.get(b);
			int comp = comparator.compare(l, r);
			if (comp < 0) {
				union.add(l);
				a++;
			}
			else if (comp > 0) {
				union.add(r);
				b++;
			}
			else {
				union.add(l);
				a++;
				b++;
			}
		}
		while (a < left.size()) {
			union.add(left.get(a++));
		}
		while (b < right.size()) {
			union.add(right.get(b++));
		}

		return union;
	}

	private Set<String> intersection(Set<String> left, Set<String> right) {
		Set<String> union = new HashSet<String>();

		for (String a: left) {
			if (right.contains(a)) {
				union.add(a);
			}
		}

		return union;
	}

	private List<T> getMatches(String token) {
		Set<String> tokens = getTokenMatches(token);

		List<T> realms = null;
		for (String t: tokens) {
			if (realms == null) {
				realms = tokenIndex.get(t);
			}
			else {
				realms = union(realms, tokenIndex.get(t));
			}
		}

		return realms;
	}

	private Set<String> getTokenMatches(String token) {
		if (token.length() == 1) return getTokens(token);
		if (token.length() == 2) return getTokens(token);
		if (token.length() == 3) return getTokens(token);

		Set<String> result = getTokens(token.substring(0, 3));
		for (int i = 1; i < token.length() - 3; i++) {
			result = intersection(result, getTokens(token.substring(i, i + 3)));
		}

		Set<String> filtered = new HashSet<String>();
		for (String r: result) {
			if (r.contains(token)) filtered.add(r);
		}

		return filtered;
	}

	private Set<String> getTokens(String tuple) {
		if (!tupleIndex.containsKey(tuple)) return emptySet;
		else return tupleIndex.get(tuple);
	}

	private void put(String tuple, String token) {
		Set<String> tokens;
		if (tupleIndex.containsKey(tuple)) {
			tokens = tupleIndex.get(tuple);
		}
		else {
			tokens = new HashSet<String>();
			tupleIndex.put(tuple, tokens);
		}
		tokens.add(token);
	}

	private void put(String token, T value) {
		List<T> values;
		if (tokenIndex.containsKey(token)) {
			values = tokenIndex.get(token);
		}
		else {
			values = new ArrayList<T>();
			tokenIndex.put(token, values);
		}
		values.add(value);
		Collections.sort(values, comparator);
	}
}