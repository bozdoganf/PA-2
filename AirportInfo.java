package com.gradescope.airportinfo;
//package week4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class AirportInfo {
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
				destinations.put(sourceAirport, destinations.get(sourceAirport) + " " + destinationAirport);
			}
		}
		
	reader.close();
	return destinations;
	}
		
	public static HashMap<String, Integer> getAirportCount(String fileName) throws FileNotFoundException {
		HashMap<String, Integer> airportCount = new HashMap<>();
		
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
			
			if (airportCount.get(sourceAirport) != null) {
				airportCount.put(sourceAirport, airportCount.get(sourceAirport) + 1);
			}
			if (airportCount.get(destinationAirport) != null) {
				airportCount.put(destinationAirport, airportCount.get(destinationAirport) + 1);
			}
			if (airportCount.get(sourceAirport) == null) {
				airportCount.put(sourceAirport, 1);
			}
			if (airportCount.get(destinationAirport) == null) {
				airportCount.put(destinationAirport, 1);
			}
		}
		
	reader.close();
	return airportCount;
	}
	
	public static String getMax(HashMap<String, Integer> airportCount) {
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
	    return "MAX FLIGHTS " + maxCount + " : " + res; // should they be printed at different lines?
	}
	public static String getDepartures(HashMap<String, String> destinations) {
		ArrayList<String> sortedKeys = new ArrayList<String>(destinations.keySet());
	    Collections.sort(sortedKeys);
	    
	    String res = "";
	    for (String key : sortedKeys) {
	    	res += key + " flies to " + destinations.get(key) + "\n"; // should you have a line break?
			
		}
	    return res;
	}
	public static String getLimits(int cutOff, HashMap<String, Integer> airportCount) {
		ArrayList<String> sortedKeys = new ArrayList<String>(airportCount.keySet()); // instead of writing this for each class,  
	    Collections.sort(sortedKeys); // find a way to write this once and/or make this more efficient
	    
	    String res = "";
	    for (String key : sortedKeys) {
	    	if (airportCount.get(key) > cutOff) {
	    		res += key + " - " + airportCount.get(key) + "\n"; // should I add a line break here?
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

/* ASK ADRIANA IF YOU SHOULD ADD MULTILINE COMMENTS AFTER EACH CLASS: EX. param: ___ returns: ___
	code clean up to do:
	
	Decomposition

Should carefully select data structures that implement the required functionality. For example, if you avoid using HashMaps, it will probably result in more complicated code and thus points off.
Should just use static methods.
Use a single file. This should be a small program (<300 lines).
Each static method should be less than 30 lines. This INCLUDES comments. It is easier to read a function if it can all fit on one screen.
Make things as simple as possible.
Avoid nested loops.
Avoid nesting conditionals.
Avoid chaining, i.e., too many levels of user-defined methods calling other user-defined methods. Putting most of the functionality in another static method that returns void that main calls is an example of this.
Code Clarity

YOU should be able to read, understand, and explain your own code to someone else a couple days after you wrote it.

There needs to be a balance between no comments and a comment for every line in the program. Either extreme will result in points off.

The file header should include instructions on how someone would use this program. To use the program, one would need to know the input file format.

Use meaningful variable names. Loop iterators can be simple (i for integers, s for strings, n for numbers, etc.).

The clearest code examples will be anonymously shown in class.

The most obfuscated code examples will be anonymously shown in class with suggestions for improvement.

The coding style in terms of spacing, etc. should be done automatically every time you save in Eclipse. As long as you stick with those defaults, the syntax style should be fine.

Write your own code. We will be using a tool that finds overly similar code. I recommend that when talking with others about the assignment, do not write anything down.
*/