package GIS;

import java.time.Instant;
import java.util.Arrays;
import Coords.myCoords;
import Geom.Point3D;

public class Data implements Meta_data{
	
	private String[] data;
	private long UTC;
	private String color;
		// [0] - MAC; 
		// [1] - SSID; 
		// [2] - AuthMode; 
		// [3] - FirstSeen;				
		// [4] - Channel;
		// [5] - RSSI;
		// [6] - Latitude;
		// [7] - Longitude
		// [8] - Altitude; 
		// [9] - AccuracyMeters; 
		// [10] - Type
	
	/*
	 * Construct a data object with UTC time only.
	 */
	public Data() { 
		this.UTC = Instant.now().toEpochMilli();
	}
	
	/*
	 *Construct a data object with UTC time and information.
	 */
	public Data(String[] str){
		this.data = new String[11];
		this.UTC = Instant.now().toEpochMilli();
		
		for (int i = 0; i < str.length; i++) {
			this.data[i] = str[i];
		}
	}
	
	/*
	 * Get values of type String from data object.
	 */
	public String getValue(String string){
		
		switch(string){
			case "MAC":{ return data[0];}
			case "SSID": return data[1];
			case "AuthMode": return data[2];
			case "FirstSeen": return data[3];
			case "Channel": return data[4];
			case "RSSI": return data[5];
			case "Latitude": return data[6];
			case "Longitude": return data[7];
			case "Altitude": return data[8];
			case "AccuracyMeters": return data[9];
			case "Type": return data[10];
			case "Color": return color;
		}
		return null;
	}
	
	
	
	/*
	 * Set the coordinates of an Element.
	 */
	public void setCoordinates(Point3D vec){ //todo: convert meter to gps.
		Point3D point = new myCoords().meterToGps(vec);
		data[6] = Double.toString(point.y());
		data[7] = Double.toString(point.x());
		data[8] = Double.toString(point.z());
	}
	
	/*
	 * Get the UTC time of this object.
	 */
	@Override
	public long getUTC() {
		return UTC;
	}

	/*
	 * Get the orientation of this object.
	 */
	@Override
	public Point3D get_Orientation() {
		return null;
	}
	
	/*
	 * Print this object.
	 */
	public String toString() {
		return String.join(",", data);
	}


}
