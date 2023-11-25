/*
 * Copyright(C) 2023 DeviceBlack
 *
 * Licensed under the Apache License, Version 2.0(the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devicewhite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * SimpleINI class for handling INI file operations.
 */
public class SimpleINI {
	private HashMap<String, HashMap<String, String>> data = new HashMap<>();
	private File ini;

	/**
	 * Constructor to initialize SimpleINI with a specified file.
	 *
	 * @param file The INI file.
	 */
	public SimpleINI(File file) {
		ini = file;
	}

	/**
	 * Load method reads and processes the content of the INI file.
	 *
	 * @return True if successful, false otherwise.
	 */
	public boolean load() {
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader(ini));
			HashMap<String, String> sectionData = new HashMap<>();
			String currentSection = "none", line;

			while((line = buffer.readLine()) != null) {
				// Check if the line contains a section header
				if(!line.startsWith("#")) {
					if(line.matches("\\[\\w+\\]")) {
						// Save the data for the current section and move to the new section
						data.put(currentSection, sectionData);
						currentSection = line.substring(1, line.length() - 1);
						sectionData = new HashMap<>();
					} else {
						// Process key-value pairs within a section
						String[] values = line.split(" = ", 2);
						if(values.length == 2) {
							sectionData.put(values[0], values[1]);
						}
					}
				}
			}

