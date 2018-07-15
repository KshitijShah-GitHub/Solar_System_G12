package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Planet {
	private String name; //Make private variables for planet name
	private int num; //for order in solar system
	private int planetNum; //for planet number

	public Planet() { //a default for debug purposes
		this.name = "Planet Name"; //sets to defaults
		this.num = 0;
		this.planetNum = 0;
	}

	public void setProperties (String name) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(name + "Info.txt")); //make a new reader to read from txt file about requested planet
		try {
			this.name = reader.readLine(); //reads info into object from file
			this.num = Integer.parseInt(reader.readLine());
			this.planetNum = Integer.parseInt(reader.readLine());
		} catch (Exception e) { //if planet file not found, quiz might not run, throw error
			ErrorDialogBox.displayError("Planet Information","The quiz is unable to run as some files regarding planet information aren't found"); 
		}
		reader.close(); //close reader
	}

	public String getName() { //method to get planet's name
		return name;
	}

	public int getNum() { //method to get space body's position
		return num;
	}
	
	public int getPlanetNum () { //method to get only PLANET order number
		return planetNum;
	}

}
