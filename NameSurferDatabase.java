/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.*;
import java.util.*;

public class NameSurferDataBase implements NameSurferConstants {
	
	private HashMap<String, String> file;
	
	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		file = new HashMap<String, String>();
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			int lineNum = 0;
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				
				//separate the name from the string
				String name = "";
				int i = 0;
				while (Character.isLetter(line.charAt(i))) {
					name += line.charAt(i);
					i++;
				}
				file.put(name, line);
				lineNum++;
			}
		} catch (IOException e) {
			
		}
	}
	
	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists.  If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		//standardizes the capitalization of the name
		String search = "" + Character.toUpperCase(name.charAt(0));
		for (int i = 1; i < name.length(); i++) {
			search += Character.toLowerCase(name.charAt(i));
		}
		
		if (file.containsKey(search)) {
			NameSurferEntry entry = new NameSurferEntry(file.get(search));
			return entry;
		} else {
			return null;
		}
	}
}