			// Save the last section data
			if(!sectionData.isEmpty()) {
				data.put(currentSection, sectionData);
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(buffer != null) {
				try {
					buffer.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

		data.clear();
		return false;
	}


	/**
	 * Save method writes the data to the INI file.
	 *
	 * @param clear If true, clears the data after saving.
	 * @return True if successful, false otherwise.
	 */
	public boolean save(int saveType) {
		BufferedWriter buffer = null;
		try {
			buffer = new BufferedWriter(new FileWriter(ini, false));
			String text = "# Created by DeviceWhite\n";

			// Save data for the 'none' section if it exists
			if(data.containsKey("none")) {
				text = text.concat(saveSection(buffer, "none"));
			}

			// Save data for other sections
			for(String section : new HashSet<>(data.keySet())) {
				if(section != "none") {
					text = text.concat(String.format("\n[%s]", section));
					text = text.concat(saveSection(buffer, section));
				}
			}

			buffer.write(text);
			buffer.flush();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(buffer != null) {
				try {
					buffer.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			switch(saveType % 3) {
				case SaveType.UNLOAD_CACHE:
					data.clear();
					break;

				case SaveType.RELOAD_CACHE:
					data.clear();
					load();
					break;
			}
		}
		return false;
	}

	/**
	 * Save method writes the data to the INI file and clears the data.
	 * 
	 * @return True if successful, false otherwise.
	 */
	public boolean save() {
		return save(SaveType.KEEP_CACHE);
	}


	/**
	 * Helper method to save a section's data.
	 *
	 * @param buffer	  The BufferedWriter to write to.
	 * @param section	 The section to save.
	 * @throws Exception  If an I/O error occurs.
	 */
	private String saveSection(BufferedWriter buffer, String section) throws Exception {
		HashMap<String, String> sectionData = data.get(section);
		Set<Map.Entry<String, String>> entrySet = sectionData.entrySet();
		StringBuilder text = new StringBuilder("\n");

		for(Map.Entry<String, String> entry : entrySet) {
			text.append(String.format("%s = %s\n", entry.getKey(), entry.getValue()));
		}

		return text.toString();
	}


	/**
	 * Gets the value associated with the specified key in the given section.
	 *
	 * @param section  The section in which to look for the key.
	 * @param key	  The key whose associated value is to be retrieved.
	 * @return		 The value to which the specified key is mapped, or null if the key is not present in the section.
	 */
	public String get(String section, String key) {
		if(section == "none") return null;
		else if(section == null) section = "none";

		HashMap<String, String> sectionData = data.getOrDefault(section, new HashMap<>());
		return sectionData.getOrDefault(key, null);
	}

	/**
	 * Gets the value associated with the specified key.
	 *
	 * @param key The key whose associated value is to be retrieved.
	 * @return	The value to which the specified key is mapped, or null if the key is not present.
	 */
	public String get(String key) {
		return get(null, key);
	}

	/**
	 * Gets the float value associated with the specified key in the given section.
	 *
	 * @param section  The section in which to look for the key.
	 * @param key	  The key whose associated float value is to be retrieved.
	 * @return		 The float value to which the specified key is mapped.
	 */
	public float getFloat(String section, String key) {
		return Float.parseFloat(get(section, key));
	}

	/**
	 * Gets the float value associated with the specified key.
	 *
	 * @param key  The key whose associated float value is to be retrieved.
	 * @return	 The float value to which the specified key is mapped.
	 */
	public float getFloat(String key) {
		return getFloat(null, key);
	}

	/**
	 * Gets the integer value associated with the specified key in the given section.
	 *
	 * @param section  The section in which to look for the key.
	 * @param key	  The key whose associated integer value is to be retrieved.
	 * @return		 The integer value to which the specified key is mapped.
	 */
	public int getInt(String section, String key) {
		return Integer.parseInt(get(section, key));
	}

	/**
	 * Gets the integer value associated with the specified key.
	 *
	 * @param key  The key whose associated integer value is to be retrieved.
	 * @return	 The integer value to which the specified key is mapped.
	 */
	public int getInt(String key) {
		return getInt(null, key);
	}

	/**
	 * Gets the boolean value associated with the specified key in the given section.
	 *
	 * @param section  The section in which to look for the key.
	 * @param key	  The key whose associated boolean value is to be retrieved.
	 * @return		 The boolean value to which the specified key is mapped.
	 */
	public boolean getBool(String section, String key) {
		return Boolean.parseBoolean(get(section, key));
	}

	/**
	 * Gets the boolean value associated with the specified key.
	 *
	 * @param key  The key whose associated boolean value is to be retrieved.
	 * @return	 The boolean value to which the specified key is mapped.
	 */
	public boolean getBool(String key) {
		return getBool(null, key);
	}

	/**
	 * Checks if a key exists in the specified section.
	 *
	 * @param section The section in which to check for the key.
	 * @param key	 The key to check for existence.
	 * @return		True if the key exists in the section, false otherwise.
	 */
	public boolean exists(String section, String key) {
		return(get(section, key) != null);
	}

	/**
	 * Checks if a key exists.
	 *
	 * @param key  The key to check for existence.
	 * @return	 True if the key exists, false otherwise.
	 */
	public boolean exists(String key) {
		return exists(null, key);
	}


	/**
	 * Sets the value associated with the specified key in the given section.
	 *
	 * @param section The section in which to set the key-value pair.
	 * @param key	 The key to set.
	 * @param value   The value to associate with the key.
	 */
	public void set(String section, String key, String value) {
		if(section == "none") return;
		else if(section == null) section = "none";

		HashMap<String, String> sectionData = data.getOrDefault(section, new HashMap<>());

		if(sectionData.containsKey(key)) {
			sectionData.replace(key, value);
		} else {
			sectionData.put(key, value);
		}

		if(!data.containsKey(section)) {
			data.put(section, sectionData);
		}
	}

	/**
	 * Sets the value associated with the specified key.
	 *
	 * @param key   The key to set.
	 * @param value The value to associate with the key.
	 */
	public void set(String key, String value) {
		set(null, key, value);
	}

	/**
	 * Sets the float value associated with the specified key in the given section.
	 *
	 * @param section The section in which to set the key-value pair.
	 * @param key	 The key to set.
	 * @param value   The float value to associate with the key.
	 */
	public void setFloat(String section, String key, float value) {
		set(section, key, Float.toString(value));
	}

	/**
	 * Sets the float value associated with the specified key.
	 *
	 * @param key   The key to set.
	 * @param value The float value to associate with the key.
	 */
	public void setFloat(String key, float value) {
		setFloat(null, key, value);
	}

	/**
	 * Sets the integer value associated with the specified key in the given section.
	 *
	 * @param section The section in which to set the key-value pair.
	 * @param key	 The key to set.
	 * @param value   The integer value to associate with the key.
	 */
	public void setInt(String section, String key, int value) {
		set(section, key, Integer.toString(value));
	}

	/**
	 * Sets the integer value associated with the specified key.
	 *
	 * @param key   The key to set.
	 * @param value The integer value to associate with the key.
	 */
	public void setInt(String key, int value) {
		setInt(null, key, value);
	}

	/**
	 * Sets the boolean value associated with the specified key in the given section.
	 *
	 * @param section The section in which to set the key-value pair.
	 * @param key	 The key to set.
	 * @param value   The boolean value to associate with the key.
	 */
	public void setBool(String section, String key, boolean value) {
		set(section, key, Boolean.toString(value));
	}

	/**
	 * Sets the boolean value associated with the specified key.
	 *
	 * @param key   The key to set.
	 * @param value The boolean value to associate with the key.
	 */
	public void setBool(String key, boolean value) {
		setBool(null, key, value);
	}
}

/**
 * Enumeration class for save types.
 */
class SaveType {
	public static final int KEEP_CACHE = 0;
	public static final int RELOAD_CACHE = 1;
	public static final int UNLOAD_CACHE = 2;
}
