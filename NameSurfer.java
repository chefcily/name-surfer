/*
 * File: NameSurfer.java
 * ---------------------
 * This program implements the viewer for the baby-name
 * database described in the CS106A assignment 6 handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	private JTextField nameEntry;
	private JButton graphName, clearGraph;
	private final static int NAME_FIELD_SIZE = 30;
	private NameSurferGraph graph;
	private NameSurferDataBase dataBase;
	
	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {
		//reads in data base
		dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		
		//adds and initializes interactors
	    add(new JLabel("Name: "), NORTH);
	    nameEntry = new JTextField(NAME_FIELD_SIZE);
	    add(nameEntry, NORTH);
	    nameEntry.addActionListener(this);
	    graphName = new JButton("Graph");
	    add(graphName, NORTH);
	    clearGraph = new JButton("Clear");
	    add(clearGraph, NORTH);
	    addActionListeners();
	    
	    //adds graph
	    graph = new NameSurferGraph();
	    add(graph);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nameEntry ||
			e.getSource() == graphName) {
			NameSurferEntry name = dataBase.findEntry(nameEntry.getText());
			if (name != null)
				graph.addEntry(name);
			graph.update();
		}
		if (e.getSource() == clearGraph) {
			graph.clear();
			graph.update();
		}
	}
}
