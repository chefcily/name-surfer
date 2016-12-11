/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	
	private ArrayList<NameSurferEntry> entries;
	
	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		entries = new ArrayList<NameSurferEntry>();
	}
	
	
	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		entries.clear();
	}
	
	
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		entries.add(entry);
	}
	
	
	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawGraph();
		plotEntries();
	}
	
	/**
	 * Draws the graph (grid lines and decade labels) for the name
	 * data to get plotted onto.
	 */
	private void drawGraph() {
		//draw vertical (decade) lines
		double deltaX = getWidth() / NDECADES;
		for (int i = 0; i < NDECADES; i++) {
			GLine decadeLine = new GLine(i * deltaX, getHeight(),
										 i * deltaX, 0);
			add(decadeLine);
		}
		
		//draw horizontal (margin) lines
		GLine topMargin = new GLine(0, GRAPH_MARGIN_SIZE,
									getWidth(), GRAPH_MARGIN_SIZE);
		add(topMargin);
		GLine botMargin = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE,
									getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(botMargin);
		
		//draw decade labels
		int year = START_DECADE;
		for (int j = 0; j < NDECADES; j++) {
			GLabel label = new GLabel(Integer.toString(year));
			add(label, j * deltaX, getHeight());
			year += 10;
		}
	}
	
	/**
	 * Draws the line graphs for all of the entries stored in the
	 * ArrayList of NameSurferEntries, in order of how they were
	 * initially entered.
	 */
	private void plotEntries() {
		for (int i = 0; i < entries.size(); i++) {
			drawLine(i);
		}
	}
	
	/**
	 * Finds the NameSurferEntry to be drawn and draws its line graph,
	 * one segment at a time, and adds a label for each plot point.
	 * 
	 * @param entryNum The entry's position in the ArrayList of entries.
	 */
	private void drawLine(int entryNum) {
		NameSurferEntry entry = entries.get(entryNum);
		double deltaX = getWidth() / NDECADES;
		double yMin = getHeight() - GRAPH_MARGIN_SIZE;
		double yMax = GRAPH_MARGIN_SIZE;
		double deltaY = (yMin - yMax) / 1000.0;
		
		for (int decade = 0; decade < NDECADES - 1; decade++) {
			//get rank values and coordinates
			int fromRank = entry.getRank(decade);
			int toRank = entry.getRank(decade + 1);
			GPoint from = new GPoint(decade * deltaX, yMax + fromRank * deltaY);
			GPoint to = new GPoint((decade + 1) * deltaX, yMax + toRank * deltaY);
			if (fromRank == 0) from.setLocation(decade * deltaX, yMin);
			if (toRank == 0) to.setLocation((decade + 1) * deltaX, yMin);
			
			drawLineSeg(entryNum, from, to);
			drawLabel(entryNum, from, fromRank);
			
			//labels the last plot point (fence post case)
			if (decade == NDECADES - 2) {
				drawLabel(entryNum, to, toRank);
			}
		}
	}
	
	/**
	 * Identifies the entry's position in the rotation of colors
	 * (cyan, blue, magenta, green) and returns the matching color.
	 * 
	 * @param entryNum The entry's position in the ArrayList of entries
	 * @return The color that the line and labels will be drawn in
	 */
	private Color getColor(int entryNum) {
		Color lineColor;
		switch (entryNum % 4) {
			case 0: lineColor = Color.CYAN; break;
			case 1: lineColor = Color.BLUE; break;
			case 2: lineColor = Color.MAGENTA; break;
			case 3: lineColor = Color.GREEN; break;
			default: lineColor = Color.RED; break;
		}
		return lineColor;
	}
	
	/**
	 * Given two GPoints, draws a line segment connecting the two,
	 * in a color that matches the entry's position in the rotation
	 * of colors.
	 */
	private void drawLineSeg(int entryNum, GPoint from, GPoint to) {
		GLine line = new GLine(from.getX(), from.getY(),
							   to.getX(), to.getY());
		line.setColor(getColor(entryNum));
		add(line);
	}
	
	/**
	 * Adds a label next to a plot point on the line graph.
	 * Shows the name in question and its rank at a given decade,
	 * in the same color as the line is drawn in.
	 * 
	 * Displays an asterisk if name is unranked for that decade.
	 */
	private void drawLabel(int entryNum, GPoint point, int rank) {
		String typed = (entries.get(entryNum)).toString();
		String name = (entries.get(entryNum)).getName(typed) + " ";
		
		if (point.getY() == getHeight() - GRAPH_MARGIN_SIZE) {
			name += "*";
		} else name += Integer.toString(rank);
		
		GLabel label = new GLabel(name);
		label.setColor(getColor(entryNum));
		add(label, point.getX(), point.getY());
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
