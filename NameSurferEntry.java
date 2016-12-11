/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	private String name;
	private int[] ranks;
	
	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file.  Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		String[] split = line.split(" ", NDECADES + 1);
		name = split[0];
		
		//converts numbers to integers and adds to rank array
		ranks = new int[NDECADES];
		for (int i = 1; i < split.length; i++) {
			String num = split[i];
			int rank = Integer.parseInt(num);
			ranks[i-1] = rank;
		}
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName(String entry) {
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular
	 * decade.  The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE.  If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		return ranks[decade];
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		String result = name + " [";
		for (int i = 0; i < ranks.length - 1; i++) {
			result += ranks[i] + " ";
		}
		result += ranks[ranks.length - 1] + "]";
		return result;
	}
}
