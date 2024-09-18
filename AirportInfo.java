package com.gradescope.airportinfo;

/**
 * File: AirportInfo.java
 * Author: Fatih Bozdogan
 * Description: This program processes flight data to perform various tasks 
 * such as the maximum number of flights, the departures from one location to 
 * another, and cutoff limit of total number of flights for each airport.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class AirportInfo {
    /**
     * builds the hashmap of destinations an airport flies to
     * @param file name
     * @return destinations
     */
	public static HashMap<String, String> getDestinations(String fileName) throws FileNotFoundException { 
		HashMap<String, String> destinations = new HashMap<>();
		
		File myFile = new File(fileName);
		
		Scanner reader = new Scanner(myFile);
		
		// skip the first line 
		reader.nextLine();
		
		// go over each line and build the dictionary
		while (reader.hasNext()) {
			String line = reader.nextLine();
			String[] data = line.split(",");
			String sourceAirport = data[2];
			String destinationAirport = data[4];
			
			if (destinations.get(sourceAirport) == null) {
				destinations.put(sourceAirport, destinationAirport);
			}
			else {
				destinations.put(sourceAirport, 
						destinations.get(sourceAirport) + " " + destinationAirport);
			}
		}
		
	reader.close();
	return destinations;
	}
    /**
     * builds a hashmap of airports and the total number of flights it has
     * @param file name
     * @return airportCount
     */
	public static HashMap<String, Integer> getAirportCount(String fileName) throws FileNotFoundException {
		
		HashMap<String, Integer> airportCount = new HashMap<>();
		
		File myFile = new File(fileName);
		Scanner reader = new Scanner(myFile);
		
		// skip the first line 
		reader.nextLine();
		
		// go over each line and build the dictionary
		while (reader.hasNext()) {
			String line = reader.nextLine();
			String[] lineList = line.split(",");
			String sourceAirport = lineList[2];
			String destinationAirport = lineList[4];
			
			if (airportCount.get(sourceAirport) == null) {
				airportCount.put(sourceAirport, 0);
			}
			airportCount.put(sourceAirport, airportCount.get(sourceAirport) + 1);
			
			if (airportCount.get(destinationAirport) == null) {
				airportCount.put(destinationAirport, 0);
			}
			airportCount.put(destinationAirport, airportCount.get(destinationAirport) + 1);
		}	
		
	reader.close();
	return airportCount;
	}
	
    /**
     * gets the airport with the maximum total number of flights
     * @param airportCount, the hashmap of airports and the total number of flights it has
     * @return a properly formatted string of maximum total number of flights
     */
	public static String getMax(HashMap<String, Integer> airportCount) {
		
		// sort the keys to print them alphabetically
	    ArrayList<String> sortedKeys = new ArrayList<String>(airportCount.keySet());
	    Collections.sort(sortedKeys);
	    
	    
	    int maxCount = 0;
	    String res = null;
	    
	    for (String key : sortedKeys) {
			if (airportCount.get(key) > maxCount) {
				maxCount = airportCount.get(key);
				res = key;
			}
			else if (airportCount.get(key) == maxCount) {
				res += " " + key;
			}
		}		
	    return "MAX FLIGHTS " + maxCount + " : " + res;
	}
    /**
     * gets the departures from one destination to another
     * @param destinations, a hashmap of airports and their destinations
     * @return res,a properly formatted string alphabetically listing all 
     * destinations an airport flies to
     */
	public static String getDepartures(HashMap<String, String> destinations) {
		
		// sort the keys to print them alphabetically 
		ArrayList<String> sortedKeys = new ArrayList<String>(destinations.keySet());
	    Collections.sort(sortedKeys);
	    
	    // go over the dictionary and put the departures in the res
	    String res = "";
	    for (String key : sortedKeys) {
	    	res += key + " flies to " + destinations.get(key) + "\n";
			
		}
	    return res;
	}
	
    /**
     * Cuts off airports below the cutOff the user enters
     * @param cutOff, the total number of flights the airport must have to not be cutOff
     * airportCount, the hashmap of airports and the total number of flights they have
     * @return destinations
     */
	public static String getLimits(int cutOff, HashMap<String, Integer> airportCount) {
		ArrayList<String> sortedKeys = new ArrayList<String>(airportCount.keySet());
	    Collections.sort(sortedKeys);
	    
	    String res = "";
	    for (String key : sortedKeys) {
	    	// adds airports and their total number of flights that meet the cutoff number to the result
	    	if (airportCount.get(key) > cutOff) {
	    		res += key + " - " + airportCount.get(key) + "\n";
	    	}
	    }
	    
	    return res;
	}
	public static void main(String[] args) throws FileNotFoundException {
        
		HashMap<String, String> destinations = getDestinations(args[0]);
        HashMap<String, Integer> airportCount = getAirportCount(args[0]);
        
        
        if (args[1].equals("MAX")) {
            System.out.println(getMax(airportCount));
        }
        
       if (args[1].equals("DEPARTURES")) {
            System.out.println(getDepartures(destinations));
       }
        
        if (args[1].equals("LIMIT")) {
            System.out.println(getLimits(Integer.valueOf(args[2]), airportCount));
        }
        
    
    }
}

