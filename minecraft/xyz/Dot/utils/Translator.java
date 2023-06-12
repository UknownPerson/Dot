package xyz.Dot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Translator {

	public static Translator instance = new Translator();

	public static Translator getInstance() {
		return instance;
	}

	public static final String ARG_MARKER = "{}";

	public static final String VAR_MARKER_START = "${";

	public static final String VAR_MARKER_END = "}";

	private Map<String, String> messages;

	private Map<String, Object> variables;

	public Translator() {
		messages = new HashMap<>();
		variables = new HashMap<>();
	}

	public void setMessage(String key, String value) {
		messages.put(key, value);
	}

	public Map<String, String> getMessages() {
		return messages;
	}

	public boolean addMessages(InputStream inputStream) {
		return add(inputStream, messages);
	}

	/**
	 * Loads messages from a string formatted as properties file. Current
	 * messages are not cleared.
	 * 
	 * @return true if the messages was processed correctly
	 */
	public boolean addMessages(String properties) {
		return add(properties, messages);
	}

	public void clearMessages() {
		messages.clear();
	}

	public void setVariable(String key, Object value) {
		variables.put(key, value);
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public boolean addVariables(InputStream inputStream) {
		return add(inputStream, variables);
	}

	/**
	 * @return the variable's value
	 */
	public Object getVariable(String variableName) {
		return variables.get(variableName);
	}

	/**
	 * Loads string variables from a string formatted as a properties file.
	 * Current variables are not cleared.
	 * 
	 * @return true if the messages was processed correctly
	 */
	public boolean addVariables(String text) {
		return add(text, variables);
	}

	public void clearVariables() {
		variables.clear();
	}

	private boolean add(InputStream inputStream, Map map) {
		StringBuilder output = new StringBuilder();
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(inputStream);
			char[] buffer = new char[256];
			while (true) {
				int length = reader.read(buffer);
				if (length == -1)
					break;
				output.append(buffer, 0, length);
			}
			return add(output.toString(), map);
		} catch (IOException ex) {
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private boolean add(String properties, Map map) {
		String[] lines = properties.split("\n");
		for (String line : lines) {
			if (line.matches("^\\s*[#].*")) {
				// ignore line-comments
				continue;
			}
			int equalsIndex = line.indexOf('=');
			if (equalsIndex == -1) {
				continue;
			}
			String key = line.substring(0, equalsIndex).trim();
			String value = line.substring(equalsIndex + 1).trim();
			map.put(key, value);
		}
		return true;
	}

	private ArrayList<String> printed = new ArrayList<>();

	/**
	 * Simple internationalized (i18n) message lookup.
	 *
	 * @param key
	 *            string key
	 * @param args
	 *            which will substitute each of the '{}' patterns found in the
	 *            text, in order.
	 * @return the i18n string
	 */
	public String m(String key, Object... args) {
		String result = messages.get(key);
		if (result == null) {
	/*		if(Client.instance.inDevelopment) {
				if(!printed.contains(key)) {
					System.out.println(key + " = " + key);
					printed.add(key);
				}
			}*/
			result = key;
		}

		StringBuilder replaced = new StringBuilder();
		int start = 0;
		int end = result.indexOf(ARG_MARKER);
		if (end != -1 && args.length > 0) {
			// Substitute arguments
			int currentArg = 0;
			do {
				if (currentArg == args.length) {
					currentArg = 0;
				}
				replaced.append(result.substring(start, end)).append(
						args[currentArg]);
				currentArg++;
				start = end + ARG_MARKER.length();
				end = result.indexOf(ARG_MARKER, start);
			} while (end != -1);
			replaced.append(result.substring(start));
		} else {
			replaced.append(result);
		}

		// Substitute variables
		start = replaced.indexOf(VAR_MARKER_START);
		end = replaced.indexOf(VAR_MARKER_END);
		if (start == -1 || end < start) {
			return replaced.toString();
		}
		do {
			String variable = replaced.substring(
					start + VAR_MARKER_START.length(), end);
			Object value = variables.get(variable);
			replaced.replace(start, end + 1,
					value == null ? "" : value.toString());
			start = replaced.indexOf(VAR_MARKER_START);
			end = replaced.indexOf(VAR_MARKER_END);
		} while (start != -1 && end > start);

		return replaced.toString();
	}

	/**
	 * Convenience method to return an internationalized message that varies in
	 * plurality according to the first parameter. For example
	 * "1 message found, delete it?" vs "2 messages found, delete them?"
	 * 
	 * @param cardinality
	 *            either 1 (singular) or any other number (plural)
	 * @param keyOne
	 *            for singular messages
	 * @param keyMany
	 *            for plural messages
	 * @param args
	 *            same as those used in the cardinality-independent version
	 * @return
	 */
	public String m(int cardinality, String keyOne, String keyMany,
			Object... args) {
		return m(cardinality == 1 ? keyOne : keyMany, args);
	}
}